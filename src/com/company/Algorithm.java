package com.company;
public class Algorithm {
    public double[] dijkstraShortestPath(Graph g, int src_node) {
        // The output arrays. Final shortest distance array
        double[] outputArray = new double[g.getNumberOfVertices()];

        // Array to tell whether shortest distance of vertex has been found
        boolean[] visited = new boolean[g.getNumberOfVertices()];

        // Initializing the arrays
        for (int i = 0; i < g.getNumberOfVertices(); i++) {
            // Initial distance is infinite
            outputArray[i] = Integer.MAX_VALUE;
            // Shortest distance for any node has not been found yet
            visited[i] = false;
        }

        // Path between vertex and itself is always 0
        outputArray[src_node] = 0;

        // Now find the shortest path for all vertices
        for (int i = 0; i < g.getNumberOfVertices(); i++) {
            // Get the closest node
            int closestVertex = getClosestVertex(outputArray, visited);

            // If closest node is infinite distance away, it means that no other node can be reached. So
            if (closestVertex == Integer.MAX_VALUE) return outputArray;

            // The current vertex is processed
            visited[closestVertex] = true;

            for (int j = 0; j < g.getNumberOfVertices(); j++) {
                // Shortest distance of the node j should not have been finalized
                if (!visited[j] && g.getMatrix()[closestVertex][j] != 0 && outputArray[closestVertex] !=
                        Integer.MAX_VALUE && outputArray[closestVertex]
                        + g.getMatrix()[closestVertex][j] < outputArray[j])
                    outputArray[j] = outputArray[closestVertex] + g.getMatrix()[closestVertex][j];
            }
        }
        return outputArray;
    }

    // getting the closest vertex
    public static int getClosestVertex(double[] outputArray, boolean[] visited) {
        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index = -1;
        for (int i = 0; i < outputArray.length; i++)
            if (!visited[i] && outputArray[i] <= min) {
                min = outputArray[i];
                min_index = i;
            }
        return min_index;
    }
}