package nl.hsleiden.investore.ui.items;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.hsleiden.investore.data.model.Item;
import nl.hsleiden.investore.databinding.ListItemsBinding;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Item> itemsList;

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

    class ViewHolder extends RecyclerView.ViewHolder {

        ListItemsBinding listItemsBinding;

        public ViewHolder(@NonNull ListItemsBinding listItemsBinding) {
            super(listItemsBinding.getRoot());
            this.listItemsBinding = listItemsBinding;

            this.listItemsBinding.itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: " + getAdapterPosition());
                }
            });
        }

        public void bindView(Item item) {
            listItemsBinding.itemName.setText(item.getName());
            listItemsBinding.itemNotes.setText(item.getNotes());
            listItemsBinding.itemEntryDate.setText(item.getEntryDate());
        }
    }
}
