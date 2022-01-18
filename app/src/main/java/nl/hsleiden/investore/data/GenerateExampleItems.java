package nl.hsleiden.investore.data;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;

import java.util.Random;
import java.util.UUID;

import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;

public class GenerateExampleItems {

    static final int NUMBER_OF_CASES = 4;

    static public Item generateItem(int randomInt) {
        String id = UUID.randomUUID().toString();
        String name = "";
        String notes = "";
        String entryDate = "";
        String sellDate = "";
        boolean sold = false;
        double buyPrice = 0;
        double sellPrice = 0;
        switch (randomInt % NUMBER_OF_CASES) {
            case 0:
                name = "Cheese";
                notes = "From the cellar from my grandfather";
                entryDate = "2020-11-07";
                buyPrice = 3.00;
                break;
            case 1:
                name = "Laptop";
                notes = "With an intel core i15!";
                entryDate = "2021-12-17";
                buyPrice = 110.00;
                break;
            case 2:
                name = "Painting of a Cow";
                notes = "";
                entryDate = "2018-10-27";
                sellDate = "2019-02-11";
                sold = true;
                buyPrice = 5.00;
                sellPrice = 20.00;
                break;
            case 3:
                name = "Table";
                notes = "A wooden table";
                entryDate = "2020-01-21";
                buyPrice = 16.30;
                break;
        }
        return new Item(id, name, notes, entryDate, sellDate, sold, buyPrice, sellPrice);
    }

    static public void insertAllExampleItemsInDB(InvestoreDB investoreDB) {
        Log.d(TAG, "insertAllExampleItemsInDB: start");
        for (int i = 0; i < NUMBER_OF_CASES; i++) {
            Log.d(TAG, "insertAllExampleItemsInDB: i" + i);
            investoreDB.addItem(generateItem(i));
        }
    }
}
