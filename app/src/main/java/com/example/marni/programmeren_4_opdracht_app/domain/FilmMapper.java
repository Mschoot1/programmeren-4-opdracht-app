package com.example.marni.programmeren_4_opdracht_app.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilmMapper {

    private static final String RESULT = "result";
    private static final String FILM_ID = "film_id";
    private static final String TITLE = "title";
    private static final String RELEASE_YEAR = "release_year";
    private static final String DESCRIPTION = "description";

    public static ArrayList<Film> mapFilmList(JSONObject response){

        ArrayList<Film> result = new ArrayList<>();
        try{
            JSONArray jsonArray = response.getJSONArray(RESULT);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonProduct = jsonArray.getJSONObject(i);

                Film f = new Film();
                int filmId = jsonProduct.getInt(FILM_ID);
                String title = jsonProduct.getString(TITLE);
                int releaseYear = jsonProduct.getInt(RELEASE_YEAR);
                String description = jsonProduct.getString(DESCRIPTION);

                f.setFilmId(filmId);
                f.setTitle(title);
                f.setReleaseYear(releaseYear);
                f.setDescription(description);

                result.add(f);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "mapFilmList JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
