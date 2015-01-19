package resistance.avalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    public static HistoryFragment newInstance(){
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //updateData();
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.tablelayout, container, false);
    }
/*
    public void updateData(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.whereEqualTo("playerName", "Dan Stemkoski");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

/*
    private void initializeUI(){
        TableLayout datesTable = (TableLayout) getActivity().findViewById(R.id.tableLayout);
        Intent intent = getIntent();
        day = intent.getIntExtra("day", 0);
        month = intent.getIntExtra("month", 0);
        year = intent.getIntExtra("year", 0);
        CityName = intent.getStringExtra("city");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        for (int i = 0; i <7; i++) {
            TableRow row= new TableRow(getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView dateText = new TextView(getActivity());
            TextView date = new TextView(getActivity());
            TextView sunrise = new TextView(getActivity());
            TextView sunriseTime = new TextView(getActivity());
            TextView sunset = new TextView(getActivity());
            TextView sunsetTime = new TextView(getActivity());

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
*/
}
