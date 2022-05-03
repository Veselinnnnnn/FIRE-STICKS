package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("C5.txt"));

        int numberOfLines = scanner.nextInt();
        int numberOfVertices = numberOfLines * 2;

        double typeOfLine;
        double timeTemp;
        double shortestTime = 0;
        double timeTemp2 = Double.MAX_VALUE;
        double[][] matrix = new double[numberOfVertices][numberOfVertices];

        boolean temp = true;

        Vertex[] vertex = new Vertex[numberOfVertices];
        Vertex medianVertex = new Vertex();

        Line[] lines = new Line[numberOfVertices];

        Graph graph = new Graph(matrix, numberOfVertices);

        //initialising arrays
        for (int i = 0; i < numberOfVertices; i++) {
            lines[i] = new Line();
            vertex[i] = new Vertex();
        }

        int i = 0;
        int k = 0;
        // reading from file
        while (scanner.hasNext()) {
            int x1 = Math.abs(scanner.nextInt());
            int y1 = Math.abs(scanner.nextInt());
            int x2 = Math.abs(scanner.nextInt());
            int y2 = Math.abs(scanner.nextInt());
            int time = Math.abs(scanner.nextInt());
            int rememberVertex1 = 0;
            int rememberVertex2 = 0;
            boolean existingVertex1 = false;
            boolean existingVertex2 = false;

            // checking if the vertex exists
            for (int j = 0; j < numberOfVertices; j++) {
                if (vertex[j].getX() == x1 && vertex[j].getY() == y1) {
                    existingVertex1 = true;
                    rememberVertex1 = j;
                    if(i == j)i++;
                    break;
                }
            }

            // setting vertex info if not exists
            if (!existingVertex1) {
                vertex[i].setX(x1);
                vertex[i].setY(y1);
                rememberVertex1 = i;
                i++;
            }

            // checking if the vertex exists
            for (int j = 0; j < numberOfVertices; j++) {
                if (vertex[j].getX() == x2 && vertex[j].getY() == y2) {
                    existingVertex2 = true;
                    rememberVertex2 = j;
                    if(i == j)i++;
                    break;
                }
            }

            // setting vertex info if not exists
            if (!existingVertex2) {
                vertex[i].setX(x2);
                vertex[i].setY(y2);
                rememberVertex2 = i;
                i++;
            }

            // checking type of line
            if (vertex[rememberVertex1].getX() != vertex[rememberVertex2].getX() &&
                    vertex[rememberVertex1].getY() != vertex[rememberVertex2].getY()) {
                typeOfLine = Math.sqrt(2);
            } else {
                typeOfLine = 1;
            }

            // setting info for line[k]
            lines[k].setA(vertex[rememberVertex1]);
            lines[k].setB(vertex[rememberVertex2]);
            lines[k].setTypeOfLine(typeOfLine);
            lines[k].setTimeToBurn(time);

            // checking what  type of diagonal it is and the direction
            if(lines[k].getTypeOfLine()==Math.sqrt(2)){
                if(lines[k].getA().getX()+1 == lines[k].getB().getX() && lines[k].getA().getY()+1 == lines[k].getB().getY()){
                    lines[k].setTypeOfDiagonal(1);
                    lines[k].setDirectionOfDiagonal(1);
                }else if(lines[k].getB().getX()+1 == lines[k].getA().getX() && lines[k].getB().getY()+1 == lines[k].getA().getY()){
                    lines[k].setTypeOfDiagonal(1);
                    lines[k].setDirectionOfDiagonal(2);
                }else if(lines[k].getA().getX()+1 == lines[k].getB().getX() && lines[k].getA().getY()-1 == lines[k].getB().getY()){
                    lines[k].setTypeOfDiagonal(2);
                    lines[k].setDirectionOfDiagonal(1);
                }else{
                    lines[k].setTypeOfDiagonal(2);
                    lines[k].setDirectionOfDiagonal(2);
                }
            }

            // adding edge to the graph
            graph.addEdge(rememberVertex1, rememberVertex2, time);
            k++;
        }

        // checking if there is a crossed diagonals
        if(graph.checkForCrossDiagonals(vertex,lines,i)){
            numberOfVertices = graph.getNumberOfVertices();
        }else{
            numberOfVertices = i;
        }

        Algorithm g = new Algorithm();

        // finding best time and median vertex
        for(i = 0; i <numberOfVertices; i++) {
            if (vertex[i].getCorrectVertex()){
                // returning array with the shortest path to the source
                double[] outputArray = g.dijkstraShortestPath(graph, i);

                // returning the best burning time from source
                double time = graph.timeToBurn(outputArray,numberOfVertices);

                timeTemp = 0;

                // counting distance to source from output array to find median vertex
                for (int j = 0; j < numberOfVertices; j++) timeTemp += Math.abs(outputArray[j]);

                //unconnected graph
                if (timeTemp < 0) {
                    temp = false;
                    break;
                }else{
                    if (timeTemp2 > timeTemp) {
                        timeTemp2 = timeTemp;
                        shortestTime = time;
                        medianVertex.setX(vertex[i].getX());
                        medianVertex.setY(vertex[i].getY());
                    }
                }
            }
        }

        if(temp) {
            System.out.println("The best burn point is with coordinate(X,Y): (" + medianVertex.getX() + "," + medianVertex.getY() + ")");
            if((int)shortestTime < shortestTime) {
                System.out.printf("Burn time: %.2f", shortestTime);
            }else{
                System.out.println("Burn time: " + (int)shortestTime);
            }
        }else{
            System.out.println("Sorry! Unconnected graph!");
        }
    }
}
