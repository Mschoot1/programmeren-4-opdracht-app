package com.example.marni.programmeren_4_opdracht_app.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.volley.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoginRequest.LoginActivityListener {

    private final String tag = getClass().getSimpleName();

    private LoginRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        request = new LoginRequest(this, this);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        Button bSignIn = (Button) findViewById(R.id.bSignIn);
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.handleLogin(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    @Override
    public void onLoginSuccessful(JSONObject response) {
        Toast.makeText(this, "Hello success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VolleyError error) {
        Log.e(tag, "onError " + error.toString());
        Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();
    }
}
