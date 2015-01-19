package resistance.avalon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NextPlayer extends Activity  {
    // Initialize arrays
    private String [] playerArray;
    private String [] characterArray;
    private String [] badGuyArray;
    private int playerNumb;
    int currentPlayer = 0;
    int badGuyCount = 0;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_person);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initializeUI();
    }

    @Override
    public void onBackPressed() {
        // Prevent using back button
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
        // Get Bundle and retrieve playerNumb, currentPlayer, badGuyCount,
        // Assign sizes to arrays using playerNumb
        Bundle dataBundle = getIntent().getExtras();
        currentPlayer = dataBundle.getInt("currentPlayer");
        playerNumb = dataBundle.getInt("playerNumb");
        badGuyCount = dataBundle.getInt("badGuyCount");

        playerArray=new String[playerNumb];
        characterArray=new String[playerNumb];
        badGuyArray=new String[badGuyCount];
        int j = 0;


        // Get Player List & Character List
        for (int i=0;i<playerNumb;i++){
            characterArray[i] = dataBundle.getString("character"+i);
            String badGuyChar = characterArray[i];
            playerArray[i] = dataBundle.getString("player"+i);
            String badGuyPlayer= playerArray[i];

            // If badGuyChar is Mordred, Morgana or Minion of Mordred,
            // Assign that players name into badGuyArray
            if(badGuyChar.equals("Mordred")||badGuyChar.equals("Morgana")||badGuyChar.equals("Minion of Mordred")) {
                badGuyArray[j] = badGuyPlayer;
                j++;
            }
            // If badGuyChar is Oberon, assign the name Oberon into the array
            // Oberon's identity must not be known, but must be known he exists
            if (badGuyChar.equals("Oberon")){
                badGuyArray[j] = "Oberon";
            }

        }

        // Retrieve textviews and imageview to replace content
        Button nextPerson = (Button) findViewById(R.id.nextPerson);
        TextView playerNameTextView = (TextView) findViewById(R.id.playerName);
        playerNameTextView.setText(playerArray[currentPlayer]);
        // Set title of activity to the current player
        this.setTitle(playerArray[currentPlayer]);

        // onClick create new Bundle
        // Put playerNumb, currentPlayer into bundle
        // Reiterate through the characterArray+i and playerArray+i to assign character/player names
        // Iterate through badGuyArray to assign who the bad players are + send badGuyCount
        // Start CharacterScreen
        nextPerson.setOnClickListener(new View.OnClickListener() {
            //Run when button is clicked
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("playerNumb", playerNumb);
                dataBundle.putInt("currentPlayer", currentPlayer);

                for(int i=0; i < playerNumb; i++) {
                    dataBundle.putString("player" + i, playerArray[i]);
                    dataBundle.putString("character" + i, characterArray[i]);
                }

                for(int i=0; i < badGuyCount; i++) {
                    dataBundle.putString("badGuyPlayer" + i, badGuyArray[i]);
                    dataBundle.putInt("badGuyCount", badGuyCount);
                }

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CharacterScreen.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

}
