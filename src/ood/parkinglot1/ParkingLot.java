package ood.parkinglot1;

public class ParkingLot {
    private Level[] levels;
    private int NUM_LEVELS;

    /**
     * 有几层，每层几行，每行几个spot
     * @param num_levels
     * @param num_rows
     * @param spots_per_row
     */
    public ParkingLot(int num_levels, int num_rows, int spots_per_row) {
        // Write your code here
        NUM_LEVELS = num_levels;
        levels = new Level[NUM_LEVELS];
        for (int i = 0; i < NUM_LEVELS; i++) {
            levels[i] = new Level(i, num_rows, spots_per_row);
        }
    }

    /**
     * 把车停在停车场
     * @param vehicle
     * @return
     */
    public boolean parkVehicle(Vehicle vehicle) {
        // Write your code here
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].parkVehicle(vehicle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 把车开出停车场
     * @param vehicle
     */
    public void unParkVehicle(Vehicle vehicle) {
        // Write your code here
        vehicle.clearSpots();
    }

//    public void print() {
//        for (int i = 0; i < levels.length; i++) {
//            System.out.print("ood.parkinglot.Level" + i + ": ");
//            levels[i].print();
//            System.out.println("");
//        }
//        System.out.println("");
//    }
}