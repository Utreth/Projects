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

    @Override
    public String toString() {
        return (new JSONObject(this)).toString(2);
    }

}
