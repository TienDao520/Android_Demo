package tdao.example.vehicle_oop;

public class Vehicle {
    private String name;
    private int speed;
    private final int maxSpeed;
    private final int maxFuelTank;
    private int numberOfWheels;
    private final boolean hasAdvanceBrakeSystem;

    public Vehicle(String name, int speed, int maxSpeed, int maxFuelTank, int numberOfWheels, boolean hasAdvanceBrakeSystem) {
        this.name = name;
        this.speed = speed;
        this.maxSpeed = maxSpeed;
        this.maxFuelTank = maxFuelTank;
        this.numberOfWheels = numberOfWheels;
        this.hasAdvanceBrakeSystem = hasAdvanceBrakeSystem;
    }
}
