package nl.hsleiden.investore.ui.items;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

import nl.hsleiden.investore.data.database.FirebaseService;
import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.databinding.FragmentItemsBinding;

public class ItemsFragment extends Fragment {
    private FragmentItemsBinding binding;

    private InvestoreDB investoreDB;
    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;

    private FirebaseService firebaseService;

    private GoogleSignInAccount account;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        itemsList = new ArrayList<>();

        recyclerView = binding.recyclerView;

        if (checkLoggedIn()) {loadDatabase();
            setItemInfoFromDB();
            setAdapter();
        }

        return root;
    }

    private boolean checkLoggedIn() {
        GoogleSignInAccount account = getAccount();
        if (account == null) {
            return false;
        }
        this.account = account;
        return true;
    }

    private GoogleSignInAccount getAccount() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        return GoogleSignIn.getLastSignedInAccount(getContext());
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(binding.getRoot().getContext(), account.getEmail());
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