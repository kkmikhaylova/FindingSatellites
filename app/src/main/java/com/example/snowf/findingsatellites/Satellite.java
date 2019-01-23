package com.example.snowf.findingsatellites;

public class Satellite {
    private int norad;
    private Coordinates coordinates;

    Satellite(int norad, Coordinates coordinates) {
        this.norad = norad;
        this.coordinates = coordinates;
    }

    public int getNorad() {
        return norad;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
