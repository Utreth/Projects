export default class Helpers {
  /**
   * Aplica las reglas de validación definidas para un formulario HTML.
   * Incluso puede indicar un callback como segundo argumento para complementar la validación
   * @param {String} formSelector Una regla CSS para referenciar el formulario a validar
   */
  static okForm = (formSelector, callBack) => {
    let ok = true
    const form = document.querySelector(formSelector)
    // si los datos del formulario no son válidos, forzar un submit para que se muestren los errores
    if (!form.checkValidity()) {
      let tmpSubmit = document.createElement('button')
      form.appendChild(tmpSubmit)
      tmpSubmit.click()
      form.removeChild(tmpSubmit)
      ok = false
    }
    if (typeof callBack === 'function') {
      ok = ok && callBack()
    }
    return ok
  }

  /**
   * Hace una petición HTTP y retorna la respuesta obtenida
   * https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
   * @param {String} url La dirección donde se encuentra el recurso
   * @param {Object} body Un objecto con el cuerpo de la petición
   * @returns Un objeto JSON con la respuesta a la solicitud
   */
  static async fetchJSON(url, data = {}) {
    if (data instanceof Object && data.body instanceof Object && Object.keys(data.body).length > 0) {
      data.headers = { 'Content-Type': 'application/json; charset=utf-8' }
      data.body = JSON.stringify(data.body)
    }

    let response
    try {
      response = await fetch(url, data)

      if (!response.ok) {
        try {
          return await response.json()
        } catch (error) {
          return { ok: false, status: response.status, statusText: response.statusText, url: response.url }
        }
      }
      return await response.json()
    } catch (error) {
      return { ok: false, message: error.message }
    }
  }

  /**
   * Carga un recurso HTML en una capa de la aplicación
   * @param {String} url La ruta donde se encuentra el recurso
   * @param {String} container Opcionalmente, la capa donde se inserta el contenido
   * @returns Si el segundo argumento se da, se retorna el container, si no se retorna el recurso
   */
  static async fetchText(url, container = null) {
    try {
      const response = await fetch(url)

      if (response.ok) {
        const html = await response.text()
        const element = document.querySelector(container)
        if (element) {
          element.innerHTML = html
        }
        return element || html // para permitir encadenamiento o para retornar el HTML
      } else {
        return `Se reporta ${response.status} - ${response.statusText}, al intentar acceder al recurso '${url}'`
      }
    } catch (e) {
      return `Se reporta ${e.message}, al intentar acceder al recurso '${url}'`
    }
  }

  /**
   * Crea el HTML correspondiente a una lista de opciones para inyectar en un select
   * @param {Object} El objeto de definición de la lista
   * @returns El HTML con la lista de opciones
   */
  static toOptionList = ({
    items = [], // el array de objetos para crear la lista
    value = '', // el nombre del atributo de cada objeto que se usará como value
    text = '', // el nombre del atributo de cada objeto que se usará como text
    selected = '', // el valor que debe marcarse como seleccionado
    firstOption = '', // opcionalmente una opción adicional para iniciar la lista
  } = {}) => {
    let options = ''
    if (firstOption) {
      options += `<option value="">${firstOption}</option>`
    }
    items.forEach(item => {
      if (item[value] == selected) {
        // comprobación débil adrede
        options += `<option value="${item[value]}" selected>${item[text]}</option>`
      } else {
        options += `<option value="${item[value]}">${item[text]}</option>`
      }
    })
    return options
  }
}
