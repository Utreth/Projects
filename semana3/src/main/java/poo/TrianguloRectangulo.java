package poo;

public class TrianguloRectangulo extends Figura {
    @Override
    public double calcularArea() {
        if (lados.length != 2) {
            throw new IllegalArgumentException("Ingrese dos lados");
        }
        return (lados[0] * lados[1]/2);
    }
}
