package poo;

public class Paralelogramo extends Figura {
    // base posicion 0 del arreglo , altura posicion 1 del arreglo
    @Override
    public double calcularArea() {
        if (lados.length != 2 ) {
            throw new IllegalArgumentException("Para obtener el area del paralelogramo digite al menos 2 lados");
        }
        
        return (lados[0] * lados[1]);
    }
}
