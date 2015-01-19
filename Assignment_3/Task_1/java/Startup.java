package com.example.sam.ass_03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sam.ass_03.R;


public class Startup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    // Start the Temperature Conversion Activity
    public void tempActivity(View view) {
        Intent intent = new Intent(this, Temperature.class);
        startActivity(intent);
    }

    // Start the Height Conversion Activity
    public void heightActivity(View view) {
        Intent intent = new Intent(this, Height.class);
        startActivity(intent);
    }
}
