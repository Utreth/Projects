package poo;

public class Pentagono extends Figura {
    //lados[0] -> perimetro
    //lados[1] -> apotema
    @Override
    public double calcularArea(int... lados) {
        return ((lados[0]*lados[1])/2);
    }
}
