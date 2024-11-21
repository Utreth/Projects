export default class Paquetes {
  static #table
  static #modal
  static #currentOption
  static #form
  static #tipoEstados = []

  constructor() {
    throw new Error('No requiere instancias, todos los métodos son estáticos. Use Paquetes.init()')
  }

  static async init() {
    try {
      Paquetes.#form = `Aún no hay un formulario de datos para mostrar`
      // intentar cargar los datos de los usuarios
      let response = null
      response = await Helpers.fetchJSON(`${urlAPI}/paquete`)
      if (response.message != 'ok') {
        throw new Error(response.message)
      }

      // agregar al <main> de index.html la capa que contendrá la tabla
      document.querySelector('main').innerHTML = `
        <div class="p-2 w-full">
            <div id="table-container" class="m-2"></div>
        </div>`

      Paquetes.#table = new Tabulator('#table-container', {
        height: tableHeight, // establecer la altura para habilitar el DOM virtual y mejorar la velocidad de procesamiento
        data: response.data,
        layout: 'fitColumns', // ajustar columnas al ancho disponible. También fitData|fitDataFill|fitDataStretch|fitDataTable|fitColumns
        columns: [
          // definir las columnas de la tabla, para tipos datetime se utiliza formatDateTime definido en index.mjs
          { formatter: editRowButton, width: 40, hozAlign: 'center', cellClick: Paquetes.#editRowClick },
          { formatter: deleteRowButton, width: 40, hozAlign: 'center', cellClick: Paquetes.#deleteRowClick },
          { title: 'Guia', field: 'nroGuia', hozAlign: 'center', width: 90 },
          { title: 'Remitente', field: 'remitente.nombre', width: 200 },
          { title: 'Destinario', field: 'destinatario.nombre', width: 200 },
          { title: 'Dice contener', field: 'contenido', width: 200 },
          { title: 'Valor', field: 'valorDeclarado', hozAlign: 'center', width: 90, formatter: 'money' },
          { title: 'Peso', field: 'peso', hozAlign: 'center', width: 90 },
          { title: 'Costo', field: 'costo', hozAlign: 'right', width: 90, formatter: 'money', formatterParams: { precision: 0 } },
          { title: 'Fragil', field: 'fragil', hozAlign: 'center', formatter: 'tickCross', width: 90 },
          { title: 'Estado Actual', field: 'estados', formatter: Paquetes.#stateFormat },
        ],
        responsiveLayout: false, // activado el scroll horizontal, también: ['hide'|true|false]
        // mostrar al final de la tabla un botón para agregar registros
        footerElement: `<div class='container-fluid d-flex justify-content-end p-0'>${addRowButton}</div>`,
      })
      Paquetes.#table.on('tableBuilt', () => document.querySelector('#add-row').addEventListener('click', Paquetes.#addRow))
    } catch (e) {
      Toast.show({ title: 'Paquetes', message: 'Falló la carga de la información', mode: 'danger', error: e })
    }

    return this
  }

  static async #addRow() {
    Paquetes.#currentOption = 'add'
    Paquetes.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Ingreso de mercancias</h5>',
      content: Paquetes.#form,
      buttons: [
        { caption: addButton, classes: 'btn btn-primary me-2', action: () => Paquetes.#add() },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Paquetes.#modal.close() },
      ],
      doSomething: Paquetes.#displayDataOnForm,
    })
    Paquetes.#modal.show()
  }

  static async #add() {
    console.warn('Sin implementar Paquetes.add()')
  }

  static #editRowClick = async (e, cell) => {
    Paquetes.#currentOption = 'edit'
    console.log(cell.getRow().getData())
    Paquetes.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Actualizacion de Paquetes</h5>',
      content: Paquetes.#form,
      buttons: [
        { caption: editButton, classes: 'btn btn-primary me-2', action: () => Paquetes.#edit(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Paquetes.#modal.close() },
      ],
      doSomething: idModal => Paquetes.#displayDataOnForm(idModal, cell.getRow().getData()),
    })
    Paquetes.#modal.show()
  }

  static async #edit(cell) {
    console.warn('Sin implementar Paquetes.edit()')
  }

  static #deleteRowClick = async (e, cell) => {
    Paquetes.#currentOption = 'delete'
    console.log(cell.getRow().getData())
    Paquetes.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Eliminacion de mercancias</h5>',
      content: `<span class="text-back dark:text-gray-300">
      Confirme la eliminacion del paquete:<br>
      ${cell.getRow().getData().nroGuia} - ${cell.getRow().getData().contenido}<br>
      Remitente: ${cell.getRow().getData().remitente.nombre}<br>
      Destinatario: ${cell.getRow().getData().destinatario.nombre}<br>
      Contenido: ${cell.getRow().getData().contenido}<br>
        </span>`,
      buttons: [
        { caption: deleteButton, classes: 'btn btn-primary me-2', action: () => Paquetes.#delete(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Paquetes.#modal.close() },
      ],
      doSomething: Paquetes.#displayDataOnForm,
    })
    Paquetes.#modal.show()
  }

  static async #delete(cell) {
    console.warn('Sin implementar Paquetes.delete()')
  }

  static #toComplete(idModal, rowData) {
    console.warn('Sin implementar Paquetes.toComplete()')
  }

  /**
   * Recupera los datos del formulario y crea un objeto para ser retornado
   * @returns Un objeto con los datos del usuario
   */
  static #getFormData() {
    console.warn('Sin implementar Paquetes.toComplete()')
  }

  static #stateFormat = cell => {
    const last = cell.getValue()[cell.getValue().length - 1]
    return `${last.fecha} - ${last.tipoEstado}`
  }

  static #displayDataOnForm(idModal, rowData) {
    console.warn('Sin implementar displayDataOnForm()')
  }
}
