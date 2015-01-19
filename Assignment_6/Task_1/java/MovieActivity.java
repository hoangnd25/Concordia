package apcmag.examples;

import java.util.HashMap;

import swin.examples.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MovieActivity extends ListActivity {
	private HashMap<String, String> movies;
	private String[] movieNames;
	
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		movies = loadMovieData();
		movieNames = getMovieNames();
		initializeUI();
	}
	
	private void initializeUI()	{
		// use custom row adapter
		setListAdapter(new RowIconAdapter()); 
		// force to display information about Melbourne
		displaySelectedMovieRating("Movie Name");
	}
	
	private String[] getMovieNames() {
		String[] movies = new String[this.movies.size()];
		movies = this.movies.keySet().toArray(movies);
		return movies;
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		String selectedItem = (String) getListView().getItemAtPosition(position);
		displaySelectedMovieRating(selectedItem);
	}
	
	private void displaySelectedMovieRating(String movieRating) {
		String loc = movies.get(movieRating);
		TextView cityTextView = (TextView) findViewById(R.id.cityNameTextView);
		TextView locTextView = (TextView) findViewById(R.id.latLongTextView);
		cityTextView.setText(movieRating);
		if (loc != null) locTextView.setText(loc.toString());
	}

	private HashMap<String, String> loadMovieData() {
        HashMap<String, String> movies = new HashMap<String, String>();
        // Assigns movies and ratings into an array
        String[][] movieList =
                {{"The Shawshank Redemption","Rating: 9.2/10"},
                {"The Godfather","Rating: 9.2/10"},
                {"Star Wars: Episode V","Rating: 8.8/10"},
                {"The Dark Knight","Rating: 8.9/10"},
                {"Pulp Fiction","Rating: 8.9/10"},
                {"The Good, the Bad and the Ugly","Rating: 8.9/10"},
                {"Schindler's List","Rating: 8.9/10"},
                {"12 Angry Men","Rating: 8.9/10"},
                {"The Lord of the Rings","Rating: 8.9/10"},
                {"Fight Club","Rating: 8.8/10"}};
        // For each item in the array, loop through it and output it
        for(int i=0;i<movieList.length;i++){
            movies.put( movieList[i][0], movieList[i][1]);
        }
        return movies;
	}
	
	// Row Adapter, check movie names to assign image and ratings
	class RowIconAdapter extends ArrayAdapter<String> {
		public RowIconAdapter() {
			super(MovieActivity.this, R.layout.listrow, R.id.row_label, movieNames);
		}
		
		public View getView(int pos, View cView, ViewGroup parent) {
			View row = super.getView(pos, cView, parent);
			// Current image & rating
			ImageView icon = (ImageView) row.findViewById(R.id.row_icon);
            TextView rating = (TextView) row.findViewById(R.id.row_rating);
			
			String movie = movieNames[pos];
			Log.i("INFO", "Movie "+pos+" "+movie);

            if (movie.startsWith("The Shaws")) {
                icon.setImageResource(R.drawable.shawshank);
                rating.setText("Rating: 9.2/10");
            } else if (movie.startsWith("The God")) {
                icon.setImageResource(R.drawable.godfather);
                rating.setText("Rating: 9.2/10");
            } else if (movie.startsWith("The Dark")) {
                icon.setImageResource(R.drawable.darkknight);
                rating.setText("Rating: 8.9/10");
            } else if (movie.startsWith("Pulp")) {
                icon.setImageResource(R.drawable.pulpfiction);
                rating.setText("Rating: 8.9/10");
            } else if (movie.startsWith("The Good")) {
                icon.setImageResource(R.drawable.goodbadugly);
                rating.setText("Rating: 8.9/10");
            } else if (movie.startsWith("Schin")) {
                icon.setImageResource(R.drawable.schindler);
                rating.setText("Rating: 8.9/10");
            } else if (movie.startsWith("12 Angry")) {
                icon.setImageResource(R.drawable.angrymen);
                rating.setText("Rating: 8.9/10");
            } else if (movie.startsWith("The Lord")) {
                icon.setImageResource(R.drawable.lotr);
                rating.setText("Rating: 8.9/10");
            } else if (movie.startsWith("Fight")) {
                icon.setImageResource(R.drawable.fightclub);
                rating.setText("Rating: 8.8/10");
            } else if (movie.startsWith("Star")) {
                icon.setImageResource(R.drawable.swtor);
                rating.setText("Rating: 8.8/10");
            } else {
                icon.setImageResource(R.drawable.location);
                rating.setText("Rating: 0/10");
            }
            return row;
		}
	}
}