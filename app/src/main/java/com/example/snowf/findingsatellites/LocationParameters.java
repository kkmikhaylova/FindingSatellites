package com.example.snowf.findingsatellites;

public class LocationParameters {
    private static float rotationMatrix[];

    private static double azimuth = 0;
    private static double elevation = 0;
    private static double longitude = Math.toRadians(0);
    private static double latitude = Math.toRadians(0);
    private static final double observerX;
    private static final double observerY;
    private static final double observerZ;
    private static int windowSize = 40;

    static {
        double heightAboveSeaLevel = 0.428;
        double equatorialRadiusOfEarth = 6378.137;
        double earthCompression = 1 / 298.257223563;
        double eccentricity2 = 2.0 * earthCompression - earthCompression * earthCompression;
        double N = equatorialRadiusOfEarth / (Math.sqrt(1 - eccentricity2 * Math.sin(latitude) * Math.sin(latitude)));
        observerX = (heightAboveSeaLevel + N) * Math.cos(latitude) * Math.cos(longitude);
        observerY = (heightAboveSeaLevel + N) * Math.cos(latitude) * Math.sin(longitude);
        observerZ = (heightAboveSeaLevel + N * (1 - eccentricity2)) * Math.sin(latitude);
    }

    public static int getWindowSize() {
        return windowSize;
    }

    public static void setWindowSize(int windowSize) {
        LocationParameters.windowSize = windowSize;
    }

    public static double getAzimuth() {
        return azimuth;
    }

    public static void setAzimuth(double azimuth) {
        LocationParameters.azimuth = azimuth;
    }

    public static double getElevation() {
        return elevation;
    }

    public static void setElevation(double elevation) {
        LocationParameters.elevation = elevation;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        LocationParameters.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        LocationParameters.latitude = latitude;
    }

    public static double getObserverX() {
        return observerX;
    }

    public static double getObserverY() {
        return observerY;
    }

    public static double getObserverZ() {
        return observerZ;
    }

    public static float[] getRotationMatrix() {
        return rotationMatrix;
    }

    static void setRotationMatrix(float[] rotationMatrix) {
        LocationParameters.rotationMatrix = rotationMatrix;
    }
}
