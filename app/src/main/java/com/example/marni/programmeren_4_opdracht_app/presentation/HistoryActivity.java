package com.example.marni.programmeren_4_opdracht_app.presentation;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Film;
import com.example.marni.programmeren_4_opdracht_app.domain.HistoryAdapter;
import com.example.marni.programmeren_4_opdracht_app.domain.HistoryMapper;
import com.example.marni.programmeren_4_opdracht_app.domain.Rental;
import com.example.marni.programmeren_4_opdracht_app.volley.FilmsActivityRequests;
import com.example.marni.programmeren_4_opdracht_app.volley.HistoryActivityRequests;

import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements HistoryActivityRequests.LoginActivityListener {

    private final String tag = getClass().getSimpleName();

    private ArrayList<Rental> history = new ArrayList<>();

    private HistoryAdapter adapter;
    private int CUSTOMER_ID;

    private HistoryActivityRequests requests;

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
        requests = new HistoryActivityRequests(getApplicationContext(), this);
        requests.handleGetCustomerRentals(CUSTOMER_ID);
        Log.i("tag", "Dit is getal" + CUSTOMER_ID);

        adapter = new HistoryAdapter(getApplicationContext(), getLayoutInflater(), history, this);
        ListView lvHistory = (ListView) findViewById(R.id.lvHistory);
        lvHistory.setAdapter(adapter);
    }

    @Override
    public void onSuccessfulCustomerRental(JSONObject response) {
        Log.i(tag, "onSuccessfulCustomerRental: " + response);
        for( Rental r : HistoryMapper.mapHistoryList(response)) {
            history.add(r);
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
}
