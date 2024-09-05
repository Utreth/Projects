package poo;

public class Cuadrado extends Figura {
    @Override
    public double calcularArea(int... lados) {
        return Math.pow(lados[0], 2);
    }
}
