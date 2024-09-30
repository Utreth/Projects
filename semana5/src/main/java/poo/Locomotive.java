package poo;

public class Locomotive extends RailVehicle {

    private TypeEngine engine;

    public Locomotive(String id, String name, int numWheels, TypeCoupler coupler, TypeEngine engine) {

        super(id, name, numWheels, coupler);
        this.engine = engine;

    }

    public TypeEngine getEngine() {
        return engine;
    }

    public void setEngine(TypeEngine engine) {
        this.engine = engine;
    }

}
