package parkinglot;

class Car extends Vehicle {
    // Write your code here
    public Car() {
        spotsNeeded = 1;
        size = VehicleSize.Compact;
    }

    public boolean canFitInSpot(ParkingSpot spot) {
        return spot.getSize() == VehicleSize.Large || spot.getSize() == VehicleSize.Compact;
    }

    public void print() {
        System.out.print("C");
    }
}
