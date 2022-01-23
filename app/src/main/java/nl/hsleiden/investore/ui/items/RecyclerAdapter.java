package nl.hsleiden.investore.ui.items;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.hsleiden.investore.EditItemActivity;
import nl.hsleiden.investore.R;
import nl.hsleiden.investore.data.model.Item;
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

        public ViewHolder(@NonNull ListItemsBinding listItemsBinding) {
            super(listItemsBinding.getRoot());
            this.listItemsBinding = listItemsBinding;

            this.listItemsBinding.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToEditItemActivity(itemsList.get(getAdapterPosition()));
                }
            });
        }

        public void bindView(Item item) {
            listItemsBinding.itemName.setText(item.getName());
            if (item.getNotes().equals("")) {
                listItemsBinding.itemNotes.setVisibility(View.GONE);
            } else {
                listItemsBinding.itemNotes.setVisibility(View.VISIBLE);
                listItemsBinding.itemNotes.setText(item.getNotes());
            }
            listItemsBinding.itemEntryDate.setText(item.getEntryDate());
            listItemsBinding.itemBuyPrice.setText(Double.toString(item.getBuyPrice()));
            if (item.getSold()) {
                listItemsBinding.sellDate.setVisibility(View.VISIBLE);
                listItemsBinding.itemSellDate.setVisibility(View.VISIBLE);
                listItemsBinding.itemSellDate.setText(item.getSellDate());
                listItemsBinding.sellPrice.setVisibility(View.VISIBLE);
                listItemsBinding.itemSellPrice.setVisibility(View.VISIBLE);
                listItemsBinding.itemSellPrice.setText(Double.toString(item.getSellPrice()));
            } else {
                listItemsBinding.sellDate.setVisibility(View.GONE);
                listItemsBinding.itemSellDate.setVisibility(View.GONE);
                listItemsBinding.sellPrice.setVisibility(View.GONE);
                listItemsBinding.itemSellPrice.setVisibility(View.GONE);
            }
        }
    }
}
