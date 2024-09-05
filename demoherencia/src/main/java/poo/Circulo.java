package poo;

public class Circulo extends Figura {
    //lados[0] -> radio
    @Override
    public double calcularArea(int... lados) {
        return (Math.PI * Math.pow(lados[0], 2));
    }
}
