package nl.hsleiden.investore;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.data.model.ItemValidationModel;
import nl.hsleiden.investore.databinding.ActivityEditItemBinding;

public class EditItemActivity extends AppCompatActivity {

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
            defaultEditSellPriceColor;
    private String itemId;
    private Item item;
    private Button editSubmitButton;

    private ActivityEditItemBinding binding;

    private ItemValidationModel itemValidationModel;
    private InvestoreDB investoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadDatabase();
        assignVariables();
    }

    private void loadDatabase() {
        if (investoreDB == null) {
            investoreDB = new InvestoreDB(this);
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

        defaultEditNameColor = binding.editItemName.getCurrentTextColor();
        defaultEditBuyDateColor = binding.editItemBuyDate.getCurrentTextColor();
        defaultEditBuyPriceColor = binding.editItemBuyPrice.getCurrentTextColor();
        defaultEditSellDateColor = binding.editItemSellDate.getCurrentTextColor();
        defaultEditSellPriceColor = binding.editItemSellPrice.getCurrentTextColor();

        loadItemDetails();

        setUpSubmitButton();
    }

    private void loadItemDetails() {
        itemId = getItemIdFromExtras();
        item = investoreDB.getItemById(itemId);

        editName.setText(item.getName());
        editEntryDate.setText(item.getEntryDate());
        editBuyPrice.setText(item.getBuyPriceFormatted());
        editNotes.setText(item.getNotes());

        if (item.getSold()) {
            editSellDate.setText(item.getSellDate());
            editSellPrice.setText(item.getSellPriceFormatted());
        }
    }

    private String getItemIdFromExtras() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getString(getString(R.string.item_id_name));
    }

    private void setUpSubmitButton() {
        editSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsAreValid()) {
                    Item item = generateItemFromInputs();
                    Log.d(TAG, "onClick: new item: " + item.toString());
                    editItemInDB(item);
                    toastEditedSuccess();
                    goToNavigationView();
                }
            }
        });
    }

    private boolean fieldsAreValid() {
        boolean isValid = true;
        binding.editItemName.getCurrentTextColor();
        if (!itemValidationModel.nameIsValid(editName.getText().toString())) {
            binding.editItemName.setTextColor(Color.RED);
            isValid = false;
        } else {
            binding.editItemName.setTextColor(defaultEditNameColor);
        }
        if (!itemValidationModel.dateIsValid(editEntryDate.getText().toString(), getString(R.string.date_format))) {
            binding.editItemBuyDate.setTextColor(Color.RED);
            isValid = false;
        } else {
            binding.editItemBuyDate.setTextColor(defaultEditBuyDateColor);
        }
        if (!itemValidationModel.priceIsValid(editBuyPrice.getText().toString())) {
            binding.editItemBuyPrice.setTextColor(Color.RED);
            isValid = false;
        } else {
            binding.editItemBuyPrice.setTextColor(defaultEditBuyPriceColor);
        }
        if (allSellFieldsAreEmpty()) {
            return isValid;
        }

        if (!itemValidationModel.dateIsValid(editSellDate.getText().toString(), getString(R.string.date_format))) {
            binding.editItemSellDate.setTextColor(Color.RED);
            isValid = false;
        } else {
            binding.editItemSellDate.setTextColor(defaultEditSellDateColor);
        }
        if (!itemValidationModel.priceIsValid(editSellPrice.getText().toString())) {
            binding.editItemSellPrice.setTextColor(Color.RED);
            isValid = false;
        } else {
            binding.editItemSellPrice.setTextColor(defaultEditSellPriceColor);
        }
        return isValid;
    }

    private boolean allSellFieldsAreEmpty() {
        if (binding.editSellDate.getText().equals("")) {
            return false;
        }
        if (binding.editSellPrice.getText().equals("")) {
            return false;
        }
        return true;
    }

    private Item generateItemFromInputs() {
        String itemId = item.getID();
        String itemName = editName.getText().toString();
        String itemEntryDate = editEntryDate.getText().toString();
        double itemBuyPrice = Double.parseDouble(editBuyPrice.getText().toString());
        String itemNotes = editNotes.getText().toString();
        if (allSellFieldsAreEmpty()) {
            return new Item(itemId, itemName, itemNotes, itemEntryDate, itemBuyPrice);
        }
        String itemSellDate = editSellDate.getText().toString();
        double itemSellPrice = Double.parseDouble(editBuyPrice.getText().toString());
        return new Item(itemId, itemName, itemNotes, itemEntryDate, itemSellDate, true, itemBuyPrice, itemSellPrice);
    }

    private void editItemInDB(Item item) {
        investoreDB.updateItem(item);
    }

    private void toastEditedSuccess() {
        Toast toast = Toast.makeText(this, R.string.item_successfully_edited, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void goToNavigationView() {
        startActivity(new Intent(this, NavigationActivity.class));
    }
}