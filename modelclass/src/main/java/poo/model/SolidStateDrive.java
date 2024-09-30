package poo.model;

public class SolidStateDrive extends StorageDevice {

    private String interType;

    public SolidStateDrive(String model, boolean wireless, double usedCapacity, double freeCapacity, double speed,
            String interType) {
        super(model, wireless, usedCapacity, freeCapacity, speed);
        this.interType = interType;
    }

    public SolidStateDrive(String interType) {
        this.interType = interType;
    }

    public String getInterType() {
        return interType;
    }

    public void setInterType(String interType) {
        this.interType = interType;
    }

   

}
