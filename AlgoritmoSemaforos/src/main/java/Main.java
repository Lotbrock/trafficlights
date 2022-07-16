import Util.*;
import static Util.Constants.programVertexListFinal;
import static Util.Constants.ruta;
import model.Car;
import model.Edge;

import java.util.Collection;

import static Util.Program.optimizeHillClimbing;
import static Util.Program.simulate;
import static Util.Utilities.*;
import static java.util.stream.Collectors.toMap;

public class Main {

    public static void main(String[] args) {
        
        Collection<Edge>  edges =  Utilities.createEdges(ruta + "red_11.txt");
       edges.forEach(e -> System.out.println(e.toString()));
        Collection<Car> cars = Utilities.createCars(ruta + "trayectos_11.txt");        
        Utilities.simulationStandar(ruta + "simulacion_11.txt");
        System.out.println("===============CARS==========");
       cars.forEach(e -> System.out.println(e.toString()));
        System.out.println("===============Constants==========");
        System.out.println("Vertex: "+Constants.numOfVertex);
        System.out.println("Edges: "+Constants.numOfEdges);
        System.out.println("Cars: "+Constants.numOfCars);
        System.out.println("Bonus: "+Constants.bonusPerCar);
        System.out.println("Time: "+Constants.simulationTime);

        var solution = programVertex(cars,edges);
        System.out.println("Initial Score: "+simulate(cars, edges,solution));

        optimizeHillClimbing(cars,edges, solution);
        System.out.println("Last Score: "+simulate(cars, edges, solution));
        createOutputFile();
        
        

   }
}
