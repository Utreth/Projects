package poo;

public class Cuadrado extends Figura {
    @Override
    public double calcularArea() {
        if (lados.length != 1) {
            throw new IllegalArgumentException("Tiene que ingresar al menos un dato");
        }
        return Math.pow(lados[0], 2);
    }
}
