package swin.examples;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Displays movie name, rating and votes. A custom icon is generated based on movie name and rating.
 * @author rvasa
 *
 */
public class MovieRatingsActivity extends ListActivity{
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private LayoutInflater mInflater;
    private ProgressDialog dialog;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        dialog = new ProgressDialog(this);
		initializeUI();
	}

	private void initializeUI() {
		mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        BackgroundTask task = new BackgroundTask();
        task.execute();
	}
    private void showContent (){
        setListAdapter(new RowIconAdapter(this, R.layout.listrow, R.id.row_label, movies));
    }

    // Referenced from http://briandolhansky.com/blog/2013/7/11/snippets-android-async-progress
    private class BackgroundTask extends AsyncTask <Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                showContent();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            InputStream inputStream = getResources().openRawResource(R.raw.ratings);
            movies = Movie.loadFromFile(inputStream);
            return null;
        }

    }
    static class ViewHolder {
        TextView movie;
        TextView votes;
        ImageView icon;
    }

	/** Custom row adatper -- that displays an icon next to the movie name */
	class RowIconAdapter extends ArrayAdapter<Movie> 
	{
		private ArrayList<Movie> movies;		
		public RowIconAdapter(Context c, int rowResourceId, int textViewResourceId, 
				ArrayList<Movie> items)
		{
			super(c, rowResourceId, textViewResourceId, items);
			movies  = items;
		}

        public View getView(int position, View cView, ViewGroup parent) {
            ViewHolder holder;
            Movie currMovie = movies.get(position);

            if (cView == null) {
                cView = mInflater.inflate(R.layout.listrow,
                        parent, false);
                holder = new ViewHolder();
                holder.movie = (TextView) cView.findViewById(R.id.row_label);
                holder.votes = (TextView) cView.findViewById(R.id.row_subtext);
                holder.icon = (ImageView) cView.findViewById(R.id.row_icon);
                cView.setTag(holder);
            } else {
                holder = (ViewHolder) cView.getTag();
            }
            holder.movie.setText(currMovie.getName());
            String votesStr = currMovie.getVotes()+" votes";
            holder.votes.setText(votesStr);
            Bitmap movieIcon = getMovieIcon(currMovie.getName(), currMovie.getRating());
            holder.icon.setImageBitmap(movieIcon);
            return cView;
        }
	}
	
	/** Creates a unique movie icon based on name and rating */
	private Bitmap getMovieIcon(String movieName, String movieRating)
	{
		int bgColor = getColor(movieName);
		Bitmap b = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);
		b.eraseColor(bgColor); // fill bitmap with the color
		Canvas c = new Canvas(b);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(getTextColor(bgColor));
		p.setTextSize(24.0f);
		c.drawText(movieRating, 8, 32, p);
		return b;
	}
	
	/** Construct a color from a movie name */
	private int getColor(String name)
	{
		String hex = toHexString(name);
		String red = "#"+hex.substring(0,2);
		String green = "#"+hex.substring(2,4);
		String blue = "#"+hex.substring(4,6);
		String alpha = "#"+hex.substring(6,8);
		int color = Color.argb(Integer.decode(alpha), Integer.decode(red), 
								Integer.decode(green), Integer.decode(blue));
		return color;
	}

	/** Given a movie name -- generate a hex value from its hashcode */
	private String toHexString(String name)
	{
		int hc = name.hashCode();
		String hex = Integer.toHexString(hc);
		if (hex.length() < 8)
		{
			hex = hex+hex+hex;
			hex = hex.substring(0,8); // use default color value
		}
		return hex;
	}
	/** Crude optimization to obtain a contrasting color -- does not work well yet */
	private int getTextColor(int bg)
	{
		
		int r = Color.red(bg);
		int g = Color.green(bg);
		int b = Color.blue(bg);
		String hex = Integer.toHexString(r)+Integer.toHexString(g);
		hex += Integer.toHexString(b);
		
		int cDec = Integer.decode("#"+hex);
		if (cDec > 0xFFFFFF/2)  // go dark for lighter shades
			return Color.rgb(0, 0, 0);
		else
		{
			r = (r+128)%256;
			g = (g+128)%256;
			b = (b+128)%256;
			return Color.rgb(r,g,b);
		}
	}
}