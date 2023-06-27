package com.example.paintingapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.graphics.Path;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import static com.example.paintingapplication.display.colorList;
import static com.example.paintingapplication.display.current_brush;
import static com.example.paintingapplication.display.pathList;

import com.google.android.material.slider.Slider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {


    private ConstraintLayout constraintLayout;

    private Slider mainSlider;
    private float strokeWidth;
    public static Path path = new Path();
    public static Paint paint_brush = new Paint();

    private Button menuButton;

    private Button saveButton;

    private Button clearButton;

    private int defaultColor;

    private Button colorPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clearButton = findViewById(R.id.buttonClear);
        colorPicker = findViewById(R.id.button7);
        constraintLayout = findViewById(R.id.container);
        defaultColor= ContextCompat.getColor(MainActivity.this, R.color.black);
        menuButton = findViewById(R.id.menuButton);
        saveButton = findViewById(R.id.buttonSave);
        mainSlider = findViewById(R.id.main_slider);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRectActivity(view);
            }
        });


        colorPicker.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                openColorPicker();
            }
        });


       mainSlider.addOnChangeListener(new Slider.OnChangeListener() {
           @Override
           public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
               strokeWidth = value;
               paint_brush.setStrokeWidth(strokeWidth);
           }
       });
    }

    /*
    //Pencil Logic
    public void updateStrokeWidth() {
            paint_brush.setStrokeWidth(strokeWidth);
               strokeWidth = value;
             paint_brush.setStrokeWidth(strokeWidth);
    }
*/

    public void eraseLastPath (View view) {
      if (!pathList.isEmpty()) {
          int lastIndex = pathList.size()-1;
          pathList.remove(lastIndex);
          colorList.remove(lastIndex);
          path.reset();
          view.invalidate();
      }
    }

    public void eraser(View view) {
        pathList.clear();
        colorList.clear();
        path.reset();

    }

    public void pencil(View view) {
        paint_brush.setColor(Color.BLACK);
        currentColor(paint_brush.getColor());
    }

    public void redColor(View view) {
        paint_brush.setColor(Color.RED);
        currentColor(paint_brush.getColor());
    }

    public void yellowColor(View view) {
        paint_brush.setColor(Color.YELLOW);
        currentColor(paint_brush.getColor());
    }

    public void greenColor(View view) {
        paint_brush.setColor(Color.GREEN);
        currentColor(paint_brush.getColor());
    }

    public void magentaColor(View view) {
        paint_brush.setColor(Color.MAGENTA);
        currentColor(paint_brush.getColor());
    }

    public void blueColor(View view) {
        paint_brush.setColor(Color.BLUE);
        currentColor(paint_brush.getColor());
    }

    public void currentColor(int c) {
        current_brush = c;
        path = new Path();
    }

    //colorPicker
      private void openColorPicker() {
          AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
              @Override
              public void onCancel(AmbilWarnaDialog dialog) {

              }

              @Override
              public void onOk(AmbilWarnaDialog dialog, int color) {
                    defaultColor = color;
                    paint_brush.setColor(color);
                    currentColor(paint_brush.getColor());
              }
          });
            ambilWarnaDialog.show();
    }

//Save Logic


    private void saveImage () {
        constraintLayout.setDrawingCacheEnabled(true);
        constraintLayout.buildDrawingCache();
        constraintLayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = constraintLayout.getDrawingCache();
        save(bitmap);
    }

    private void save(Bitmap bitmap) {

        File directory = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "Drawing.jpg";
        File myFile = new File(directory, fileName);

        if (myFile.exists()) {
            myFile.delete();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(myFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(this, "Saved... : " + directory, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "save: "+ directory);
            constraintLayout.setDrawingCacheEnabled(false);

        }

        catch (Exception e) {
            Toast.makeText(this, "Error : " +e.toString(), Toast.LENGTH_SHORT).show();
        }


    }



  //Starten der RectActivity

    public void startRectActivity (View view) {
        Intent intent = new Intent(this, RectActivity.class);
        startActivity(intent);
    }

}



