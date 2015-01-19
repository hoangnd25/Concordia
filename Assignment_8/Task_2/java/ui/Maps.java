package swindroid.suntime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TabHost;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import swindroid.suntime.R;

public class Maps extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
    }

    public void onBackPressed() {
        Log.i("IMAGE DETAILS", "Back Button Pressed");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}