package com.example.sam.ass_03;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sam.ass_03.R;

public class Temperature extends Activity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);
        initializeUI();
    }

    private void initializeUI()
    {
        Button convertButton = (Button)findViewById(R.id.convertButton);
        convertButton.setOnClickListener(convertBtnListener);
        convertButtonClicked();
    }

    /** Handle convert button click */
    private View.OnClickListener convertBtnListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            convertButtonClicked();
        }
    };

    private void convertButtonClicked()
    {
        EditText inputTempText = (EditText) findViewById(R.id.inputTempEditText);
        TextView convertedText = (TextView) findViewById(R.id.convertedTempTextView);
        String frStr = convertToFh(inputTempText.getText().toString());
        convertedText.setText(frStr + " F");
    }

    private String convertToFh(String pCelcius)
    {
        try
        {
            double c = Double.parseDouble(pCelcius);
            double f = c * (9.0/5.0) + 32.0;
            return String.format("%3.2f", f);
        }
        catch (NumberFormatException nfe)
        {
            return "ERR";
        }
    }
}