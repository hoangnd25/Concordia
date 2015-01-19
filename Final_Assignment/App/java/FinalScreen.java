package resistance.avalon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseObject;

public class FinalScreen extends Activity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_screen);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initializeUI();
        // Initialize Parse
        Parse.initialize(this, "wuHDDbOwKqCzzkbxyqB93JaaAs36l0Aupn9C8Elz", "F7VLDIKNH5gFeYiO2GmPn1XQWzSGRr3ZlVPWjJGn");
    }

    // Tell the setDisplayHomeAsUpEnabled(true) where to go
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeUI(){
        // Get Bundle and retrieve playerNumb
        // Iterate through the character+i and player+i to get character/player names
        Bundle dataBundle = getIntent().getExtras();
        final int playerNumb = dataBundle.getInt("playerNumb");
        final String [] playerArray=new String[playerNumb];
        final String [] characterArray=new String[playerNumb];
        for (int i=0;i<playerNumb;i++){
            characterArray[i] = dataBundle.getString("character"+i);
            playerArray[i] = dataBundle.getString("player"+i);
        }


        Button mordredWon = (Button) findViewById(R.id.mordredWon);
        Button arthurWon = (Button) findViewById(R.id.arthurWon);

        // onClick of either mordredWon or arthurWon send an object to Parse
        // Concatenate the players and assigned characters into one string
        // save object in background
        // Start MainActivity
        mordredWon.setOnClickListener(new View.OnClickListener() {
            //Run when button is clicked
            @Override
            public void onClick(View v) {
                ParseObject recentWins = new ParseObject("RecentWins");
                recentWins.put("winner", "Mordred");

                String playerString = "";
                for(int i = 0; i < playerNumb; i++) {
                    playerString += playerArray[i] + " was " + characterArray[i] + ", ";
                }
                recentWins.put("players", playerString);
                recentWins.saveInBackground();

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        arthurWon.setOnClickListener(new View.OnClickListener() {
            //Run when button is clicked
            @Override
            public void onClick(View v) {
                ParseObject recentWins = new ParseObject("RecentWins");
                recentWins.put("winner", "Arthur");

                String playerString = "";
                for(int i = 0; i < playerNumb; i++) {
                    playerString += playerArray[i] + " was " + characterArray[i] + ", ";
                }
                recentWins.put("players", playerString);
                recentWins.saveInBackground();

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
