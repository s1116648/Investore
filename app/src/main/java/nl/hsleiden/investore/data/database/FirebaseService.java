package nl.hsleiden.investore.data.database;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import nl.hsleiden.investore.data.FirebaseListener;

public class FirebaseService {

    private final String DATABASE_URL = "https://investore-1eb78-default-rtdb.europe-west1.firebasedatabase.app/";

    private FirebaseDatabase firebaseDatabase;

    public FirebaseService() {
        firebaseDatabase = FirebaseDatabase.getInstance(DATABASE_URL);
    }

    public void writeAMessage() {

        // Write a message to the database
        DatabaseReference myRef = firebaseDatabase.getReference("message");

        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @NonNull
    public void setUpEventListener(@NonNull String referencePath, FirebaseListener listener) {
        DatabaseReference dbRef = firebaseDatabase.getReference(referencePath);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                listener.receiveSnapshot(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}
