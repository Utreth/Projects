package poo;

public class Cuadrado extends Figura {
    @Override
    public double calcularArea() {
        if (lados.length != 1) {
            throw new IllegalArgumentException("Para obtener el area del cuadrado digite 1 lado");
        }
        return Math.pow(lados[0], 2);
    }
}
