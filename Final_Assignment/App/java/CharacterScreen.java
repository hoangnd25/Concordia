package resistance.avalon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CharacterScreen extends Activity{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initializeUI();
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
        // Iterate through the character+i and player+i to get character/player names
        Bundle dataBundle = getIntent().getExtras();
        final int playerNumb = dataBundle.getInt("playerNumb");
        final int currentPlayer = dataBundle.getInt("currentPlayer");
        final int badGuyCount = dataBundle.getInt("badGuyCount");

        final String [] badGuyArray=new String[badGuyCount];
        final String [] playerArray=new String[playerNumb];
        final String [] characterArray=new String[playerNumb];
        for (int i=0;i<playerNumb;i++){
            characterArray[i] = dataBundle.getString("character"+i);
            playerArray[i] = dataBundle.getString("player"+i);
        }

        // Retrieve textviews and imageview to replace content
        Button setUpButton = (Button) findViewById(R.id.nextPerson);
        TextView alignmentTextView = (TextView) findViewById(R.id.alignment);
        TextView characterNameTextView = (TextView) findViewById(R.id.characterName);
        ImageView characterImageView = (ImageView) findViewById(R.id.characterImage);

        // Set title of activity to the current player
        this.setTitle(playerArray[currentPlayer]);

        // Switch case to check what the current character is and what style the screen should have
        // Set text color, character description, character name and image
        switch (characterArray[currentPlayer]) {
            case "Mordred":
                alignmentTextView.setTextColor(Color.parseColor("#ffff0937"));
                alignmentTextView.setText(R.string.mordred_desc);
                characterNameTextView.setText(R.string.mordred);
                characterImageView.setImageResource(R.drawable.mordred);
                break;
            case "Morgana":
                alignmentTextView.setTextColor(Color.parseColor("#ffff0937"));
                alignmentTextView.setText(R.string.morgana_desc);
                characterNameTextView.setText(R.string.morgana);
                characterImageView.setImageResource(R.drawable.morgana);
                break;
            case "Oberon":
                alignmentTextView.setTextColor(Color.parseColor("#ffff0937"));
                alignmentTextView.setText(R.string.oberon_desc);
                characterNameTextView.setText(R.string.oberon);
                characterImageView.setImageResource(R.drawable.oberon);
                break;
            case "Minion of Mordred":
                alignmentTextView.setTextColor(Color.parseColor("#ffff0937"));
                alignmentTextView.setText(R.string.minion_desc);
                characterNameTextView.setText(R.string.minion);
                characterImageView.setImageResource(R.drawable.minion);
                break;
            case "Merlin":
                alignmentTextView.setTextColor(Color.parseColor("#3399ff"));
                alignmentTextView.setText(R.string.merlin_desc);
                characterNameTextView.setText(R.string.merlin);
                characterImageView.setImageResource(R.drawable.merlin);
                break;
            case "Percival":
                alignmentTextView.setTextColor(Color.parseColor("#3399ff"));
                alignmentTextView.setText(R.string.percival_desc);
                characterNameTextView.setText(R.string.percival);
                characterImageView.setImageResource(R.drawable.percival);
                break;
            case "Servant of Arthur":
                alignmentTextView.setTextColor(Color.parseColor("#3399ff"));
                alignmentTextView.setText(R.string.servant_desc);
                characterNameTextView.setText(R.string.servant);
                characterImageView.setImageResource(R.drawable.servant);
                break;
        }

        // Set textviews of allies (not dynamically generated :( )
        TextView ally1 = (TextView) findViewById(R.id.ally1);
        TextView ally2 = (TextView) findViewById(R.id.ally2);
        TextView ally3 = (TextView) findViewById(R.id.ally3);
        TextView ally4 = (TextView) findViewById(R.id.ally4);

        // If the current player is Mordred, Morgana or Minion of Mordred, increment badGuyCount
        if(characterArray[currentPlayer].equals("Mordred")||characterArray[currentPlayer].equals("Morgana")||characterArray[currentPlayer].equals("Minion of Mordred")) {
            for (int i = 0; i < badGuyCount; i++) {
                // Assign bad player names as badGuyPlayer+i into bundle
                badGuyArray[i] = dataBundle.getString("badGuyPlayer" + i);
                switch (i) {
                    case 3:
                        ally4.setText(badGuyArray[3]);
                    case 2:
                        ally3.setText(badGuyArray[2]);
                    case 1:
                        ally2.setText(badGuyArray[1]);
                    case 0:
                        ally1.setText(badGuyArray[0]);
                        break;
                }
            }
        }
        // onClick create new Bundle
        // Assign currentPlayer (+1)
        // Put badGuyCount, playerNumb, currentPlayerValue into bundle
        // Reiterate through the character+i and player+i to assign character/player names
        // If the currentPlayerValue is equal to the number of players, show the final screen
        // else start NextPlayer
        setUpButton.setOnClickListener(new View.OnClickListener() {
            //Run when button is clicked
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                int currentPlayerValue = currentPlayer+1;

                dataBundle.putInt("badGuyCount", badGuyCount);
                dataBundle.putInt("playerNumb", playerNumb);
                dataBundle.putInt("currentPlayer", currentPlayerValue);

                for(int i=0; i < playerNumb; i++) {
                    dataBundle.putString("player" + i, playerArray[i]);
                    dataBundle.putString("character" + i, characterArray[i]);
                }

                if(currentPlayerValue==playerNumb){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), FinalScreen.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), NextPlayer.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                }
            }
        });
    }
}

