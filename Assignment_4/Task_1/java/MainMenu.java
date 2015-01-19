package com.example.sam.Ass_04_T01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.Ass_04_T01.R;

import java.util.ArrayList;

public class MainMenu extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
    }

    public String food;
    // public int identifier  = this.getApplicationContext().getResources().getIdentifier("cake", "drawable", getPackageName());

    public void cakeClickHandler(View v) {
        Intent i = new Intent(getApplicationContext(),
                imageDetails.class);
        i.putExtra("food", "cake");
        startActivityForResult(i, 0);
        food = "cake";
    }

    public void dumplingClickHandler(View v) {
        Intent i = new Intent(getApplicationContext(),
                imageDetails.class);
        i.putExtra("food", "dumpling");
        startActivityForResult(i, 0);
        food = "dumpling";
    }

    // Resumes the activity
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null)
            Log.i("ON-ACTIVITY-RESULT-Intent", "IS NULL");
        else {
            Log.i("ON-ACTIVITY-RESULT-Intent", "Has DATA");
            ArrayList<imageData> imageData = intent.getParcelableArrayListExtra("IMAGE_DATA");
            imageData p = imageData.get(0);

            if(food.equals("cake")) {
                TextView detailsTextView = (TextView) findViewById(R.id.imageNameTextViewCake);
                detailsTextView.setText(p.toString());
            }
            else {
                TextView detailsTextView = (TextView) findViewById(R.id.imageNameTextViewDumplings);
                detailsTextView.setText(p.toString());
            }
        }
    }
}