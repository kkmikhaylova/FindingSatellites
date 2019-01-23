package com.example.snowf.findingsatellites;

import java.util.ArrayList;

public class SatelliteParameters {
    private static ArrayList<Satellite> satelliteData = new ArrayList<>();
    private static boolean showTitle = true;

    public static ArrayList<Satellite> getSatelliteData() {
        return satelliteData;
    }

    public static void setSatelliteData(ArrayList<Satellite> satelliteData) {
        SatelliteParameters.satelliteData = satelliteData;
    }

    public static boolean isShowTitle() {
        return showTitle;
    }

    public static void setShowTitle(boolean showTitle) {
        SatelliteParameters.showTitle = showTitle;
    }
}
