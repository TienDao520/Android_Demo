package tdao.example.vehicle_oop;

import androidx.annotation.NonNull;

public class Vehicle extends Object{
    private String name;
    private int speed;
    private final int maxSpeed;
    private final int maxFuelTank;
    private int numberOfWheels;
    private final boolean hasAdvanceBrakeSystem;

    /****
     * Constructor
     * @param name
     * @param speed
     * @param maxSpeed
     * @param maxFuelTank
     * @param numberOfWheels
     * @param hasAdvanceBrakeSystem
     */
    public Vehicle(String name, int speed, int maxSpeed, int maxFuelTank, int numberOfWheels, boolean hasAdvanceBrakeSystem) {
        this.name = name;
        this.speed = speed;
        this.maxSpeed = maxSpeed;
        this.maxFuelTank = maxFuelTank;
        this.numberOfWheels = numberOfWheels;
        this.hasAdvanceBrakeSystem = hasAdvanceBrakeSystem;
    }

    /****
     * Getter & Setter
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxFuelTank() {
        return maxFuelTank;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public boolean isHasAdvanceBrakeSystem() {
        return hasAdvanceBrakeSystem;
    }
    /*****Getter & Setter ***** EMD *****/

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s \n%s %s \n%s %s \n%s %s \n%s %s \n%s %s",
                "Name: ", getName(),
                "Speed: ", getSpeed(),
                "Max Speed: ", getMaxSpeed(),
                "Fuel Tank: ", getMaxFuelTank(),
                "Total wheels", getNumberOfWheels(),
                "ABS: ", isHasAdvanceBrakeSystem()
                );
    }

    public String sound() {
        return String.format("%s", "Brrrrrrr");
    }
}
