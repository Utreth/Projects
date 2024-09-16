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

    @Override
    public String toString() {
        String strWireless = wireless ? "SI" : "NO";
        return "\nSolidStateDrive= [Modelo= " + model + ", Intertipo= " + interType + ", Capacidad usada= "
                + usedCapacity
                + ", Inalambrico= " + strWireless + ", Capacidad libre= " + freeCapacity + ", Velocidad= " + speed;
    }

}
