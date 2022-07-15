package Util;

import model.Car;
import model.CarPositionSimulation;
import model.Edge;
import model.Vertex;

import java.util.*;

import static Util.Constants.*;
import static Util.Constants.simulationTime;
import static Util.Utilities.sortedArray;

public class Program {
    public static void optimizeHillClimbing(Iterable<Car> cars, Collection<Edge> edges){
        int lastScore = simulate(cars,edges);
        System.out.println("first Score " +lastScore);
        var maxTrafficLights = programVertexList.stream().max(Comparator.comparing(e -> e.programTraficLights.size())).orElseThrow().programTraficLights.size();

        while (true)
        {
            System.out.println("again");
            //try to find a new best solution changing the traffic lights order
            optimizeByChangingTrafficLightOrder(cars,edges,maxTrafficLights);
            System.out.println("new Score: ");
        }
    }
    public static void optimizeByChangingTrafficLightOrder(Iterable<Car> cars, Collection<Edge> edges, int maxPos)
    {
        int actualBestScore = simulate(cars, edges);
        for (int j= 0; j < programVertexList.size(); j++) {
            int loops = Math.min(maxPos, programVertexList.toArray(Vertex[]::new)[j].programTraficLights.size());
            for (int pos2 = 1; pos2 < loops; pos2++) {
                for (int pos1 = 0; pos1 < pos2; pos1++) {
                    var oldProgramVertexList = programVertexList;
                    Collections.swap(programVertexList.toArray(Vertex[]::new)[j].programTraficLights,pos1,pos2);
                    int newScore = simulate(cars, edges);
                    if (newScore < actualBestScore)
                    {
                        programVertexList = oldProgramVertexList;
                    }
                    else {
                        actualBestScore = newScore;
                    }

                }
            }
        }
    }
    public static int simulate(Iterable<Car> cars, Collection<Edge> edges)
    {
        int score = 0;
        int currentTime = 0;
        List<CarPositionSimulation> carsPositionSimulation = new ArrayList<>();
        var comparator = new Comparator<CarPositionSimulation>() {
            @Override
            public int compare(CarPositionSimulation o1, CarPositionSimulation o2) {
                if (o1.timeGotHere > o2.timeGotHere) {
                    return 1;
                } else if (o1.timeGotHere < o2.timeGotHere) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        int simulationCarIndexStart = -(numOfCars);
        //init car positions in simulation
        for (Car car: cars
        ) {
            carsPositionSimulation.add(new CarPositionSimulation(car, simulationCarIndexStart));
            simulationCarIndexStart++;
        }

        //Init trafficLights
        for (var promagramVertex: programVertexList
        ) {
            promagramVertex.initTrafficLightCycle();
            promagramVertex.lastCycle = -1;

        }

        while(currentTime <= simulationTime){

            for (int i = 0; i < carsPositionSimulation.size();i++)
            {
                var carSimulate = carsPositionSimulation.get(i);
                if(carSimulate.timeGotHere >= currentTime)
                    continue;

                var edgeName = carSimulate.car.route[carSimulate.edgeNumber];
                Edge edge = edges.stream().filter(e -> e.name.equals(edgeName)).findFirst().get();

                var vertex = programVertexList.stream().filter(e->e.getId() == edge.destiny).findFirst().get();
                if(vertex.lastCycle >= currentTime)
                    continue;

                //if is the first trafficLight continue to the next
                if (vertex.greenLightsCycle.length == 0)
                    continue;


                var currentTrafficLight = vertex.greenLightsCycle[currentTime % vertex.greenLightsCycle.length];

                //if the trafficLigth is not green, move to the next car
                if (!edge.name.equals(currentTrafficLight.streetName))
                    continue;
                vertex.lastCycle = currentTime;

                if(carSimulate.edgeNumber == carSimulate.car.route.length-1)
                {
                    carSimulate.timeGotHere = currentTime + edge.weigth;
                    score += bonusPerCar +(simulationTime-carSimulate.timeGotHere);
                    carsPositionSimulation.remove(i);
                    continue;
                }

                //move the car to the next edge

                carSimulate.edgeNumber++;
                var newEdgeName = carSimulate.car.route[carSimulate.edgeNumber];
                Edge newEdge = edges.stream().filter(e -> e.name.equals(newEdgeName)).findFirst().get();

                //add way time of car
                carSimulate.timeGotHere = currentTime + newEdge.weigth;
                carsPositionSimulation.remove(i);
                i--;
                //validate if car finished
                if(carSimulate.edgeNumber == carSimulate.car.route.length-1)
                {
                    if(carSimulate.timeGotHere <= simulationTime){
                        score += bonusPerCar +(simulationTime-carSimulate.timeGotHere);
                    }

                }else {
                    //compare the simulation time for every car and sort the list in base in it
                    sortedArray(carsPositionSimulation,carSimulate,comparator);

                    //dont works, its necessary do a new sort method
//                        carsPositionSimulation.sort(comparator);
                }
            }
            currentTime++;
        }
        //System.out.println(score);
        return score;
    }
}
