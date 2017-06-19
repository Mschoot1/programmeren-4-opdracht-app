package com.example.marni.programmeren_4_opdracht_app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Rental;
import com.example.marni.programmeren_4_opdracht_app.volley.HistoryActivityRequests;
import com.example.marni.programmeren_4_opdracht_app.volley.InventoriesActivityRequests;
import com.example.marni.programmeren_4_opdracht_app.volley.InventoryPutRequest;

import java.util.ArrayList;

public class RentalAdapter extends BaseAdapter {

    private final String tag = this.getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Rental> rentals;
    private InventoryPutRequest.InventoryPutRequestListener inventoryPutRequestListener;

    public RentalAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Rental> rentals, InventoryPutRequest.InventoryPutRequestListener inventoryPutRequestListener) {
        this.mContext = context;
        this.mInflater = layoutInflater;
        this.rentals = rentals;
        this.inventoryPutRequestListener = inventoryPutRequestListener;
    }

    @Override
    public int getCount() {
        return rentals.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i(tag, "getItem() at " + position);
        return rentals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(tag, "getView at " + position);
        RentalAdapter.ViewHolder viewHolder;
        if (convertView == null) {

            Log.i(tag, "convertView is NULL - nieuwe maken");
            convertView = mInflater.inflate(R.layout.rental_list_view_row, null);

            viewHolder = new RentalAdapter.ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvRentalFilmTitle);
            viewHolder.tvInventoryId = (TextView) convertView.findViewById(R.id.tvRentalInventoryId);
            viewHolder.tvRentalDate = (TextView) convertView.findViewById(R.id.tvRentalRentalDate);
            viewHolder.bReturn = (Button) convertView.findViewById(R.id.bReturn);

            convertView.setTag(viewHolder);
        } else {
            Log.i(tag, "convertView BESTOND AL - hergebruik");
            viewHolder = (RentalAdapter.ViewHolder) convertView.getTag();
        }
        final Rental r = rentals.get(position);
        String title = r.getTitle();
        String inventoryId = Integer.toString(r.getInventoryId());
        String rentalDate = r.getRentalDate();

        viewHolder.tvTitle.setText(title);
        viewHolder.tvInventoryId.setText(inventoryId);
        viewHolder.tvRentalDate.setText(rentalDate);
        if (r.getReturnDate() == null) {
            viewHolder.bReturn.setVisibility(View.VISIBLE);
            viewHolder.bReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InventoryPutRequest inventoryPutRequest = new InventoryPutRequest(mContext, inventoryPutRequestListener);
                    inventoryPutRequest.handleReturnRental(r.getInventoryId());
                }
            });
        } else {
            viewHolder.bReturn.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvInventoryId;
        TextView tvRentalDate;
        Button bReturn;
    }
}