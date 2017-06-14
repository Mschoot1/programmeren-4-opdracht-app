package com.example.marni.programmeren_4_opdracht_app.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilmMapper {

    private static final String RESULT = "result";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    public static List<Film> mapToDoList(JSONObject response){

        List<Film> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray(RESULT);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonProduct = jsonArray.getJSONObject(i);

                Film f = new Film();
                String title = jsonProduct.getString(TITLE);
                String description = jsonProduct.getString(DESCRIPTION);

                f.setTitle(title);
                f.setDescription(description);

                result.add(f);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "mapToDoList JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
