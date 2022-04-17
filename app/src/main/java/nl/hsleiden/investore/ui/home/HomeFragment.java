package nl.hsleiden.investore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;

import nl.hsleiden.investore.R;
import nl.hsleiden.investore.data.database.FirebaseService;
import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private GoogleSignInClient mGoogleSignInClient;

    private  GoogleSignInAccount account;

    private InvestoreDB investoreDB;
    private FirebaseService firebaseService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        if (checkLoggedIn()) {
            loadDatabase();
            initialiseFirebaseService();
            buttonSetup();
            updateUI(true);
        } else {
            updateUI(false);
        }


        super.onStart();
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
        if (loggedIn) {
            binding.homeLoginInstructions.setVisibility(View.GONE);
            binding.homeInstructions.setVisibility(View.VISIBLE);
            binding.cloudOptions.setVisibility(View.VISIBLE);
            binding.homeExportButton.setVisibility(View.VISIBLE);
            binding.homeExportButton.setVisibility(View.VISIBLE);
        } else {
            binding.homeLoginInstructions.setVisibility(View.VISIBLE);
            binding.homeInstructions.setVisibility(View.GONE);
            binding.cloudOptions.setVisibility(View.GONE);
            binding.homeExportButton.setVisibility(View.GONE);
            binding.homeExportButton.setVisibility(View.GONE);
        }
    }

    private GoogleSignInAccount getAccount() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        return GoogleSignIn.getLastSignedInAccount(getContext());
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(getContext(), account.getEmail());
        }
    }

    private void initialiseFirebaseService() {
        firebaseService = new FirebaseService();
    }

    private void buttonSetup() {
        binding.homeExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportDB();
            }
        });
        binding.homeImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importDB();
            }
        });
    }

    public void exportDB() {
        firebaseService.deleteDB(account.getEmail());
        ArrayList<Item> items = investoreDB.getAllItems();
        for (Item item : items) {
            firebaseService.writeDB(account.getEmail(), item);
        }
        Toast.makeText(getContext(), R.string.items_exported, Toast.LENGTH_SHORT).show();
    }

    public void importDB() {
        firebaseService.getDB(account.getEmail(), investoreDB, getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}