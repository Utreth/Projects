export default class Mercancias {
  static #table
  static #modal
  static #currentOption
  static #form
  static #customers

  constructor() {
    throw new Error('No requiere instancias, todos los métodos son estáticos. Use Mercancias.init()')
  }

  static async init() {
    try {
      Mercancias.#form = await Helpers.fetchText('./resources/html/mercancias.html')
      // acceder a la información de clientes
      let response = await Helpers.fetchJSON(`${urlAPI}/cliente`)
      if (response.message != 'ok') {
        throw new Exception(response) // JavaScript no cuenta con una clase Exception
      }
      // crear las opciones para un select de clientes
      Mercancias.#customers = Helpers.toOptionList({
        items: response.data,
        value: 'id',
        text: 'nombre',
        firstOption: 'Seleccione cliente',
      })
      response = await Helpers.fetchJSON(`${urlAPI}/mercancia`)
      if (response.message != 'ok') {
        throw new Error(response.message)
      }

      // agregar al <main> de index.html la capa que contendrá la tabla
      document.querySelector('main').innerHTML = `
        <div class="p-2 w-full">
            <div id="table-container" class="m-2"></div>
        </div>`

      Mercancias.#table = new Tabulator('#table-container', {
        height: tableHeight, // establecer la altura para habilitar el DOM virtual y mejorar la velocidad de procesamiento
        data: response.data,
        layout: 'fitDataTable', // ajustar columnas al ancho disponible. También fitData|fitDataFill|fitDataStretch|fitDataTable|fitColumns
        columns: [
          // definir las columnas de la tabla, para tipos datetime se utiliza formatDateTime definido en index.mjs
          { formatter: editRowButton, width: 40, hozAlign: 'center', cellClick: Mercancias.#editRowClick },
          { formatter: deleteRowButton, width: 40, hozAlign: 'center', cellClick: Mercancias.#deleteRowClick },
          { title: 'Id', field: 'id', hozAlign: 'center', width: 120 },
          { title: 'Cliente', field: 'cliente.nombre', hozAlign: 'center', width: 212 },
          { title: 'Dice contener', field: 'contenido', hozAlign: 'center', width: 300 },
          { title: 'Ingreso', field: 'fechaHoraIngreso', width: 200, hozAlign: 'center', formatter: 'datetime', formatterParams: formatDateTime },
          { title: 'Salida', field: 'fechaHoraSalida', width: 200, hozAlign: 'center', formatter: 'datetime', formatterParams: formatDateTime },
          { title: 'Días', field: 'dias', hozAlign: 'center', width: 80 },
          { title: 'Alto', field: 'volumen', hozAlign: 'center', visible: false },
          { title: 'Ancho', field: 'volumen', hozAlign: 'center', visible: false },
          { title: 'Largo', field: 'volumen', hozAlign: 'center', visible: false },
          { title: 'Vol. m³', field: 'volumen', hozAlign: 'center', width: 80 },
          { title: 'Costo', field: 'costo', hozAlign: 'center', width: 150, formatter: 'money', formatterParams: { precision: 0 } },
          { title: 'Bodega', field: 'bodega', width: 250 },
        ],
        responsiveLayout: false, // activado el scroll horizontal, también: ['hide'|true|false]
        initialSort: [
          // establecer el ordenamiento inicial de los datos
          { column: 'fechaHoraIngreso', dir: 'asc' },
        ],
        columnDefaults: {
          tooltip: true, //show tool tips on cells
        },

        // mostrar al final de la tabla un botón para agregar registros
        footerElement: `<div class='container-fluid d-flex justify-content-end p-0'>${addRowButton}</div>`,
      })

      Mercancias.#table.on('tableBuilt', () => document.querySelector('#add-row').addEventListener('click', Mercancias.#addRow))
    } catch (e) {
      Toast.show({ title: 'Mercancias', message: 'Falló la carga de la información', mode: 'danger', error: e })
    }

    return this
  }

  static #editRowClick = async (e, cell) => {
    Mercancias.#currentOption = 'edit'
    console.log(cell.getRow().getData())
    Mercancias.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Actualizacion de mercancias</h5>',
      content: Mercancias.#form,
      buttons: [
        { caption: editButton, classes: 'btn btn-primary me-2', action: () => Mercancias.#edit(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Mercancias.#modal.remove() },
      ],
      doSomething: idModal => Mercancias.#displayDataOnForm(idModal, cell.getRow().getData()),
    })
    Mercancias.#modal.show()
  }

  static #deleteRowClick = async (e, cell) => {
    Mercancias.#currentOption = 'delete'
    //console.log(cell.getRow().getData())

    Mercancias.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Eliminacion de mercancias</h5>',
      content: `<span class="text-back dark:text-gray-300">
      Confirme la eliminacion de la mercancia:<br>
      ${cell.getRow().getData().id} - ${cell.getRow().getData().contenido}<br>
      Bodega: ${cell.getRow().getData().bodega}<br>
      Propietario: ${cell.getRow().getData().cliente.nombre}<br>
        </span>`,
      buttons: [
        { caption: deleteButton, classes: 'btn btn-primary me-2', action: () => Mercancias.#delete(cell) },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Mercancias.#modal.remove() },
      ],
    })
    Mercancias.#modal.show()
  }

  static async #addRow() {
    Mercancias.#currentOption = 'add'
    Mercancias.#modal = new Modal({
      classes: 'col-12 col-sm-10 col-md-9 col-lg-8 col-xl-7',
      title: '<h5>Ingreso de mercancias</h5>',
      content: Mercancias.#form,
      buttons: [
        { caption: addButton, classes: 'btn btn-primary me-2', action: () => Mercancias.#add() },
        { caption: cancelButton, classes: 'btn btn-secondary', action: () => Mercancias.#modal.remove() },
      ],
      doSomething: Mercancias.#displayDataOnForm,
    })
    Mercancias.#modal.show()
  }

  static async #add() {
    try {
      // verificar si los datos cumplen con las restricciones indicadas en el formulario HTML
      if (!Helpers.okForm('#form-mercancias', Mercancias.#otherValidations)) {
        return
      }

      // obtener del formulario el objeto con los datos que se envían a la solicitud POST
      const body = Mercancias.#getFormData()
      console.log('Datos enviados:', body)

      // enviar la solicitud de creación con los datos del formulario
      let response = await Helpers.fetchJSON(`${urlAPI}/mercancia`, {
        method: 'POST',
        body,
      })

      if (response.message === 'ok') {
        Mercancias.#table.addRow(response.data) // agregar la mercancía a la tabla
        Mercancias.#modal.remove()
        Toast.show({ message: 'Agregado exitosamente' })
      } else {
        Toast.show({ message: 'No se pudo agregar el registro', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Falló la operación de creación del registro', mode: 'danger', error: e })
    }
  }

  static async #edit(cell) {
    1
    try {
      // verificar si los datos cumplen con las restricciones indicadas en el formulario HTML
      if (!Helpers.okForm('#form-mercancias', Mercancias.#otherValidations)) {
        return
      }

      // obtener del formulario el objeto con los datos que se envían a la solicitud PATCH
      const body = Mercancias.#getFormData()
      console.log('Datos enviados:', body)

      // configurar la url para enviar la solicitud PATCH
      const url = `${urlAPI}/mercancia/${cell.getRow().getData().id}`

      // intentar enviar la solicitud de actualizacion
      let response = await Helpers.fetchJSON(url, {
        method: 'PATCH',
        body,
      })

      if (response.message === 'ok') {
        Mercancias.#table.addRow(response.data) // agregar la mercancía a la tabla
        Mercancias.#modal.remove()
        cell.getRow().delete()
        Toast.show({ message: 'Mercancia actualizada exitosamente' })
      } else {
        Toast.show({ message: 'No se pudo actualizar la mercancia', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Problemas al actualizar la mercancia', mode: 'danger', error: e })
    }
  }

  static async #delete(cell) {
    try {
      const url = `${urlAPI}/mercancia/${cell.getRow().getData().id}`

      //enviar la solicitud de eliminacion
      let response = await Helpers.fetchJSON(url, {
        method: 'DELETE',
      })

      if (response.message === 'ok') {
        Toast.show({ message: 'Mercancia eliminada exitosamente' })
        cell.getRow().delete()
        Mercancias.#modal.close()
      } else {
        Toast.show({ message: 'No se pudo eliminar la mercancia', mode: 'danger', error: response })
      }
    } catch (e) {
      Toast.show({ message: 'Problemas al eliminar la mercancia', mode: 'danger', error: e })
    }
  }

  static #displayDataOnForm(idModal, rowData) {
    // referenciar el select "cliente"
    const selectCustomers = document.querySelector(`#${idModal} #cliente`)

    // asignar la lista de opciones al select "cliente" de mercancias.html
    selectCustomers.innerHTML = Mercancias.#customers

    if (Mercancias.#currentOption === 'edit') {
      //mostrar los datos de la fila actual en el formulario html
      document.querySelector(`#${idModal} #id`).value = rowData.id
      document.querySelector(`#${idModal} #contenido`).value = rowData.contenido
      document.querySelector(`#${idModal} #alto`).value = rowData.alto
      document.querySelector(`#${idModal} #ancho`).value = rowData.ancho
      document.querySelector(`#${idModal} #largo`).value = rowData.largo
      document.querySelector(`#${idModal} #ingreso`).value = rowData.fechaHoraIngreso
      document.querySelector(`#${idModal} #salida`).value = rowData.fechaHoraSalida
      document.querySelector(`#${idModal} #bodega`).value = rowData.bodega
      selectCustomers.value = rowData.cliente.id
    } else {
      //por defecto, asignar a ingreso del formulario la hora y fecha actual
      const now = DateTime.now()
      document.querySelector(`#form-mercancias #ingreso`).value = now.toFormat('yyyy-MM-dd HH:mm')
      document.querySelector(`#form-mercancias #salida`).value = now.plus({ hours: 1 }).toFormat('yyyy-MM-dd HH:mm')
    }
  }

  /**
   * Recupera los datos del formulario y crea un objeto para ser retornado
   * @returns Un objeto con los datos del usuario
   */
  static #getFormData() {
    //Recuerde utilizar parseInt(), parseFloat() o Number() cuando sea necesario
    return {
      id: document.querySelector(`#${Mercancias.#modal.id} #id`).value,
      cliente: document.querySelector(`#${Mercancias.#modal.id} #cliente`).value,
      contenido: document.querySelector(`#${Mercancias.#modal.id} #contenido`).value,
      alto: parseFloat(document.querySelector(`#${Mercancias.#modal.id} #alto`).value),
      ancho: parseFloat(document.querySelector(`#${Mercancias.#modal.id} #ancho`).value),
      largo: parseFloat(document.querySelector(`#${Mercancias.#modal.id} #largo`).value),
      fechaHoraIngreso: document.querySelector(`#${Mercancias.#modal.id} #ingreso`).value,
      fechaHoraSalida: document.querySelector(`#${Mercancias.#modal.id} #salida`).value,
      bodega: document.querySelector(`#${Mercancias.#modal.id} #bodega`).value,
    }
  }

  static #otherValidations() {
    // Referencie los elementos <select> remitente y destinatario

    const cliente = document.querySelector('#cliente')
    

    if (!cliente.value) {
      Toast.show({ message: 'Falta seleccionar un cliente', mode: 'warning' })
      return false
    }

    return true
  }
}
