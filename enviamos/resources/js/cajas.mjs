export default class Cajas {
  static #table
  static #modal
  static #currentOption
  static #form
  static #customers

  constructor() {
    throw new Error('No requiere instancias, todos los métodos son estáticos. Use Cajas.init()')
  }

  static async init() {
    try {
      Cajas.#form = await Helpers.fetchText('./resources/html/cajas.html')
      // acceder a la información de clientes
      let response = await Helpers.fetchJSON(`${urlAPI}/cliente`)
      if (response.message != 'ok') {
        throw new Exception(response) // JavaScript no cuenta con una clase Exception
      }
      // crear las opciones para un select de clientes
      Cajas.#customers = Helpers.toOptionList({
        items: response.data,
        value: 'id',
        text: 'nombre',
        firstOption: 'Seleccione cliente',
      })
      response = await Helpers.fetchJSON(`${urlAPI}/caja`)
      if (response.message != 'ok') {
        throw new Error(response.message)
      }

      // agregar al <main> de index.html la capa que contendrá la tabla
      document.querySelector('main').innerHTML = `
          <div class="p-2 w-full">
              <div id="table-container" class="m-2"></div>
          </div>`

      Cajas.#table = new Tabulator('#table-container', {
        height: tableHeight, // establecer la altura para habilitar el DOM virtual y mejorar la velocidad de procesamiento
        data: response.data,
        layout: 'fitColumns', // ajustar columnas al ancho disponible. También fitData|fitDataFill|fitDataStretch|fitDataTable|fitColumns
        columns: [
          // definir las columnas de la tabla, para tipos datetime se utiliza formatDateTime definido en index.mjs
          { formatter: editRowButton, width: 40, hozAlign: 'center', cellClick: Cajas.#editRowClick },
          { formatter: deleteRowButton, width: 40, hozAlign: 'center', cellClick: Cajas.#deleteRowClick },
          { title: 'Guia', field: 'nroGuia', hozAlign: 'center', width: 90 },
          { title: 'Remitente', field: 'remitente.nombre', width: 200 },
          { title: 'Destinario', field: 'destinatario.nombre', width: 200 },
          { title: 'Dice contener', field: 'contenido', width: 200 },
          { title: 'Valor', field: 'valorDeclarado', hozAlign: 'center', width: 90, formatter: 'money' },
          { title: 'Peso', field: 'peso', hozAlign: 'center', width: 90 },
          { title: 'Costo', field: 'costo', hozAlign: 'right', width: 90, formatter: 'money', formatterParams: { precision: 0 } },
          { title: 'Fragil', field: 'fragil', hozAlign: 'center', formatter: 'tickCross', width: 90 },
          { title: 'Estado Actual', field: 'estados', formatter: Cajas.#stateFormat },
        ],
        responsiveLayout: false, // activado el scroll horizontal, también: ['hide'|true|false]
        // mostrar al final de la tabla un botón para agregar registros
        footerElement: `<div class='container-fluid d-flex justify-content-end p-0'>${addRowButton}</div>`,
      })
      Cajas.#table.on('tableBuilt', () => document.querySelector('#add-row').addEventListener('click', Cajas.#addRow))
    } catch (e) {
      Toast.show({ title: 'Cajas', message: 'Falló la carga de la información', mode: 'danger', error: e })
    }

    return this
  }

  static #editRowClick = async (e, cell) => {
    Cajas.#currentOption = 'edit'
    console.log(cell.getRow().getData())
    Cajas.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Actualizacion de mercancias</h5>',
      content: Cajas.#form,
      buttons: [
        { caption: editButton, classes: 'btn btn-primary me-2', action: () => Cajas.#edit(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Cajas.#modal.close() },
      ],
      doSomething: idModal => Cajas.#displayDataOnForm(idModal, cell.getRow().getData()),
    })
    Cajas.#modal.show()
  }

  static #deleteRowClick = async (e, cell) => {
    Cajas.#currentOption = 'delete'
    console.log(cell.getRow().getData())
    Cajas.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Eliminacion de cajas</h5>',
      content: `<span class="text-back dark:text-gray-300">
        Confirme la eliminacion de la caja:<br>
         ${cell.getRow().getData().nroGuia} - ${cell.getRow().getData().contenido}<br>
        Remitente: ${cell.getRow().getData().remitente.nombre}<br>
        Destinatario: ${cell.getRow().getData().destinatario.nombre}<br>
          </span>`,
      buttons: [
        { caption: deleteButton, classes: 'btn btn-primary me-2', action: () => Cajas.#delete(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Cajas.#modal.close() },
      ],
    })
    Cajas.#modal.show()
  }

  static async #addRow() {
    Cajas.#currentOption = 'add'
    Cajas.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Ingreso de mercancias</h5>',
      content: Cajas.#form,
      buttons: [
        { caption: addButton, classes: 'btn btn-primary me-2', action: () => Cajas.#add() },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Cajas.#modal.close() },
      ],
      doSomething: Cajas.#displayDataOnForm,
    })
    Cajas.#modal.show()
  }

  static async #add() {
    try {
      // verificar si los datos cumplen con las restricciones indicadas en el formulario HTML
      if (!Helpers.okForm('#form-cajas')) {
        return
      }

      // obtener del formulario el objeto con los datos que se envían a la solicitud POST
      const body = Cajas.#getFormData()
      console.log('Datos enviados:', body)

      // enviar la solicitud de creación con los datos del formulario
      let response = await Helpers.fetchJSON(`${urlAPI}/caja`, {
        method: 'POST',
        body,
      })

      if (response.message === 'ok') {
        Cajas.#table.addRow(response.data) // agregar la mercancía a la tabla
        Cajas.#modal.remove()
        Toast.show({ message: 'Agregado exitosamente' })
      } else {
        Toast.show({ message: 'No se pudo agregar el registro', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Falló la operación de creación del registro', mode: 'danger', error: e })
    }
  }

  static async #edit(cell) {
    try {
      // verificar si los datos cumplen con las restricciones indicadas en el formulario HTML
      if (!Helpers.okForm('#form-cajas')) {
        return
      }

      // obtener del formulario el objeto con los datos que se envían a la solicitud PATCH
      const body = Cajas.#getFormData()
      console.log('Datos enviados:', body)

      // configurar la url para enviar la solicitud PATCH
      const url = `${urlAPI}/caja/${cell.getRow().getData().id}`

      // intentar enviar la solicitud de actualizacion
      let response = await Helpers.fetchJSON(url, {
        method: 'PATCH',
        body,
      })

      if (response.message === 'ok') {
        Cajas.#table.addRow(response.data) // agregar la mercancía a la tabla
        Cajas.#modal.remove()
        Toast.show({ message: 'Caja actualizada exitosamente' })
      } else {
        Toast.show({ message: 'No se pudo actualizar la caja', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Problemas al actualizar la caja', mode: 'danger', error: e })
    }
  }

  static async #delete(cell) {
    try {
      const url = `${urlAPI}/caja/${cell.getRow().getData().nroGuia}`

      //enviar la solicitud de eliminacion
      let response = await Helpers.fetchJSON(url, {
        method: 'DELETE',
      })

      if (response.message === 'ok') {
        Toast.show({ message: 'Caja eliminada exitosamente' })
        cell.getRow().delete()
        Cajas.#modal.close()
      } else {
        Toast.show({ message: 'No se pudo eliminar la caja', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Problemas al eliminar la caja', mode: 'danger', error: e })
    }
  }

  static #displayDataOnForm(idModal, rowData) {
    // referenciar el select "cliente"
    const selectCustomers = document.querySelector(`#${idModal} #remitente`)
    const selectCustomers2 = document.querySelector(`#${idModal} #destinatario`)

    // asignar la lista de opciones al select "cliente" de mercancias.html
    selectCustomers.innerHTML = Cajas.#customers
    selectCustomers2.innerHTML = Cajas.#customers

    if (Cajas.#currentOption === 'edit') {
      //mostrar los datos de la fila actual en el formulario html
      document.querySelector(`#${idModal} #nroGuia`).value = rowData.nroGuia
      document.querySelector(`#${idModal} #contenido`).value = rowData.contenido
      document.querySelector(`#${idModal} #alto`).value = rowData.alto
      document.querySelector(`#${idModal} #ancho`).value = rowData.ancho
      document.querySelector(`#${idModal} #largo`).value = rowData.largo
      document.querySelector(`#${idModal} #peso`).value = rowData.peso
      document.querySelector(`#${idModal} #valorDeclarado`).value = rowData.valorDeclarado
      document.querySelector(`#${idModal} #fragil`).checked = rowData.fragil
      selectCustomers.value = rowData.remitente.id
      selectCustomers.value = rowData.destinatario.id
    }
  }

  /**
   * Recupera los datos del formulario y crea un objeto para ser retornado
   * @returns Un objeto con los datos del usuario
   */
  static #getFormData() {
    //Recuerde utilizar parseInt(), parseFloat() o Number() cuando sea necesario
    return {
      nroGuia: document.querySelector(`#${Cajas.#modal.id} #nroGuia`).value,
      remitente: document.querySelector(`#${Cajas.#modal.id} #remitente`).value,
      destinatario: document.querySelector(`#${Cajas.#modal.id} #destinatario`).value,
      contenido: document.querySelector(`#${Cajas.#modal.id} #contenido`).value,
      alto: parseFloat(document.querySelector(`#${Cajas.#modal.id} #alto`).value),
      ancho: parseFloat(document.querySelector(`#${Cajas.#modal.id} #ancho`).value),
      largo: parseFloat(document.querySelector(`#${Cajas.#modal.id} #largo`).value),
      peso: parseInt(document.querySelector(`#${Cajas.#modal.id} #peso`).value),
      valorDeclarado: parseInt(document.querySelector(`#${Cajas.#modal.id} #valorDeclarado`).value),  
      fragil: document.querySelector(`#${Cajas.#modal.id} #fragil`).checked
    }
  }

  static #stateFormat = cell => {
    const last = cell.getValue()[cell.getValue().length - 1]
    return `${last.fecha} - ${last.tipoEstado}`
  }
}
