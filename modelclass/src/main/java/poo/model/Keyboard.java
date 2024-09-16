package poo.model;

public class Keyboard extends ComputerDevice {

    private boolean gamer;
    private boolean integrated;
    private Language language;

    public Keyboard(String model, boolean wireless, boolean gamer, boolean integrated, Language language) {
        super(model, wireless);
        this.gamer = gamer;
        this.integrated = integrated;
        this.language = language;
    }

    public Keyboard(boolean gamer, boolean integrated, Language language) {
        this.gamer = gamer;
        this.integrated = integrated;
        this.language = language;
    }

    public boolean isGamer() {
        return gamer;
    }

    public void setGamer(boolean gamer) {
        this.gamer = gamer;
    }

    public boolean isIntegrated() {
        return integrated;
    }

    public void setIntegrated(boolean integrated) {
        this.integrated = integrated;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Keyboard [model=" + model + ", gamer=" + gamer + ", wireless=" + wireless + ", integrated=" + integrated
                + ", language=" + language + "]";
    }

}
