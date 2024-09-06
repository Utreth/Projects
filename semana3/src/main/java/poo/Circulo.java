package poo;

public class Circulo extends Figura {
    // lados[0] -> radio
    @Override
    public double calcularArea() {
        if (lados.length != 1) {
            throw new IllegalArgumentException("Para obtener el area del circulo digite solo 1 valor");

        }

        return (Math.PI * Math.pow(lados[0], 2));
    }
}
