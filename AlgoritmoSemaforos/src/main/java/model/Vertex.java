package model;

import Util.ProgramTraficLight;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    public int id;
    public int numberOfEdges;
    public List<ProgramTraficLight> programTraficLights = new ArrayList<ProgramTraficLight>();

    public int currentGreenLight;

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
