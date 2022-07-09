package Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Utilities {

    public static ArrayList<Edge> createEdges(String path)
    {
        ArrayList<Edge> edges = new ArrayList<>();
        try {
            String[] newEdge = new String[3];
            File doc =
                    new File(path);
            Scanner obj = new Scanner(doc);
            var VertexAndEdges = obj.nextLine().split(" ");
            System.out.println("Vertex:"+ VertexAndEdges[0]+" Edges: "+VertexAndEdges[1]);
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
}
