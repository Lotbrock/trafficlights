package Util;

import model.Vertex;

import java.util.ArrayList;
import java.util.Collection;

public class Constants {
    public static int numOfEdges = 0;
    public static int numOfVertex = 0;
    public static int numOfCars = 0;
    public static Collection<Vertex> programVertexListFinal = new ArrayList<>();
    
    public static String ruta = "C:\\Users\\John\\Downloads\\testdata\\";
    public static String outputFilepath = ruta+"_output.txt";
    public static int loops= 0;
    public static int toleranceLoops = 6000;
    public static int bonusPerCar = 0;
    public static int simulationTime = 0;
    public static int finalScore = 0;

}
