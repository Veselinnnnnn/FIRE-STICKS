package com.company;

public class Line {
    private Vertex A = new Vertex();
    private Vertex B = new Vertex();
    private double typeOfLine;
    private int typeOfDiagonal;
    private int timeToBurn;
    private int directionOfDiagonal;

    public void setA(Vertex a) {
        A = a;
    }

    public void setB(Vertex b) {
        B = b;
    }

    public void setTypeOfLine(double typeOfLine) {
        this.typeOfLine = typeOfLine;
    }

    public void setTimeToBurn(int timeToBurn) {
        this.timeToBurn = timeToBurn;
    }


    public int getTimeToBurn() {
        return timeToBurn;
    }

    public double getTypeOfLine() {
        return typeOfLine;
    }

    public Vertex getA() {
        return A;
    }

    public Vertex getB() {
        return B;
    }

    public int getTypeOfDiagonal(){
        return typeOfDiagonal;
    }
    public void setTypeOfDiagonal(int type){
        this.typeOfDiagonal = type;
    }

    public int getDirectionOfDiagonal() {
        return directionOfDiagonal;
    }

    public void setDirectionOfDiagonal(int directionOfDiagonal) {
        this.directionOfDiagonal = directionOfDiagonal;
    }
}
