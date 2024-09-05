package poo;

public class Rombo extends Figura {
    // lados[0] -> diagonal mayor
    // lados[1] -> diagonal menor
    @Override
    public double calcularArea() {
        return ((lados[0] * lados[1])/2);
    }
}
