export default class Helpers {
  static okForm = (formSelector = null, callBack = null) => {
    let ok = true
    const form = document.querySelector(formSelector)

    // si los datos del formulario no son válidos, forzar un submit para que se muestren los errores
    if (form && !form.checkValidity()) {
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
        return Se `reporta ${response.status} - ${response.statusText}, al intentar acceder al recurso '${url}'`
      }
    } catch (e) {
      return Se `reporta ${e.message}, al intentar acceder al recurso '${url}'`
    }
  }
}

