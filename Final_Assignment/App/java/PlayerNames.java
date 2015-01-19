package resistance.avalon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerNames extends Activity {

    List<EditText> allEds = new ArrayList<EditText>();
    int playerNumb;
    boolean merlin, percival, mordred, morgana, oberon;
    boolean emptyField = false;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_names);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initializeUI();
    }

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
        // Pull the data from MainActivity.java
        Bundle dataBundle = getIntent().getExtras();
        playerNumb = dataBundle.getInt("playerNumb");
        merlin = dataBundle.getBoolean("merlin");
        percival = dataBundle.getBoolean("percival");
        mordred = dataBundle.getBoolean("mordred");
        morgana = dataBundle.getBoolean("morgana");
        oberon = dataBundle.getBoolean("oberon");
        LinearLayout ll = new LinearLayout(this);
        ll.setBackgroundResource(R.drawable.brick_bg);


        ScrollView sv = new ScrollView(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        for (int l = 0; l < playerNumb; l++) {
            EditText et = new EditText(this);

            int maxLength = 10;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            et.setFilters(fArray);

            et.setHint("Player " + (l+1) +"");
            allEds.add(et);
            ll.addView(et);
        }
        Button b = new Button(this);
        b.setText("Next");
        ll.addView(b);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("playerNumb", playerNumb);
                String [] items=new String[allEds.size()];
                String [] playerNames=new String [allEds.size()];

                // Get player names & randomize Array
                for(int i=0; i < allEds.size(); i++){
                    items[i]=allEds.get(i).getText().toString();
                    if (items[i].matches("")){
                        emptyField = true;
                    }
                    playerNames[i]=items[i];
                }
                Collections.shuffle(Arrays.asList(playerNames));

                // Put Randomized Player Names into Bundle
                for(int i=0; i < playerNames.length; i++){
                    dataBundle.putString("player" + i, playerNames[i]);
                }
                playerSetUp(dataBundle);

                if (!emptyField){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), NextPlayer.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "A player name is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        this.setContentView(sv);
    }

    public void playerSetUp(Bundle dataBundle) {
        int goodGuys;
        int badGuys;
        int goodGuyCount=0;
        int badGuyCount=0;

        switch (playerNumb){
            case 5:
                goodGuys = 3;
                badGuys = 2;
                break;
            case 6:
                goodGuys = 4;
                badGuys = 2;
                break;
            case 7:
                goodGuys = 4;
                badGuys = 3;
                break;
            case 8:
                goodGuys = 5;
                badGuys = 3;
                break;
            case 9:
                goodGuys = 6;
                badGuys = 3;
                break;
            case 10:
                goodGuys = 6;
                badGuys = 4;
                break;
            default:
                //TODO: Toast Error
                goodGuys = 6;
                badGuys = 4;
                break;
        }
        // Assign roles for every true checkbox, set checkbox to false if character has been assigned
        String [] characterAssignment=new String[playerNumb];
        for(int i=0; i < characterAssignment.length; i++) {
            if (merlin) {
                characterAssignment[i] = "Merlin";
                goodGuyCount++;
                merlin = false;
            } else if (percival) {
                characterAssignment[i] = "Percival";
                goodGuyCount++;
                percival = false;
            } else if (mordred) {
                characterAssignment[i] = "Mordred";
                badGuyCount++;
                mordred = false;
            } else if (morgana) {
                characterAssignment[i] = "Morgana";
                badGuyCount++;
                morgana = false;
            } else if (oberon) {
                characterAssignment[i] = "Oberon";
                badGuyCount++;
                oberon = false;
            }
            else if (goodGuyCount!=goodGuys) {
                characterAssignment[i] = "Servant of Arthur";
                goodGuyCount++;
            }
            else if(badGuyCount!=badGuys){
                characterAssignment[i] = "Minion of Mordred";
                badGuyCount++;
            }
        }
        // Shuffle Character Array & put into Bundle
        Collections.shuffle(Arrays.asList(characterAssignment));
        dataBundle.putInt("badGuyCount", badGuys);

        for(int i=0; i < characterAssignment.length; i++){
            dataBundle.putString("character" + i, characterAssignment[i]);
        }
    }
}