package poo.model;

public abstract class ComputerDevice {

    protected String model;
    protected boolean wireless = false;

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ComputerDevice(String model, boolean wireless) {
        this.model = model;
        this.wireless = wireless;

    }

    public ComputerDevice() {

    }

    public String getType(Computer c1) {

        TypeComputer tc1 = c1.getType();
        return tc1.toString();
    }

    @Override
    public String toString() {
        return "ComputerDevice [model=" + model + ", wireless=" + wireless + "]";
    }

}
