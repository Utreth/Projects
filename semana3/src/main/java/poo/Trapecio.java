package poo;

public class Trapecio extends Figura {
    
    @Override
    public double calcularArea() {
        if (lados.length != 3) {
            throw new IllegalArgumentException("Ingrese base mayor, base menor, y la altura para obtener el area de un trapecio");
        }
        return (((lados[0] + lados[1]) / 2) * lados[2]);
    }
}
