package poo;

public class Rectangulo extends Figura {
    @Override
    public double calcularArea() {
        if (lados.length != 2) {
            throw new IllegalArgumentException("Para obtener el area de un rectangulo ingrese dos de sus lados");
        }
        return lados[0] * lados[1];
    }
}
