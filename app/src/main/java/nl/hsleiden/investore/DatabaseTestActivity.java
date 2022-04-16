package nl.hsleiden.investore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Random;
import java.util.UUID;

import nl.hsleiden.investore.data.GenerateExampleItems;
import nl.hsleiden.investore.data.database.FirebaseService;
import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;

public class DatabaseTestActivity extends AppCompatActivity {

    private InvestoreDB investoreDB;

    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        if (checkLoggedIn()) {
            loadDatabase();
            initialiseFirebaseService();
        } else {
            boolean loggedIn = false;
            updateUI(loggedIn);
        }
    }

    private boolean checkLoggedIn() {
        GoogleSignInAccount account = getAccount();
        if (account == null) {
            return false;
        }
        this.account = account;
        return true;
    }

    private void updateUI(Boolean loggedIn) {
        // ToDo
    }

    private GoogleSignInAccount getAccount() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        return GoogleSignIn.getLastSignedInAccount(this);
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(this, account.getEmail());
        }
    }

    public void addRandomEntry(View view) {
        Item randomItem = generateRandomItem();
        investoreDB.addItem(randomItem);
    }

    private Item generateRandomItem() {
        String id = UUID.randomUUID().toString();
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

    public void putExampleItemsInDB(View view) {
        GenerateExampleItems.insertAllExampleItemsInDB(investoreDB);
    }

    private FirebaseService firebaseService;

    private void initialiseFirebaseService() {
        firebaseService = new FirebaseService();
    }

    public void writeMessageInFirebase(View view) {
        firebaseService.writeAMessage();
    }
}