package Util;

import java.util.Arrays;

public class Edge {
    String name;
    Car[] carsInEdge;
    int origin;
    int destiny;
    Integer ligthTraficTime;
    Integer weigth;

    public Edge(String name, Car[] carsInEdge, int origin, int destiny, Integer ligthTraficTime, Integer weigth) {
        this.name = name;
        this.carsInEdge = carsInEdge;
        this.origin = origin;
        this.destiny = destiny;
        this.ligthTraficTime = ligthTraficTime;
        this.weigth = weigth;
    }

    @Override
    public String toString() {
        return  "Edge{" +
                "name='" + name + '\'' +
                ", carsInEdge=" + Arrays.toString(carsInEdge) +
                ", origin=" + origin +
                ", destiny=" + destiny +
                ", ligthTraficTime=" + ligthTraficTime +
                ", weigth=" + weigth +
                '}';
    }

    public void addCar()
    {

    }
}
