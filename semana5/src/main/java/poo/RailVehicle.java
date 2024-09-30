package poo;

public abstract class RailVehicle {

    protected String id;
    protected String name;
    protected int numWheels;
    protected TypeCoupler coupler;

    public RailVehicle() {

    }

    public RailVehicle(String id, String name, int numWheels, TypeCoupler coupler) {
        this.id = id;
        this.name = name;
        this.numWheels = numWheels;
        this.coupler = coupler;
    }

    public TypeCoupler getCoupler() {
        return coupler;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumWheels() {
        return numWheels;
    }

    public void setCoupler(TypeCoupler coupler) {
        this.coupler = coupler;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumWheels(int numWheels) {
        this.numWheels = numWheels;
    }

    public String getType() {

        return this.getClass().getSimpleName();

    }

}
