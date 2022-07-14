package model;

public class CarPositionSimulation {
    public Car Car;
    public int StreetNumber;
    public int TimeGotHere;

    public CarPositionSimulation(model.Car car, int timeGotHere) {
        Car = car;
        StreetNumber = 0;
        TimeGotHere = timeGotHere;
    }
}
