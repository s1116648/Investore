package nl.hsleiden.investore.data.model;

import java.util.ArrayList;

public class Items {
    private ArrayList<Item> items;

    public Items(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items1 = (Items) o;
        return items.equals(items1.items);
    }

    public Item getItem (int index) {
        return items.get(index);
    }

    public void setItem (Item newItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getID().equals(newItem.getID())) {
                this.items.set(i, newItem);
                return;
            }
        }
        class ItemNotFoundException
                extends RuntimeException {
            public ItemNotFoundException(String errorMessage) {
                super(errorMessage);
            }
        }
        throw new ItemNotFoundException("Item not found: " + newItem.toString());
    }
}
