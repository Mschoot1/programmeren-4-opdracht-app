package com.example.marni.programmeren_4_opdracht_app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Film;

import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter {
	
	private final String TAG = this.getClass().getSimpleName();
	
	private Context mContext;
	private LayoutInflater mInflator;
	private ArrayList<Film> filmArrayList;
	
	public FilmAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Film> filmArrayList) {
		this.mContext = context;
		this.mInflator = layoutInflater;
		this.filmArrayList = filmArrayList;
	}
	
	@Override
	public int getCount() {
		int size = filmArrayList.size();
		Log.i(TAG, "getCount() = " + size);
		return size;
	}
	
	@Override
	public Object getItem(int position) {
		Log.i(TAG, "getItem() at " + position);
		return filmArrayList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "getView at " + position);
		
		ViewHolder viewHolder;
		
		if(convertView == null){
			
			Log.i(TAG, "convertView is NULL - nieuwe maken");
			
			// Koppel de convertView aan de layout van onze eigen row
			convertView = mInflator.inflate(R.layout.film_listview_row, null);
			
			// Maak een ViewHolder en koppel de schermvelden aan de velden uit onze eigen row.
			viewHolder = new ViewHolder();
			viewHolder.textViewFilmId = (TextView) convertView.findViewById(R.id.film_id_tv);
			viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.film_title_tv);
			viewHolder.textViewReleaseYear = (TextView) convertView.findViewById(R.id.film_release_year_tv);
			viewHolder.textViewDescription = (TextView) convertView.findViewById(R.id.film_description_tv);
			
			// Sla de viewholder op in de convertView
			convertView.setTag(viewHolder);
		} else {
			Log.i(TAG, "convertView BESTOND AL - hergebruik");
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Film film = filmArrayList.get(position);
		viewHolder.textViewFilmId.setText(film.getFilmId());
		viewHolder.textViewTitle.setText(film.getTitle());
		viewHolder.textViewReleaseYear.setText(film.getReleaseYear());
		viewHolder.textViewDescription.setText(film.getDescription());
		
		return convertView;
	}
	
	private static class ViewHolder {
		public TextView textViewFilmId;
		public TextView textViewTitle;
		public TextView textViewReleaseYear;
		public TextView textViewDescription;
		// public TextView textViewContents;
	}
}