package poo;

public class Pentagono extends Figura {
    //lados[0] -> perimetro
    //lados[1] -> apotema
    @Override
    public double calcularArea() {
        if (lados.length != 2) {
            throw new IllegalArgumentException("Ingrese perimetro y apotema para obtener el area de un pentagono");
        }
        return ((lados[0]*lados[1])/2);
    }
}
