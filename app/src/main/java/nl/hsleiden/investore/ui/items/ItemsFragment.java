package nl.hsleiden.investore.ui.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.databinding.FragmentItemsBinding;

public class ItemsFragment extends Fragment {
    private FragmentItemsBinding binding;

    private InvestoreDB investoreDB;
    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        itemsList = new ArrayList<>();

        recyclerView = binding.recyclerView;

        loadDatabase();
        setItemInfoFromDB();
        setAdapter();

        investoreDB.logAllItems();

        return root;
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(binding.getRoot().getContext());
        }
    }

    private void setAdapter() {
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
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