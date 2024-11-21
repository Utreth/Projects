import Intro from './intro.mjs'
import Helpers from './helpers.mjs'

class App {
  static #cliente
  static #data
  static #index = 0

  static async main() {
    let intro = new Intro()
    intro.saludar()

    let request = await fetch(`./resources/assets/config.json`)
    let response = await request.json()

    request = await fetch(`${response.url}/cliente`)
    response = await request.json()
    App.#data = response.data

    await Helpers.fetchText('./resources/html/clientes.html', 'main')

    document.querySelector('#id').value = App.#data[App.#index].id
    document.querySelector('#nombre').value = App.#data[App.#index].nombre
    document.querySelector('#direccion').value = App.#data[App.#index].direccion
    document.querySelector('#telefono').value = App.#data[App.#index].telefono
    document.querySelector('#ciudad').value = App.#data[App.#index].ciudad

    document.querySelector('#registrar').addEventListener('click', e => {
      e.preventDefault()
      if (Helpers.okForm('#frmclientes')) {
        App.#cliente = App.getCliente()
        console.log(App.#cliente)
      } else {
        console.log('El formulario no pasa la prueba')
      }
    })

    document.querySelector('#siguiente').addEventListener('click', e => {
      e.preventDefault()
      if (App.#data[App.#index + 1]) {
        App.#index += 1

        document.querySelector('#id').value = App.#data[App.#index].id
        document.querySelector('#nombre').value = App.#data[App.#index].nombre
        document.querySelector('#direccion').value = App.#data[App.#index].direccion
        document.querySelector('#telefono').value = App.#data[App.#index].telefono
        document.querySelector('#ciudad').value = App.#data[App.#index].ciudad
        
      }
    })

    document.querySelector('#anterior').addEventListener('click', e => {
      e.preventDefault()
      if (App.#data[App.#index-1]) {
       
        App.#index -= 1

        document.querySelector('#id').value = App.#data[App.#index].id
        document.querySelector('#nombre').value = App.#data[App.#index].nombre
        document.querySelector('#direccion').value = App.#data[App.#index].direccion
        document.querySelector('#telefono').value = App.#data[App.#index].telefono
        document.querySelector('#ciudad').value = App.#data[App.#index].ciudad
        
      }
    })

    const result = Helpers.okForm('abcd', App.hacerAlgo())

    console.info(`La validación fue un ${result ? 'exito' : 'fracaso'}`)
  }
  static getCliente() {
    return {
      id: document.querySelector('#id').value,
      nombre: document.querySelector('#nombre').value,
      direccion: document.querySelector('#direccion').value,
      telefono: document.querySelector('#telefono').value,
      ciudad: document.querySelector('#ciudad').value,
    }
  }

  static hacerAlgo() {
    console.warn('Nada adicional para validar')
    return true
  }
}

App.main()
