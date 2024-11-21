/**
 * Manejo de eventos, arrays de objetos, DOM
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/findIndex
 */
import Helpers from './helpers.mjs'
import Intro from './intro.mjs'

class App {
  static #indice
  static #totalRegistros
  static #localidades
  static #urlAPI

  static async main() {
    let response = await Helpers.fetchJSON('./resources/assets/config.json')
    App.#urlAPI = response.url

    await Helpers.fetchText('./resources/html/cliente.html', 'main')

    App.#localidades = await Helpers.fetchJSON('./resources/assets/ciudades.json')
    document.querySelector('#ciudad').innerHTML = Helpers.toOptionList({
      items: App.#localidades,
      value: 'codigo',
      text: 'nombre',
    })

    App.#indice = 0
    // obtener el conteo de los registros de los clientes
    response = await Helpers.fetchJSON(`${App.#urlAPI}/cliente/conteo`)
    if (response.message != 'ok') {
      console.log(response)
      return
    }

    App.#totalRegistros = response.size

    App.displayData()

    // avanzar por los registros
    document.querySelectorAll('button').forEach(b => {
      b.addEventListener('click', App.action)
    })
  }

  static async displayData() {
    // recuperar el registro dado por el índice y mostrarlo si ok
    const response = await Helpers.fetchJSON(`${App.#urlAPI}/cliente/indice/${App.#indice}`)
    if (response.message == 'ok') {
      document.querySelector('#id').value = response.data.id
      document.querySelector('#nombre').value = response.data.nombre
      document.querySelector('#direccion').value = response.data.direccion
      document.querySelector('#telefono').value = response.data.telefono

      const searched = response.data.ciudad // La subcadena que queremos encontrar
      const index = App.#localidades.findIndex(item => {
        const regex = new RegExp(searched, 'i') // Expresión regular insensible a mayúsculas
        return regex.test(item.nombre)
      })
      document.querySelector('#ciudad').selectedIndex = index
    } else {
      console.log(response)
    }
  }

  static async action(e) {
    e.preventDefault()
    switch (e.target.id) {
      case 'next':
        if (App.#indice + 1 < App.#totalRegistros) {
          App.#indice++
        }
        break
      case 'previous':
        if (App.#indice > 0) {
          App.#indice--
        }
        break
      case 'first':
        App.#indice = 0
        break
      case 'last':
        App.#indice = App.#totalRegistros - 1
        break
      case 'update':
        await App.update()
        break
      default:
        console.error(`Error para la opción ${e.target.id}`)
    }
    App.displayData()
  }

  static async update() {
    if (Helpers.okForm('#frmclientes')) {
      const body = App.getBody()
      console.log(body)
      const response = await Helpers.fetchJSON(`${App.#urlAPI}/cliente/${body.id}`, {
        method: 'PATCH',
        body,
      })
    } else {
      console.log('el formulario tiene econsole.log(response)rrores')
    }
  }

  static getBody() {
    const cities = document.querySelector('#ciudad')
    return {
      id: document.querySelector('#id').value,
      nombre: document.querySelector('#nombre').value,
      direccion: document.querySelector('#direccion').value,
      telefono: document.querySelector('#telefono').value,
      // cambiar esto por un select
      ciudad: cities.options[cities.selectedIndex].text,
    }
  }
}

App.main()
