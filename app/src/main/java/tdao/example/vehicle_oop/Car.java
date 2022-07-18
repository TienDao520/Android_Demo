package tdao.example.vehicle_oop;

public class Car extends Vehicle{

    private final String engineType;

    public Car(String name, int speed, int maxSpeed, int maxFuelTank, int numberOfWheels, boolean hasAdvanceBrakeSystem, String engineType) {
        super(name, speed, maxSpeed, maxFuelTank, numberOfWheels, hasAdvanceBrakeSystem);
        this.engineType = engineType;
    }
}
