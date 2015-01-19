package com.example.sam.ass_03;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class ImageDisplay extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodview);
        initializeUI();
    }

    private void initializeUI()   {
        // Pull the data from MainActivity.java
        Bundle dataBundle = getIntent().getExtras();
        String foodDesc = dataBundle.getString("description");
        String foodName = dataBundle.getString("name");
        Drawable foodImg = getResources().getDrawable(dataBundle.getInt("image"));

        // Find the data that needs to be replaced
        TextView foodDescView = (TextView) findViewById(R.id.foodDescView);
        TextView foodTextView = (TextView) findViewById(R.id.foodTextView);
        android.widget.ImageView foodImageView = (android.widget.ImageView) findViewById(R.id.foodImageView);

        // Replace data with the correct information
        foodDescView.setText(foodDesc);
        foodTextView.setText(foodName);
        foodImageView.setImageDrawable(foodImg);
    }
}