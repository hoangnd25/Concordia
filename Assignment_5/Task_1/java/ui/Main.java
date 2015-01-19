package swindroid.suntime.ui;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.res.AssetManager;

import swindroid.suntime.R;
import swindroid.suntime.calc.AstronomicalCalendar;
import swindroid.suntime.calc.GeoLocation;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;

import java.util.Arrays;

public class Main extends ListActivity {
    private HashMap<String, Location> locations;
    private String CityName;
    private String latitude;
    private String longitude;
    private String tZone;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locations = loadLocationData();
        initializeUI();

        // force to display information about Melbourne
        displaySelectedCityInfo("Melbourne");
    }

    private void initializeUI(){
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        dp.init(year,month,day,dateChangeHandler); // setup initial values and reg. handler
        updateTime(year, month, day, CityName, latitude, longitude, tZone);

        String[] cities = getCityNames();
        // simple_list_item_1 is a SDK provided layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                cities);
        setListAdapter(adapter); // use data from this adapter
    }

    private void updateTime(int year, int monthOfYear, int dayOfMonth, String CityName, String latitude, String longitude, String tZone){
        TextView cityTextView = (TextView) findViewById(R.id.cityNameTextView);
        CityName = cityTextView.getText().toString();
        TextView latLongTextView = (TextView) findViewById(R.id.latLongTextView);
        String latLong = latLongTextView.getText().toString();

        String[] latLongSeparated = latLong.split(",|\\n");

        latitude = latLongSeparated[0];
        //System.out.println(latLongSeparated[0]);
        longitude = latLongSeparated[1];
        //System.out.println(latLongSeparated[1]);
        tZone = "Australia/Melbourne";
        //System.out.println(latLongSeparated[2]);

        double latitudeDbl = Double.parseDouble(latitude);
        double longitudeDbl = Double.parseDouble(longitude);

        TimeZone timeZone = TimeZone.getTimeZone(tZone);

        System.out.println(latitudeDbl);
        System.out.println(longitudeDbl);
        System.out.println(latLongSeparated[2]);
        // System.out.println(timeZone);

        GeoLocation geolocation = new GeoLocation(CityName, latitudeDbl, longitudeDbl, timeZone);
        AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
        ac.getCalendar().set(year, monthOfYear, dayOfMonth);
        Date srise = ac.getSunrise();
        Date sset = ac.getSunset();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        TextView sunriseTV = (TextView) findViewById(R.id.sunriseTimeTV);
        TextView sunsetTV = (TextView) findViewById(R.id.sunsetTimeTV);
        Log.d("SUNRISE Unformatted", srise+"");

        sunriseTV.setText(sdf.format(srise));
        sunsetTV.setText(sdf.format(sset));
    }

    OnDateChangedListener dateChangeHandler = new OnDateChangedListener()
    {
        public void onDateChanged(DatePicker dp, int year, int monthOfYear, int dayOfMonth)
        {
            updateTime(year, monthOfYear, dayOfMonth, CityName, latitude, longitude, tZone);
        }
    };

    private String[] getCityNames()
    {
        String[] cities = new String[locations.size()];
        cities = locations.keySet().toArray(cities);
        return cities;
    }

    public void onListItemClick(ListView l, View v, int position, long id)
    {
        String selectedItem = (String) getListView().getItemAtPosition(position);
        displaySelectedCityInfo(selectedItem);
    }

    private void displaySelectedCityInfo(String cityName)
    {
        Location loc = locations.get(cityName);
        TextView cityTextView = (TextView) findViewById(R.id.cityNameTextView);
        TextView locTextView = (TextView) findViewById(R.id.latLongTextView);
        cityTextView.setText(cityName);
        if (loc != null) locTextView.setText(loc.toString());
    }

    private HashMap<String, Location> loadLocationData(){
        HashMap<String, Location> locations = new HashMap<String, Location>();
        AssetManager assetManager=getAssets();
        InputStream input;

        // Reading Data From File
        String read_data = null;
        try {
            input=assetManager.open("L06-au_locations.txt");

            int size=input.available();

            byte[] buffer=new byte[size];
            input.read(buffer);
            input.close();

            String text=new String(buffer);

            String[] words = text.split(",|\\n");

            List<String> wordList = Arrays.asList(words);

            for(int i = 0; i<wordList.size();i=i+4){
                System.out.println("locations.put("+wordList.get(i)+", new Location("+wordList.get(i+1)+", "+wordList.get(i+2)+"))");
                locations.put(wordList.get(i), new Location(wordList.get(i+1), wordList.get(i+2), wordList.get(i+3)));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return locations;
    }
}