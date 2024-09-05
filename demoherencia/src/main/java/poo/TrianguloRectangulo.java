package poo;

public class TrianguloRectangulo extends Figura {
    @Override
    public double calcularArea(int... lados) {
        return (lados[0] * lados[1]/2);
    }
}
