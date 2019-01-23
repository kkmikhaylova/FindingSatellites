package com.example.snowf.findingsatellites;

public class Matrix {

    private double[] matrix;
    private float[] floatMatrix;
    private boolean is_float = false;

    public Matrix(Axis axis, double angle) {
        switch (axis) {
            case X:
                matrix = new double[] {
                        1,           0,           0,
                        0,           cos(angle),  sin(angle),
                        0,           -sin(angle), cos(angle)
                };
                break;
            case Y:
                matrix = new double[] {
                        cos(angle),  0,           sin(angle),
                        0,           1,           0,
                        -sin(angle), 0,           cos(angle)
                };
                break;
            case Z:
                matrix = new double[] {
                        cos(angle),  sin(angle),  0,
                        -sin(angle), cos(angle),  0,
                        0,           0,           1
                };
                break;
        }
    }

    public Matrix(float[] matrix) {
        this.floatMatrix = matrix;
        this.is_float = true;
    }

    public void setFloatMatrix(float[] floatMatrix) {
        this.floatMatrix = floatMatrix;
    }

    public Coordinates multiply(Coordinates coordinates) {
        double x = coordinates.getX();
        double y = coordinates.getY();
        double z = coordinates.getZ();

        double X;
        double Y;
        double Z;

        if (is_float) {
            X = x * floatMatrix[0] + y * floatMatrix[3] + z * floatMatrix[6];
            Y = x * floatMatrix[1] + y * floatMatrix[4] + z * floatMatrix[7];
            Z = x * floatMatrix[2] + y * floatMatrix[5] + z * floatMatrix[8];
        } else {
            X = x * matrix[0] + y * matrix[1] + z * matrix[2];
            Y = x * matrix[3] + y * matrix[4] + z * matrix[5];
            Z = x * matrix[6] + y * matrix[7] + z * matrix[8];
        }

        return new Coordinates(X, Y, Z);
    }

    private double sin(double angle) {
        return Math.sin(angle);
    }

    private double cos(double angle) {
        return Math.cos(angle);
    }
}
