package com.example.marni.programmeren_4_opdracht_app.mappers;

import android.util.Log;

import com.example.marni.programmeren_4_opdracht_app.domain.Rental;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.marni.programmeren_4_opdracht_app.mappers.FilmMapper.TITLE;

public class RentalMapper {

    private final String RENTAL_MAPPER = "RentalMapper";

    private static final String RENTAL_DATE = "rental_date";
    private static final String CUSTOMER_ID = "customer_id";
    public static final String RETURN_DATE = "return_date";

    public static Rental mapRental(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(FilmMapper.RESULT);
            JSONObject jsonProduct = jsonArray.getJSONObject(0);

            Rental r = new Rental();
            int inventoryId = jsonProduct.getInt(InventoryMapper.INVENTORY_ID);
            String rentalDate = getFormattedDate(jsonProduct.getString(RENTAL_DATE));
            int customerId = jsonProduct.getInt(CUSTOMER_ID);

            r.setInventoryId(inventoryId);
            r.setRentalDate(rentalDate);
            r.setCustomerId(customerId);
            return r;
        } catch (JSONException ex) {
            Log.e("RentalMapper", "mapRental JSONException " + ex.getLocalizedMessage());
        } catch (ParseException e) {
            Log.e("RentalMapper", "mapRental ParseException " + e.getLocalizedMessage());
        }
        return null;
    }

    public static List<Rental> mapRentalList(JSONObject response) {
        List<Rental> rentals = new ArrayList<>();
        try {
            JSONArray jsonArray = response.getJSONArray(FilmMapper.RESULT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonProduct = jsonArray.getJSONObject(i);

                Rental r = new Rental();
                int inventoryId = jsonProduct.getInt(InventoryMapper.INVENTORY_ID);
                String rentalDate = getFormattedDate(jsonProduct.getString(RENTAL_DATE));
                int customerId = jsonProduct.getInt(CUSTOMER_ID);
                String title = jsonProduct.getString(TITLE);
                String returnDate = null;
                if (!jsonProduct.isNull(RETURN_DATE)) {
                    returnDate = getFormattedDate(jsonProduct.getString(RETURN_DATE));
                    Log.i("mapRentalList", "returnDate: " + returnDate);
                }

                r.setInventoryId(inventoryId);
                r.setRentalDate(rentalDate);
                r.setCustomerId(customerId);
                r.setTitle(title);
                r.setReturnDate(returnDate);
                rentals.add(r);
            }
        } catch (JSONException ex) {
            Log.e("RentalMapper", "mapRentalList JSONException " + ex.getLocalizedMessage());
        } catch (ParseException e) {
            Log.e("RentalMapper", "mapRental ParseException " + e.getLocalizedMessage());
        }
        return rentals;
    }

    private static String getFormattedDate(String s) throws ParseException {

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
        Date parsedDate = sdf.parse(s);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        return sdf.format(parsedDate);
    }
}
