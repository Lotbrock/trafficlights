package Util;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    int id;
    int numberOfEdges;
    List<ProgramTraficLight> programTraficLights = new ArrayList<ProgramTraficLight>();
    public static void insertCar(Car car)
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    public List<ProgramTraficLight> getProgramTraficLights() {
        return programTraficLights;
    }

    public void setProgramTraficLights(List<ProgramTraficLight> programTraficLights) {
        this.programTraficLights = programTraficLights;
    }

}
