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

import java.util.HashMap;
import java.util.Map;

public class LoginActivityRequests {
    private Context context;
    private final String tag = this.getClass().getSimpleName();

    private LoginActivityListener listener;

    /**
     * Constructor
     *
     * @param context  a description
     * @param listener a description
     */
    public LoginActivityRequests(Context context, LoginActivityListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void handleLogin(final String email, final String password) {
        final String body = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        try {
            final JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST,
                            Config.URL_LOGIN,
                            jsonBody,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    listener.onSuccessfulLogin(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onLoginError(error);
                        }
                    });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            Log.e("LoginActivityRequests", "JSONException " + e.getLocalizedMessage());
        }
    }

    public void handleRegister(final String email, final String password) {
        final String body = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        try {
            JSONObject jsonBody = new JSONObject(body);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,
                        Config.URL_REGISTER,
                        jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                listener.onSuccessfulRegister(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRegisterError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1500, // SOCKET_TIMEOUT_MS,
                2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        VolleyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface LoginActivityListener {
        void onSuccessfulLogin(JSONObject response);

        void onSuccessfulRegister(JSONObject response);

        void onLoginError(VolleyError error);

        void onRegisterError(VolleyError error);
    }
}

