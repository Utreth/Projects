package poo;

public class Paralelogramo extends Figura {
    // base posicion 0 del arrego , altura posicion 1 del arreglo
    @Override
    public double calcularArea(int... lados) {
        return (lados[0] * lados[1]);
    }
}
