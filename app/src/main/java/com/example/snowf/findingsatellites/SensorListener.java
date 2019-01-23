package com.example.snowf.findingsatellites;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorListener implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor gravitySensor;
    private Sensor geomagneticSensor;
    private float[] gravityData = new float[3];
    private float[] geomagneticData = new float[3];

    SensorListener(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        geomagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    void start() {
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, geomagneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.9f;
        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                gravityData[0] = alpha * gravityData[0] + (1 - alpha) * event.values[0];
                gravityData[1] = alpha * gravityData[1] + (1 - alpha) * event.values[1];
                gravityData[2] = alpha * gravityData[2] + (1 - alpha) * event.values[2];
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagneticData[0] = alpha * geomagneticData[0] + (1 - alpha) * event.values[0];
                geomagneticData[1] = alpha * geomagneticData[1] + (1 - alpha) * event.values[1];
                geomagneticData[2] = alpha * geomagneticData[2] + (1 - alpha) * event.values[2];
            }

            float rotationMatrix[] = new float[9];
            float inclinationMatrix[] = new float[9];

            if (SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, gravityData, geomagneticData)) {
                LocationParameters.setRotationMatrix(rotationMatrix);
                float orientation[] = new float[3];
                SensorManager.getOrientation(rotationMatrix, orientation);
                float azimuthDegree = (float) Math.toDegrees(orientation[0]);
                azimuthDegree = 360 - (azimuthDegree + 90);
                LocationParameters.setAzimuth(Math.toRadians(azimuthDegree));
                LocationParameters.setElevation(Math.acos(rotationMatrix[8]));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}