package com.example.marni.programmeren_4_opdracht_app.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.marni.programmeren_4_opdracht_app.service.Config;
import com.example.marni.programmeren_4_opdracht_app.service.VolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.marni.programmeren_4_opdracht_app.service.Config.URL_LOGIN;

public class LoginRequest {
    private Context context;
    private final String tag = this.getClass().getSimpleName();

    private LoginActivityListener listener;

    /**
     * Constructor
     *
     * @param context  a description
     * @param listener a description
     */
    public LoginRequest(Context context, LoginActivityListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleLogin(String email, String password) {
        final String body = "{\"username\":\"" + email + "\",\"password\":\"" + password + "\"}";
        Log.i(tag, "handleLogin - body = " + body);

        try {
            final JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST,
                            Config.URL_LOGIN,
                            jsonBody,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    listener.onLoginSuccessful(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onError(error);
                        }
                    });

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            Log.e("", "JSONException " + e.getLocalizedMessage());
        }
    }

    public interface LoginActivityListener {
        void onLoginSuccessful(JSONObject response);

        void onError(VolleyError error);
    }
}

