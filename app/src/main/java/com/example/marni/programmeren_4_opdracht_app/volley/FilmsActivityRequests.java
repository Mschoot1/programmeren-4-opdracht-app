package com.example.marni.programmeren_4_opdracht_app.volley;

import android.content.Context;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.marni.programmeren_4_opdracht_app.service.Config;
import com.example.marni.programmeren_4_opdracht_app.service.VolleyRequestQueue;

import org.json.JSONObject;

public class FilmsActivityRequests {
    private Context context;

    private LoginActivityListener listener;

    /**
     * Constructor
     *
     * @param context  a description
     * @param listener a description
     */
    public FilmsActivityRequests(Context context, LoginActivityListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleGetFilms(int offset, int count) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        Config.URL_FILMS +
                                "?offset=" + offset +
                                "&count=" + count,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                listener.onSuccessfulGetFilms(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onGetFilmsError(error);
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1500, // SOCKET_TIMEOUT_MS,
                2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface LoginActivityListener {
        void onSuccessfulGetFilms(JSONObject response);

        void onGetFilmsError(VolleyError error);
    }
}

