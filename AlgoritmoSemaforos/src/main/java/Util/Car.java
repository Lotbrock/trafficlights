package Util;

import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Date;

public class Car {
    String[] route;
    double initTime;
    double endTime;

    public Car(String[] route, double initTime, double endTime) {
        this.route = route;
        this.initTime = initTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Car{" +
                "route=" + Arrays.toString(route) +
                ", initTime=" + initTime +
                ", endTime=" + endTime +
                '}';
    }
}
