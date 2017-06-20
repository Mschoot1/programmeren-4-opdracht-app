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
import com.example.marni.programmeren_4_opdracht_app.service.Config;
import com.example.marni.programmeren_4_opdracht_app.service.VolleyRequestQueue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qsl on 17/06/2017.
 */

public class HistoryActivityRequests {

	private Context context;
	private final String tag = this.getClass().getSimpleName();

	private String jwt;
	private int customerId;

	private HistoryActivityListener listener;

	public HistoryActivityRequests(Context context, HistoryActivityListener listener) {
		this.context = context;
		this.listener = listener;

		SharedPreferences prefs = context.getSharedPreferences(
				context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		jwt = prefs.getString(context.getString(R.string.jwt), "");
		customerId = prefs.getInt(context.getString(R.string.customer_id), 0);
		setCustomerId(customerId);
	}

	public void handleGetCustomerRentals(int customerId) {
		final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
				(Request.Method.GET,
						Config.URL_RENTALS_CUSTOMER + customerId,
						null,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								listener.onSuccessfulCustomerRental(response);
							}
						}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onGetHistoryError(error);
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
				1500,
				2,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
		listener.onGetCustomerId(customerId);
	}

	public interface HistoryActivityListener {
		void onSuccessfulCustomerRental(JSONObject response);

		void onGetCustomerId(int customerId);

		void onGetHistoryError(VolleyError error);
	}
}
