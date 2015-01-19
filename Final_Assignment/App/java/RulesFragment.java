
package resistance.avalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RulesFragment extends ListFragment {

    public static RulesFragment newInstance(){
        RulesFragment fragment = new RulesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public RulesFragment(){
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Section names
        String[] values = new String[] { "Set Up", "Script", "Game Play", "Quest Phase",
                "Game End", "Optional Characters", "Optional Rules", "Credits" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String selectedItem = (String) getListView().getItemAtPosition(position);
        getRulesContent(selectedItem);
    }
    private void getRulesContent(String sectionTitle) {

        // New Bundle to send title
        Bundle dataBundle = new Bundle();
        dataBundle.putString("title", sectionTitle);

        // Start the Activity and Create the Bundle of information to be sent
        Intent intent = new Intent();
        intent.setClass(getActivity(), RuleContent.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }
}
