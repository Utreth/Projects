package poo.model;

public class FlashMemory extends StorageDevice {

    protected String typeUsb;

    public FlashMemory(String model, boolean wireless, double usedCapacity, double freeCapacity, double speed,
            String typeUsb) {
        super(model, wireless, usedCapacity, freeCapacity, speed);
        this.typeUsb = typeUsb;
    }

    public FlashMemory(String typeUsb) {
        this.typeUsb = typeUsb;
    }

    public String getTypeUsb() {
        return typeUsb;
    }

    public void setTypeUsb(String typeUsb) {
        this.typeUsb = typeUsb;
    }

    
}
