package com.strang6.tinkofffintechschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Map;
import java.util.TreeMap;


public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraphView graphView = (GraphView) findViewById(R.id.graphView);

        Bundle bundle = getIntent().getExtras();
        TreeMap<Float, Float> coordinates = new TreeMap<>((Map<Float, Float>) bundle.getSerializable("coordinates"));
        graphView.setCoordinates(coordinates);

        int graphColor = bundle.getInt("graphColor");
        graphView.setColor(graphColor);

        boolean isSmoothing = bundle.getBoolean("isSmoothing");
        graphView.setSmoothing(isSmoothing);

        String xSign = bundle.getString("xSign");
        String ySign = bundle.getString("ySign");
        graphView.setSign(xSign, ySign);

        if (bundle.containsKey("xStep") && bundle.containsKey("yStep")) {
            float xStep = bundle.getFloat("xStep");
            float yStep = bundle.getFloat("yStep");
            graphView.setStep(xStep, yStep);
        }

        if (bundle.containsKey("isGrid")) {
            boolean isGrid = bundle.getBoolean("isGrid");
            graphView.setGrid(isGrid);
        }
    }
}
