package com.example.sam.Ass_04_T02;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sam.Ass_04_T02.R;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.size_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void button18pt(View view) {
        setContentView(R.layout.size18p);
    }
    public void button16pt(View view) {
        setContentView(R.layout.size16p);
    }
    public void button14pt(View view) {
        setContentView(R.layout.size14p);
    }
    public void button12pt(View view) {
        setContentView(R.layout.size12p);
    }
    public void button10pt(View view) {
        setContentView(R.layout.size10p);
    }
    public void button8pt(View view) {
        setContentView(R.layout.size8p);
    }

}
