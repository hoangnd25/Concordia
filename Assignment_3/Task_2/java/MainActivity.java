package com.example.sam.ass_03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    // Data for each of the foods (description, title, image)
    public void souvlakiImgBtn(View v) { showFoodView("\"Greek fast food consisting of small pieces of meat and sometimes vegetables grilled on a skewer.\"", "Souvlaki", R.drawable.souvlaki); }
    public void pieImgBtn(View v) { showFoodView("\"A French Canadian dish similar to English cottage pie or shepherd's pie.\"", "Pâté Chinois", R.drawable.pie); }
    public void dumplingImgBtn(View v) { showFoodView("\"Wontons filled with pork and served with a peanut butter chili sauce.\"", "Peanut Butter Dumplings", R.drawable.dumpling); }
    public void cakeImgBtn(View v) { showFoodView("\"A cake, often sponge cake, which is made with coffee or has a coffee flavor.\"", "Coffee Cake", R.drawable.cake); }


    private void showFoodView(String foodDesc, String foodName, int drawableImage) {
        Bundle dataBundle = new Bundle();
        dataBundle.putString("description", foodDesc);
        dataBundle.putString("name", foodName);
        dataBundle.putInt("image", drawableImage);

        // Start the Activity and Create the Bundle of information to be sent
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ImageDisplay.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }
}