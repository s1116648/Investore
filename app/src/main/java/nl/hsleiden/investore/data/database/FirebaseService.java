package nl.hsleiden.investore.data.database;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.investore.R;
import nl.hsleiden.investore.data.FirebaseListener;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.data.model.Items;

public class FirebaseService {

    private final String DATABASE_URL = "https://investore-1eb78-default-rtdb.europe-west1.firebasedatabase.app/";

    private FirebaseDatabase firebaseDatabase;

    public FirebaseService() {
        firebaseDatabase = FirebaseDatabase.getInstance(DATABASE_URL);
    }

    public void writeDB(String accountMail, Item item) {
        Log.d(TAG, "writeDB: " + makeValidPath(accountMail));
        DatabaseReference myRef = firebaseDatabase.getReference(makeValidPath(accountMail));

        myRef.child("items").child(item.getID()).child("ID").setValue(item.getID());
        myRef.child("items").child(item.getID()).child("name").setValue(item.getName());
        myRef.child("items").child(item.getID()).child("notes").setValue(item.getNotes());
        myRef.child("items").child(item.getID()).child("entryDate").setValue(item.getEntryDate());
        myRef.child("items").child(item.getID()).child("sellDate").setValue(item.getSellDate());
        myRef.child("items").child(item.getID()).child("sold").setValue(item.getSold());
        myRef.child("items").child(item.getID()).child("buyPrice").setValue(item.getBuyPrice());
        myRef.child("items").child(item.getID()).child("sellPrice").setValue(item.getSellPrice());
    }

    public void deleteDB(String accountMail) {
        DatabaseReference myRef = firebaseDatabase.getReference(makeValidPath(accountMail));
        myRef.child("items").removeValue();
    }

    public ArrayList<Items> getDB(String accountMail, InvestoreDB investoreDB, Context context) {
        DatabaseReference myRef = firebaseDatabase.getReference(makeValidPath(accountMail));
        myRef.child("items").get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            investoreDB.clearItems();
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                Item item = ds.getValue(Item.class);
                                investoreDB.addItem(item);
                            }
                            Toast.makeText(context, R.string.items_exported, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        return null;
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

    private String makeValidPath(String string) {
        string = string.replace(".", "");
        string = string.replace("#", "");
        string = string.replace("$", "");
        string = string.replace("[", "");
        string = string.replace("]", "");
        return string;
    }

}
