export default class Estados {
  static #table
  static #modal
  static #currentOption
  static #form
  static #form2
  static #customers
  static #estados
  static #listaEstados

  static async init() {
    try {
      Estados.#form = await Helpers.fetchText('./resources/html/estados.html')
      Estados.#form2 = await Helpers.fetchText('./resources/html/nuevoEstado.html')
      const responseEstados = await Helpers.fetchJSON(`${urlAPI}/envio/estados`)

      if (responseEstados.message != 'ok') {
        throw new Error(responseEstados.message)
      }

      // crear las opciones para un select de clientes
      Estados.#estados = Helpers.toOptionList({
        items: responseEstados.data,
        value: 'key',
        text: 'value',
        firstOption: 'Seleccione un estado',
      })

      // Agregar al <main> de index.html la capa que contendrá la tabla
      document.querySelector('main').innerHTML = `
        <div class="p-5 w-full">
            <div id="table-container" class="m-2"></div>
        </div>
        `

      document.querySelector('main').insertAdjacentHTML('afterbegin', Estados.#form)

      Estados.horaFechaActual()

      // Inicializa la tabla vacía
      Estados.#table = new Tabulator('#table-container', {
        height: (tableHeight2 = 200), // Tiene su propio tamaño para no cambiar los demas tabulator
        data: [],
        layout: 'fitColumns',
        columns: [
          { formatter: deleteRowButton, width: 40, hozAlign: 'center', cellClick: Estados.#deleteRowClick },
          {
            title: 'Fecha',
            field: 'fecha',
            width: 400,
            formatter: function (cell) {
              const fechaHora = cell.getValue()
              // Formatear fecha y hora
              let fechaHoraFormateada = DateTime.fromISO(fechaHora).toFormat('yyyy-MM-dd hh:mm')
              // Retornar la fecha con formato
              return `${fechaHoraFormateada}`
            },
          },

          {
            title: 'Tipo de estado',
            field: 'tipoEstado',
            width: 400,
            formatter: function (cell) {
              const tipoEstado = cell.getValue()
              let estadosTipo = responseEstados.data
              // Buscar en tiposEstados el estado humano
              let tiposEstados = estadosTipo.find(e => e.key === tipoEstado)
              // Retornar el estado en formato humano junto con la fecha y hora
              return `${tiposEstados.value}`
            },
          },
        ],
        responsiveLayout: false, // Scroll horizontal si es necesario
        columnDefaults: {
          tooltip: true, //show tool tips on cells
        },

        // mostrar al final de la tabla un botón para agregar registros
        footerElement: `<div class='container-fluid d-flex justify-content-end p-0'>${addRowButton}</div>`,
      })
      Estados.#table.on('tableBuilt', () => document.querySelector('#add-row').addEventListener('click', Estados.#addRow))

      // listener para realizar busqueda
      document.querySelector('#buscar').addEventListener('click', async e => {
        e.preventDefault()
        await Estados.#encontrarEnvio()
      })
    } catch (e) {
      Toast.show({ title: 'Estados', message: 'Falló la inicialización de la tabla', mode: 'danger', error: e })
    }

    return this
  }

  static async #encontrarEnvio() {
    try {
      const tipo = document.querySelector('#tipoEnvio').value
      const nroGuia = document.querySelector('#nroGuia').value

      const busqueda = await Helpers.fetchJSON(`${urlAPI}/${tipo}/id/${nroGuia}`)
      if (busqueda.message !== 'ok') {
        const resultados = document.querySelector('#resultados')
        resultados.innerHTML = `
          <div class="alert alert-dismissible alert-danger">
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
          <h4 class="alert-heading">Busqueda fallida</h4>
          <p class="mb-0">No se encontro un envio con numero de guia: <a>${nroGuia}</a></p>
          </div>
        `
      }

      const info = busqueda.data
      const datosTabla = info.estados
      Estados.#listaEstados = info.estados
      console.log(Estados.#listaEstados)

      // Funcion de tabulator que reemplaza los datos en la tabla con los de la busqueda
      Estados.#table.replaceData(datosTabla)

      // Muestra informacion del envio
      const resultados = document.querySelector('#resultados')
      resultados.innerHTML = `
      <div class="alert alert-dismissible alert-info">
        <div><strong><h5>Información del envío de tipo ${tipo}</h5></div>
        <div><strong>Remitente:</strong> ${info.remitente.nombre} - ${info.remitente.direccion} - ${info.remitente.ciudad}</div>
        <div><strong>Destinatario:</strong> ${info.destinatario.nombre} - ${info.destinatario.direccion} - ${info.destinatario.ciudad}</div>
        <div><strong>Contenido:</strong> ${info.contenido}</div>
        <div><strong>Valor del envío:</strong> ${info.costo}</div>
        </div>
      `
    } catch (error) {}
  }

  static #editRowClick = async (e, cell) => {
    Estados.#currentOption = 'edit'
    console.log(cell.getRow().getData())

    Estados.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: `<h5>Actualización de </h5>`,
      content: Estados.#form,
      buttons: [
        { caption: editButton, classes: 'btn btn-primary me-2', action: () => Estados.#edit(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Estados.#modal.close() },
      ],
      doSomething: idModal => Estados.#displayDataOnForm(idModal, cell.getRow().getData()),
    })
    Estados.#modal.show()
  }

  static #deleteRowClick = async (e, cell) => {
    Estados.#currentOption = 'delete'
    console.log(cell.getRow().getData())
    Estados.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: `<h5>Eliminación de estados</h5>`,
      content: `<span class = "text-back dark:text-gray-300">
                    Confirme la eliminación del estado:<br>
                    ${cell.getRow().getData().nroGuia} - ${cell.getRow().getData().contenido}<br>
                    Remitente: ${cell.getRow().getData().remitente.nombre}<br>
                    Destinatario: ${cell.getRow().getData().destinatario.nombre}<br>
                  </span>`,
      buttons: [
        { caption: deleteButton, classes: 'btn btn-primary me-2', action: () => Estados.#delete(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Estados.#modal.close() },
      ],
    })
    Estados.#modal.show()
  }

  static async #addRow() {
    Estados.#currentOption = 'add'
    Estados.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7 mi-estilo',
      title: `<h5>Ingreso de estados</h5>`,
      content: Estados.#form2,
      buttons: [
        { caption: addButton, classes: 'btn btn-success me-2', action: () => Estados.#add() },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Estados.#modal.close() },
      ],
      doSomething: Estados.#displayDataOnForm,
    })
    Estados.#modal.show()
  }

  static async #add() {
    try {
      //verificar si los datos cumplen con las restricciones indicadas en el formulario HTML
      if (!Helpers.okForm('#form-estadosNuevos')) {
        return
      }

      // obtener del formulario el objeto con los datos que se envían a la solicitud POST
      const nuevoEstado = Estados.#getFormData()
      Estados.#listaEstados.push(nuevoEstado)
      const body = { estados: Estados.#listaEstados }
      console.log(body)
      const tipo = document.querySelector('#tipoEnvio').value
      const nroGuia = document.querySelector('#nroGuia').value

      // enviar la solicitud de creación con los datos del formulario
      let response = await Helpers.fetchJSON(`${urlAPI}/${tipo}/${nroGuia}`, {
        method: 'PATCH',
        body,
      })

      console.log(response)

      if (response.message === 'ok') {
        Estados.#table.replaceData(response.data.estados) // agregar la caja a la tabla
        Estados.#modal.remove()
        
        Toast.show({ message: 'Agregado exitosamente' })
      } else {
        Toast.show({ message: 'No se pudo agregar el estado', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Falló la operación de creación del estado', mode: 'danger', error: e })
    }
  }

  static async #edit(cell) {
    try {
      //verificar si los datos cumplen con las restricciones indicadas en el formulario HTML
      if (!Helpers.okForm('#form-envios')) {
        return
      }

      // obtener del formulario el objeto con los datos que se envían a la solicitud PATCH
      const body = Estados.#getFormData()

      // configurar la url para enviar la solicitud PACTH
      const url = `${urlAPI}//${cell.getRow().getData().nroGuia}`

      // intentar enviar la solicitud de actualización
      let response = await Helpers.fetchJSON(url, {
        method: 'PATCH',
        body,
      })

      if (response.message === 'ok') {
        Toast.show({ message: ` actualizado exitosamente` })
        cell.getRow().update(response.data)
        Estados.#modal.remove()
      } else {
        Toast.show({ message: `Nose pudo actualizar el `, mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: `Problemas al actualizar el `, mode: 'danger', error: e })
    }
  }

  static async #delete(cell) {
    try {
      const url = `${urlAPI}//${cell.getRow().getData().nroGuia}`

      // enviar la solicitud de eliminación
      let response = await Helpers.fetchJSON(url, {
        method: 'DELETE',
      })

      if (response.message === 'ok') {
        Toast.show({ message: ` eliminado exitosamente` })
        cell.getRow().delete()
        Estados.#modal.close()
      } else {
        Toast.show({ message: `Nose pudo eliminar el estado`, mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: `Problemas al eliminar el estado`, mode: 'danger', error: e })
    }
  }

  static #displayDataOnForm(idModal, data = {}) {
    const form = document.querySelector(`#${idModal} #form-estadosNuevos`)
    console.log(form)

    if (!form) {
      console.error('El formulario no se cargó correctamente en el modal.')
      throw new Error('El formulario no está disponible.')
    }

    // Configurar campos del formulario
    const selectTipos = form.querySelector('#estado')
    const inputFechaHora = form.querySelector('#fechaHora')

    selectTipos.innerHTML = Estados.#estados
    inputFechaHora.value = data.fechaHora || new Date().toISOString().slice(0, 16) // Formato ISO para datetime-local
    console.log(inputFechaHora.value)
  }

  static #getFormData() {
    return {
      tipoEstado: document.querySelector(`#${Estados.#modal.id} #estado`).value,
      fecha: document.querySelector(`#${Estados.#modal.id} #fechaHora`).value,
    }
  }

  static horaFechaActual() {
    const actualizarFechaHora = () => {
      const idHoraFecha = document.querySelector('#horaFecha')
      if (idHoraFecha) {
        const actual = new Date()
        const formato = actual.toLocaleString('es-CO')
        idHoraFecha.textContent = formato
      }
    }
    actualizarFechaHora()
    setInterval(actualizarFechaHora, 1000)
  }
}
