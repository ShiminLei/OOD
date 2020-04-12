package ood.parkinglot;

import java.util.ArrayList;

//abstract ood.parkinglot.Vehicle class
abstract class Vehicle {
    // Write your code here
    protected int spotsNeeded;
    protected VehicleSize size;
    protected String licensePlate;  // id for a vehicle

    protected ArrayList<ParkingSpot> parkingSpots = new ArrayList<>(); // id for parking where may occupy multi

    public int getSpotsNeeded() {
        return spotsNeeded;
    }

    public VehicleSize getSize() {
        return size;
    }

    /* Park vehicle in this spot (among others, potentially) */
    public void parkInSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
    }

    /* Remove car from spot, and notify spot that it's gone */
    public void clearSpots() {
        for (int i = 0; i < parkingSpots.size(); i++) {
            parkingSpots.get(i).removeVehicle();
        }
        parkingSpots.clear();
    }
    //this need to be implement in subclass
    public abstract boolean canFitInSpot(ParkingSpot spot);
    public abstract void print();
}
