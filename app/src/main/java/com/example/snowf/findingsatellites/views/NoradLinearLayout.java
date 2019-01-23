package com.example.snowf.findingsatellites.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snowf.findingsatellites.RequestParameters;

import java.util.ArrayList;

public class NoradLinearLayout extends LinearLayout implements View.OnClickListener {


    public NoradLinearLayout(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public NoradLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public NoradLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        TextView textView = (TextView) linearLayout.getChildAt(0);
        ArrayList<Integer> norads = RequestParameters.getNorads();
        norads.remove(norads.indexOf(Integer.parseInt(textView.getText().toString())));
        RequestParameters.setNorads(norads);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }
}
