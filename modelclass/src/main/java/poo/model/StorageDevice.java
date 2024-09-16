package poo.model;

public abstract class StorageDevice extends ComputerDevice {

    protected double usedCapacity;
    protected double freeCapacity;
    protected double speed;

    public StorageDevice(String model, boolean wireless, double usedCapacity, double freeCapacity, double speed) {

        super(model, wireless);
        this.freeCapacity = freeCapacity;
        this.usedCapacity = usedCapacity;
        this.speed = speed;

    }

    public StorageDevice() {

    }

    public void setFreeCapacity(double freeCapacity) {
        this.freeCapacity = freeCapacity;
    }

    public double getFreeCapacity() {
        return freeCapacity;
    }

    public void setUsedCapacity(double usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public double getUsedCapacity() {
        return usedCapacity;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public String getType(Computer c1) {

        TypeComputer tc1 = c1.getType();
        return tc1.toString();
    }

    
    

}
