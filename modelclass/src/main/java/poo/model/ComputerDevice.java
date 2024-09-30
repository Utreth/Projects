package poo.model;

import org.json.JSONObject;

public abstract class ComputerDevice {

    protected String model;
    protected boolean wireless = false;

    public ComputerDevice() {

    }

    public ComputerDevice(String model, boolean wireless) {
        this.model = model;
        this.wireless = wireless;

    }

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

    public String getType() {

        return this.getClass().getSimpleName();

    }

    @Override
    public String toString() {

        return (new JSONObject(this)).toString(2);
    }

    

}
