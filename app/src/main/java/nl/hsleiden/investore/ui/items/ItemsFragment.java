package nl.hsleiden.investore.ui.items;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import nl.hsleiden.investore.data.FirebaseListener;
import nl.hsleiden.investore.data.database.FirebaseService;
import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.databinding.FragmentItemsBinding;

public class ItemsFragment extends Fragment implements FirebaseListener {
    private FragmentItemsBinding binding;

    private InvestoreDB investoreDB;
    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;

    private FirebaseService firebaseService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        itemsList = new ArrayList<>();

        recyclerView = binding.recyclerView;

        loadDatabase();
        setItemInfoFromDB();
        setAdapter();

        loadFirebase();
        // Test
        setupEventListener("testPath");


        return root;
    }

    private void loadFirebase() {
        firebaseService = new FirebaseService();
    }

    @Override
    public void setupEventListener(String referencePath) {
        firebaseService.setUpEventListener(referencePath, this);
    }

    @Override
    public void receiveSnapshot(DataSnapshot snapshot) {
        Log.d(TAG, "receiveSnapshot: Hoh, I recieved it: " + snapshot);
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(binding.getRoot().getContext());
        }
    }

    private void setAdapter() {
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext());
        binding.recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.updateItemList(itemsList);
    }

    private void setItemInfoFromDB() {
        itemsList = investoreDB.getAllItems();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}