package com.example.marni.programmeren_4_opdracht_app.adapters;

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
import com.example.marni.programmeren_4_opdracht_app.domain.Inventory;
import com.example.marni.programmeren_4_opdracht_app.volley.InventoriesActivityRequests;
import com.example.marni.programmeren_4_opdracht_app.volley.InventoryPutRequest;

import java.util.ArrayList;

public class InventoryAdapter extends BaseAdapter {

    private final String tag = this.getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Inventory> inventories;
    private InventoryPutRequest.InventoryPutRequestListener inventoryPutRequestListener;
    private InventoriesActivityRequests.InventoryActivityRequstsListener inventoryActivityRequstsListener;

    public InventoryAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Inventory> inventories, InventoryPutRequest.InventoryPutRequestListener inventoryPutRequestListener, InventoriesActivityRequests.InventoryActivityRequstsListener inventoryActivityRequstsListener) {
        this.mContext = context;
        this.mInflater = layoutInflater;
        this.inventories = inventories;
        this.inventoryPutRequestListener = inventoryPutRequestListener;
        this.inventoryActivityRequstsListener = inventoryActivityRequstsListener;
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
        ViewHolder viewHolder;
        if (convertView == null) {

            Log.i(tag, "convertView is NULL - nieuwe maken");
            convertView = mInflater.inflate(R.layout.inventory_list_view_row, null);

            viewHolder = new ViewHolder();
            viewHolder.tvInventoryId = (TextView) convertView.findViewById(R.id.tvInventoryId);
            viewHolder.ivNotAvailable = (ImageView) convertView.findViewById(R.id.ivNotAvailable);
            viewHolder.tvRentalDate = (TextView) convertView.findViewById(R.id.tvRentalDate);
            viewHolder.bRent = (Button) convertView.findViewById(R.id.bRent);
            viewHolder.bReturn = (Button) convertView.findViewById(R.id.bReturn);

            convertView.setTag(viewHolder);
        } else {
            Log.i(tag, "convertView BESTOND AL - hergebruik");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Inventory inventory = inventories.get(position);
        String filmId = Integer.toString(inventory.getInventoryId());
        String rentalDate = inventory.getRentalDate();

        viewHolder.tvInventoryId.setText(filmId);

        Log.i(tag, "inventory.getStatus(): " + inventory.getStatus());
        final InventoriesActivityRequests inventoriesActivityRequests = new InventoriesActivityRequests(mContext, inventoryActivityRequstsListener);
        final InventoryPutRequest inventoryPutRequest = new InventoryPutRequest(mContext, inventoryPutRequestListener);
        switch (inventory.getStatus()) {
            case AVAILABLE:
                viewHolder.ivNotAvailable.setVisibility(View.INVISIBLE);
                viewHolder.tvRentalDate.setVisibility(View.INVISIBLE);
                viewHolder.bReturn.setVisibility(View.INVISIBLE);
                viewHolder.bRent.setVisibility(View.VISIBLE);
                viewHolder.bRent.setEnabled(true);
                viewHolder.bRent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inventoriesActivityRequests.handleRentRental(inventory.getInventoryId());
                    }
                });
                break;
            case NOT_AVAILABLE:
                viewHolder.ivNotAvailable.setVisibility(View.VISIBLE);
                viewHolder.tvRentalDate.setVisibility(View.VISIBLE);
                viewHolder.bReturn.setVisibility(View.INVISIBLE);
                viewHolder.bRent.setVisibility(View.VISIBLE);
                viewHolder.bRent.setEnabled(false);
                break;
            case MINE:
                viewHolder.ivNotAvailable.setVisibility(View.VISIBLE);
                viewHolder.tvRentalDate.setVisibility(View.VISIBLE);
                viewHolder.bRent.setVisibility(View.INVISIBLE);
                viewHolder.bReturn.setVisibility(View.VISIBLE);
                viewHolder.bReturn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inventoryPutRequest.handleReturnRental(inventory.getInventoryId());
                    }
                });
                break;
            default:
                // empty
        }
        viewHolder.tvRentalDate.setText(rentalDate);

        return convertView;
    }

    private static class ViewHolder {
        TextView tvInventoryId;
        ImageView ivNotAvailable;
        TextView tvRentalDate;
        Button bRent;
        Button bReturn;
    }
}