package com.example.snowf.findingsatellites.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.example.snowf.findingsatellites.R;
import com.example.snowf.findingsatellites.SatelliteParameters;

public class ShowSatelliteTitleButton extends AppCompatButton implements View.OnClickListener {
    public ShowSatelliteTitleButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public ShowSatelliteTitleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public ShowSatelliteTitleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        SatelliteParameters.setShowTitle(!SatelliteParameters.isShowTitle());
        if (SatelliteParameters.isShowTitle()) {
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hide));
        } else {
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.view));
        }
    }
}
