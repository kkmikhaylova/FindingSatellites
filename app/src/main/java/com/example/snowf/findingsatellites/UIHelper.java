package com.example.snowf.findingsatellites;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.example.snowf.findingsatellites.views.NoradLinearLayout;

public class UIHelper {

    static void drawRangeBar(Activity activity, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                height - dpToPx(activity, 110),
                dpToPx(activity, 60));
        layoutParams.setMargins(
                - (height / 2 - dpToPx(activity, 85)),
                height / 2 - dpToPx(activity, 40),
                0, 0);
        RangeBar rangeSeekBar = activity.findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setLayoutParams(layoutParams);
        rangeSeekBar.setTickCount(38);
        rangeSeekBar.setTickHeight(4);
        rangeSeekBar.setBarColor(Color.WHITE);
        rangeSeekBar.setConnectingLineColor(ContextCompat.getColor(activity, R.color.colorRangeBar));
        rangeSeekBar.setThumbColorNormal(ContextCompat.getColor(activity, R.color.colorRangeBar));
        rangeSeekBar.setThumbColorPressed(ContextCompat.getColor(activity, R.color.colorRangeBar));
        rangeSeekBar.setThumbRadius(10);

        final TextView tvMin = activity.findViewById(R.id.tv1);
        final TextView tvMax = activity.findViewById(R.id.tv2);

        rangeSeekBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                int min = 200 + (leftThumbIndex > 23 ? 23 : leftThumbIndex) * 100;
                if (leftThumbIndex >= 24) min += 500;
                if (leftThumbIndex > 24) min += (leftThumbIndex > 31 ? 7 : leftThumbIndex - 24) * 1000;
                if (leftThumbIndex > 31) min += (leftThumbIndex - 31) * 10000;
                if (min < 200) min = 200;
                RequestParameters.setMinHeight(min);
                RequestParameters.setLeftIndex(leftThumbIndex);
                int max = 200 + (rightThumbIndex > 23 ? 23 : rightThumbIndex) * 100;
                if (rightThumbIndex >= 24) max += 500;
                if (rightThumbIndex > 24)max += (rightThumbIndex > 31 ? 7 : rightThumbIndex - 24) * 1000;
                if (rightThumbIndex > 31) max += (rightThumbIndex - 31) * 10000;
                if (max > 70000) max = 70000;
                RequestParameters.setMaxHeight(max);
                RequestParameters.setRightIndex(rightThumbIndex);
                tvMin.setText(String.valueOf(min));
                tvMax.setText(String.valueOf(max));
            }
        });
    }

    static void drawHint(Activity activity, int height) {
        activity.findViewById(R.id.hint_layout)
                .setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height - dpToPx(activity, 190)));
    }

    static void restoreRequestParameters(Activity activity) {
        RangeBar rangeSeekBar = activity.findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setThumbIndices(RequestParameters.getLeftIndex(), RequestParameters.getRightIndex());
        Button trashButton = activity.findViewById(R.id.trash_button);
        if (RequestParameters.isShowTrash()) {
            trashButton.setTextColor(ContextCompat.getColor(activity, R.color.colorFon));
            trashButton.setBackground(ContextCompat.getDrawable(activity, R.drawable.circle_right));
        } else {
            trashButton.setTextColor(ContextCompat.getColor(activity, R.color.colorType));
            trashButton.setBackground(ContextCompat.getDrawable(activity, R.drawable.circle_right_stroke));
        }
        Button satelliteButton = activity.findViewById(R.id.satellite_button);
        if (RequestParameters.isShowSatellite()) {
            satelliteButton.setTextColor(ContextCompat.getColor(activity, R.color.colorFon));
            satelliteButton.setBackground(ContextCompat.getDrawable(activity, R.drawable.circle_left));
        } else {
            satelliteButton.setTextColor(ContextCompat.getColor(activity, R.color.colorType));
            satelliteButton.setBackground(ContextCompat.getDrawable(activity, R.drawable.circle_left_stroke));
        }

        for (int norad : RequestParameters.getNorads()) {
            TextView textView = new TextView(activity);
            textView.setText("" + norad);
            textView.setTextSize(13);
            textView.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    UIHelper.dpToPx(activity, 20));

            ImageView imageView = new ImageView(activity);
            imageView.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.close));
            LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(
                    UIHelper.dpToPx(activity, 20),
                    UIHelper.dpToPx(activity, 10));
            imageViewLayoutParams.gravity = Gravity.CENTER;
            imageView.setForegroundGravity(Gravity.END);
            imageView.setPadding(UIHelper.dpToPx(activity, 10), 0, 0, 0);

            NoradLinearLayout linearLayout = new NoradLinearLayout(activity);
            LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
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

            LinearLayout noradContainer = activity.findViewById(R.id.norad_container);
            noradContainer.addView(linearLayout, linearLayoutLayoutParams);
        }
    }

    static void restoreTitleVisibilityParameters(Activity activity) {
        Button button = activity.findViewById(R.id.show_titles_button);
        if (SatelliteParameters.isShowTitle()) {
            button.setBackground(ContextCompat.getDrawable(activity, R.drawable.hide));
        } else {
            button.setBackground(ContextCompat.getDrawable(activity, R.drawable.view));
        }
    }

    public static int dpToPx(Context context, float dpValue) {
        return (int)((dpValue * context.getResources().getDisplayMetrics().density) + 0.5);
    }
}
