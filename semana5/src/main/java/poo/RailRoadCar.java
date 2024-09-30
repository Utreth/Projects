package poo;

public abstract class RailRoadCar extends RailVehicle {

    private TypeSpeed speed;

    public RailRoadCar(String id, String name, int numWheels, TypeCoupler coupler, TypeSpeed speed) {

        super(id, name, numWheels, coupler);
        this.speed = speed;

    }

    public TypeSpeed getSpeed() {
        return speed;
    }

    public void setSpeed(TypeSpeed speed) {
        this.speed = speed;
    }

}