package poo.model;

public class Mouse extends ComputerDevice {

    private int dpi;
    private int buttons;

    public Mouse(String model, boolean wireless, int dpi, int buttons) {
        super(model, wireless);
        this.dpi = dpi;
        this.buttons = buttons;
    }

    public Mouse(int dpi, int buttons) {
        this.dpi = dpi;
        this.buttons = buttons;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public int getButtons() {
        return buttons;
    }

    public void setButtons(int buttons) {
        this.buttons = buttons;
    }

    @Override
    public String toString() {
        return "Mouse [dpi=" + dpi + ", model=" + model + ", buttons=" + buttons + ", wireless=" + wireless + "]";
    }

}
