package com.example.snowf.findingsatellites;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

public class SpaceActivity extends AppCompatActivity implements LoadSatelliteParametersATListener {
    private SensorListener sensorListener;
    private String siteName = "sitename";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);

        sensorListener = new SensorListener(this);
        new LoadSatelliteParametersAT(this).execute(siteName);

        UIHelper.restoreTitleVisibilityParameters(this);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RenderingSatellitesView r = findViewById(R.id.renderingSatellitesView);
                r.surfaceDestroyed(r.getHolder());
                Intent intent = new Intent(SpaceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.container).setOnTouchListener(new CustomOnTouchListener());
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorListener.stop();
        RenderingSatellitesView r = findViewById(R.id.renderingSatellitesView);
        r.surfaceDestroyed(r.getHolder());
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorListener.start();
        new LoadSatelliteParametersAT(this).execute(siteName);
    }

    @Override
    public void onLoadFinished() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        new LoadSatelliteParametersAT(this).execute(siteName);
    }
}
