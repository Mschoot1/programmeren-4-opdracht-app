package com.example.marni.programmeren_4_opdracht_app.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.marni.programmeren_4_opdracht_app.domain.FilmMapper.RESULT;
import static com.example.marni.programmeren_4_opdracht_app.domain.InventoryMapper.INVENTORY_ID;

public class RentalMapper {

    private static final String RENTAL_DATE = "rental_date";
    private static final String CUSTOMER_ID = "customer_id";

    public static ArrayList<Rental> mapRentalList(JSONObject response) {

        ArrayList<Rental> result = new ArrayList<>();
        try {
            JSONArray jsonArray = response.getJSONArray(RESULT);

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonProduct = jsonArray.getJSONObject(j);

                Rental r = new Rental();
                int inventoryId = jsonProduct.getInt(INVENTORY_ID);
                String rentalDate = getFormattedDate(jsonProduct.getString(RENTAL_DATE));
                int customerId = jsonProduct.getInt(CUSTOMER_ID);

                r.setInventoryId(inventoryId);
                r.setRentalDate(rentalDate);
                r.setCustomerId(customerId);

                result.add(r);
            }
        } catch (JSONException ex) {
            Log.e("RentalMapper", "mapRentalList JSONException " + ex.getLocalizedMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getFormattedDate(String s) throws ParseException {

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
        Date parsedDate = sdf.parse(s);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        return sdf.format(parsedDate);
    }
}
