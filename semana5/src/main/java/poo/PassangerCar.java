package poo;

public class PassangerCar extends RailRoadCar {
    
    private int numSeats;
    private int numRows;

    public PassangerCar(String id, String name, int numWheels, TypeCoupler coupler, TypeSpeed speed, int numSeats,
            int numRows) {

        super(id, name, numWheels, coupler, speed);
        this.numSeats = numSeats;
        this.numRows = numRows;

    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

}
