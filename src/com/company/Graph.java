package com.company;

public class Graph {
    private final double[][] matrix;
    private int numberOfVertices;

    // constructor
    public Graph(double[][] matrix, int numberOfVertices){
        this.matrix = matrix;
        this.numberOfVertices = numberOfVertices;
    }

    // adding edge to the graph
    public void addEdge(int src, int dest, double edgeWeight){
        // wont create duplicate sticks
        if(matrix[src][dest] == 0 && matrix[dest][src] ==0){
            matrix[src][dest] = edgeWeight;
            matrix[dest][src] = edgeWeight;
        }
    }

    // adding vertex from crossed diagonals
    public Vertex[] addVertex(Vertex[] vertices,int i){
        vertices[i].setCorrectVertex(false);
        setNumberOfVertices(i+1);
        return vertices;
    }

    // adding edge connected to the vertex that was created by crossed diagonals
    public void addEdgeFromDiagonal(Line[] lines,Vertex[] vertices,int m, int n){
        for(int i = 0 ; i < vertices.length; i++){

            if(lines[m].getA().getX() == vertices[i].getX() && lines[m].getA().getY() == vertices[i].getY()){
                addEdge(getNumberOfVertices()-1,i,(double)lines[m].getTimeToBurn()/2);
            }

            if(lines[m].getB().getX() == vertices[i].getX() && lines[m].getB().getY() == vertices[i].getY()){
                addEdge(getNumberOfVertices()-1,i,(double)lines[m].getTimeToBurn()/2);
            }

            if(lines[n].getA().getX() == vertices[i].getX() && lines[n].getA().getY() == vertices[i].getY()){
                addEdge(getNumberOfVertices()-1,i,(double)lines[n].getTimeToBurn()/2);
            }

            if(lines[n].getB().getX() == vertices[i].getX() && lines[n].getB().getY() == vertices[i].getY()){
                addEdge(getNumberOfVertices()-1,i,(double)lines[n].getTimeToBurn()/2);
                break;
            }

        }

        // set old edges to zero
        for(int i=0;i<vertices.length;i++) {
            if (lines[m].getA().getX() == vertices[i].getX() && lines[m].getA().getY() == vertices[i].getY()) {
                for (int j = 0; j < vertices.length; j++) {
                    if (lines[m].getB().getX() == vertices[j].getX() && lines[m].getB().getY() == vertices[j].getY()) {
                        matrix[i][j] = 0;
                        matrix[j][i] = 0;
                    }

                }
            }
        }
    }

    // calculating time to burn the whole figure
    public double timeToBurn(double[] outputArray, int numberOfVertices){
        double farthestVertex = 0;
        double biggestEdge = 0;
        double distanceToSourceFromFirstVertex = 0;
        double distanceToSourceFromSecondVertex = 0;
        double timeToBurn = 0;

        boolean ifExistsBiggerEdge = false;

        // Finding the farthest vertex
        for(int i=0;i<numberOfVertices;i++){
            if(farthestVertex < outputArray[i]){
                farthestVertex = outputArray[i];
                timeToBurn = outputArray[i];
                biggestEdge = outputArray[i];
            }
        }

        // Finding if there is bigger edge than source to the farthest vertex
        for(int i = 0; i < matrix.length; i++ ){
            for(int j = 0; j < matrix.length; j++){
                if(biggestEdge < matrix[i][j]){
                    biggestEdge = matrix[i][j];
                    // setting distance from first vertex of the biggest edge to source vertex
                    distanceToSourceFromFirstVertex = outputArray[j];
                    // setting distance from second vertex of the biggest edge to source vertex
                    distanceToSourceFromSecondVertex = outputArray[i];
                    ifExistsBiggerEdge = true;
                }
            }
        }

        //calculating time
        if(ifExistsBiggerEdge){
            if(distanceToSourceFromFirstVertex == 0 || distanceToSourceFromSecondVertex == 0) {
                if (distanceToSourceFromFirstVertex < distanceToSourceFromSecondVertex) {
                    biggestEdge -= (distanceToSourceFromSecondVertex - distanceToSourceFromFirstVertex);
                    biggestEdge = biggestEdge / 2;
                } else if (distanceToSourceFromFirstVertex > distanceToSourceFromSecondVertex) {
                    biggestEdge -= (distanceToSourceFromFirstVertex - distanceToSourceFromSecondVertex);
                    biggestEdge = biggestEdge / 2;
                } else {
                    biggestEdge = biggestEdge / 2;
                }
            }else{
                if (distanceToSourceFromFirstVertex < distanceToSourceFromSecondVertex) {
                    biggestEdge -= (distanceToSourceFromSecondVertex - distanceToSourceFromFirstVertex);
                    biggestEdge = biggestEdge / 2;
                    farthestVertex -= distanceToSourceFromSecondVertex;
                } else if (distanceToSourceFromFirstVertex > distanceToSourceFromSecondVertex) {
                    biggestEdge -= (distanceToSourceFromFirstVertex - distanceToSourceFromSecondVertex);
                    biggestEdge = biggestEdge / 2;
                    farthestVertex -= distanceToSourceFromSecondVertex;
                } else {
                    biggestEdge = biggestEdge / 2;
                }

            }
            timeToBurn = biggestEdge + farthestVertex;
        }

        return timeToBurn;
    }

    // Finding crossed diagonals
    public boolean checkForCrossDiagonals(Vertex[] vertices,Line[] lines,int i){
        for(int m = 0; m < lines.length; m++) {
            if (lines[m].getTypeOfLine() == Math.sqrt(2)) {
                for (int n = m + 1; n < lines.length; n++) {
                    if (lines[n].getTypeOfLine() == Math.sqrt(2)) {
                        if (lines[m].getTypeOfDiagonal() == 1 && lines[n].getTypeOfDiagonal() == 2) {
                            if (lines[m].getDirectionOfDiagonal() == 1 && lines[n].getDirectionOfDiagonal() == 1) {
                                if (lines[m].getA().getX() == lines[n].getA().getX() && lines[m].getA().getY() + 1 == lines[n].getA().getY() &&
                                        lines[m].getB().getX() == lines[n].getB().getX() && lines[m].getB().getY() - 1 == lines[n].getB().getY()) {
                                    vertices = addVertex(vertices, i);
                                    addEdgeFromDiagonal(lines, vertices, m, n);
                                    return true;
                                }
                            } else if (lines[m].getDirectionOfDiagonal() == 1 && lines[n].getDirectionOfDiagonal() == 2) {
                                if (lines[m].getA().getX() + 1 == lines[n].getA().getX() && lines[m].getA().getY() == lines[n].getA().getY() &&
                                        lines[m].getB().getX() - 1 == lines[n].getB().getX() && lines[m].getB().getY() == lines[n].getB().getY()) {
                                    vertices = addVertex(vertices, i);
                                    addEdgeFromDiagonal(lines, vertices, m, n);
                                    return true;

                                }
                            } else if (lines[m].getDirectionOfDiagonal() == 2 && lines[n].getDirectionOfDiagonal() == 1) {
                                if (lines[m].getA().getX() - 1 == lines[n].getA().getX() && lines[m].getA().getY() == lines[n].getA().getY() &&
                                        lines[m].getB().getX() + 1 == lines[n].getB().getX() && lines[m].getB().getY() == lines[n].getB().getY()) {
                                    vertices = addVertex(vertices, i);
                                    addEdgeFromDiagonal(lines, vertices, m, n);
                                    return true;
                                }
                            } else if (lines[m].getDirectionOfDiagonal() == 2 && lines[n].getDirectionOfDiagonal() == 2) {
                                if (lines[m].getA().getX() == lines[n].getA().getX() && lines[m].getA().getY() - 1 == lines[n].getA().getY() &&
                                        lines[m].getB().getX() == lines[n].getB().getX() && lines[m].getB().getY() + 1 == lines[n].getB().getY()) {
                                    vertices = addVertex(vertices, i);
                                    addEdgeFromDiagonal(lines, vertices, m, n);
                                    return true;

                                }
                            }
                        } else if (lines[m].getTypeOfDiagonal() == 2 && lines[n].getTypeOfDiagonal() == 1) {

                            if (lines[m].getDirectionOfDiagonal() == 1 && lines[n].getDirectionOfDiagonal() == 1) {
                                if (lines[m].getA().getX() == lines[n].getA().getX() && lines[m].getA().getY() - 1 == lines[n].getA().getY() &&
                                        lines[m].getB().getX() == lines[n].getB().getX() && lines[m].getB().getY() + 1 == lines[n].getB().getY()) {
                                    vertices = addVertex(vertices, i);
                                    addEdgeFromDiagonal(lines, vertices, m, n);
                                    return true;

                                }
                            } else if (lines[m].getDirectionOfDiagonal() == 1 && lines[n].getDirectionOfDiagonal() == 2) {
                                if (lines[m].getA().getX() + 1 == lines[n].getA().getX() && lines[m].getA().getY() == lines[n].getA().getY() &&
                                        lines[m].getB().getX() - 1 == lines[n].getB().getX() && lines[m].getB().getY() == lines[n].getB().getY()) {
                                    vertices = addVertex(vertices, i);
                                    addEdgeFromDiagonal(lines, vertices, m, n);
                                    return true;

                                }
                            } else if (lines[m].getDirectionOfDiagonal() == 2 && lines[n].getDirectionOfDiagonal() == 1) {
                                if (lines[m].getA().getX() - 1 == lines[n].getA().getX() && lines[m].getA().getY() == lines[n].getA().getY() &&
                                        lines[m].getB().getX() + 1 == lines[n].getB().getX() && lines[m].getB().getY() == lines[n].getB().getY()) {
                                    vertices = addVertex(vertices, i);
                                    addEdgeFromDiagonal(lines, vertices, m, n);
                                    return true;

                                }
                            } else if (lines[m].getDirectionOfDiagonal() == 2 && lines[n].getDirectionOfDiagonal() == 2) {
                                if (lines[m].getA().getX() == lines[n].getA().getX() && lines[m].getA().getY() + 1 == lines[n].getA().getY() &&
                                        lines[m].getB().getX() == lines[n].getB().getX() && lines[m].getB().getY() - 1 == lines[n].getB().getY()) {
                                    vertices = addVertex(vertices, i);
                                    addEdgeFromDiagonal(lines, vertices, m, n);
                                    return true;

                                }
                            }
                        }
                    }
                }
            }

        }
        return false;
    }

    public void setNumberOfVertices(int numberOfVertices){
        this.numberOfVertices = numberOfVertices;
    }

    public int getNumberOfVertices(){
        return numberOfVertices;
    }

    public double[][] getMatrix(){
        return matrix;
    }

}
