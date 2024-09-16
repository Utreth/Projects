package poo.model;

public class HardDiskDrive extends StorageDevice {

    private int rmp;
    private double inches;

    public HardDiskDrive(String model, boolean wireless, double usedCapacity, double freeCapacity, double speed,
            int rmp, double inches) {
        super(model, wireless, usedCapacity, freeCapacity, speed);
        this.rmp = rmp;
        this.inches = inches;
    }

    public HardDiskDrive(int rmp, double inches) {
        this.rmp = rmp;
        this.inches = inches;
    }

    public int getRmp() {
        return rmp;
    }

    public void setRmp(int rmp) {
        this.rmp = rmp;
    }

    public double getInches() {
        return inches;
    }

    public void setInches(double inches) {
        this.inches = inches;
    }

    @Override
    public String toString() {
        String strWireless = wireless ? "SI" : "NO";
        return "\nHardDiskDrive [Modelo= " + model + ", RMP= " + rmp + ", Capacidad usada= " + usedCapacity + " GB"
                + ", Inalambrico= "
                + strWireless + ", Pulgadas=" + inches + ", Capacidad libre= " + freeCapacity + " GB" + ", Velocidad= "
                + speed;
    }

}
