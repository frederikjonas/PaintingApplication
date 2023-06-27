package com.example.paintingapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import yuku.ambilwarna.AmbilWarnaDialog;

public class RectActivity extends AppCompatActivity {

    private DisplayShapes displayShapes;
    private Button rectButton;
    private Button triangleButton;
    private Button circleButton;

    private int defaultColor;

    private Button changeToMain;

    private Button colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect);

        displayShapes = findViewById(R.id.displayShapes);
        colorPicker = findViewById(R.id.colorLens);
        rectButton = findViewById(R.id.rectangles);
        triangleButton = findViewById(R.id.triangle);
        circleButton = findViewById(R.id.circle_center);
        defaultColor= ContextCompat.getColor(RectActivity.this, R.color.black);
        changeToMain = findViewById(R.id.menuButton2);


        changeToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity(view);
            }
        });

        rectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawRectangle();
            }
        });

        triangleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawTriangle();
            }
        });

        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawCircle();
            }
        });

        colorPicker.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                openColorPicker();
            }
        });



    }

    private void drawRectangle() {
        DisplayShapes display = (DisplayShapes) displayShapes;
        display.setShapeType(DisplayShapes.ShapeType.RECTANGLE);
        display.invalidate();
    }

    private void drawTriangle() {
        DisplayShapes display = (DisplayShapes) displayShapes;
        display.setShapeType(DisplayShapes.ShapeType.TRIANGLE);
        display.invalidate();
    }

    private void drawCircle() {
        DisplayShapes display = (DisplayShapes) displayShapes;
        display.setShapeType(DisplayShapes.ShapeType.CIRCLE);
        display.invalidate();
    }

    private void openColorPicker() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                displayShapes.setColor(color);
                displayShapes.invalidate();
            }
        });
        ambilWarnaDialog.show();
    }


    public void startMainActivity (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
