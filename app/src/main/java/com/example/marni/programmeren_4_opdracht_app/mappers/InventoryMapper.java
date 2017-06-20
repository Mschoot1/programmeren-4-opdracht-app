package com.example.marni.programmeren_4_opdracht_app.mappers;

import android.util.Log;

import com.example.marni.programmeren_4_opdracht_app.domain.Inventory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InventoryMapper {

    public static final String INVENTORY_ID = "inventory_id";

    public static List<Inventory> mapInventoryList(JSONObject response) {

        List<Inventory> result = new ArrayList<>();
        try {
            JSONArray jsonArray = response.getJSONArray(FilmMapper.RESULT);
            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonProduct = jsonArray.getJSONObject(j);

                Inventory i = new Inventory();
                int inventoryId = jsonProduct.getInt(INVENTORY_ID);
                i.setInventoryId(inventoryId);
                result.add(i);
            }
        } catch (JSONException ex) {
            Log.e("InventoryMapper", "mapRental JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
