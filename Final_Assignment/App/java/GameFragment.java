
package resistance.avalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class GameFragment extends Fragment {

    private int playerNumbers=0;
    private Button setUpButton;

    public static GameFragment newInstance(){
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.games, container, false);

        //Populate the spinner in the fragment from the strings.xml
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.players_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        playerNumbers = 5;
                        break;
                    case 1:
                        playerNumbers = 6;
                        break;
                    case 2:
                        playerNumbers = 7;
                        break;
                    case 3:
                        playerNumbers = 8;
                        break;
                    case 4:
                        playerNumbers = 9;
                        break;
                    case 5:
                        playerNumbers = 10;
                        break;
                    default:
                        break;
                }

                Button setUpButton = (Button) getView().findViewById(R.id.setUpButton);

                setUpButton.setOnClickListener(new View.OnClickListener() {
                    //Run when button is clicked
                    @Override
                    public void onClick(View v) {
                        setUpGameButton();
                    }
                });
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        return rootView;
    }

    // Retrieve all checkboxes and check if they are true/false (checked/not)
    // New bundle to save the booleans of the checkboxes
    // Start PlayerNames Activity
    public void setUpGameButton(){

        CheckBox chkMerlinBox = (CheckBox) getView().findViewById(R.id.merlinCheck);
        final Boolean chkMerlin = chkMerlinBox.isChecked();
        CheckBox chkPercivalBox = (CheckBox) getView().findViewById(R.id.percivalCheck);
        final Boolean chkPercival = chkPercivalBox.isChecked();
        CheckBox chkMordredBox = (CheckBox) getView().findViewById(R.id.mordredCheck);
        final Boolean chkMordred = chkMordredBox.isChecked();
        CheckBox chkMorganaBox = (CheckBox) getView().findViewById(R.id.morganaCheck);
        final Boolean chkMorgana = chkMorganaBox.isChecked();
        CheckBox chkOberonBox = (CheckBox) getView().findViewById(R.id.oberonCheck);
        final Boolean chkOberon = chkOberonBox.isChecked();

        Bundle dataBundle = new Bundle();
        dataBundle.putInt("playerNumb", playerNumbers);
        dataBundle.putBoolean("merlin", chkMerlin);
        dataBundle.putBoolean("percival", chkPercival);
        dataBundle.putBoolean("mordred", chkMordred);
        dataBundle.putBoolean("morgana", chkMorgana);
        dataBundle.putBoolean("oberon", chkOberon);

        // Start the Activity and Create the Bundle of information to be sent
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlayerNames.class);
        intent.putExtras(dataBundle);
        startActivity(intent);

    }
}
