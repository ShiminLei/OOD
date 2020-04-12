package ood.parkinglot;

public class ParkingLot {
    private Level[] levels;
    private int NUM_LEVELS;

    // @param n number of leves
    // @param num_rows  each level has num_rows rows of spots
    // @param spots_per_row each row has spots_per_row spots
    public ParkingLot(int n, int num_rows, int spots_per_row) {
        // Write your code here
        NUM_LEVELS = n;
        levels = new Level[NUM_LEVELS];
        for (int i = 0; i < NUM_LEVELS; i++) {
            levels[i] = new Level(i, num_rows, spots_per_row);
        }
    }

    // Park the vehicle in a spot (or multiple spots)
    // Return false if failed
    public boolean parkVehicle(Vehicle vehicle) {
        // Write your code here
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].parkVehicle(vehicle)) {
                return true;
            }
        }
        return false;
    }

    // unPark the vehicle
    public void unParkVehicle(Vehicle vehicle) {
        // Write your code here
        vehicle.clearSpots();
    }

    public void print() {
        for (int i = 0; i < levels.length; i++) {
            System.out.print("ood.parkinglot.Level" + i + ": ");
            levels[i].print();
            System.out.println("");
        }
        System.out.println("");
    }
}