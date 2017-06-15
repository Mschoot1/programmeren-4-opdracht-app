package com.example.marni.programmeren_4_opdracht_app.domain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marni.programmeren_4_opdracht_app.R;

import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter {
	
	private final String tag = this.getClass().getSimpleName();
	
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Film> films;
	
	public FilmAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Film> films) {
		this.mContext = context;
		this.mInflater = layoutInflater;
		this.films = films;
	}
	
	@Override
	public int getCount() {
		return films.size();
	}
	
	@Override
	public Object getItem(int position) {
		Log.i(tag, "getItem() at " + position);
		return films.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(tag, "getView at " + position);
		ViewHolder viewHolder;
		if(convertView == null){
			
			Log.i(tag, "convertView is NULL - nieuwe maken");
			convertView = mInflater.inflate(R.layout.film_listview_row, null);

			viewHolder = new ViewHolder();
			viewHolder.textViewFilmId = (TextView) convertView.findViewById(R.id.tvFilmId);
			viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.tvFilmTitle);
			viewHolder.textViewReleaseYear = (TextView) convertView.findViewById(R.id.tvFilmReleaseYear);
			viewHolder.textViewDescription = (TextView) convertView.findViewById(R.id.tvFilmDescription);
			
			convertView.setTag(viewHolder);
		} else {
			Log.i(tag, "convertView BESTOND AL - hergebruik");
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Film film = films.get(position);

		String filmId = Integer.toString(film.getFilmId());
		String title = film.getTitle();
		String releaseYear = Integer.toString(film.getReleaseYear());
		String description = film.getDescription();

		viewHolder.textViewFilmId.setText(filmId);
		viewHolder.textViewTitle.setText(title);
		viewHolder.textViewReleaseYear.setText(releaseYear);
		viewHolder.textViewDescription.setText(description);
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView textViewFilmId;
		TextView textViewTitle;
		TextView textViewReleaseYear;
		TextView textViewDescription;
	}
}