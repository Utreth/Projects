package poo;

public class Triangulo extends Figura {
    @Override
    public double calcularArea(int... lados) {
        return (lados[0] * lados[1]/2);
    }
}
