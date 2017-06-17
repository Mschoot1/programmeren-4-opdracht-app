package com.example.marni.programmeren_4_opdracht_app.domain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.volley.HistoryActivityRequests;
import com.example.marni.programmeren_4_opdracht_app.volley.InventoriesActivityRequests;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {

	private final String tag = this.getClass().getSimpleName();

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Inventory> inventories;
	private HistoryActivityRequests.LoginActivityListener listener;

	public HistoryAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Inventory> inventories, HistoryActivityRequests.LoginActivityListener listener) {
		this.mContext = context;
		this.mInflater = layoutInflater;
		this.inventories = inventories;
		this.listener = listener;
	}

	@Override
	public int getCount() {
		return inventories.size();
	}

	@Override
	public Object getItem(int position) {
		Log.i(tag, "getItem() at " + position);
		return inventories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(tag, "getView at " + position);
		HistoryAdapter.ViewHolder viewHolder;
		if (convertView == null) {

			Log.i(tag, "convertView is NULL - nieuwe maken");
			convertView = mInflater.inflate(R.layout.history_list_view_row, null);

			viewHolder = new HistoryAdapter.ViewHolder();
			viewHolder.tvHistoryFilmId = (TextView) convertView.findViewById(R.id.tvHistoryFilmId);
			viewHolder.tvHistoryFilmTitle = (TextView) convertView.findViewById(R.id.tvHistoryFilmTitle);
			viewHolder.tvHistoryInventoryId = (TextView) convertView.findViewById(R.id.tvHistoryInventoryId);
			viewHolder.tvHistoryRentalDate = (TextView) convertView.findViewById(R.id.tvHistoryRentalDate);

			convertView.setTag(viewHolder);
		} else {
			Log.i(tag, "convertView BESTOND AL - hergebruik");
			viewHolder = (HistoryAdapter.ViewHolder) convertView.getTag();
		}

		final Inventory inventory = inventories.get(position);
		String filmId = Integer.toString(inventory.getInventoryId());
		String rentalDate = inventory.getRentalDate();

		return convertView;
	}

	private static class ViewHolder {
		TextView tvHistoryFilmId;
		TextView tvHistoryFilmTitle;
		TextView tvHistoryInventoryId;
		TextView tvHistoryRentalDate;
	}
}