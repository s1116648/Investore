package nl.hsleiden.investore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import nl.hsleiden.investore.data.database.InvestoreDB;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.databinding.ActivityEditItemBinding;

public class EditItemActivity extends AppCompatActivity {

    private EditText editName, editEntryDate, editBuyPrice, editNotes, editSellDate, editSellPrice;
    private int defaultEditNameColor, defaultEditBuyDateColor, defaultEditBuyPriceColor, defaultEditSellDateColor;
    private String itemId;
    private Item item;
    private ActivityEditItemBinding binding;
    private Button editSubmitButton;

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

        loadItemDetails();
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
}