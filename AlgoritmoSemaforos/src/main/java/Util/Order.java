package Util;

import java.util.*;

public class Order {

    public static void OrderCollection(int[] A) {
        int i, j, aux;
        for (i = 0; i < A.length - 1; i++) {
            for (j = 0; j < A.length - i - 1; j++) {
                if (A[j + 1] < A[j]) {
                    aux = A[j + 1];
                    A[j + 1] = A[j];
                    A[j] = aux;
                }
            }
        }
        for (int a: A) {
            System.out.print(a);
        }
    }


    public static void BucketSort(int arrayToSort[], int maxVal) {
        int[] buckets = new int[maxVal + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = 0;
        }

        for (int i = 0; i < arrayToSort.length; i++) {
            buckets[arrayToSort[i]]++;
        }

        int outPos = 0;
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i]; j++) {
                arrayToSort[outPos++] = i;
            }
        }
    }

    public void BFS(int[] G, int source)
    {
        int n = G.length;
        int d[] = new int[n];
        d[source] = 0;
        int pi[] = new int[n];
        char state[] = new char[n];
        Arrays.fill(state, 'N');
        state[source] = 'D';
    }
}
