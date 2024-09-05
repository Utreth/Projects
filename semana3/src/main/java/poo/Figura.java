package poo;

public abstract class Figura {

    protected String color;
    protected int[] lados;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int[] getLados() {
        return lados;
    }
    public void setLados(int... lados) {
        this.lados = lados;
    }

    public abstract double calcularArea();
}
