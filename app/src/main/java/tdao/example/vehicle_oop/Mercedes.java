package tdao.example.vehicle_oop;

public class Mercedes extends Car{

    private final String seatColor;
    private final String roofTop;
    private String bodyColor;

    public Mercedes(String name, int speed, int maxSpeed, int maxFuelTank, int numberOfWheels, boolean hasAdvanceBrakeSystem, String engineType, String seatColor, String roofTop, String bodyColor) {
        super(name, speed, maxSpeed, maxFuelTank, numberOfWheels, hasAdvanceBrakeSystem, engineType);
        this.seatColor = seatColor;
        this.roofTop = roofTop;
        this.bodyColor = bodyColor;
    }

    public String getSeatColor() {
        return seatColor;
    }

    public String getRoofTop() {
        return roofTop;
    }

    public String getBodyColor() {
        return bodyColor;
    }

    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor;
    }
}
