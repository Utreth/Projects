export default class Envios {
  static #table
  static #modal
  static #currentOption
  static #form
  static #customers
  static #mode

  
  static async init(mode = '') {
    Envios.#mode = mode
    try {
      Envios.#form = await Helpers.fetchText('./resources/html/envios.html')

      // intentar cargar los datos de los usuarios
      const response = await Helpers.fetchJSON(`${urlAPI}/${mode}`)
      const responseEstados = await Helpers.fetchJSON(`${urlAPI}/envio/estados`)

      if (response.message != 'ok') {
        throw new Error(response.message)
      }

      if (responseEstados.message != 'ok') {
        throw new Error(responseEstados.message)
      }

      let responseCliente = await Helpers.fetchJSON(`${urlAPI}/cliente`)
      if (responseCliente.message != 'ok') {
        throw new Exception(responseCliente) // JavaScript no cuenta con una clase Exception
      }

      // crear las opciones para un select de clientes
      Envios.#customers = Helpers.toOptionList({
        items: responseCliente.data,
        value: 'id',
        text: 'nombre',
        firstOption: 'Seleccione cliente',
      })

      // agregar al <main> de index.html la capa que contendrá la tabla
      document.querySelector('main').innerHTML = `
      <div class="p-2 w-full">
          <div id="table-container" class="m-2"></div>
      </div>`

      Envios.#table = new Tabulator('#table-container', {
        height: tableHeight, // establecer la altura para habilitar el DOM virtual y mejorar la velocidad de procesamiento
        data: response.data,
        layout: 'fitDataTable', // ajustar columnas al ancho disponible. También fitData|fitDataFill|fitDataStretch|fitDataTable|fitColumns
        columns: [
          // definir las columnas de la tabla, para tipos datetime se utiliza formatDateTime definido en index.mjs
          { formatter: editRowButton, width: 40, hozAlign: 'center', cellClick: Envios.#editRowClick },
          { formatter: deleteRowButton, width: 40, hozAlign: 'center', cellClick: Envios.#deleteRowClick },
          { title: 'Guia', field: 'nroGuia', hozAlign: 'center', width: 130 },
          { title: 'Remitente', field: 'remitente.nombre', width: 230, hozAlign: 'center' },
          { title: 'Destinario', field: 'destinatario.nombre', width: 230, hozAlign: 'center' },
          { title: 'Dice contener', field: 'contenido', width: 200 },
          { title: 'Valor', field: 'valorDeclarado', hozAlign: 'center', width: 90, formatter: 'money', visible: mode !== 'sobre' },
          { title: 'Peso', field: 'peso', hozAlign: 'center', width: 90, visible: mode !== 'sobre' },
          { title: 'Costo', field: 'costo', hozAlign: 'center', width: 90, formatter: 'money', formatterParams: { precision: 0 } },
          { title: 'Fragil', field: 'fragil', hozAlign: 'center', formatter: 'tickCross', width: 110, visible: mode !== 'sobre' },
          { title: 'Certificado', field: 'certificado', hozAlign: 'center', formatter: 'tickCross', width: 110, visible: mode == 'sobre' },
          {
            title: 'Estado actual',
            field: 'estados',
            width: 280,
            formatter: function (cell) {
              const estadosArray = cell.getValue()
              if (estadosArray && estadosArray.length > 0) {
                let ultimoEstado = estadosArray[estadosArray.length - 1]
                let estadoId = ultimoEstado.tipoEstado
                let fechaHora = ultimoEstado.fecha
                let estadosTipo = responseEstados.data
                // Buscar en tiposEstados el estado humano
                let tiposEstados = estadosTipo.find(e => e.key === estadoId)
                // Formatear fecha y hora
                let fechaHoraFormateada = DateTime.fromISO(fechaHora).toFormat('yyyy-MM-dd hh:mm')
                // Retornar el estado en formato humano junto con la fecha y hora
                return `${fechaHoraFormateada} - ${tiposEstados.value}`
              }
              return 'No disponible'
            },
          },
        ],
        responsiveLayout: false, // activado el scroll horizontal, también: ['hide'|true|false]

        columnDefaults: {
          tooltip: true, //show tool tips on cells
        },

        // mostrar al final de la tabla un botón para agregar registros
        footerElement: `<div class='container-fluid d-flex justify-content-end p-0'>${addRowButton}</div>`,
      })
      Envios.#table.on('tableBuilt', () => document.querySelector('#add-row').addEventListener('click', Envios.#addRow))
    } catch (e) {
      Toast.show({ title: `${Envios.#mode}`, message: 'Falló la carga de la información', mode: 'danger', error: e })
    }

    return this
  }

  static #editRowClick = async (e, cell) => {
    Envios.#currentOption = 'edit'
    console.log(cell.getRow().getData())

    Envios.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: `<h5>Actualización de ${Envios.#mode}</h5>`,
      content: Envios.#form,
      buttons: [
        { caption: editButton, classes: 'btn btn-primary me-2', action: () => Envios.#edit(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Envios.#modal.close() },
      ],
      doSomething: idModal => Envios.#displayDataOnForm(idModal, cell.getRow().getData()),
    })
    Envios.#modal.show()
  }

  static #deleteRowClick = async (e, cell) => {
    Envios.#currentOption = 'delete'
    console.log(cell.getRow().getData())
    Envios.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: `<h5>Eliminación de ${Envios.#mode}</h5>`,
      content: `<span class = "text-back dark:text-gray-300">
                  Confirme la eliminación del ${Envios.#mode}:<br>
                  ${cell.getRow().getData().nroGuia} - ${cell.getRow().getData().contenido}<br>
                  Remitente: ${cell.getRow().getData().remitente.nombre}<br>
                  Destinatario: ${cell.getRow().getData().destinatario.nombre}<br>
                </span>`,
      buttons: [
        { caption: deleteButton, classes: 'btn btn-primary me-2', action: () => Envios.#delete(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Envios.#modal.close() },
      ],
    })
    Envios.#modal.show()
  }

  static async #addRow() {
    Envios.#currentOption = 'add'
    Envios.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: `<h5>Ingreso de ${Envios.#mode}</h5>`,
      content: Envios.#form,
      buttons: [
        { caption: addButton, classes: 'btn btn-primary me-2', action: () => Envios.#add() },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Envios.#modal.close() },
      ],
      doSomething: Envios.#displayDataOnForm,
    })
    Envios.#modal.show()
  }

  static async #add() {
    try {
      //verificar si los datos cumplen con las restricciones indicadas en el formulario HTML
      if (!Helpers.okForm('#form-envios', Envios.#otherValidations)) {
        return
      }

      // obtener del formulario el objeto con los datos que se envían a la solicitud POST
      const body = Envios.#getFormData()
      console.log(body)

      // enviar la solicitud de creación con los datos del formulario
      let response = await Helpers.fetchJSON(`${urlAPI}/${Envios.#mode}`, {
        method: 'POST',
        body,
      })

      if (response.message === 'ok') {
        Envios.#table.addRow(response.data) // agregar el envio a la tabla
        Envios.#modal.remove()
        Toast.show({ message: 'Agregado exitosamente' })
      } else {
        Toast.show({ message: 'Nose pudo agregar el registro', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Falló la operación de creación del registro', mode: 'danger', error: e })
    }
  }

  static async #edit(cell) {
    try {
      //verificar si los datos cumplen con las restricciones indicadas en el formulario HTML
      if (!Helpers.okForm('#form-envios', Envios.#otherValidations)) {
        return
      }

      // obtener del formulario el objeto con los datos que se envían a la solicitud PATCH
      const body = Envios.#getFormData()

      // configurar la url para enviar la solicitud PACTH
      const url = `${urlAPI}/${Envios.#mode}/${cell.getRow().getData().nroGuia}`

      // intentar enviar la solicitud de actualización
      let response = await Helpers.fetchJSON(url, {
        method: 'PATCH',
        body,
      })

      console.log(body)

      if (response.message === 'ok') {
        Toast.show({ message: `${Envios.#mode} actualizado exitosamente` })
        cell.getRow().update(response.data)
        Envios.#modal.remove()
      } else {
        Toast.show({ message: `Nose pudo actualizar el ${Envios.#mode}`, mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: `Problemas al actualizar el ${Envios.#mode}`, mode: 'danger', error: e })
    }
  }

  static async #delete(cell) {
    try {
      const url = `${urlAPI}/${Envios.#mode}/${cell.getRow().getData().nroGuia}`

      // enviar la solicitud de eliminación
      let response = await Helpers.fetchJSON(url, {
        method: 'DELETE',
      })

      if (response.message === 'ok') {
        Toast.show({ message: `${Envios.#mode} eliminado exitosamente` })
        cell.getRow().delete()
        Envios.#modal.close()
      } else {
        Toast.show({ message: `Nose pudo eliminar el ${Envios.#mode}`, mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: `Problemas al eliminar el ${Envios.#mode}`, mode: 'danger', error: e })
    }
  }

  static #displayDataOnForm(idModal, rowData) {
    // referenciar el select "cliente"
    const selectCustomers = document.querySelector(`#${idModal} #remitente`)
    const selectCustomers2 = document.querySelector(`#${idModal} #destinatario`)

    // asignar la lista de opciones al select "cliente" de Envios.html
    selectCustomers.innerHTML = Envios.#customers
    selectCustomers2.innerHTML = Envios.#customers

    if (Envios.#currentOption === 'edit') {
      //mostrar los datos de la fila actual en el formulario html
      document.querySelector(`#${idModal} #nroGuia`).value = rowData.nroGuia
      document.querySelector(`#${idModal} #certificado`).checked = rowData.certificado
      document.querySelector(`#${idModal} #fragil`).checked = rowData.fragil
      document.querySelector(`#${idModal} #contenido`).value = rowData.contenido
      document.querySelector(`#${idModal} #peso`).value = rowData.peso
      document.querySelector(`#${idModal} #valorDeclarado`).value = rowData.valorDeclarado
      selectCustomers.value = rowData.remitente.id
      selectCustomers2.value = rowData.destinatario.id
    }

    if (Envios.#mode === 'sobre') {
      document.querySelector(`#${idModal} #div-certificado`).style.visibility = 'visible'
      document.querySelector(`#${idModal} #div-peso-valor`).style.display = 'none'
      document.querySelector(`#${idModal} #contenido`).value = 'Documentos'
      document.querySelector(`#${idModal} #fragil`).disabled = true
    }
  }

  static #getFormData() {
    // recuerde utilizar parseInt(), parseFloat() Number() cuando sea necesario
    return {
      nroGuia: document.querySelector(`#${Envios.#modal.id} #nroGuia`).value,
      fragil: document.querySelector(`#${Envios.#modal.id} #fragil`).checked,
      certificado: document.querySelector(`#${Envios.#modal.id} #certificado`).checked,
      remitente: document.querySelector(`#${Envios.#modal.id} #remitente`).value,
      destinatario: document.querySelector(`#${Envios.#modal.id} #destinatario`).value,
      contenido: document.querySelector(`#${Envios.#modal.id} #contenido`).value,
      peso: parseFloat(document.querySelector(`#${Envios.#modal.id} #peso`).value),
      valorDeclarado: parseFloat(document.querySelector(`#${Envios.#modal.id} #valorDeclarado`).value),
    }
  }

  static #otherValidations() {
    const remitente = document.querySelector('#remitente')
    const destinatario = document.querySelector('#destinatario')
    if (!remitente.value) {
      Toast.show({ message: 'Falta seleccionar un remitente', mode: 'warning' })
      return false
    }

    if (!destinatario.value) {
      Toast.show({ message: 'Falta seleccionar un destinatario', mode: 'warning' })
      return false
    }

    if (remitente.value === destinatario.value) {
      Toast.show({
        message: 'El destinatario debe ser distinto al remitente',
        mode: 'warning',
      })
      return false
    }

    return true
  }

  
}
