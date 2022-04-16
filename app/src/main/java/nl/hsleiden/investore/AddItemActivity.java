package nl.hsleiden.investore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import nl.hsleiden.investore.data.DatePickerListener;
import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.data.tools.DatePickerFragment;
import nl.hsleiden.investore.data.tools.ItemValidationModel;
import nl.hsleiden.investore.databinding.ActivityAddItemBinding;

public class AddItemActivity
        extends AppCompatActivity
        implements DatePickerListener {

    private EditText editName, editEntryDate, editBuyPrice, editNotes;
    private int defaultAddNameColor, defaultAddBuyDateColor, defaultAddBuyPriceColor;
    private Button addSubmitButton;

    private ActivityAddItemBinding binding;

    private ItemValidationModel itemValidationModel;
    private InvestoreDB investoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemValidationModel = new ItemValidationModel();

        loadDatabase();
        assignVariables();
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            String accountMail = getAccountMail();
            investoreDB = new InvestoreDB(this, accountMail);
        }
    }

    private String getAccountMail() {
        GoogleSignInAccount account = getAccount();
        return account.getEmail();
    }

    private GoogleSignInAccount getAccount() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        return account;
    }

    private void assignVariables() {
        editName = binding.editName;
        editEntryDate = binding.editEntryDate;
        editBuyPrice = binding.editBuyPrice;
        editNotes = binding.editNotes;
        addSubmitButton = binding.addSubmitButton;

        defaultAddNameColor = binding.addItemName.getCurrentTextColor();
        defaultAddBuyDateColor = binding.addItemBuyDate.getCurrentTextColor();
        defaultAddBuyPriceColor = binding.addItemBuyPrice.getCurrentTextColor();

        addSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldsAreValid()) {
                    Item item = generateItemFromInputs();
                    addItemToDB(item);
                    toastAddedSuccess();
                    goToNavigationView();
                }
            }
        });
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void receiveDate(String date) {
        binding.editEntryDate.setText(date);
    }

    private boolean fieldsAreValid() {
        boolean isValid = true;
        binding.addItemName.getCurrentTextColor();
        if (!itemValidationModel.nameIsValid(editName.getText().toString())) {
            binding.addItemName.setTextColor(Color.RED);
            isValid = false;
        } else {
            binding.addItemName.setTextColor(defaultAddNameColor);
        }
        if (!itemValidationModel.dateIsValid(editEntryDate.getText().toString(), getString(R.string.date_format))) {
            binding.addItemBuyDate.setTextColor(Color.RED);
            isValid = false;
        } else {
            binding.addItemBuyDate.setTextColor(defaultAddBuyDateColor);
        }
        if (!itemValidationModel.priceIsValid(editBuyPrice.getText().toString())) {
            binding.addItemBuyPrice.setTextColor(Color.RED);
            isValid = false;
        } else {
            binding.addItemBuyPrice.setTextColor(defaultAddBuyPriceColor);
        }
        return isValid;
    }

    private Item generateItemFromInputs() {
        String itemName = editName.getText().toString();
        String itemEntryDate = editEntryDate.getText().toString();
        double itemBuyPrice = Double.parseDouble(editBuyPrice.getText().toString());
        String itemNotes = editNotes.getText().toString();
        return new Item(itemName, itemNotes, itemEntryDate, itemBuyPrice);
    }

    private void addItemToDB(Item item) {
        investoreDB.addItem(item);
    }

    private void toastAddedSuccess() {
        Toast toast = Toast.makeText(this, R.string.item_successfully_added, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void goToNavigationView() {
        startActivity(new Intent(this, NavigationActivity.class));
    }
}