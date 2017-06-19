package com.example.marni.programmeren_4_opdracht_app.presentation;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.adapters.RentalAdapter;
import com.example.marni.programmeren_4_opdracht_app.domain.Inventory;
import com.example.marni.programmeren_4_opdracht_app.domain.Rental;
import com.example.marni.programmeren_4_opdracht_app.mappers.RentalMapper;
import com.example.marni.programmeren_4_opdracht_app.volley.HistoryActivityRequests;
import com.example.marni.programmeren_4_opdracht_app.volley.InventoryPutRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements
        HistoryActivityRequests.HistoryActivityListener, InventoryPutRequest.InventoryPutRequestListener {

    private final String tag = getClass().getSimpleName();

    private ArrayList<Rental> rentals = new ArrayList<>();

    private RentalAdapter adapter;
    private int CUSTOMER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.close_white);
        ab.setTitle("History");
        HistoryActivityRequests requests = new HistoryActivityRequests(getApplicationContext(), this);
        requests.handleGetCustomerRentals(CUSTOMER_ID);
        Log.i("tag", "Dit is getal" + CUSTOMER_ID);

        adapter = new RentalAdapter(getApplicationContext(), getLayoutInflater(), rentals, this);
        ListView lvHistory = (ListView) findViewById(R.id.lvRentals);
        lvHistory.setAdapter(adapter);
    }

    @Override
    public void onSuccessfulCustomerRental(JSONObject response) {
        Log.i(tag, "onSuccessfulCustomerRental: " + response);
        for( Rental r : RentalMapper.mapRentalList(response)) {
            rentals.add(r);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetCustomerId(int customerId) {
        Log.i(tag, "onGetCustomerRental: " + customerId);
        this.CUSTOMER_ID = customerId;
    }

    @Override
    public void onGetHistoryError(VolleyError error) {
        Log.e(tag, "onGetHistoryError: " + error.getLocalizedMessage());
    }

    @Override
    public void onSuccessfulReturnRental(Inventory i) {
        for (Rental r : rentals) {
            if (r.getInventoryId() == i.getInventoryId()) {
                r.setReturnDate(i.getReturnDate());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReturnRentalError(VolleyError error) {
        Log.e(tag, "onReturnRentalError: " + error.getLocalizedMessage());
    }
}
