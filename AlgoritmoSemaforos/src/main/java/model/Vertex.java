package model;

import Util.ProgramTraficLight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vertex {

    public int id;
    public int numberOfEdges;
    public List<ProgramTraficLight> programTraficLights = new ArrayList<ProgramTraficLight>();

    public int currentGreenLight;
    public ProgramTraficLight[] greenLightsCycle;
    public int lastCycle;
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

    /***
     * Init the Cycle for the TrafficLights, that fill an array with every second duration
     */
    public void initTrafficLightCycle()
    {
        int sum = programTraficLights.stream().mapToInt(ProgramTraficLight::getGreenSeconds).sum();
        greenLightsCycle = new ProgramTraficLight[sum];
        int nexPos = 0;
        for (var trafficLight :programTraficLights
             ) {
            Arrays.fill(greenLightsCycle, nexPos, nexPos+trafficLight.getGreenSeconds(), trafficLight);
            nexPos += trafficLight.getGreenSeconds();
        }

    }

}
