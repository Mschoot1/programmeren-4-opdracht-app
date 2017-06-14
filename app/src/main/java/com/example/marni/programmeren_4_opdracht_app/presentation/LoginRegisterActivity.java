package com.example.marni.programmeren_4_opdracht_app.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.volley.LoginActivityRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRegisterActivity extends AppCompatActivity implements LoginActivityRequests.LoginActivityListener, View.OnClickListener {

    private final String tag = getClass().getSimpleName();

    private LoginActivityRequests request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        request = new LoginActivityRequests(this, this);
        Button bSignIn = (Button) findViewById(R.id.bSignIn);
        Button bRegister = (Button) findViewById(R.id.bRegister);
        bSignIn.setOnClickListener(this);
        bRegister.setOnClickListener(this);
    }

    @Override
    public void onSuccessfulLogin(JSONObject response) {
        Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show();
        try {
            String token = response.getString(getString(R.string.token));

            Context context = getApplicationContext();
            SharedPreferences prefs = context.getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getString(R.string.token), token);
            editor.apply();

            Intent intent = new Intent(context, SecondActivity.class);
            startActivity(intent);

            finish();
        } catch (JSONException e) {
            Log.e(tag, e.getMessage());
        }
    }

    @Override
    public void onSuccessfulRegister(JSONObject response) {
        Toast.makeText(this, "Email registered", Toast.LENGTH_SHORT).show();
        try {
            String email = response.getString("email");
            String password = response.getString("password");
            request.handleLogin(email, password);
        } catch (JSONException e) {
            Log.e(tag, e.getMessage());
        }
    }

    @Override
    public void onLoginError(VolleyError error) {
        Log.e(tag, "onLoginError " + error.toString());
        Toast.makeText(this, "Login failed. Please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterError(VolleyError error) {
        Log.e(tag, "onRegisterError " + error.toString());
        Toast.makeText(this, "This email address already exists", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        switch (v.getId()) {
            case R.id.bSignIn :
                if(isValidEmail(etEmail.getText().toString())) {
                    request.handleLogin(email, password);
                } else {
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bRegister:
                request.handleRegister(email, password);
                break;
            default:
                // empty
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
