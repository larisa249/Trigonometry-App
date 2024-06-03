package com.example.trigo;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Progres extends AppCompatActivity {

    private TextView scoresText;
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progres);
        scoresText = findViewById(R.id.scores_text);
        graph = findViewById(R.id.graph);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", "defaultUser");
        String userScoreKey = username + "_scores";
        String scores = prefs.getString(userScoreKey, "");
        if (!scores.isEmpty()) {
            String[] scoreArray = scores.split(";");
            DataPoint[] dataPoints = new DataPoint[scoreArray.length];
            for (int i = 0; i < scoreArray.length; i++) {
                dataPoints[i] = new DataPoint(i, Integer.parseInt(scoreArray[i]));
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            graph.addSeries(series);

            Viewport viewport = graph.getViewport();
            viewport.setYAxisBoundsManual(true);
            viewport.setMinY(0);
            viewport.setMaxY(15);




            graph.getGridLabelRenderer().setNumVerticalLabels(16);
        } else {
            scoresText.setText("Nu există scoruri salvate.");
        }
    }
}
