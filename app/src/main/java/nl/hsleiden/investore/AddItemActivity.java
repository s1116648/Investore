package nl.hsleiden.investore;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nl.hsleiden.investore.data.model.ItemValidationModel;
import nl.hsleiden.investore.databinding.ActivityAddItemBinding;

public class AddItemActivity extends AppCompatActivity {

    private EditText editName, editEntryDate, editBuyPrice, editNotes;
    private int defaultAddNameColor, defaultAddBuyDateColor, defaultAddBuyPriceColor;
    private Button addSubmitButton;

    private ActivityAddItemBinding binding;

    private ItemValidationModel itemValidationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemValidationModel = new ItemValidationModel();

        assignVariables();
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
                if (checkIfValid()) {
                    addItemToDB();
                }
            }
        });
    }

    private boolean checkIfValid() {
        binding.addItemName.getCurrentTextColor();
        if (!itemValidationModel.nameIsValid(editName.getText().toString())) {
            binding.addItemName.setTextColor(Color.RED);
        } else {
            binding.addItemName.setTextColor(defaultAddNameColor);
        }
        if (!itemValidationModel.dateIsValid(editEntryDate.getText().toString(), getString(R.string.date_format))) {
            binding.addItemBuyDate.setTextColor(Color.RED);
        } else {
            binding.addItemBuyDate.setTextColor(defaultAddBuyDateColor);
        }
        if (!itemValidationModel.priceIsValid(editBuyPrice.getText().toString())) {
            binding.addItemBuyPrice.setTextColor(Color.RED);
        } else {
            binding.addItemBuyPrice.setTextColor(defaultAddBuyPriceColor);
        }
        return true;
    }

    private void addItemToDB() {
    }


}