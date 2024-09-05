package poo;

public class Trapecio extends Figura {
    // lados[0] -> base mayor
    // lados[1] -> base menor
    // lados[2] -> base altura
    @Override
    public double calcularArea(int... lados) {
        return (((lados[0] + lados[1]) / 2) * lados[2]);
    }
}
