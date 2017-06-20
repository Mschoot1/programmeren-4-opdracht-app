package com.example.marni.programmeren_4_opdracht_app.presentation;

import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Film;
import com.example.marni.programmeren_4_opdracht_app.domain.Inventory;
import com.example.marni.programmeren_4_opdracht_app.adapters.InventoryAdapter;
import com.example.marni.programmeren_4_opdracht_app.mappers.InventoryMapper;
import com.example.marni.programmeren_4_opdracht_app.domain.Rental;
import com.example.marni.programmeren_4_opdracht_app.mappers.RentalMapper;
import com.example.marni.programmeren_4_opdracht_app.volley.InventoriesActivityRequests;
import com.example.marni.programmeren_4_opdracht_app.volley.InventoryPutRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.marni.programmeren_4_opdracht_app.presentation.FilmsActivity.FILM;

public class InventoriesActivity extends AppCompatActivity implements InventoriesActivityRequests.InventoryActivityRequstsListener, InventoryPutRequest.InventoryPutRequestListener {

    private final String tag = getClass().getSimpleName();

    private ProgressBar progressBar;

    private ArrayList<Inventory> inventories = new ArrayList<>();
    private InventoryAdapter adapter;

    private InventoriesActivityRequests requests;

    private int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventories);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Bundle bundle = getIntent().getExtras();
        Film film = (Film) bundle.getSerializable(FILM);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(film.getTitle());

        adapter = new InventoryAdapter(getApplicationContext(), getLayoutInflater(), inventories, this, this);
        ListView lvInventories = (ListView) findViewById(R.id.lvInventories);
        lvInventories.setAdapter(adapter);

        progressBar = (ProgressBar) findViewById(R.id.pb);
        progressBar.setVisibility(View.VISIBLE);
        requests = new InventoriesActivityRequests(getApplicationContext(), this);
        requests.handleGetInventories(film.getFilmId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                // do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessfulGetInventories(JSONObject response) {
        Log.i(tag, "onSuccessfulGetInventories: " + response);
        ArrayList<Inventory> al = InventoryMapper.mapInventoryList(response);
        for (Inventory i : al) {
            inventories.add(i);
            requests.handleGetInventoryRentals(i.getInventoryId());
        }
        TextView tvEmptyArray = (TextView) findViewById(R.id.tvEmptyArray);
        if (al.isEmpty()) {
            tvEmptyArray.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            tvEmptyArray.setVisibility(View.INVISIBLE);
        }
        Log.i(tag, "inventories.size(): " + inventories.size());
    }

    @Override
    public void onGetInventoriesError(VolleyError error) {
        Log.e(tag, "onGetInventoriesError: " + error.getLocalizedMessage());
    }

    @Override
    public void onSuccessfulGetInventoryRentals(JSONObject response) {
        Log.i(tag, "onSuccessfulGetInventoryRentals: " + response);
        Rental r = RentalMapper.mapRental(response);
        if (r != null) {
            for (Inventory i : inventories) {
                if (i.getInventoryId() == r.getInventoryId()) {
                    SharedPreferences prefs = getSharedPreferences(
                            getString(R.string.preference_file_key), MODE_PRIVATE);
                    if (r.getCustomerId() == prefs.getInt(getString(R.string.customer_id), 0)) {
                        i.setStatus(Inventory.Status.MINE);
                    } else {
                        i.setStatus(Inventory.Status.NOT_AVAILABLE);
                    }
                    i.setAvailable((i.getInventoryId() != r.getInventoryId()));
                    i.setRentalDate(r.getRentalDate());

                    Log.i(tag, "r.getCustomerId(): " + r.getCustomerId());
                    Log.i(tag, "prefs.getInt(getString(R.string.customer), 0): " + prefs.getInt(getString(R.string.customer_id), 0));
                    Log.i(tag, "i.getStatus(): " + i.getStatus());
                }
            }
        }
        j++;
        if (j == inventories.size()) {
            Log.i(tag, "adapter.notifyDataSetChanged()");
            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetInventoryRentalsError(VolleyError error) {
        Log.e(tag, "onGetInventoryRentalsError: " + error.getLocalizedMessage());
    }

    @Override
    public void onSuccessfulRentRental(Inventory i) {
        j = inventories.size() - 1;
        requests.handleGetInventoryRentals(i.getInventoryId());
    }

    @Override
    public void onRentRentalError(VolleyError error) {
        Log.e(tag, "onRentRentalError: " + error.getLocalizedMessage());
    }

    @Override
    public void onSuccessfulReturnRental(Inventory i) {
        for (Inventory inventory : inventories) {
            if (inventory.getInventoryId() == i.getInventoryId()) {
                inventory.setStatus(Inventory.Status.AVAILABLE);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReturnRentalError(VolleyError error) {
        Log.e(tag, "onReturnRentalError: " + error.getLocalizedMessage());
    }
}
