package model;

import java.util.Arrays;

public class Edge {
    public String name;
    public Car[] carsInEdge;
    public int origin;
    public  int destiny;
    public Integer ligthTraficTime;
    public Integer weigth;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car[] getCarsInEdge() {
        return carsInEdge;
    }

    public void setCarsInEdge(Car[] carsInEdge) {
        this.carsInEdge = carsInEdge;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestiny() {
        return destiny;
    }

    public void setDestiny(int destiny) {
        this.destiny = destiny;
    }

    public Integer getLigthTraficTime() {
        return ligthTraficTime;
    }

    public void setLigthTraficTime(Integer ligthTraficTime) {
        this.ligthTraficTime = ligthTraficTime;
    }

    public Integer getWeigth() {
        return weigth;
    }

    public void setWeigth(Integer weigth) {
        this.weigth = weigth;
    }

    public void addCar()
    {

    }
}
