package poo;

public class Rectangulo extends Figura {
    @Override
    public double calcularArea() {
        return lados[0] * lados[1];
    }
}
