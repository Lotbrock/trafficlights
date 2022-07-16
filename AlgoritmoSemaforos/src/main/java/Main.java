import Util.*;
import model.Car;
import model.Edge;

import java.util.Collection;

import static Util.Program.optimizeHillClimbing;
import static Util.Program.simulate;
import static Util.Utilities.*;
import static java.util.stream.Collectors.toMap;

public class Main {

    public static void main(String[] args) {

        Collection<Edge>  edges =  Utilities.createEdges("D:\\Trabajos\\Uni\\8vo\\Diplomado\\software\\trafficlights\\AlgoritmoSemaforos\\src\\main\\resources\\red.txt");
       edges.forEach(e -> System.out.println(e.toString()));
        Collection<Car> cars = Utilities.createCars("D:\\Trabajos\\Uni\\8vo\\Diplomado\\software\\trafficlights\\AlgoritmoSemaforos\\src\\main\\resources\\trayectos.txt");
        System.out.println("===============CARS==========");
       cars.forEach(e -> System.out.println(e.toString()));
        System.out.println("===============Constants==========");
        System.out.println("vertex: "+Constants.numOfVertex);
        System.out.println("edges: "+Constants.numOfEdges);
        System.out.println("cars: "+Constants.numOfCars);

        var solution = programVertex(cars,edges);
        System.out.println("Initial Score: "+simulate(cars, edges,solution));

        optimizeHillClimbing(cars,edges, solution);
        System.out.println("last score"+simulate(cars, edges,solution));
        createOutputFile();
        //        FloydWarshall fw = new FloydWarshall(5);
//        fw.addEdge(0, 1, 6);
//        fw.addEdge(0, 3, 7);
//        fw.addEdge(1, 3, 8);
//        fw.addEdge(1, 2, 5);
//        fw.addEdge(1, 4, -4);
//        fw.addEdge(2, 1, -2);
//        fw.addEdge(3, 4, 9);
//        fw.addEdge(3, 2, -3);
//        fw.addEdge(4, 2, 7);
//        fw.addEdge(4, 0, 2);
//
//        var result = fw.floydWarshall();
//        double[][] distances = (double[][]) result[0];
//        int[][] next = (int[][]) result[1];
//
//        for (int i = 0; i < 5; i++) {
//            System.out.print("{");
//            for (int j = 0; j < 5; j++) {
//                System.out.print(distances[i][j] + ", ");
//            }
//            System.out.print("}");
//            System.out.println("");
//        }
//        System.out.println("============== NEXT Matrix ============ ");
//        for (int i = 0; i < 5; i++) {
//            System.out.print("{");
//            for (int j = 0; j < 5; j++) {
//                System.out.print(next[i][j] + ", ");
//            }
//            System.out.print("}");
//            System.out.println("");
//        }
   }
}
