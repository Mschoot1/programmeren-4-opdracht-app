package com.example.marni.programmeren_4_opdracht_app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Film;

import java.util.List;

public class FilmAdapter extends BaseAdapter {

	private static final int COUNT = 10;
	private final String tag = this.getClass().getSimpleName();

	private LayoutInflater mInflater;
	private List<Film> films;
	private OnLoadMoreItems listener;

	public FilmAdapter(Context context, LayoutInflater layoutInflater, List<Film> films, OnLoadMoreItems listener) {
		this.mInflater = layoutInflater;
		this.films = films;
		this.listener = listener;
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

		View v = convertView;
		if(v == null){
			
			Log.i(tag, "convertView is NULL - nieuwe maken");
			v = mInflater.inflate(R.layout.film_list_view_row, null);

			viewHolder = new ViewHolder();
			viewHolder.textViewFilmId = (TextView) v.findViewById(R.id.tvFilmId);
			viewHolder.textViewTitle = (TextView) v.findViewById(R.id.tvFilmTitle);
			viewHolder.textViewReleaseYear = (TextView) v.findViewById(R.id.tvFilmReleaseYear);
			viewHolder.textViewDescription = (TextView) v.findViewById(R.id.tvFilmDescription);
			viewHolder.bLoadMoreItems = (Button) v.findViewById(R.id.bLoadMoreItems);
			viewHolder.rlFilm = (RelativeLayout) v.findViewById(R.id.rlFilm);

			v.setTag(viewHolder);
		} else {
			Log.i(tag, "convertView BESTOND AL - hergebruik");
			viewHolder = (ViewHolder) v.getTag();
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
		viewHolder.bLoadMoreItems.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.loadMoreItems(getCount(), COUNT);
			}
		});
		if(reachedEndOfList(position)) {
			viewHolder.bLoadMoreItems.setVisibility(View.VISIBLE);
			viewHolder.rlFilm.setBackgroundResource(R.drawable.shadow_drawable);
		} else {
			viewHolder.bLoadMoreItems.setVisibility(View.GONE);
			viewHolder.rlFilm.setBackgroundResource(0);
		}
		return v;
	}

	private boolean reachedEndOfList(int position) {
		return position == getCount() - 1;
	}

	private static class ViewHolder {
		TextView textViewFilmId;
		TextView textViewTitle;
		TextView textViewReleaseYear;
		TextView textViewDescription;
		Button bLoadMoreItems;
		RelativeLayout rlFilm;
	}

	public interface OnLoadMoreItems {
		void loadMoreItems(int offset, int count);
	}
}