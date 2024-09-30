package poo.model;

public class Keyboard extends ComputerDevice {

    private boolean gamer;
    private boolean integrated;
    private TypeLanguage language;

    public Keyboard(String model, boolean wireless, boolean gamer, boolean integrated, TypeLanguage language) {
        super(model, wireless);
        this.gamer = gamer;
        this.integrated = integrated;
        this.language = language;
    }

    public Keyboard(boolean gamer, boolean integrated, TypeLanguage language) {
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

    public TypeLanguage getLanguage() {
        return language;
    }

    public void setLanguage(TypeLanguage language) {
        this.language = language;
    }

   

}
