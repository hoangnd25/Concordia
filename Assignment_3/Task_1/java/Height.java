package com.example.sam.ass_03;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sam.ass_03.R;


public class Height extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.height);
        initializeUI();
    }

    private void initializeUI()
    {
        Button convertButton = (Button)findViewById(R.id.convertButton);
        convertButton.setOnClickListener(convertBtnListener);
        convertButtonClicked();
    }

    /** Handle convert button click */
    private OnClickListener convertBtnListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            convertButtonClicked();
        }
    };
    public void meterClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            boolean checked = true;
        }
    }

    private void convertButtonClicked() {
        // Find the ID's of the text fields of Feet, Inches, Meter Checkbox and hidden Text Message
        EditText inputFeetTempText = (EditText) findViewById(R.id.inputFeetTempEditText);
        EditText inputInchTempText = (EditText) findViewById(R.id.inputInchTempEditText);
        TextView convertedText = (TextView) findViewById(R.id.convertedTempTextView);
        CheckBox meterCheckBox = (CheckBox) findViewById(R.id.checkbox_meter);
        TextView setUnRealisticHuman = (TextView)findViewById(R.id.unRealisticHuman);
        // Pass the Inch & Feet variables through the convertToCm script
        String cmStr = (convertToCm(inputFeetTempText.getText().toString(), inputInchTempText.getText().toString()));
        // If the number of cm's is greater than 240cm, it is an unrealistic human height and should show an error msg
        double cmDouble = Double.parseDouble(cmStr);
        if (cmDouble>240){
            setUnRealisticHuman.setVisibility(View.VISIBLE); //SHOW the button
        }
        else {
            setUnRealisticHuman.setVisibility(View.INVISIBLE); //HIDE the button
        }

        // If checkbox is checked convert to meters, otherwise leave as cm
        if (meterCheckBox.isChecked()) {
            String mStr = (convertToM(cmStr));
            convertedText.setText(mStr + " m");
        }
        else {
            convertedText.setText(cmStr + " cm");
        }
    }

    private String convertToCm(String pFeet, String pInch)
    {
        try
        {
            double f = Double.parseDouble(pFeet);
            double cf = f / 0.032808;
            double i = Double.parseDouble(pInch);
            double ci = i / 0.39370;
            double c = cf + ci;
            return String.format("%3.2f", c);
        }
        catch (NumberFormatException nfe)
        {
            return "ERR";
        }
    }
    private String convertToM(String pCm)
    {
        try
        {
            double cm = Double.parseDouble(pCm);
            double m = cm / 100;
            return String.format("%3.2f", m);
        }
        catch (NumberFormatException nfe)
        {
            return "ERR";
        }
    }
}
