//seccion para importar estaticos

class App {
  static async main() {
    App.variables()
    App.instancia()
    App.arreglos()
    App.operadores()
  }
  static variables() {
    //variables y tipos de datos
    let cantidadProductos = 10
    let precioUnitario = 15.99
    let nombreProducto = 'Camisa'
    console.info(`nombre: ${nombreProducto}\ncantidad: ${cantidadProductos}\nprecio ${precioUnitario}`)

    const PI = 3.141592653589793
    const URL_API = 'http://localhost:7070'
    console.info(`PI: ${PI}\nULR API: ${URL_API}`)

    let esEstudiante = true
    let tieneDescuento = true
    console.info(`Es estudiante: ${esEstudiante}\ntiene descuento: ${tieneDescuento ? 'Si' : 'No'}`)

    let valorNulo = null
    let valorIndefinido
    console.info(`Valor nulo: ${valorNulo}\nValor indefinido: ${valorIndefinido}`)
  }

  static instancia() {
    let persona = {
      edad: 25,
      nombre: 'Juan',
      ciudad: 'Monterrey',
      toString: () => `{ Nombre: ${persona.nombre}, edad: ${persona.edad}, ciudad: ${persona.ciudad} }`,
    }

    console.info('Instancia:', persona)
    console.info(`Atributo nombre: ${persona.nombre}`)
    console.info(`Atributo edad: ${persona['edad']}`)
    console.info(persona.toString())
    return persona
  }

  static arreglos() {
    let colores = ['rojo', 'azul', 'verde']
    let datos = [1, 2, 3, 4, 5, 'seis', App.instancia()]

    console.info('Colores:', colores)
    console.info('Datos:', datos)
  }

  static operadores() {
    // https://developer.mozilla.org/es/docs/Web/JavaScript/Reference/Operators/Nullish_coalescing
    const a = 10
    const b = 3
    const bb = '3'
    console.info(`a = ${a}, b = ${b}`)
    // aritméticos
    console.info(`suma ${a} + ${b} = ${a + b}`)
    console.info(`resta ${a} - ${b} = ${a - b}`)
    console.info(`multiplicacion ${a} * ${b} = ${a * b}`)
    console.info(`division ${a} / ${b} = ${a / b}`)
    console.info(`modulo ${a} % ${b} = ${a % b}`)
    // de comparación
    console.warn(`b == bb Es debilmente Igual: ${b == bb}`)
    console.info(`b === bb Es fuertemente Igual: ${b === bb}`)
    console.info(`b !== bb Es fuertemente diferente: ${b !== bb}`)
    console.warn(`b != bb Es debilmente diferente: ${b != bb}`)
    console.info(`a != b Es Diferente: ${a != b}`)
    console.info(`a > b Es Mayor: ${a > b}`)
    console.info(`-a <= b Es Menor o igual: ${-a <= b}`)
    // lógicos
    console.info(`(a + b) < (a * b) && a === 10 = ${a + b < a * b && a === 10}`)
    console.info(`(a + b) < (a * b) || a === 10 = ${a + b < a * b && a === 10}`)
    console.info(`!((a + b) < (a * b)) || a === 10 = ${!(a + b < a * b) && a === 10}`)
  }

}

App.main()
