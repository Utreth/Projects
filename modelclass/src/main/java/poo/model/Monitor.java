package poo.model;

public class Monitor extends ComputerDevice {

    private double inches;

    public Monitor(String model, boolean wireless, double inches) {
        super(model, wireless);
        this.inches = inches;
    }

    public Monitor(double inches) {
        this.inches = inches;
    }

    public double getInches() {
        return inches;
    }

    public void setInches(double inches) {
        this.inches = inches;
    }


}
