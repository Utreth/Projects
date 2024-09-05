package poo;

public class App {
    public static void main(String[] args) {
        /*NOTA: Figura f = new Figura(); ERROR
        * Al ser figura una clase abstracta, no se puede instanciar.
        * System.out.println(f.calcularArea(1,2,3,4,5));*/
        Cuadrado c = new Cuadrado();
        System.out.println("Area del cuadrado: " + c.calcularArea(10));
        Rectangulo r = new Rectangulo();
        System.out.println("El area del rectangulo: " + r.calcularArea(10, 20));
        Figura c2 = new Cuadrado();
        System.out.println(c2.calcularArea(2));
        Paralelogramo p = new Paralelogramo();
        System.out.println("El area del paralelogramo es: " + p.calcularArea(2, 4));
        Rombo rom = new Rombo();
        System.out.println("El área del rombo es: " + rom.calcularArea(5, 15));
        Trapecio t = new Trapecio();
        System.out.println("El área del trapecio es: " + t.calcularArea(8, 6, 4));
        Pentagono pentagono = new Pentagono();
        System.out.println("El área del pentágono es: " + pentagono.calcularArea(3, 2));
        Circulo circulo = new Circulo();
        System.out.println("Área del círculo es: " + circulo.calcularArea(5));
    }
}
