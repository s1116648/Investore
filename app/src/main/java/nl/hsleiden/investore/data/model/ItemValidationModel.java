package nl.hsleiden.investore.data.model;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import nl.hsleiden.investore.databinding.ActivityAddItemBinding;

public class ItemValidationModel {

    public ItemValidationModel() {
    }

    public boolean nameIsValid(String name) {
        return name.trim().length() > 0;
    }

    public boolean dateIsValid(String date, String dateFormatString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatString);

        // Input to be parsed should strictly follow the defined date format
        // above.
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public boolean priceIsValid(String price) {
        try {
            double priceInDouble = Double.parseDouble(price);
            return (true);
        } catch (Exception e) {
            return false;
        }
    }
}
