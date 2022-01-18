package nl.hsleiden.investore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Random;

import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;

public class DatabaseTestActivity extends AppCompatActivity {

    private InvestoreDB investoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);
        loadDatabase();
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(this);
        }
    }

    public void addRandomEntry(View view) {
        Item randomItem = generateRandomItem();
        investoreDB.addItem(randomItem);
    }

    private Item generateRandomItem() {
        String id = "1";
        String name = "Kaas";
        String notes = "";
        String entryDate = "2022-01-18";
        String sellDate = "";
        boolean sold = false;
        int buyPrice = new Random().nextInt();
        int sellPrice = 0;
        return new Item(id, name, notes, entryDate, sellDate, sold, buyPrice, sellPrice);
    }

    public void clearItems(View view) {
        investoreDB.clearItems();
    }

    public void removeItem(View view) {
        investoreDB.removeItem(generateRandomItem());
    }
}