package com.strang6.tinkofffintechschool;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import java.util.Iterator;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private int graphColor = Color.BLACK;
    private CheckBox smoothingCheckBox, signCheckBox, stepsCheckBox, gridCheckBox;
    private LinearLayout coordinatesLayout;
    private EditText xSignEditText, ySignEditText, xStepEditText, yStepEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smoothingCheckBox = (CheckBox) findViewById(R.id.smoothingCheckBox);
        coordinatesLayout = (LinearLayout) findViewById(R.id.coordinatesLayout);
        xSignEditText = (EditText) findViewById(R.id.xSignEditText);
        ySignEditText = (EditText) findViewById(R.id.ySignEditText);
        signCheckBox = (CheckBox) findViewById(R.id.signCheckBox);
        xStepEditText = (EditText) findViewById(R.id.xStepEditText);
        yStepEditText = (EditText) findViewById(R.id.yStepEditText);
        stepsCheckBox = (CheckBox) findViewById(R.id.stepsCheckBox);
        gridCheckBox = (CheckBox) findViewById(R.id.gridCheckBox);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        boolean isSign = savedInstanceState.getBoolean("isSign");
        xSignEditText.setVisibility(isSign ? View.VISIBLE : View.INVISIBLE);
        ySignEditText.setVisibility(isSign ? View.VISIBLE : View.INVISIBLE);

        boolean isStep = savedInstanceState.getBoolean("isStep");
        xStepEditText.setVisibility(isStep ? View.VISIBLE : View.INVISIBLE);
        yStepEditText.setVisibility(isStep ? View.VISIBLE : View.INVISIBLE);
        gridCheckBox.setEnabled(isStep);

        TreeMap<Float, Float> coordinates = (TreeMap<Float, Float>) savedInstanceState.getSerializable("coordinates");
        if (coordinates != null) {
            int count = coordinates.size() - coordinatesLayout.getChildCount();
            for (; count > 0; count--) {
                coordinatesLayout.addView(new CoordinatesView(this));
            }
            Iterator<Float> xIterator = coordinates.keySet().iterator();
            Iterator<Float> yIterator = coordinates.values().iterator();
            CoordinatesView coordinatesView;
            int i = 0;
            while (xIterator.hasNext() && yIterator.hasNext()) {
                coordinatesView = (CoordinatesView) coordinatesLayout.getChildAt(i);
                coordinatesView.setCoordinateX(xIterator.next());
                coordinatesView.setCoordinateY(yIterator.next());
                i++;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isSign", signCheckBox.isChecked());
        outState.putBoolean("isStep", stepsCheckBox.isChecked());
        outState.putSerializable("coordinates", getCoordinates());
        super.onSaveInstanceState(outState);
    }

    public void okOnClick(View view) {
        Intent intent = new Intent(this, GraphActivity.class);
        intent.putExtra("graphColor", graphColor);
        intent.putExtra("isSmoothing", smoothingCheckBox.isChecked());
        //sign
        if (signCheckBox.isChecked()) {
            String xSign = xSignEditText.getText().toString();
            String ySign = ySignEditText.getText().toString();
            if (xSign.isEmpty() || ySign.isEmpty()) {
                Toast.makeText(this, "Проверьте подписи осей", Toast.LENGTH_SHORT).show();
                return;
            }
            intent.putExtra("xSign", xSign);
            intent.putExtra("ySign", ySign);
        }
        //steps
        if (stepsCheckBox.isChecked()) {
            String xStep = xStepEditText.getText().toString();
            String yStep = yStepEditText.getText().toString();
            if (xStep.isEmpty() || yStep.isEmpty() || xStep.equals("0") || yStep.equals("0")) {
                Toast.makeText(this, "Проверьте шаги", Toast.LENGTH_SHORT).show();
                return;
            }
            intent.putExtra("xStep", Float.parseFloat(xStep));
            intent.putExtra("yStep", Float.parseFloat(yStep));
        }
        //grid
        if (gridCheckBox.isEnabled()) {
            intent.putExtra("isGrid", gridCheckBox.isChecked());
        }
        //coordinates
        TreeMap<Float, Float> coordinates = getCoordinates();
        if (coordinates == null) {
            Toast.makeText(this, "Проверьте координаты", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("coordinates", coordinates);
        startActivity(intent);
    }

    public TreeMap<Float, Float> getCoordinates() {
        TreeMap<Float, Float> coordinates = new TreeMap<>();
        int count = coordinatesLayout.getChildCount();
        CoordinatesView coordinatesView;
        Float x, y;
        for (int i = 0; i < count; i++) {
            coordinatesView = (CoordinatesView) coordinatesLayout.getChildAt(i);
            if ((x = coordinatesView.getCoordinateX()) == null ||
                    (y = coordinatesView.getCoordinateY()) == null ||
                    coordinates.containsKey(x)) {
                return null;
            }
            coordinates.put(x, y);
        }
        return coordinates;
    }

    public void colorOnClick(View view) {
        ColorPickerDialog.newBuilder().setColor(Color.BLUE).show(this);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        graphColor = color;
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    public void addCoordinatesOnClick(View view) {
        coordinatesLayout.addView(new CoordinatesView(this));
    }

    public void deleteCoordinatesOnClick(View view) {
        int count = coordinatesLayout.getChildCount();
        if (count != 2) {
            coordinatesLayout.removeViewAt(count - 1);
        }
    }

    public void onVisibilityClick(View view) {
        int visibility;
        if (((CheckBox) view).isChecked()) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.INVISIBLE;
        }
        switch (view.getId()) {
            case R.id.signCheckBox:
                xSignEditText.setVisibility(visibility);
                ySignEditText.setVisibility(visibility);
                break;
            case R.id.stepsCheckBox:
                xStepEditText.setVisibility(visibility);
                yStepEditText.setVisibility(visibility);
                gridCheckBox.setEnabled(visibility == View.VISIBLE);
        }
    }
}
