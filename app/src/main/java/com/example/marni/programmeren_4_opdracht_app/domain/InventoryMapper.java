package com.example.marni.programmeren_4_opdracht_app.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.marni.programmeren_4_opdracht_app.domain.FilmMapper.RESULT;

public class InventoryMapper {

    public static final String INVENTORY_ID = "inventory_id";

    public static ArrayList<Inventory> mapInventoryList(JSONObject response) {

        ArrayList<Inventory> result = new ArrayList<>();
        try {
            JSONArray jsonArray = response.getJSONArray(RESULT);
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonProduct = jsonArray.getJSONObject(j);

                Inventory i = new Inventory();
                int inventoryId = jsonProduct.getInt(INVENTORY_ID);
                i.setInventoryId(inventoryId);
                result.add(i);
            }
        } catch (JSONException ex) {
            Log.e("InventoryMapper", "mapRentalList JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
