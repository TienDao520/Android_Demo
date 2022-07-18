package tdao.example.vehicle_oop;

import androidx.annotation.NonNull;

public class Motocycle extends Vehicle{

    private final int totalSeats;

    public Motocycle(String name, int speed, int maxSpeed, int maxFuelTank, int numberOfWheels, boolean hasAdvanceBrakeSystem, int totalSeats) {
        super(name, speed, maxSpeed, maxFuelTank, numberOfWheels, hasAdvanceBrakeSystem);
        this.totalSeats = totalSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s \n%s %s", super.toString(), "Total Seats: ", getTotalSeats());
    }

    public String sound() {
        return String.format("%s", "Motocycle Brrrrrrr");
    }
}
