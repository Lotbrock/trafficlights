package Util;

import model.Car;
import model.CarPositionSimulation;
import model.Edge;
import model.Vertex;

import java.util.*;

import static Util.Constants.*;
import static Util.Utilities.sortedArray;

public class Program {


    public static void optimizeHillClimbing(Iterable<Car> cars, Collection<Edge> edges, Collection<Vertex> programVertexList) {
        int lastScore = simulate(cars, edges, programVertexList);
        Collection<Vertex> newProgramList = programVertexList;
        System.out.println("first Score " + lastScore);
        var maxTrafficLights = programVertexList.stream().max(Comparator.comparing(e -> e.programTraficLights.size())).orElseThrow().programTraficLights.size();

        while (true) {
            System.out.println("again");
            //try to find a new best solution changing the traffic lights order
            newProgramList = optimizeByChangingTrafficLightOrder(cars, edges, maxTrafficLights, newProgramList.toArray(Vertex[]::new));
            //System.out.println("aqui debe llegar el max "+simulate(cars,edges,programVertexListFinal));
            //Change the greenLight time for every traffic light, it can plus or less the value delta to green light time
            for (int delta = 1; delta <= 3; delta++) {
                newProgramList = optimizeByChangingTrafficLightGreenSeconds(cars, edges, maxTrafficLights, newProgramList.toArray(Vertex[]::new), delta);
                newProgramList = optimizeByChangingTrafficLightGreenSeconds(cars, edges, maxTrafficLights, newProgramList.toArray(Vertex[]::new), -delta);
            }

            int score = simulate(cars, edges, newProgramList);
            loops++;
            System.out.println("loop: " + loops + " score: " + score);
            if (lastScore <= score) {
                break;
            }
            if (loops > toleranceLoops) {
                break;
            } else {
                lastScore = score;
            }
        }
        programVertexListFinal = programVertexList = newProgramList;
    }

    public static Collection<Vertex> optimizeByChangingTrafficLightGreenSeconds(Iterable<Car> cars, Collection<Edge> edges, int maxPos, Vertex[] programVertexList, int delta) {
        int actualBestScore = simulate(cars, edges, Arrays.asList(programVertexList));

        for (int i = 0; i < programVertexList.length; i++) {
            int loops = Math.min(maxPos, programVertexList[i].programTraficLights.size());
            for (int pos = 0; pos < loops; pos++) {
                var newProgramVertexList = programVertexList;
                newProgramVertexList[i].programTraficLights.toArray(ProgramTraficLight[]::new)[pos].greenSeconds += delta;

                if (newProgramVertexList[i].programTraficLights.toArray(ProgramTraficLight[]::new)[pos].greenSeconds < 0) {
                    continue;
                }
                int newScore = simulate(cars, edges, Arrays.asList(newProgramVertexList));
                if ((newScore > actualBestScore) || ((newScore == actualBestScore) && (delta < 0))) {
                    programVertexList = newProgramVertexList;
                    actualBestScore = newScore;
//                    System.out.println("partialNewScore with change in green light "+ newScore);
                }
            }
        }
        return Arrays.asList(programVertexList);
    }

    public static Collection<Vertex> optimizeByChangingTrafficLightOrder(Iterable<Car> cars, Collection<Edge> edges, int maxPos, Vertex[] programVertexList) {
        int actualBestScore = simulate(cars, edges, Arrays.asList(programVertexList));
        int maxLoopsWithoutNewScore = 0;
        for (int j = 0; j < programVertexList.length; j++) {
            int loops = Math.min(maxPos, programVertexList[j].programTraficLights.size());
            for (int pos2 = 1; pos2 < loops; pos2++) {
                for (int pos1 = 0; pos1 < pos2; pos1++) {
                    var oldProgramVertexList = programVertexList.clone();
                    var a = oldProgramVertexList[j].programTraficLights;
                    //change the order
                    Collections.swap(a, pos1, pos2);
                    oldProgramVertexList[j].programTraficLights = a;
                    int newScore = simulate(cars, edges, Arrays.asList(oldProgramVertexList));
                    if (newScore > actualBestScore) {
                        System.out.println("partialNewScore " + newScore);
                        programVertexList = oldProgramVertexList;
                        programVertexListFinal = Arrays.asList(oldProgramVertexList);
                        actualBestScore = newScore;
                        maxLoopsWithoutNewScore = 0;
                    } else {
                        maxLoopsWithoutNewScore++;
                    }
                    if (maxLoopsWithoutNewScore > 15) {
                        return Arrays.asList(programVertexList);
                    }
                }
            }
        }
        System.out.println("new Score with swap: " + actualBestScore);
        return Arrays.asList(programVertexList);
    }

    public static int simulate(Iterable<Car> cars, Collection<Edge> edges, Collection<Vertex> programVertexList) {
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
        for (Car car : cars) {
            carsPositionSimulation.add(new CarPositionSimulation(car, simulationCarIndexStart));
            simulationCarIndexStart++;
        }

        //Init trafficLights
        for (var promagramVertex : programVertexList) {
            promagramVertex.initTrafficLightCycle();
            promagramVertex.lastCycle = -1;

        }

        while (currentTime <= simulationTime) {

            for (int i = 0; i < carsPositionSimulation.size(); i++) {
                var carSimulate = carsPositionSimulation.get(i);
                if (carSimulate.timeGotHere >= currentTime) {
                    continue;
                }

                var edgeName = carSimulate.car.route[carSimulate.edgeNumber];
                Edge edge = edges.stream().filter(e -> e.name.equals(edgeName)).findFirst().get();

                var vertex = programVertexList.stream().filter(e -> e.getId() == edge.destiny).findFirst().get();
                if (vertex.lastCycle >= currentTime) {
                    continue;
                }

                //if is the first trafficLight continue to the next
                if (vertex.greenLightsCycle.length == 0) {
                    continue;
                }

                var currentTrafficLight = vertex.greenLightsCycle[currentTime % vertex.greenLightsCycle.length];

                //if the trafficLigth is not green, move to the next car
                if (!edge.name.equals(currentTrafficLight.streetName)) {
                    continue;
                }
                vertex.lastCycle = currentTime;

                if (carSimulate.edgeNumber == carSimulate.car.route.length - 1) {
                    carSimulate.timeGotHere = currentTime + edge.weigth;
                    score += bonusPerCar + (simulationTime - carSimulate.timeGotHere);
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
                if (carSimulate.edgeNumber == carSimulate.car.route.length - 1) {
                    if (carSimulate.timeGotHere <= simulationTime) {
                        score += bonusPerCar + (simulationTime - carSimulate.timeGotHere);
                    }

                } else {
                    //compare the simulation time for every car and sort the list in base in it
                    sortedArray(carsPositionSimulation, carSimulate, comparator);

                    //dont works, its necessary do a new sort method
//                        carsPositionSimulation.sort(comparator);
                }
            }
            currentTime++;
//            System.out.println("sumando tiempo");
        }
        //System.out.println(score);
        return score;
    }
}
