package poo;

public abstract class Figura {
    protected String color;
    protected double base;
    protected double altura;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    //public double calcularArea(int ...lados){
    //    return Double.NEGATIVE_INFINITY;
    //}
    public abstract double calcularArea(int ...lados);
}
