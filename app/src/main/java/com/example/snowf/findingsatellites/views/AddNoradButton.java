package com.example.snowf.findingsatellites.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.snowf.findingsatellites.R;
import com.example.snowf.findingsatellites.RequestParameters;
import com.example.snowf.findingsatellites.UIHelper;

public class AddNoradButton extends AppCompatButton implements View.OnClickListener {

    public AddNoradButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public AddNoradButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public AddNoradButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            Activity activity = (Activity) getContext();
            LinearLayout noradContainer = activity.findViewById(R.id.norad_container);
            EditText noradEditText = activity.findViewById(R.id.norad_edit_text);
            int norad = Integer.parseInt(noradEditText.getText().toString());
            noradEditText.setText("");
            if (RequestParameters.getNorads().contains(norad)) {
                return;
            }
            TextView textView = new TextView(activity);
            textView.setText("" + norad);
            textView.setTextSize(13);
            textView.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    UIHelper.dpToPx(activity, 20));

            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.close));
            LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(
                    UIHelper.dpToPx(activity, 20),
                    UIHelper.dpToPx(activity, 10));
            imageViewLayoutParams.gravity = Gravity.CENTER;
            imageView.setForegroundGravity(Gravity.END);
            imageView.setPadding(UIHelper.dpToPx(activity, 10), 0, 0, 0);

            NoradLinearLayout linearLayout = new NoradLinearLayout(getContext());
            LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    UIHelper.dpToPx(activity, 30));
            if (RequestParameters.getNorads().size() != 0) {
                linearLayoutLayoutParams.setMargins(UIHelper.dpToPx(activity, 10), 0, 0, 0);
            }
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setBackgroundResource(R.drawable.circle);
            linearLayout.setPadding(
                    UIHelper.dpToPx(activity, 15),
                    UIHelper.dpToPx(activity, 5),
                    UIHelper.dpToPx(activity, 15),
                    UIHelper.dpToPx(activity, 5));
            linearLayout.addView(textView, textViewLayoutParams);
            linearLayout.addView(imageView, imageViewLayoutParams);

            noradContainer.addView(linearLayout, linearLayoutLayoutParams);
            RequestParameters.getNorads().add(norad);
        } catch (NumberFormatException ignored) {}
    }
}
