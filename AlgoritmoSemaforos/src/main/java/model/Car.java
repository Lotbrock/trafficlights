package model;

import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Date;

public class Car {
    public String[] route;
    public double initTime;
    public double endTime;
    public int numberOfEdges;
    public boolean endRoute = false;

    public Car(String[] route, double initTime, double endTime, int numberOfEdges) {
        this.route = route;
        this.initTime = initTime;
        this.endTime = endTime;
        this.numberOfEdges = numberOfEdges;
    }

    @Override
    public String toString() {
        return "Car{" +
                "route=" + Arrays.toString(route) +
                ", initTime=" + initTime +
                ", endTime=" + endTime +
                ", numberOfEdges=" + numberOfEdges +
                '}';
    }
}
