package com.example.sam.Ass_04_T01;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.sam.Ass_04_T01.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class imageDetails extends Activity {

    imageData ImageData;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_menu);
        initializeUI();
    }
    private void initializeUI() {
        // Set the layout to the edit_menu
        setContentView(R.layout.edit_menu);
        Intent intent = getIntent();
        String whichFood = intent.getStringExtra("food");
        ImageView foodImageView = (ImageView) findViewById(R.id.imageMini);
        Log.i("IMAGE DETAILS", whichFood);

        if(whichFood.equals("cake")) {
            foodImageView.setImageDrawable((getResources().getDrawable(R.drawable.cake)));
        }
        else {
            foodImageView.setImageDrawable((getResources().getDrawable(R.drawable.dumpling)));
        }

        // Get the content from the fields
        EditText imageNameText = (EditText) findViewById(R.id.imageNameText);
        EditText locationText = (EditText) findViewById(R.id.locationText);
        EditText keywordText = (EditText) findViewById(R.id.keywordText);
        DatePicker datePickerText = (DatePicker) findViewById(R.id.datePickerText);
        ToggleButton shareToggleButton = (ToggleButton) findViewById(R.id.shareToggleButton);
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText ratingText = (EditText) findViewById(R.id.ratingText);

        // If any field is focused, call fcl to store the data in storeImageData()
        imageNameText.setOnFocusChangeListener(fcl);
        locationText.setOnFocusChangeListener(fcl);
        keywordText.setOnFocusChangeListener(fcl);
        datePickerText.setOnFocusChangeListener(fcl);
        shareToggleButton.setOnFocusChangeListener(fcl);
        emailText.setOnFocusChangeListener(fcl);
        ratingText.setOnFocusChangeListener(fcl);
    }

    // When fcl is triggered, send data to storeImageData()
    View.OnFocusChangeListener fcl = new View.OnFocusChangeListener() {
        public void onFocusChange(View v, boolean hasFocus) {
            storeImageData();
        }
    };

    // When the back button is pressed, create a new intent and store all the current information in an array
    public void onBackPressed() {
        Log.i("IMAGE DETAILS", "Back Button Pressed");
        storeImageData();
        Intent resultIntent = new Intent();
        ArrayList<imageData> dataList = new ArrayList<imageData>();
        dataList.add(ImageData);
        resultIntent.putParcelableArrayListExtra("IMAGE_DATA", dataList);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    // Get the content from the fields and then re-assign it to a string
    private void storeImageData() {
        EditText imageNameText = (EditText) findViewById(R.id.imageNameText);
        EditText locationText = (EditText) findViewById(R.id.locationText);
        EditText keywordText = (EditText) findViewById(R.id.keywordText);
        DatePicker datePickerText = (DatePicker) findViewById(R.id.datePickerText);
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText ratingText = (EditText) findViewById(R.id.ratingText);

        String imageName = imageNameText.getText().toString();
        String location = locationText.getText().toString();
        String keyword = keywordText.getText().toString();
        int dayPicker = datePickerText.getDayOfMonth();
        int monthPicker = datePickerText.getMonth()+1;
        int yearPicker = datePickerText.getYear();
        String email = emailText.getText().toString();
        String rating = ratingText.getText().toString();

        // If there is nothing already saved in ImageData (fresh activity), create a new imageData
        if (ImageData == null)
            ImageData = new imageData(imageName, location, keyword, dayPicker, monthPicker, yearPicker, shareToggle, email, rating);
        // Otherwise update imageData with new information
        else {
            // If the name and email fields are not empty & valid, send the information
            if (imageName != null && isEmailValid(email))  {
                ImageData.update(imageName, location, keyword, dayPicker, monthPicker, yearPicker, shareToggle, email, rating);
            }
            // If the name is empty, display error toast
            if (imageName.matches("")){
                Toast.makeText(getApplicationContext(), "Empty name!",
                        Toast.LENGTH_SHORT).show();
            }
            // If the email is invalid, display error toast
            if (!isEmailValid(email)){
                Toast.makeText(getApplicationContext(), "Invalid email!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        // Send information to log to see if data is being sent correctly
        Log.i("UPDATE IMAGE INFO", ImageData.toString());
    }

    // Listen to see if Toggle is toggled and if yes, set shareToggle to yes, otherwise set it to no
    String shareToggle;
    public void onToggleClicked(View view) {
        boolean toggled = ((ToggleButton) view).isChecked();
        if (toggled)
            shareToggle = "yes";
        else
            shareToggle = "no";
    }

    // Method to check if email is valid
    // http://stackoverflow.com/a/6119777/1461021
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}