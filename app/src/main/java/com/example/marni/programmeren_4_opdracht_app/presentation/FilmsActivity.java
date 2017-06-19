package com.example.marni.programmeren_4_opdracht_app.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.marni.programmeren_4_opdracht_app.R;
import com.example.marni.programmeren_4_opdracht_app.domain.Film;
import com.example.marni.programmeren_4_opdracht_app.adapters.FilmAdapter;
import com.example.marni.programmeren_4_opdracht_app.mappers.FilmMapper;
import com.example.marni.programmeren_4_opdracht_app.volley.FilmsActivityRequests;

import org.json.JSONObject;

import java.util.ArrayList;

public class FilmsActivity extends AppCompatActivity implements FilmsActivityRequests.LoginActivityListener, FilmAdapter.OnLoadMoreItems {

    private ProgressBar progressBar;

    public static final String FILM = "film";
    private final String tag = getClass().getSimpleName();

    private ArrayList<Film> films = new ArrayList<>();
    private FilmAdapter adapter;

    private FilmsActivityRequests requests;
    public static final int INITIAL_OFFSET = 0;
    public static final int INITIAL_COUNT = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.films);

        progressBar = (ProgressBar) findViewById(R.id.pb);

        adapter = new FilmAdapter(getApplicationContext(), getLayoutInflater(), films, this);
        final ListView lvFilms = (ListView) findViewById(R.id.lvFilms);
        lvFilms.setAdapter(adapter);
        lvFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), InventoriesActivity.class);
                intent.putExtra(FILM, films.get(position));
                startActivity(intent);
            }
        });

        requests = new FilmsActivityRequests(getApplicationContext(), this);
        requests.handleGetFilms(INITIAL_OFFSET, INITIAL_COUNT);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_films, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_item_sign_out:
                SharedPreferences prefs = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();

                intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                Toast.makeText(this, R.string.signed_out, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;
            case R.id.menu_item_history:
                intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
                break;
            default:
                // empty
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessfulGetFilms(JSONObject response) {
        progressBar.setVisibility(View.INVISIBLE);
        Log.i(tag, "response: " + response);
        for (Film f : FilmMapper.mapFilmList(response)) {
            films.add(f);
            adapter.notifyDataSetChanged();
        }
        Log.i(tag, "films.size(): " + films.size());
    }

    @Override
    public void onGetFilmsError(VolleyError error) {
        progressBar.setVisibility(View.INVISIBLE);
        Log.e(tag, "onGetFilmsError " + error.toString());
    }

    @Override
    public void loadMoreItems(int offset, int count) {
        requests.handleGetFilms(offset, count);
    }
}
