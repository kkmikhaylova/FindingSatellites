package com.example.snowf.findingsatellites;

import java.util.ArrayList;

public class RequestParameters {
    private static ArrayList<Integer> norads = new ArrayList<>();
    private static boolean showSatellite = true;
    private static boolean showTrash = false;
    private static int minHeight = 200;
    private static int maxHeight = 70000;
    private static int leftIndex = 0;
    private static int rightIndex = 37;

    public static ArrayList<Integer> getNorads() {
        return norads;
    }
    public static void setNorads(ArrayList<Integer> norads) {
        RequestParameters.norads = norads;
    }
    public static boolean isShowSatellite() {
        return showSatellite;
    }
    public static void setShowSatellite(boolean showSatellite) {
        RequestParameters.showSatellite = showSatellite;
    }
    public static boolean isShowTrash() {
        return showTrash;
    }
    public static void setShowTrash(boolean showTrash) {
        RequestParameters.showTrash = showTrash;
    }
    public static int getMinHeight() {
        return minHeight;
    }
    public static void setMinHeight(int minHeight) {
        RequestParameters.minHeight = minHeight;
    }
    public static int getMaxHeight() {
        return maxHeight;
    }
    public static void setMaxHeight(int maxHeight) {
        RequestParameters.maxHeight = maxHeight;
    }
    public static int getLeftIndex() {
        return leftIndex;
    }
    public static void setLeftIndex(int leftIndex) {
        RequestParameters.leftIndex = leftIndex;
    }
    public static int getRightIndex() {
        return rightIndex;
    }
    public static void setRightIndex(int rightIndex) {
        RequestParameters.rightIndex = rightIndex;
    }
}
