package com.strang6.tinkofffintechschool;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Strang6 on 30.10.2017.
 */

public class CoordinatesView extends LinearLayout {
    public CoordinatesView(Context context) {
        this(context, null);
    }

    public CoordinatesView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoordinatesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(getContext()).inflate(R.layout.coordinates_view, this);
    }

    public Float getCoordinateX() {
        EditText xEditText = (EditText) findViewById(R.id.xEditText);
        String x = xEditText.getText().toString();
        if (x.isEmpty())
            return null;
        return Float.parseFloat(x);
    }

    public void setCoordinateY(float y) {
        EditText yEditText = (EditText) findViewById(R.id.yEditText);
        yEditText.setText(Float.toString(y));
    }

    public void setCoordinateX(float x) {
        EditText xEditText = (EditText) findViewById(R.id.xEditText);
        xEditText.setText(Float.toString(x));
    }

    public Float getCoordinateY() {
        EditText yEditText = (EditText) findViewById(R.id.yEditText);
        String y = yEditText.getText().toString();
        if (y.isEmpty())
            return null;
        return Float.parseFloat(y);
    }
}
