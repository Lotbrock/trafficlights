package Util;

import java.io.File;
import java.util.*;

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
            System.out.println("Vertex:"+ vertexAndEdges[0]+" Edges: "+vertexAndEdges[1]);
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
            System.out.println("NumCars:"+ journeys[0]);
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
}
