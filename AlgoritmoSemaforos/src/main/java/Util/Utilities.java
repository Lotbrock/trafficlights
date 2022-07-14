package Util;

import model.Car;
import model.CarPositionSimulation;
import model.Edge;
import model.Vertex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import static Util.Constants.*;
import static java.util.stream.Collectors.toMap;

public class Utilities {

    public static ArrayList<Edge> createEdges(String path)
    {
        ArrayList<Edge> edges = new ArrayList<>();
        try {
            String[] newEdge = new String[3];
            File doc =
                    new File(path);
            Scanner obj = new Scanner(doc);
            var vertexAndEdges = obj.nextLine().split(" ");
            Constants.numOfEdges = Integer.parseInt(vertexAndEdges[1]);
            Constants.numOfVertex = Integer.parseInt(vertexAndEdges[0]);
            while (obj.hasNextLine()) {
                newEdge = obj.nextLine().split(" ");
                edges.add(new Edge(
                        newEdge[2],
                        null,
                        Integer.parseInt(newEdge[0]),
                        Integer.parseInt(newEdge[1]),
                        null,
                        Integer.parseInt(newEdge[3])
                ));
            }
        } catch (Exception e) {

        }
        return edges;

    }

    public static ArrayList<Car> createCars(String path)
    {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            ArrayList<String> newCar = new ArrayList<>();
            File doc =
                    new File(path);
            Scanner obj = new Scanner(doc);
            var journeys = obj.nextLine().split(" ");
            Constants.numOfCars = Integer.parseInt(journeys[0]);
            while (obj.hasNextLine()) {
                newCar.addAll(List.of(obj.nextLine().split(" ")));
                int numberOfEdges = Integer.parseInt(newCar.get(0));
                newCar.remove(0);
                cars.add(new Car(
                        newCar.toArray(String[]::new),
                        0,
                        0,
                        numberOfEdges
                ));
                newCar.clear();
            }
        } catch (Exception e) {

        }
        return cars;

    }

    public static void programVertex(Collection<Car> cars, Collection<Edge>  edges)
    {
        List<String[]> routes = new ArrayList<>();
        cars.forEach(e-> routes.add(e.route));
        for( int i = 0 ; i < Constants.numOfVertex; i++)
        {
            int finalI = i;
            var uniqueEdges =edges.stream().filter(e -> e.destiny == finalI).collect(Collectors.toList());

            Vertex programVertex = new Vertex();
            programVertex.setNumberOfEdges(uniqueEdges.size());
            programVertex.id = finalI;

            for (var edge: uniqueEdges
                 ) {
                ProgramTraficLight program = new ProgramTraficLight();
                program.streetName = edge.name;

                int initOfPath = 0;
                for(var route : routes)
                {
                        if (route[0].equals(edge.name))
                            initOfPath = 1;
                }
                program.greenSeconds = 1+ initOfPath;
                programVertex.programTraficLights.add(program);
            }
            if(programVertex != null)
            {
                programVertexList.add(programVertex);
            }
        }

    }

    public static void createOutputFile()
    {
        String newLine = "\r\n";
        String outputFile = programVertexList.size() + newLine;

        for (var vertex: programVertexList)
        {
            outputFile += vertex.id +newLine+ vertex.numberOfEdges +newLine;

            for (var traficLight: vertex.programTraficLights)
            {
                outputFile += traficLight.streetName + " " + traficLight.greenSeconds + newLine;
            }
        }

        try {
            File file = new File(outputFilepath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(outputFile);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sortedArray(List<CarPositionSimulation> list, CarPositionSimulation car, Comparator<CarPositionSimulation> comparator)
    {
        if(list.size()== 0)
        {
            list.add(car);
            return;
        }
        if(comparator.compare(list.get(list.size()-1),car) <= 0)
        {
            list.add(car);
            return;
        }
        if (comparator.compare(list.get(0),car)>=0)
        {
            list.add(0,car);
            return;
        }

        int index = Collections.binarySearch(list,car, comparator);
        if(index < 0)
            index = ~index;
        list.add(index,car);
    }

    public static int simulate(Iterable<Car> cars, Collection<Edge> edges)
    {
        int score = 0;
        int currentTime = 0;
        List<CarPositionSimulation> carsPositionSimulation = new ArrayList<>();

        int simulationCarIndexStart = -(numOfCars);
        //init car positions in simulation 
        for (Car car: cars
             ) {

            if (car.numberOfEdges == 1)
            {
                score += bonusPerCar + simulationTime;
            }
            else {
                carsPositionSimulation.add(new CarPositionSimulation(car, simulationCarIndexStart));
            }
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

                        //TO DO==== Do sort algorithm to change the car position to the right one position
                     /*   carsPositionSimulation.sort(new Comparator<CarPositionSimulation>() {
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
                        });*/
                    }
            }
            currentTime++;
        }
        System.out.println(score);
        return score;
    }


}
