package com.strang6.tinkofffintechschool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Strang6 on 29.10.2017.
 */

public class GraphView extends View {
    private Map<Float, Float> coordinates;
    private int graphColor;
    private boolean isSmoothing, isGrid;
    private String xSign, ySign;
    private Float xStep, yStep;
    private static final float GRAPH_SMOOTHNESS = 0.15f, textSize = 48, stepTextSize = 40;
    private Paint graphPaint, coordinatesPaint, stepPaint;

    public GraphView(Context context) {
        this(context, null);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setCoordinates(Map<Float, Float> coordinates) {
        this.coordinates = coordinates;
    }

    public void setColor(int graphColor) {
        this.graphColor = graphColor;
    }

    public void setSmoothing(boolean isSmoothing) {
        this.isSmoothing = isSmoothing;
    }

    public void setStep(float xStep, float yStep) {
        this.xStep = xStep;
        this.yStep = yStep;
    }

    public void setGrid(boolean isGrid) {
        this.isGrid = isGrid;
    }

    public void setSign(String xSign, String ySign) {
        this.xSign = xSign;
        this.ySign = ySign;
    }

    private void init() {
        graphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        graphPaint.setStyle(Paint.Style.STROKE);
        graphPaint.setStrokeWidth(5);
        coordinatesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        coordinatesPaint.setStyle(Paint.Style.STROKE);
        coordinatesPaint.setColor(Color.BLACK);
        coordinatesPaint.setStrokeWidth(3);
        coordinatesPaint.setTextSize(textSize);
        stepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        stepPaint.setStrokeWidth(2);
        stepPaint.setStyle(Paint.Style.STROKE);
        stepPaint.setColor(Color.DKGRAY);
        stepPaint.setTextSize(stepTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCoordinates(canvas);
        graphPaint.setColor(graphColor);
        Path graphPath;
        if (isSmoothing) {
            graphPath = createSmoothPath();
        } else {
            graphPath = createPath();
        }
        canvas.drawPath(graphPath, graphPaint);
    }

    private Path createSmoothPath() {
        int size = coordinates.size();
        Float[] x = new Float[size], y = new Float[size];
        x = coordinates.keySet().toArray(x);
        y = coordinates.values().toArray(y);

        Path path = new Path();
        path.moveTo(getPosX(x[0]), getPosY(y[0]));
        for (int i = 0; i < size - 1; i++) {
            float currentX = getPosX(x[i]);
            float currentY = getPosY(y[i]);
            float nextX = getPosX(x[i + 1]);
            float nextY = getPosY(y[i + 1]);

            int n = (i == 0) ? 0 : (i - 1);
            float startDiffX = nextX - getPosX(x[n]);
            float startDiffY = nextY - getPosY(y[n]);
            n = ((i + 2) > (size - 1)) ? (size - 1) : (i + 2);
            float endDiffX = getPosX(x[n]) - currentX;
            float endDiffY = getPosY(y[n]) - currentY;

            float firstControlX = currentX + (GRAPH_SMOOTHNESS * startDiffX);
            float firstControlY = currentY + (GRAPH_SMOOTHNESS * startDiffY);
            float secondControlX = nextX - (GRAPH_SMOOTHNESS * endDiffX);
            float secondControlY = nextY - (GRAPH_SMOOTHNESS * endDiffY);

            path.cubicTo(firstControlX, firstControlY, secondControlX, secondControlY, nextX, nextY);
        }
        return path;
    }

    private Path createPath() {
        Path path = new Path();
        Iterator<Float> x = coordinates.keySet().iterator();
        Iterator<Float> y = coordinates.values().iterator();
        path.moveTo(getPosX(x.next()), getPosY(y.next()));
        while (x.hasNext()) {
            path.lineTo(getPosX(x.next()), getPosY(y.next()));
        }
        return path;
    }

    private void drawCoordinates(Canvas canvas) {
        float left = getPaddingLeft(), top = getPaddingTop();
        float right = getWidth() - getPaddingRight();
        float bottom = getHeight() - getPaddingBottom();
        canvas.drawRect(left, top, right, bottom, coordinatesPaint);
        if (xSign != null && ySign != null) {
            canvas.drawText(xSign, right - textSize, bottom, coordinatesPaint);
            canvas.drawText(ySign, left, top + textSize, coordinatesPaint);
        }
        if (xStep != null && yStep != null) {
            float lineSize = 24;
            float maxX = getMaxX();
            float x = getMinX();
            float posX = getPosX(x);
            while (x <= maxX) {
                canvas.drawLine(posX, bottom - lineSize, posX, bottom + lineSize, stepPaint);
                canvas.drawText(Float.toString(x), posX, bottom + stepTextSize, stepPaint);
                if (isGrid) {
                    canvas.drawLine(posX, bottom, posX, top, stepPaint);
                }
                x += xStep;
                posX = getPosX(x);
            }
            float maxY = getMaxY();
            float y = getMinY();
            float posY = getPosY(y);
            while (y <= maxY) {
                canvas.drawLine(left - lineSize, posY, left + lineSize, posY, stepPaint);
                String yText = Float.toString(y);
                canvas.drawText(yText, left - stepTextSize * (yText.length() - 1.5f), posY, stepPaint);
                if (isGrid) {
                    canvas.drawLine(left, posY, right, posY, stepPaint);
                }
                y += yStep;
                posY = getPosY(y);
            }
        }
    }

    private float getMaxX() {
        return Collections.max(coordinates.keySet());
    }

    private float getMinX() {
        return Collections.min(coordinates.keySet());
    }

    private float getMaxY() {
        return Collections.max(coordinates.values());
    }

    private float getMinY() {
        return Collections.min(coordinates.values());
    }

    private float getPosY(float y) {
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        float maxY = getMaxY();
        float minY = getMinY();
        float posY = height / (maxY - minY) * (y - minY);
        posY = height - posY + getPaddingTop();
        return posY;
    }

    private float getPosX(float x) {
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float maxX = getMaxX();
        float minX = getMinX();
        float posX = width / (maxX - minX) * (x - minX);
        posX += getPaddingLeft();
        return posX;
    }

}
