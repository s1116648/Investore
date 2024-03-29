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
import nl.hsleiden.investore.data.tools.ItemDataToStringTool;
import nl.hsleiden.investore.databinding.ActivityEditItemBinding;

public class EditItemActivity
        extends AppCompatActivity
        implements DatePickerListener {

    private EditText
            editName,
            editEntryDate,
            editBuyPrice,
            editNotes,
            editSellDate,
            editSellPrice;
    private int
            defaultEditNameColor,
            defaultEditBuyDateColor,
            defaultEditBuyPriceColor,
            defaultEditSellDateColor,
            defaultEditSellPriceColor,
            wrongColor;
    private String itemId, currency;
    private Item item;
    private Button
            editSubmitButton,
            editCancelButton,
            editDeleteButton;

    private enum DatePickerDate {
        ENTRY_DATE,
        DEFAULT,
        SELL_DATE
    }
    private DatePickerDate datePickerDate;

    private ActivityEditItemBinding binding;

    private ItemValidationModel itemValidationModel;
    private InvestoreDB investoreDB;
    private ItemDataToStringTool stringTool;

    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (checkLoggedIn()) {
            loadDatabase();
            assignVariables();
        }
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
        return GoogleSignIn.getLastSignedInAccount(this);
    }


    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(this, account.getEmail());
        }
    }

    private void assignVariables() {
        itemValidationModel = new ItemValidationModel();

        editName = binding.editName;
        editEntryDate = binding.editEntryDate;
        editBuyPrice = binding.editBuyPrice;
        editNotes = binding.editNotes;
        editSellDate = binding.editSellDate;
        editSellPrice = binding.editSellPrice;

        editSubmitButton = binding.editSubmitButton;
        editCancelButton = binding.editCancelButton;
        editDeleteButton = binding.editDeleteButton;

        stringTool = new ItemDataToStringTool();

        datePickerDate = DatePickerDate.DEFAULT;

        setUpColors();
        loadItemDetails();
        setUpButtons();
    }

    private void setUpColors() {
        defaultEditNameColor = binding.editItemName.getCurrentTextColor();
        defaultEditBuyDateColor = binding.editItemBuyDate.getCurrentTextColor();
        defaultEditBuyPriceColor = binding.editItemBuyPrice.getCurrentTextColor();
        defaultEditSellDateColor = binding.editItemSellDate.getCurrentTextColor();
        defaultEditSellPriceColor = binding.editItemSellPrice.getCurrentTextColor();
        wrongColor = Color.RED;
    }

    private void loadItemDetails() {
        itemId = getItemIdFromExtras();
        item = investoreDB.getItemById(itemId);

        initialiseCurrency();

        editName.setText(item.getName());
        editEntryDate.setText(item.getEntryDate());
        String buyPrice = stringTool.doubleInCurrency(item.getBuyPrice(), currency);
        editBuyPrice.setText(buyPrice);
        editNotes.setText(item.getNotes());

        if (item.getSold()) {
            editSellDate.setText(item.getSellDate());
            String sellPrice = stringTool.doubleInCurrency(item.getSellPrice(), currency);
            editSellPrice.setText(sellPrice);
        }
    }

    private void initialiseCurrency() {
        currency = "€";
    }

    private String getItemIdFromExtras() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getString(getString(R.string.item_id_name));
    }

    private void setUpButtons() {
        setUpSubmitButton();
        setUpCancelButton();
        setUpDeleteButton();
    }

    private void setUpSubmitButton() {
        editSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsAreValid()) {
                    Item item = generateItemFromInputs();
                    investoreDB.updateItem(item);
                    toastEditedSuccess();
                    goToNavigationView();
                } else {
                    toastEditedFailure();
                }
            }
        });
    }

    private void setUpCancelButton() {
        editCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastCanceled();
                goToNavigationView();
            }
        });
    }

    private void setUpDeleteButton() {
        editDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                investoreDB.removeItem(item);
                toastDeletedSuccess();
                goToNavigationView();
            }
        });
    }

    public void showEntryDatePickerDialog(View view) {
        datePickerDate = DatePickerDate.ENTRY_DATE;
        showDatePickerDialog(view);
    }

    public void showSellDatePickerDialog(View view) {
        datePickerDate = DatePickerDate.SELL_DATE;
        showDatePickerDialog(view);
    }

    private void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void receiveDate(String date) {
        switch (datePickerDate) {
            case ENTRY_DATE:
                binding.editEntryDate.setText(date);
            case SELL_DATE:
                binding.editSellDate.setText(date);
        }
    }

    private boolean fieldsAreValid() {
        boolean isValid = true;
        int colorInvalid = Color.RED;

        int editNameColor;
        if (!itemValidationModel.nameIsValid(editName.getText().toString())) {
            editNameColor = colorInvalid;
            isValid = false;
        } else {
            editNameColor = defaultEditNameColor;
        }
        binding.editItemName.setTextColor(editNameColor);

        int editEntryDateColor;
        if (!itemValidationModel.dateIsValid(editEntryDate.getText().toString(), getString(R.string.date_format))) {
            editEntryDateColor = colorInvalid;
            isValid = false;
        } else {
            editEntryDateColor = defaultEditBuyDateColor;
        }
        binding.editItemBuyDate.setTextColor(editEntryDateColor);

        int editBuyPriceColor;
        if (!itemValidationModel.priceIsValid
                (editBuyPrice.getText().toString().replace(currency, ""))
        ) {
            editBuyPriceColor = Color.RED;
            isValid = false;
        } else {
            editBuyPriceColor = defaultEditBuyPriceColor;
        }
        binding.editItemBuyPrice.setTextColor(editBuyPriceColor);

        if (allSellFieldsAreEmpty()) {
            return isValid;
        }

        int editSellDateColor;
        if (!itemValidationModel.dateIsValid(editSellDate.getText().toString(), getString(R.string.date_format))) {
            isValid = false;
            editSellDateColor = wrongColor;
        } else {
            editSellDateColor = defaultEditSellDateColor;
        }
        binding.editItemSellDate.setTextColor(editSellDateColor);

        int editSellPriceColor;
        if (!itemValidationModel.priceIsValid
                (editSellPrice.getText().toString().replace(currency, ""))
        ) {
            isValid = false;
            editSellPriceColor = wrongColor;
        } else {
            editSellPriceColor = defaultEditSellPriceColor;
        }
        binding.editItemSellPrice.setTextColor(editSellPriceColor);

        return isValid;
    }

    private boolean allSellFieldsAreEmpty() {
        if (!binding.editSellDate.getText().toString().equals("")) {
            return false;
        }
        if (!binding.editSellPrice.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private Item generateItemFromInputs() {
        String itemId = item.getID();
        String itemName = editName.getText().toString();
        String itemEntryDate = editEntryDate.getText().toString();
        double itemBuyPrice =
                Double.parseDouble(editBuyPrice.getText().toString().replace(currency, ""));
        String itemNotes = editNotes.getText().toString();
        if (allSellFieldsAreEmpty()) {
            return new Item(itemId, itemName, itemNotes, itemEntryDate, itemBuyPrice);
        }
        String itemSellDate = editSellDate.getText().toString();
        double itemSellPrice =
                Double.parseDouble(editSellPrice.getText().toString().replace(currency, ""));
        return new Item(itemId, itemName, itemNotes, itemEntryDate, itemSellDate, true, itemBuyPrice, itemSellPrice);
    }

    private void toastEditedSuccess() {
        throwAToastMessage(getString(R.string.item_successfully_edited));
    }

    private void toastEditedFailure() {
        throwAToastMessage(getString(R.string.item_failure_failure));
    }

    private void toastCanceled() {
        throwAToastMessage(getString(R.string.edit_item_canceled));
    }

    private void toastDeletedSuccess() {
        throwAToastMessage(getString(R.string.item_successfully_deleted));
    }

    private void throwAToastMessage(String toastText) {
        Toast toast = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void goToNavigationView() {
        startActivity(new Intent(this, NavigationActivity.class));
    }
}