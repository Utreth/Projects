package poo;

import java.util.ArrayList;

import org.json.JSONObject;

public class Train {

    private String id;
    private ArrayList<RailVehicle> railVehicles = new ArrayList<>();

    public Train() {

    }

    public Train(String id, ArrayList<RailVehicle> railVehicles) {

        this.id = id;
        this.railVehicles = railVehicles;
    }

    public String getId() {
        return id;
    }

    public ArrayList<RailVehicle> getRailVehicles() {
        return railVehicles;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRailVehicles(ArrayList<RailVehicle> railVehicles) {
        this.railVehicles = railVehicles;
    }

    public int getFullSeats() {

        int fullSeats = 0;

        for (RailVehicle j : railVehicles) {
            if (j instanceof PassangerCar) {
                int seatsTo = ((PassangerCar) j).getNumSeats();
                int rawsTo = ((PassangerCar) j).getNumRows();

                fullSeats += seatsTo * rawsTo;
            }
        }
        return fullSeats;
    }

    public boolean isTrain() {

        int electrico = 0;
        int jaula = 0;
        int cisterna = 0;
        int carroPasajeros = 0;
        boolean esTren = false;

        for (RailVehicle wagons : railVehicles) {

            if (wagons instanceof Locomotive) {
                if (((Locomotive) wagons).getEngine() == TypeEngine.ELECTRICO) {
                    electrico += 1;
                }
            }

            if (wagons instanceof GoodWagons) {
                if (((GoodWagons) wagons).getLoad() == TypeLoad.JAULA) {
                    jaula += 1;
                }
                if (((GoodWagons) wagons).getLoad() == TypeLoad.CISTERNA) {
                    cisterna += 1;
                }
            }
            if (wagons instanceof PassangerCar) {
                
                carroPasajeros += 1;

            }

        }
        if (electrico == 1 && jaula == 1 && cisterna == 1 && carroPasajeros == 0) {
            esTren = true;
        }

        return esTren;
    }

    @Override
    public String toString() {
        return (new JSONObject(this)).toString(2);
    }

}
