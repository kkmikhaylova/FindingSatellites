package com.example.snowf.findingsatellites.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.example.snowf.findingsatellites.R;
import com.example.snowf.findingsatellites.RequestParameters;

public class TrashButton extends AppCompatButton implements View.OnClickListener {

    public TrashButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public TrashButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public TrashButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        if (!RequestParameters.isShowSatellite() && RequestParameters.isShowTrash())
            return;
        RequestParameters.setShowTrash(!RequestParameters.isShowTrash());
        if (RequestParameters.isShowTrash()) {
            button.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFon));
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle_right));
        } else {
            button.setTextColor(ContextCompat.getColor(getContext(), R.color.colorType));
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle_right_stroke));
        }
    }
}
