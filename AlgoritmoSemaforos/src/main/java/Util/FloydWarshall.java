package Util;

import java.util.Arrays;
import java.util.Map;

public class FloydWarshall {

    // matriz de adyacencia
    private double[][] LightsGraph;

    private boolean negativeCycle;

    public FloydWarshall(int n) {
        this.LightsGraph = new double[n][n];
        initGraph();
    }

    private void initGraph() {
        for (int i = 0; i < LightsGraph.length; i++) {
            for (int j = 0; j < LightsGraph.length; j++) {
                if (i == j) {
                    LightsGraph[i][j] = 0;
                } else {
                    LightsGraph[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }

    public boolean hasNegativeCycle() {
        return this.negativeCycle;
    }

    // añade aristas a la lista de adyacencia
    public void addEdge(int from, int to, double weight) {
        LightsGraph[from][to] = weight;
    }

    // devuelve la matriz con los caminos mas cortos
    public Object[] floydWarshall() {
        double[][] distances;
        int n = this.LightsGraph.length;
        distances = Arrays.copyOf(this.LightsGraph, n);
        int[][] next = new int[n][n];

        for (int e = 0; e < n; e++) {
            for (int r = 0; r < n; r++) {
                if (distances[e][r] != Double.POSITIVE_INFINITY)
                {
                    next[e][r] = r;
                }
            }

        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distances[i][j] > distances[i][k] + distances[k][j])
                    {
                        distances[i][j] = distances[i][k] + distances[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }

            if (distances[k][k] < 0.0) {
                this.negativeCycle = true;
            }
        }

        return new Object[]{distances, next};
    }
}