package tdao.example.vehicle_oop;

import androidx.annotation.NonNull;

public class Car extends Vehicle{

    private final String engineType;

    public Car(String name, int speed, int maxSpeed, int maxFuelTank, int numberOfWheels, boolean hasAdvanceBrakeSystem, String engineType) {
        super(name, speed, maxSpeed, maxFuelTank, numberOfWheels, hasAdvanceBrakeSystem);
        this.engineType = engineType;
    }

    public String getEngineType() {
        return engineType;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s \n%s %s", super.toString(), "Engine: ", getEngineType());
    }

    public String sound() {
        return String.format("%s", "Car Brrrrrrr");
    }
}
