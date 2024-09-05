package poo;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.crypto.dsig.TransformException;

public class App {
    public static void main(String[] args) {

        /*
         * NOTA: Figura f = new Figura(); ERROR
         * Al ser figura una clase abstracta, no se puede instanciar.
         * System.out.println(f.calcularArea(1,2,3,4,5));
         */

        ArrayList<Figura> lf = new ArrayList<>();
        Circulo circulo = new Circulo();
        circulo.setColor("verde");
        circulo.setLados(237);
        lf.add(circulo);

        Cuadrado cuadrado = new Cuadrado();
        cuadrado.setColor("amarillo");
        cuadrado.setLados(45);
        lf.add(cuadrado);

        Paralelogramo paralelogramo = new Paralelogramo();
        paralelogramo.setColor("Rojo");
        paralelogramo.setLados(23, 56);
        lf.add(paralelogramo);

        Pentagono pentagono = new Pentagono();
        pentagono.setColor("Azul");
        pentagono.setLados(60, 70);
        lf.add(pentagono);

        Rectangulo rectangulo = new Rectangulo();
        rectangulo.setColor("Cyan");
        rectangulo.setLados(10, 20);
        lf.add(rectangulo);

        Rombo rombo = new Rombo();
        rombo.setColor("Cafe");
        rombo.setLados(20, 2);
        lf.add(rombo);

        Trapecio trapecio = new Trapecio();
        trapecio.setColor("Rosado");
        trapecio.setLados(10, 20, 20);
        lf.add(trapecio);

        TrianguloRectangulo trianguloRectangulo = new TrianguloRectangulo();
        trianguloRectangulo.setColor("Negro");
        trianguloRectangulo.setLados(5, 8);
        lf.add(trianguloRectangulo);

        for (Figura f : lf) {

            System.out.println("\n" + f.getClass().getSimpleName() + " de color " + f.getColor());
            System.out.println("El area es: " + f.calcularArea());

        }

        // argumento por valor
        int x = 10;
        hacerAlgo(x);
        System.out.println(x);

        // argumento por referencia
        int[] a = { 1, 2, 3, 4 };
        hacerAlgo2(a);
        System.out.println(Arrays.toString(a));

    }

    private static void hacerAlgo(int x) {
        x = 20;
    }

    private static void hacerAlgo2(int... a) {

        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * 10;
        }
    }
}
