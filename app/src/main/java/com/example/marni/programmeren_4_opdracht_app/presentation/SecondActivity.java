package com.example.marni.programmeren_4_opdracht_app.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Film;
import com.example.marni.programmeren_4_opdracht_app.domain.FilmMapper;
import com.example.marni.programmeren_4_opdracht_app.volley.FilmsActivityRequests;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements FilmsActivityRequests.LoginActivityListener {

    private final String tag = getClass().getSimpleName();

    private List<Film> films = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        FilmsActivityRequests requests = new FilmsActivityRequests(getApplicationContext(), this);
        requests.handleGetFilms(0, 10);
    }

    @Override
    public void onSuccessfulGetFilms(JSONObject response) {
        Log.i(tag, "response: " + response);
        films = FilmMapper.mapToDoList(response);
    }

    @Override
    public void onGetFilmsError(VolleyError error) {
        Log.e(tag, "onGetFilmsError " + error.toString());
    }
}
