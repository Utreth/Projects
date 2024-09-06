package poo;

public class Rombo extends Figura {
    // lados[0] -> diagonal mayor
    // lados[1] -> diagonal menor
    @Override
    public double calcularArea() {
        if (lados.length != 2) {
            throw new IllegalArgumentException("Ingrese un diagonal menor y un diagonal mayor par abtener el area de un rombo");
        }
        return ((lados[0] * lados[1])/2);
    }
}
