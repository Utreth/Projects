export default class Cv {
  static #form

  static async init() {
      try {
        
        document.querySelector('main').innerHTML = `
        
        `
        Cv.#form = await Helpers.fetchText('./resources/html/cv.html')
  
        document.querySelector('main').insertAdjacentHTML('afterbegin', Cv.#form)
      
    } catch (e) {
      Toast.show({ title: 'Hoja de vida', message: 'Falló la carga de la información', mode: 'danger', error: e })
    }
  }
    
   
}
