package com.example.snowf.findingsatellites;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LoadSatelliteParametersAT extends AsyncTask<String, Void, String> {
    private boolean isLoad = false;
    private final LoadSatelliteParametersATListener listener;

    public LoadSatelliteParametersAT(LoadSatelliteParametersATListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isLoad) {
            return;
        }
    }

    @Override
    protected String doInBackground(String... params) {
        if (isLoad) {
            return null;
        }
        int min = RequestParameters.getMinHeight();
        int max = RequestParameters.getMaxHeight();
        String type = (RequestParameters.isShowSatellite() && RequestParameters.isShowTrash() ? "both" :
                (RequestParameters.isShowSatellite() ? "sat" : "trash"));
        StringBuilder urlAddress = new StringBuilder();
        urlAddress.append(params[0]).append("?min=").append(min).append("&max=").append(max).append("&type=").append(type);
        for (int norad : RequestParameters.getNorads()) {
            urlAddress.append("&n[]=").append(norad);
        }
        String result = "";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Log.d("REQUEST", urlAddress.toString());
            URL url = new URL(urlAddress.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        if (isLoad) {
            return;
        }
        Log.d("Load", "Success");
        Log.d("Load", json);
        JSONObject data;
        try {
            data = new JSONObject(json);
            Double starTime = data.getDouble("star_time");
            JSONArray satelliteList = data.getJSONArray("satellites");
            ArrayList<Satellite> satellites = new ArrayList<>();
            Coordinates coordinates;
            double azimuth;
            double elevation;

            double longitude = LocationParameters.getLongitude();
            double latitude = LocationParameters.getLatitude();
            double observerX = LocationParameters.getObserverX();
            double observerY = LocationParameters.getObserverY();
            double observerZ = LocationParameters.getObserverZ();

            for (int i = 0; i < data.getInt("count"); i++) {
                JSONObject satellite = satelliteList.getJSONObject(i);
                coordinates = new Coordinates(
                        satellite.getInt("x"),
                        satellite.getInt("y"),
                        satellite.getInt("z"));
                coordinates = new Matrix(Axis.Z, starTime).multiply(coordinates);
                coordinates.setX(coordinates.getX() - observerX);
                coordinates.setY(coordinates.getY() - observerY);
                coordinates.setZ(coordinates.getZ() - observerZ);
                coordinates = new Matrix(Axis.Z, longitude).multiply(coordinates);
                coordinates = new Matrix(Axis.Y, latitude).multiply(coordinates);
                azimuth = Math.atan2(coordinates.getY(), -coordinates.getX());
                elevation = Math.atan2(
                        coordinates.getZ(),
                        Math.sqrt(coordinates.getX() * coordinates.getX() + coordinates.getY() * coordinates.getY())
                );
                satellites.add(new Satellite(
                        satellite.getInt("n"),
                        new Coordinates(
                        Math.cos(elevation) * Math.cos(azimuth - Math.PI / 2),
                        Math.cos(elevation) * Math.sin(azimuth - Math.PI / 2),
                        Math.sin(elevation)
                )));
            }
            SatelliteParameters.setSatelliteData(satellites);
            isLoad = true;
            listener.onLoadFinished();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}