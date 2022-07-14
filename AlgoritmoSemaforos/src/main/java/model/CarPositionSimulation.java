package model;

import java.util.Comparator;

public class CarPositionSimulation{
    public Car car;
    public int edgeNumber;
    public int timeGotHere;

    public CarPositionSimulation(model.Car car, int timeGotHere) {
        this.car = car;
        edgeNumber = 0;
        this.timeGotHere = timeGotHere;
    }

}
