package com.example.snowf.findingsatellites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

import com.example.snowf.findingsatellites.LocationParameters;
import com.example.snowf.findingsatellites.Axis;
import com.example.snowf.findingsatellites.Coordinates;
import com.example.snowf.findingsatellites.Matrix;
import com.example.snowf.findingsatellites.Satellite;
import com.example.snowf.findingsatellites.SatelliteParameters;

import java.util.ArrayList;

public class RenderingSatellites extends Thread {
    private final SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private Paint paint = new Paint();
    private Matrix matrix = new Matrix(null);
    private Matrix yRotation;
    //private Matrix zRotation;

    RenderingSatellites(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        yRotation = new Matrix(Axis.Y, Math.PI);
        //zRotation = new Matrix(Axis.Z, Math.PI / 2);
    }

    void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    private void onDraw(Canvas canvas) {
        int deg = LocationParameters.getWindowSize();
        double azimuthN = LocationParameters.getAzimuth();
        float rotationMatrix[] = LocationParameters.getRotationMatrix();
        double elevationN = LocationParameters.getElevation();

        if (elevationN > Math.PI / 2) {
            azimuthN = -(azimuthN + Math.PI);
        }

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        float radius = 3;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#38324E"));
        canvas.drawRect(0, 0, width, height, paint);

        matrix.setFloatMatrix(rotationMatrix);
        double alpha;
        double beta;

        Coordinates newCoordinates;
        ArrayList<Satellite> satelliteList = SatelliteParameters.getSatelliteData();
        paint.setTextAlign(Paint.Align.CENTER);
        if (satelliteList.size() > 0) {
            for (Satellite satellite : satelliteList) {
                Coordinates satelliteCoordinates = satellite.getCoordinates();
                newCoordinates = matrix.multiply(satelliteCoordinates);
                newCoordinates = yRotation.multiply(newCoordinates);
                alpha = Math.atan2(
                        -newCoordinates.getY(),
                        newCoordinates.getX()
                );
                beta = Math.PI / 2 - Math.asin(newCoordinates.getZ());

                paint.setColor(Color.parseColor("#8560C0"));
                if (Math.toDegrees(beta) < deg) {
                    double r = (width / 2 + 100) * (Math.toDegrees(beta) / deg);
                    float x = (float) (r * sin(alpha));
                    float y = (float) (r * cos(alpha));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(width / 2 + x, height / 2 + y, radius, paint);
                    int rectWidth = 0;
                    switch (Integer.toString(satellite.getNorad()).length()) {
                        case 1:
                            rectWidth = 12;
                            break;
                        case 2:
                            rectWidth = 20;
                            break;
                        case 3:
                            rectWidth = 28;
                            break;
                        case 4:
                            rectWidth = 36;
                            break;
                        case 5:
                            rectWidth = 42;
                            break;
                    }
                    if (SatelliteParameters.isShowTitle()) {
                        paint.setColor(Color.parseColor("#403A60"));
                        canvas.drawRect(width / 2 + x - rectWidth / 2,
                                height / 2 + y - 18,
                                width / 2 + x + rectWidth / 2,
                                height / 2 + y - 4, paint);
                        paint.setColor(Color.WHITE);
                        paint.setTextSize(10);
                        canvas.drawText(Integer.toString(satellite.getNorad()),
                                width / 2 + x,
                                height / 2 + y - 7, paint);
                    }
                }
            }
        }
    }

    private double sin(double angle) {
        return Math.sin(angle);
    }

    private double cos(double angle) {
        return Math.cos(angle);
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    onDraw(canvas);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
