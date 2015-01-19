package resistance.avalon;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;

public class RuleContent extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.rule_details);
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


    private String getRulesContent(String sectionName) {
        // Check Assets folder
        AssetManager assetManager = getAssets();
        InputStream input;
        String text ="";
        // Try opening file with the name sectionName + ".txt"
        // Assign text from file into string called text
        try {
            input = assetManager.open(sectionName + ".txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            text = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return downloaded text to the activity
        return text;
    }


    private void initializeUI(){
        // Get Bundle and title
        // Assign title to activity title
        Bundle dataBundle = getIntent().getExtras();
        String sectionTitle = dataBundle.getString("title");
        this.setTitle(sectionTitle);
        TextView contentTextView = (TextView) findViewById(R.id.sectionContentTextView);

        // Switch case to check what the title is and what content should be shown
        // Retrieve text from getRulesContent function
        switch (sectionTitle) {
            case "Set Up":
                contentTextView.setText(getRulesContent("set_up"));
                break;
            case "Script":
                contentTextView.setText(getRulesContent("script"));
                break;
            case "Game Play":
                contentTextView.setText(getRulesContent("game_play"));
                break;
            case "Quest Phase":
                contentTextView.setText(getRulesContent("quest_phase"));
                break;
            case "Game End":
                contentTextView.setText(getRulesContent("game_end"));
                break;
            case "Optional Characters":
                contentTextView.setText(getRulesContent("character"));
                break;
            case "Optional Rules":
                contentTextView.setText(getRulesContent("optional_rules"));
                break;
            case "Credits":
                contentTextView.setText(getRulesContent("credits"));
                break;
            default:
                contentTextView.setText("Couldn't load section");
                break;
        }

    }
}