package com.example.paintingapplication;

import static com.example.paintingapplication.MainActivity.paint_brush;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class DisplayShapes extends View {



    private Paint paint;

    private Path path;

    private List<PointF> points;

    private ShapeType currentShape;

    private int color;

    public DisplayShapes(Context context) {
        super(context);
        init(context);
    }

    public DisplayShapes(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DisplayShapes(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        path = new Path();
        points = new ArrayList<>();
        currentShape = ShapeType.CIRCLE;
    }


    public enum ShapeType {
        RECTANGLE, TRIANGLE, CIRCLE
    }

    public void setShapeType (ShapeType shapeType){
        this.currentShape = shapeType;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        PointF centerpoint = new PointF(getWidth()/2, getHeight()/2);

            switch (currentShape) {
                case RECTANGLE:
                    drawRect(canvas, centerpoint);
                    break;
                case TRIANGLE:
                    drawTriangle(canvas, centerpoint);
                    break;
                case CIRCLE:
                    drawCircle(canvas, centerpoint);
                    break;
        }
    }

    private void drawTriangle(Canvas canvas, PointF centerPoint) {
        float size = 200;
        float halfSize = size / 2;
        float x1 = centerPoint.x - halfSize;
        float y1 = centerPoint.y + halfSize;
        float x2 = centerPoint.x + halfSize;
        float y2 = centerPoint.y + halfSize;
        float x3 = centerPoint.x;
        float y3 = centerPoint.y - halfSize;

        path.reset();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close();

        canvas.drawPath(path, paint);
    }

    private void drawRect(Canvas canvas, PointF centerPoint) {
        float size = 200;
        float left = centerPoint.x - size / 2;
        float top = centerPoint.y - size / 2;
        float right = centerPoint.x + size / 2;
        float bottom = centerPoint.y + size / 2;

        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void drawCircle(Canvas canvas, PointF centerPoint) {
        float radius = 200;
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                points.add(new PointF(x, y));
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
        invalidate(); // Invalidate the view to trigger a redraw
    }
}



