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

      // listener para realizar busqueda
      document.querySelector('#buscar').addEventListener('click', async e => {
        e.preventDefault()
        await Estados.#encontrarEnvio()
      })
    } catch (e) {
      console.error("Error al realizar la operación:", e);
      Toast.show({ title: 'Estados', message: `Error: ${e.message || e}`, mode: 'danger' });
    }

    return this
  }

  static async #encontrarEnvio() {
    try {
      const tipo = document.querySelector('#tipoEnvio').value
      const nroGuia = document.querySelector('#nroGuia').value
      const responseEstados = await Helpers.fetchJSON(`${urlAPI}/envio/estados`)

      const busqueda = await Helpers.fetchJSON(`${urlAPI}/${tipo}/id/${nroGuia}`)
      const resultados = document.querySelector('#resultados')

      if (busqueda.message !== 'ok') {
        resultados.innerHTML = `
          <div class="alert alert-dismissible alert-danger">
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            <h4 class="alert-heading">Búsqueda fallida</h4>
            <p class="mb-0">No se encontró un envío con número de guía: <a>${nroGuia}</a></p>
          </div>
        `
        return
      }

      const info = busqueda.data
      const datosTabla = info.estados
      Estados.#listaEstados = info.estados

      // Mostrar la tabla y la informacion del envio juntos
      resultados.innerHTML = `
        <div class="card">
          <div class="card-header">
            <h5>Información del envío</h5>
          </div>
          <div class="card-body">
            <p><strong>Tipo de Envío:</strong> ${tipo}</p>
            <p><strong>Remitente:</strong> ${info.remitente.nombre} - ${info.remitente.direccion} - ${info.remitente.ciudad}</p>
            <p><strong>Destinatario:</strong> ${info.destinatario.nombre} - ${info.destinatario.direccion} - ${info.destinatario.ciudad}</p>
            <p><strong>Contenido:</strong> ${info.contenido}</p>
            <p><strong>Valor del Envío:</strong> ${info.costo}</p>
          </div>
          <div class="card-footer">
            <h5>Historial de Estados</h5>
            <div id="table-container"></div>
          </div>
        </div>
      `

      // Reconstruir la tabla dentro del contenedor actualizado
      Estados.#table = new Tabulator('#table-container', {
        height: (tableHeight2 = 200), // Mantiene su tamaño
        data: datosTabla,
        layout: 'fitColumns',
        columns: [
          { formatter: deleteRowButton, width: 40, hozAlign: 'center', cellClick: Estados.#deleteRowClick },
          {
            title: 'Fecha',
            field: 'fecha',
            width: 422,
            formatter: function (cell) {
              const fechaHora = cell.getValue()
              let fechaHoraFormateada = DateTime.fromISO(fechaHora).setLocale('es').toFormat("hh:mm a 'del' cccc dd 'de' MMMM 'de' yyyy")
              return `${fechaHoraFormateada}`
            },
          },
          {
            title: 'Tipo de Estado',
            field: 'tipoEstado',
            width: 422,
            formatter: function (cell) {
              const tipoEstado = cell.getValue();
              let tiposEstados = responseEstados.data.find(e => e.key === tipoEstado);
              return tiposEstados ? tiposEstados.value : "Estado desconocido";
            },
          },
        ],
        responsiveLayout: false,
        columnDefaults: { tooltip: true },
        footerElement: `<div class='container-fluid d-flex justify-content-end p-0'>${addRowButton}</div>`,
      })
      Estados.#table.on('tableBuilt', () => document.querySelector('#add-row').addEventListener('click', Estados.#addRow))
    } catch (error) {
      const resultados = document.querySelector('#resultados')
      resultados.innerHTML = `
        <div class="alert alert-dismissible alert-danger">
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
          <h4 class="alert-heading">Error</h4>
          <p class="mb-0">Ocurrió un error al realizar la búsqueda.</p>
        </div>
      `
      console.error(error)
    }
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
        Estados.#table.replaceData(response.data.estados) // agregar el estado a la tabla
        Estados.#modal.remove()

        Toast.show({ message: 'Agregado exitosamente' })
      } else {
        Toast.show({ message: 'No se pudo agregar el estado', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Falló la operación de creación del estado', mode: 'danger', error: e })
    }
  }

  static async #delete(cell) {
    try {
      const tipo = document.querySelector('#tipoEnvio').value
      const nroGuia = document.querySelector('#nroGuia').value
      

      // enviar la solicitud de eliminación
      let response = await Helpers.fetchJSON(`${urlAPI}/${tipo}/${nroGuia}`, {
        method: 'DELETE',
        body,
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

  static #deleteRowClick = async (e, cell) => {
    Estados.#currentOption = 'delete'
    const tipoEstado = cell.getRow().getData().tipoEstado
    console.log(tipoEstado);
    
    Estados.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: `<h5>Eliminación de estados</h5>`,
      content: `<span class = "text-back dark:text-gray-300">
                    Confirme la eliminación del estado:<br><br>
                     ${tipoEstado}
                    
                  </span>`,
      buttons: [
        {
          caption: deleteButton,
          classes: 'btn btn-primary me-2',
          action: () => {
            cell.getRow().delete()
            const estados = Estados.#table.getData()
          },
        },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Estados.#modal.remove() },
      ],
    })
    Estados.#modal.show()
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

  static async update(estados) {
    console.log(estados)
  }
}
