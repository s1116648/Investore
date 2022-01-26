package nl.hsleiden.investore.data.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseService {

    public void writeAMessage() {

        String databaseUrl = "https://investore-1eb78-default-rtdb.europe-west1.firebasedatabase.app/";

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseUrl);
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
}
