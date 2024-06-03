package com.example.trigo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class Grafic extends AppCompatActivity {

    private EditText editTextFunction;
    private Button buttonPlot;
    private GraphView graph;
    private MediaPlayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafic);

        editTextFunction = (EditText) findViewById(R.id.editTextFunction);
        buttonPlot = (Button) findViewById(R.id.buttonPlot);
        graph = (GraphView) findViewById(R.id.graph);

        buttonPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(Grafic.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                String function = editTextFunction.getText().toString().trim().toLowerCase();
                plotFunction(function);
            }
        });
    }

    private void plotFunction(String expressionText) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        Expression expression;

        for (int x = -180; x <= 180; x++) {
            try {
                expression = new ExpressionBuilder(expressionText)
                        .variables("x")
                        .build()
                        .setVariable("x", x);
                double y = expression.evaluate();
                series.appendData(new DataPoint(x, y), true, 721);
            } catch (Exception e) {
                Toast.makeText(this, "Eroare la evaluarea expresiei: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        }
        series.setThickness(8);
        graph.removeAllSeries();
        graph.addSeries(series);
        setGraphViewports();
    }


    private void setGraphViewports() {
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-5);
        graph.getViewport().setMaxY(5);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-50);
        graph.getViewport().setMaxX(50);

        graph.getViewport().setScalable(true);

    }
}
