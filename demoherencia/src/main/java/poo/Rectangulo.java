package poo;

public class Rectangulo extends Figura{
    @Override
    public double calcularArea(int... lados) {
        return lados[0]*lados[1];
    }
    }
