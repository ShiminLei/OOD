package ood.parkinglot;

public class ParkingSpot {
    private final VehicleSize size;
    private Vehicle currentVehicle;

    public ParkingSpot(VehicleSize size) {
        this.size = size;
    }

    public boolean fit(Vehicle v){
        return currentVehicle==null && size.getSize()>=v.getSize().getSize();
    }

    public void park(Vehicle v){
        currentVehicle = v;
    }

    public void leave(){
        currentVehicle = null;
    }

    public Vehicle getVehicle(){
        return currentVehicle;
    }
}