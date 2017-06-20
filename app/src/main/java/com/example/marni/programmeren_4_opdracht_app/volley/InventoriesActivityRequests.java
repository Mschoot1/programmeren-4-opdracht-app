package com.example.marni.programmeren_4_opdracht_app.volley;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Inventory;
import com.example.marni.programmeren_4_opdracht_app.service.Config;
import com.example.marni.programmeren_4_opdracht_app.service.VolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.marni.programmeren_4_opdracht_app.mappers.InventoryMapper.INVENTORY_ID;

public class InventoriesActivityRequests {

    private Context context;
    private final String tag = this.getClass().getSimpleName();

    private String jwt;
    private int customerId;

    private InventoryActivityRequstsListener listener;

    /**
     * Constructor
     *
     * @param context  a description
     * @param listener a description
     */
    public InventoriesActivityRequests(Context context, InventoryActivityRequstsListener listener) {
        this.context = context;
        this.listener = listener;

        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        jwt = prefs.getString(context.getString(R.string.jwt), "");
        customerId = prefs.getInt(context.getString(R.string.customer_id), 0);
    }

    public void handleGetInventories(int filmId) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        Config.URL_INVENTORIES + filmId,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                listener.onSuccessfulGetInventories(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onGetInventoriesError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Config.CONTENT_TYPE, Config.APPLICATION_JSON);
                headers.put(Config.AUTHORIZATION, Config.BEARER + jwt);
                Log.i(tag, "headers: " + headers.toString());
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1500, // SOCKET_TIMEOUT_MS,
                2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void handleGetInventoryRentals(int inventoryId) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        Config.URL_RENTALS_INVENTORY + inventoryId,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                listener.onSuccessfulGetInventoryRentals(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onGetInventoryRentalsError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Config.CONTENT_TYPE, Config.APPLICATION_JSON);
                headers.put(Config.AUTHORIZATION, Config.BEARER + jwt);
                Log.i(tag, "headers: " + headers.toString());
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1500, // SOCKET_TIMEOUT_MS,
                2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public void handleRentRental(int inventory) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,
                        Config.URL_RENTALS + customerId + "/" + inventory,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Inventory i = new Inventory();
                                try {
                                    i.setInventoryId(response.getInt(INVENTORY_ID));
                                } catch (JSONException e) {
                                    Log.e("IAR", "handleRentRental JSONException " + e.getLocalizedMessage());
                                }
                                listener.onSuccessfulRentRental(i);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRentRentalError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Config.CONTENT_TYPE, Config.APPLICATION_JSON);
                headers.put(Config.AUTHORIZATION, Config.BEARER + jwt);
                Log.i(tag, "headers: " + headers.toString());
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1500, // SOCKET_TIMEOUT_MS,
                2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface InventoryActivityRequstsListener {
        void onSuccessfulGetInventories(JSONObject response);

        void onGetInventoriesError(VolleyError error);

        void onSuccessfulGetInventoryRentals(JSONObject response);

        void onGetInventoryRentalsError(VolleyError error);

        void onSuccessfulRentRental(Inventory i);

        void onRentRentalError(VolleyError error);
    }
}

