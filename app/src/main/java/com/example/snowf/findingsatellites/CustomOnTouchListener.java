package com.example.snowf.findingsatellites;

import android.view.MotionEvent;
import android.view.View;

public class CustomOnTouchListener implements View.OnTouchListener{
    private float mPrimStartTouchEventX = -1;
    private float mPrimStartTouchEventY = -1;
    private float mSecStartTouchEventX = -1;
    private float mSecStartTouchEventY = -1;
    private float mPrimSecStartTouchDistance = 0;
    private int mPtrCount = 0;

    private int diff = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = (event.getAction() & MotionEvent.ACTION_MASK);

        float mPrimEndTouchEventX;
        float mPrimEndTouchEventY;
        float mSecEndTouchEventX;
        float mSecEndTouchEventY;

        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                mPtrCount++;
                if (mPtrCount == 1) {
                    mPrimStartTouchEventX = event.getX(0);
                    mPrimStartTouchEventY = event.getY(0);
                }
                if (mPtrCount == 2) {
                    // Starting distance between fingers
                    mSecStartTouchEventX = event.getX(1);
                    mSecStartTouchEventY = event.getY(1);
                    mPrimSecStartTouchDistance = distance(event, 0, 1);
                }

                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:

                mPtrCount--;
                if (mPtrCount == 1) {
                    mSecStartTouchEventX = -1;
                    mSecStartTouchEventY = -1;
                    diff = 0;
                }
                if (mPtrCount < 1) {
                    mPrimStartTouchEventX = -1;
                    mPrimStartTouchEventY = -1;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (mPtrCount > 1 && isPinchGesture(event)) {
                    mPrimEndTouchEventX = event.getX(0);
                    mPrimEndTouchEventY = event.getY(0);
                    mSecEndTouchEventX = event.getX(1);
                    mSecEndTouchEventY = event.getY(1);

                    double startDistance = Math.sqrt(
                            (mSecStartTouchEventX - mPrimStartTouchEventX) *
                                    (mSecStartTouchEventX - mPrimStartTouchEventX) +
                                    (mSecStartTouchEventY - mPrimStartTouchEventY) *
                                            (mSecStartTouchEventY - mPrimStartTouchEventY));
                    double endDistance = Math.sqrt(
                            (mSecEndTouchEventX - mPrimEndTouchEventX) *
                                    (mSecEndTouchEventX - mPrimEndTouchEventX) +
                                    (mSecEndTouchEventY - mPrimEndTouchEventY) *
                                            (mSecEndTouchEventY - mPrimEndTouchEventY));
                    int deg = LocationParameters.getWindowSize();
                    deg = deg + ((int) ((endDistance - startDistance) / 20)) - diff;
                    diff += ((int) ((endDistance - startDistance) / 20)) - diff;
                    if (diff > 30) diff = 30;
                    if (diff < -30) diff = -30;
                    if (deg > 40) deg = 40;
                    if (deg < 10) deg = 10;
                    LocationParameters.setWindowSize(deg);

                }
                break;
        }
        return true;
    }

    private float distance(MotionEvent event, int first, int second) {
        if (event.getPointerCount() >= 2) {
            final float x = event.getX(first) - event.getX(second);
            final float y = event.getY(first) - event.getY(second);
            return (float) Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }

    private boolean isPinchGesture(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            final float distanceCurrent = distance(event, 0, 1);
            final float diffPrimX = mPrimStartTouchEventX - event.getX(0);
            final float diffPrimY = mPrimStartTouchEventY - event.getY(0);
            final float diffSecX = mSecStartTouchEventX - event.getX(1);
            final float diffSecY = mSecStartTouchEventY - event.getY(1);
            final int mViewScaledTouchSlop = 35;
            if (Math.abs(distanceCurrent - mPrimSecStartTouchDistance) > mViewScaledTouchSlop
                    && (diffPrimY * diffSecY) <= 0
                    && (diffPrimX * diffSecX) <= 0) {
                return true;
            }
        }
        return false;
    }
}
