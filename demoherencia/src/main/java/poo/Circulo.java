package poo;

public class Circulo extends Figura {
    // lados[0] -> radio
    @Override
    public double calcularArea(int... lados) {
        if (lados.length != 1) {
            throw new IllegalArgumentException("Digite un solo dato");

        }

        return (Math.PI * Math.pow(lados[0], 2));
    }
}
