package nl.hsleiden.investore.data;

import com.google.firebase.database.DataSnapshot;

public interface FirebaseListener {

    public void setupEventListener(String referencePath);

    public void receiveSnapshot(DataSnapshot snapshot);
}
