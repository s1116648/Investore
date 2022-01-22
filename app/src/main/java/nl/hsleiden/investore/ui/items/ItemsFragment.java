package nl.hsleiden.investore.ui.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import nl.hsleiden.investore.R;
import nl.hsleiden.investore.data.GenerateExampleItems;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.databinding.FragmentItemsBinding;

public class ItemsFragment extends Fragment {
    private FragmentItemsBinding binding;

    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        itemsList = new ArrayList<>();

        recyclerView = binding.recyclerView;
        setItemInfo();
        setAdapter();

        return root;
    }

    private void setAdapter() {
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        binding.recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.updateItemList(itemsList);
    }

    private void setItemInfo() {
        Item item1 = GenerateExampleItems.generateItem(1);
        itemsList.add(item1);
        Item item2 = GenerateExampleItems.generateItem(2);
        itemsList.add(item2);
        Item item3 = GenerateExampleItems.generateItem(3);
        itemsList.add(item3);
        Item item4 = GenerateExampleItems.generateItem(4);
        itemsList.add(item4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}