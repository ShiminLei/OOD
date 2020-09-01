package ood.parkinglot;

public enum VehicleSize {
    Compact(1),
    Large(2);

    int size;

    VehicleSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
