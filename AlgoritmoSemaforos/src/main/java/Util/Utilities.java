package Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import static Util.Constants.outputFilepath;
import static Util.Constants.programVertexList;
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
                    for(String road: route) {
                        if (road == edge.name)
                            initOfPath = 1;
                    }
                }
                program.greenSeconds = 1+initOfPath;
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
}
