package com.example.marni.programmeren_4_opdracht_app.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Film;
import com.example.marni.programmeren_4_opdracht_app.domain.FilmAdapter;
import com.example.marni.programmeren_4_opdracht_app.domain.FilmMapper;
import com.example.marni.programmeren_4_opdracht_app.volley.FilmsActivityRequests;

import org.json.JSONObject;

import java.util.ArrayList;

public class FilmsActivity extends AppCompatActivity implements FilmsActivityRequests.LoginActivityListener {

    public static final String FILM = "film";
    private final String tag = getClass().getSimpleName();

    private ArrayList<Film> films = new ArrayList<>();
    private FilmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);

        adapter = new FilmAdapter(getApplicationContext(), getLayoutInflater(), films);
        ListView lvFilms = (ListView) findViewById(R.id.lvFilms);
        lvFilms.setAdapter(adapter);
        lvFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), InventoriesActivity.class);
                intent.putExtra(FILM, films.get(position));
                startActivity(intent);
            }
        });


        FilmsActivityRequests requests = new FilmsActivityRequests(getApplicationContext(), this);
        requests.handleGetFilms(0, 10);
    }

    @Override
    public void onSuccessfulGetFilms(JSONObject response) {
        Log.i(tag, "response: " + response);
        for(Film f : FilmMapper.mapFilmList(response)) {
            films.add(f);
            adapter.notifyDataSetChanged();
        }
        Log.i(tag, "films.size(): " + films.size());
    }

    @Override
    public void onGetFilmsError(VolleyError error) {
        Log.e(tag, "onGetFilmsError " + error.toString());
    }
}
