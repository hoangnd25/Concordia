package swindroid.suntime.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import swindroid.suntime.R;
import swindroid.suntime.ui.Main;
import swindroid.suntime.calc.AstronomicalCalendar;
import swindroid.suntime.calc.GeoLocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Table extends Activity {
    private String CityName;
    private String latitude;
    private String longitude;
    private String tZone;
    private int year;
    private int month;
    private int day;
    private String sunRise;
    private String sunSet;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablelayout);
        initializeUI();
    }

    private void initializeUI(){
        TableLayout datesTable = (TableLayout) findViewById(R.id.tableLayout);
        Intent intent = getIntent();
        day = intent.getIntExtra("day", 0);
        month = intent.getIntExtra("month", 0);
        year = intent.getIntExtra("year", 0);
        CityName = intent.getStringExtra("city");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        for (int i = 0; i <7; i++) {
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView dateText = new TextView(this);
            TextView date = new TextView(this);
            TextView sunrise = new TextView(this);
            TextView sunriseTime = new TextView(this);
            TextView sunset = new TextView(this);
            TextView sunsetTime = new TextView(this);

            updateTime(year, month, (day+1), CityName, latitude, longitude, tZone);

            dateText.setText("Date: ");
            date.setText(day + "/" + (month+1) + "/" + year + " ");
            sunrise.setText("Sunrise: ");
            sunriseTime.setText(sunRise + " ");
            sunset.setText("Sunset: ");
            sunsetTime.setText(sunSet + " ");
            row.addView(dateText);
            row.addView(date);
            row.addView(sunrise);
            row.addView(sunriseTime);
            row.addView(sunset);
            row.addView(sunsetTime);
            datesTable.addView(row,i);
            day++;
        }
    }

    private void updateTime(int year, int monthOfYear, int dayOfMonth, String CityName, String latitude, String longitude, String tZone){

        double latitudeDbl = Double.parseDouble(latitude);
        double longitudeDbl = Double.parseDouble(longitude);

        tZone = "Australia/Melbourne";
        TimeZone timeZone = TimeZone.getTimeZone(tZone);

        GeoLocation geolocation = new GeoLocation(CityName, latitudeDbl, longitudeDbl, timeZone);
        AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
        ac.getCalendar().set(year, monthOfYear, dayOfMonth);
        Date srise = ac.getSunrise();
        Date sset = ac.getSunset();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sunRise = sdf.format(srise);
        sunSet = sdf.format(sset);
    }

    public void onBackPressed() {
        Log.i("IMAGE DETAILS", "Back Button Pressed");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}