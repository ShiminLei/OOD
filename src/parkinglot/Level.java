package parkinglot;

/* Represents a level in a parking garage */
class Level {
    // Write your code here
    private int floor;
    private ParkingSpot[] spots;
    private int availableSpots = 0; // number of free spots
    private int SPOTS_PER_ROW;


    public Level(int flr, int num_rows, int spots_per_row) {
        floor = flr;
        int SPOTS_PER_ROW = spots_per_row;
        int numberSpots  = 0;
        spots = new ParkingSpot[num_rows * spots_per_row];

        //init size for each spot in array spots
        for (int row = 0; row < num_rows; ++row) {
            for (int spot = 0; spot < spots_per_row / 4; ++spot) {
                VehicleSize sz = VehicleSize.Motorcycle;
                spots[numberSpots] = new ParkingSpot(this, row, numberSpots, sz);
                numberSpots ++;
            }
            for (int spot = spots_per_row / 4; spot < spots_per_row / 4 * 3; ++spot) {
                VehicleSize sz = VehicleSize.Compact;
                spots[numberSpots] = new ParkingSpot(this, row, numberSpots, sz);
                numberSpots ++;
            }
            for (int spot = spots_per_row / 4 * 3; spot < spots_per_row; ++spot) {
                VehicleSize sz = VehicleSize.Large;
                spots[numberSpots] = new ParkingSpot(this, row, numberSpots, sz);
                numberSpots ++;
            }
        }

        availableSpots = numberSpots;
    }

    /* Try to find a place to park this vehicle. Return false if failed. */
    public boolean parkVehicle(Vehicle vehicle) {
        if (availableSpots() < vehicle.getSpotsNeeded()) {
            return false; // no enough spots
        }
        int spotNumber = findAvailableSpots(vehicle);
        if(spotNumber < 0) {
            return false;
        }
        return parkStartingAtSpot(spotNumber, vehicle);
    }

    /* find a spot to park this vehicle. Return index of spot, or -1 on failure. */
    private int findAvailableSpots(Vehicle vehicle) {
        int spotsNeeded = vehicle.getSpotsNeeded();
        int lastRow = -1;
        int spotsFound = 0;

        for(int i = 0; i < spots.length; i++){
            ParkingSpot spot = spots[i];
            if(lastRow != spot.getRow()){
                spotsFound = 0;
                lastRow = spot.getRow();
            }
            if(spot.canFitVehicle(vehicle)){
                spotsFound++;
            }else{
                spotsFound = 0;
            }
            if(spotsFound == spotsNeeded){
                return i - (spotsNeeded - 1); // index of spot
            }
        }
        return -1;
    }

    /* Park a vehicle starting at the spot spotNumber, and continuing until vehicle.spotsNeeded. */
    private boolean parkStartingAtSpot(int spotNumber, Vehicle vehicle) {
        vehicle.clearSpots();

        boolean success = true;

        for (int i = spotNumber; i < spotNumber + vehicle.spotsNeeded; i++) {
            success &= spots[i].park(vehicle);
        }

        availableSpots -= vehicle.spotsNeeded;
        return success;
    }

    /* When a car was removed from the spot, increment availableSpots */
    public void spotFreed() {
        availableSpots++;
    }

    public int availableSpots() {
        return availableSpots;
    }

    public void print() {
        int lastRow = -1;
        for (int i = 0; i < spots.length; i++) {
            ParkingSpot spot = spots[i];
            if (spot.getRow() != lastRow) {
                System.out.print("  ");
                lastRow = spot.getRow();
            }
            spot.print();
        }
    }
}
