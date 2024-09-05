package poo;

import java.util.ArrayList;

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
        lf.add(circulo);

        Cuadrado cuadrado = new Cuadrado();
        cuadrado.setColor("amarillo");
        lf.add(cuadrado);

        Paralelogramo paralelogramo = new Paralelogramo();
        paralelogramo.setColor("Rojo");
        lf.add(paralelogramo);

        Pentagono pentagono = new Pentagono();
        pentagono.setColor("Azul");
        lf.add(pentagono);

        Rectangulo rectangulo = new Rectangulo();
        rectangulo.setColor("Cyan");
        lf.add(rectangulo);

        Rombo rombo = new Rombo();
        rombo.setColor("Cafe");
        lf.add(rombo);

        Trapecio trapecio = new Trapecio();
        trapecio.setColor("Rosado");
        lf.add(trapecio);

        TrianguloRectangulo trianguloRectangulo = new TrianguloRectangulo();
        trianguloRectangulo.setColor("Negro");
        lf.add(trianguloRectangulo);

        for (Figura f : lf){

            System.out.println("\n" + f.getClass().getSimpleName() + " el color es " + f.getColor());
            
        }







        

    }
}
