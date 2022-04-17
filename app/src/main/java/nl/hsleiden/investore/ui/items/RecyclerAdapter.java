package nl.hsleiden.investore.ui.items;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.hsleiden.investore.EditItemActivity;
import nl.hsleiden.investore.R;
import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.data.tools.ItemDataToStringTool;
import nl.hsleiden.investore.databinding.ListItemsBinding;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Item> itemsList;

    private Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void updateItemList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemsBinding listItemsBinding = ListItemsBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(listItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private void goToEditItemActivity(Item item) {
        Intent intent = new Intent(context, EditItemActivity.class);
        intent.putExtra(context.getResources().getString(R.string.item_id_name), item.getID());
        context.startActivity(intent);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ListItemsBinding listItemsBinding;
        ItemDataToStringTool stringTool;
        String currency;

        public ViewHolder(@NonNull ListItemsBinding listItemsBinding) {
            super(listItemsBinding.getRoot());
            this.listItemsBinding = listItemsBinding;

            this.listItemsBinding.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToEditItemActivity(itemsList.get(getAdapterPosition()));
                }
            });

            stringTool = new ItemDataToStringTool();
        }

        public void bindView(Item item) {
            initialiseCurrency();

            listItemsBinding.itemName.setText(item.getName());
            if (item.getNotes().equals("")) {
                listItemsBinding.itemNotes.setVisibility(View.GONE);
            } else {
                listItemsBinding.itemNotes.setVisibility(View.VISIBLE);
                listItemsBinding.itemNotes.setText(item.getNotes());
            }
            listItemsBinding.itemEntryDate.setText(item.getEntryDate());
            String buyPrice = stringTool.doubleInCurrency(item.getBuyPrice(), currency);
            listItemsBinding.itemBuyPrice.setText(buyPrice);

            setUpSoldData(item);
        }

        private void initialiseCurrency() {
            currency = "€";
        }

        private void setUpSoldData(Item item) {
            boolean sold = item.getSold();
            setUpSoldFieldsVisibilities(sold);
            if (sold) {
                fillInSoldTextFields(item);

                fillInProfitTextFields(item);
            }
        }

        private void setUpSoldFieldsVisibilities(boolean sold) {
            int visibility;
            if (sold) {
                visibility = View.VISIBLE;
            } else {
                visibility = View.GONE;
            }

            // Sell fields
            listItemsBinding.sellDate.setVisibility(visibility);
            listItemsBinding.itemSellDate.setVisibility(visibility);
            listItemsBinding.sellPrice.setVisibility(visibility);
            listItemsBinding.itemSellPrice.setVisibility(visibility);

            // Profit fields
            listItemsBinding.profit.setVisibility(visibility);
            listItemsBinding.itemProfit.setVisibility(visibility);
            listItemsBinding.itemProfitPercentage.setVisibility(visibility);
        }

        private void fillInSoldTextFields(Item item) {
            listItemsBinding.itemSellDate.setText(item.getSellDate());
            String sellPrice = stringTool.doubleInCurrency(item.getSellPrice(), currency);
            listItemsBinding.itemSellPrice.setText(sellPrice);
        }

        private void fillInProfitTextFields(Item item) {
            setUpProfitColors(item.getProfit());
            String profitString = stringTool.doubleInCurrency(item.getProfit(), currency);
            listItemsBinding.itemProfit.setText(profitString);
            String profitPercentageString = stringTool.doubleInPercentage(item.getProfitPercentage());
            listItemsBinding.itemProfitPercentage.setText(profitPercentageString);
        }

        private void setUpProfitColors(double profit) {
            int profitColor;
            if (profit > 0) {
                profitColor = Color.GREEN;
            } else {
                profitColor = Color.RED;
            }
            listItemsBinding.itemProfit.setTextColor(profitColor);
            listItemsBinding.itemProfitPercentage.setTextColor(profitColor);
        }
    }
}
