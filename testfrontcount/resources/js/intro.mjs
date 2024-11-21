export default class Intro {
  static #PI = 3.141592653589793
  static URL_API = 'http://localhost:7070'

  static variables() {
    // variables y tipos de datos
    let cantidadProductos = 10
    let precioUnitario = 15.99
    let nombreProducto = 'Camisa'
    console.info(`nombre: ${nombreProducto}\ncantidad: ${cantidadProductos}\nprecio $${precioUnitario}`)

    console.info(`PI: ${Intro.#PI}\nULR API: ${Intro.URL_API}`)

    let esEstudiante = true
    let tieneDescuento = false
    console.info(`Es estudiante: ${esEstudiante}\ntiene descuento: ${tieneDescuento ? 'Si' : 'No'}`)
    // ..
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
    return persona
  }

  static arreglos() {
    let colores = ['rojo', 'azul', 'verde']
    let datos = [1, 2, 3, 4, 5, 'seis', Intro.instancia()]

    console.info('Colores:', colores)
    console.info('Datos:', datos)
  }

  static operadores() {
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

  static estructurasDeControl() {
    let edad = 18
    if (edad >= 18) {
      console.log('Eres mayor de edad')
    } else {
      console.log('Eres menor de edad')
    }

    for (let i = 1; i <= 5; i++) {
      console.log(`Número: ${i}`)
    }

    let c = 0
    while (c < 5) {
      console.log(`Contador: ${c}`)
      c++
    }

    const persona = Intro.instancia()
    for (const propiedad in persona) {
      console.log(`${propiedad}: ${persona[propiedad]}`)
    }

    const dias = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo']
    const unDiaCualquiera = Math.floor(Math.random() * 8) // Debería ser hasta 7
    switch (unDiaCualquiera) {
      case 0:
        console.log('Lunes: Nuevo comienzo, nuevas oportunidades.')
        break
      case 1:
        console.log('Martes: La constancia es la clave del éxito.')
        break
      case 2:
        console.log('Miércoles: La mitad de semana ya pasó, ¡a celebrar!')
        break
      case 3:
        console.log('Jueves: Casi viernes, ¡aguanta un poco más!')
        break
      case 4:
        console.log('Viernes: Fin de semana a la vista, ¡disfrútalo!')
        break
      case 5:
        console.log('Sábado: Tiempo para ti, para recargar energías.')
        break
      case 6:
        console.log('Domingo: Reflexiona, agradece y prepárate para una nueva semana.')
        break
      default:
        console.log(`No existe ${unDiaCualquiera}`)
    }

    const valores = [1, 2, 3, 4, 5, 'seis', Intro.instancia()]
    const masValores = [...valores, 'Carolina', 'Jhon'] // OJO
    masValores.forEach(item => console.log(`Elemento: ${item}`))
  }

  static funcionesComoExpresiones() {
    Intro.saludar('PuebloPueblo de Orfalese')
    Intro.despedir('PuebloPueblo de Orfalese')

    const amigos = ['Ana', 'María', 'Laura', 'Sofía', 'Lucía', 'Juan', 'Carlos', 'Javier', 'Luis', 'Pedro', 'Elena']
    amigos.forEach(Intro.saludar)
  }

  static saludar = function (nombre) {
    console.log(`¡Hola, ${nombre}!`)
  }

  static despedir = nombre => console.log(`Adios, ${nombre}!`)
}
