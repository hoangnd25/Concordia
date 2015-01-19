package swindroid.suntime.ui;
import swindroid.suntime.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


public class addCity extends Activity {

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcity);
        Button next = (Button) findViewById(R.id.addCity);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    storeData();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void storeData() throws IOException {
        EditText cityNameText = (EditText)findViewById(R.id.cityNameEditText);
        EditText latitudeText = (EditText)findViewById(R.id.latitudeEditText);
        EditText longitudeText = (EditText)findViewById(R.id.longitudeEditText);
        EditText timeZoneText = (EditText)findViewById(R.id.timeZoneEditText);

        String cityName = cityNameText.getText().toString();
        String latitude = latitudeText.getText().toString();
        String longitude = longitudeText.getText().toString();
        String timeZone = timeZoneText.getText().toString();

        String addToCityFile = cityName +","+ latitude +","+ longitude +","+ timeZone;
        System.out.println(addToCityFile);

        writeToFile(addToCityFile);
    }
    private void writeToFile(String data) throws IOException {
        String FILENAME = "test4";
        String string = data;
        String newline = "\r\n";
        FileOutputStream fos = openFileOutput(FILENAME,
                Context.MODE_APPEND);
        fos.write(string.getBytes());
        fos.write(newline.getBytes());
        fos.close();
    }
}