package poo;

public class GoodWagons extends RailRoadCar {

    private TypeLoad load;

    public GoodWagons(String id, String name, int numWheels, TypeCoupler coupler, TypeSpeed speed, TypeLoad load) {

        super(id, name, numWheels, coupler, speed);
        this.load = load;

    }

    public TypeLoad getLoad() {
        return load;
    }

    public void setLoad(TypeLoad load) {
        this.load = load;
    }

}
