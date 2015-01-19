package swindroid.suntime.ui;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.app.ListActivity;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;

import java.util.Arrays;
import android.app.ActionBar;

public class Main extends ListActivity {
    private HashMap<String, Location> locations;
    private String CityName;
    private String latitude;
    private String longitude;
    private String tZone;
    private int year;
    private int month;
    private int day;
    Date srise;
    Date sset;
    private String sunrise;
    private String sunset;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        locations = loadLocationData();
        initializeUI();
        displaySelectedCityInfo("Melbourne");

        Button next = (Button) findViewById(R.id.addCityID);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), addCity.class);
                startActivityForResult(myIntent, 0);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        TextView sunriseTV = (TextView) findViewById(R.id.sunriseTimeTV);
        TextView sunsetTV = (TextView) findViewById(R.id.sunsetTimeTV);

        sunriseTV.setText(sdf.format(srise));
        sunsetTV.setText(sdf.format(sset));
        sunrise = sdf.format(srise);
        sunset = sdf.format(sset);

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "On " + day + "/" + month + "/" + year + " the sun will rise at " + sunrise + " and the sun will set at " + sunset +". Enjoy!");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Share?"));
                return true;
            case R.id.menu_map:
                Intent myIntent = new Intent(this, MainActivity.class);
                startActivityForResult(myIntent, 0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeUI(){
        TextView latLongTextView = (TextView) findViewById(R.id.latLongTextView);
        String latLong = latLongTextView.getText().toString();

        String[] latLongSeparated = latLong.split(",|\\n");

        latitude = latLongSeparated[0];
        longitude = latLongSeparated[1];

        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
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
        longitude = latLongSeparated[1];
        tZone = "Australia/Melbourne";

        double latitudeDbl = Double.parseDouble(latitude);
        double longitudeDbl = Double.parseDouble(longitude);

        TimeZone timeZone = TimeZone.getTimeZone(tZone);

        GeoLocation geolocation = new GeoLocation(CityName, latitudeDbl, longitudeDbl, timeZone);
        AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
        ac.getCalendar().set(year, monthOfYear, dayOfMonth);
        srise = ac.getSunrise();
        sset = ac.getSunset();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        TextView sunriseTV = (TextView) findViewById(R.id.sunriseTimeTV);
        TextView sunsetTV = (TextView) findViewById(R.id.sunsetTimeTV);
        Log.d("SUNRISE Unformatted", srise+"");

        sunriseTV.setText(sdf.format(srise));
        sunsetTV.setText(sdf.format(sset));

        day = dayOfMonth;
        month = monthOfYear;
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

        try {
            FileInputStream fis = openFileInput("test4");

            int size=fis.available();

            byte[] buffer=new byte[size];
            fis.read(buffer);
            fis.close();

            String text=new String(buffer);

            String[] words = text.split(",|\\n");

            List<String> wordList = Arrays.asList(words);

            for(int i = 0; i<wordList.size();i=i+4){
                //System.out.println("locations.put("+wordList.get(i)+", new Location("+wordList.get(i+1)+", "+wordList.get(i+2)+"))");
                locations.put("* "+wordList.get(i), new Location(wordList.get(i+1), wordList.get(i+2), wordList.get(i+3)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
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
                //System.out.println("locations.put("+wordList.get(i)+", new Location("+wordList.get(i+1)+", "+wordList.get(i+2)+"))");
                locations.put(wordList.get(i), new Location(wordList.get(i+1), wordList.get(i+2), wordList.get(i+3)));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return locations;
    }

    public void dateClick(View v) {
        Intent myIntent = new Intent(v.getContext(), Table.class);
        myIntent.putExtra("day", day);
        myIntent.putExtra("month", month);
        myIntent.putExtra("year", year);
        myIntent.putExtra("city", CityName);
        myIntent.putExtra("latitude", latitude);
        myIntent.putExtra("longitude", longitude);
        startActivityForResult(myIntent, 0);
    }
}