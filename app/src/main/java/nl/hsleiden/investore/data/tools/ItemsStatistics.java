package nl.hsleiden.investore.data.tools;

import java.util.ArrayList;

import nl.hsleiden.investore.data.model.Item;

public class ItemsStatistics {
    ArrayList<Item> items;

    public ItemsStatistics(ArrayList<Item> items) {
        this.items = items;
    }

    public double getTotalProfit() {
        double totalProfit = 0;
        for (Item item: items) {
            totalProfit += item.getProfit();
        }
        return totalProfit;
    }

    public double getAverageProfitPercentage() {
        double totalProfitPercentage = 0;
        for (Item item: items) {
            totalProfitPercentage += item.getProfitPercentage();
        }
        return totalProfitPercentage / items.size();
    }

    public double getTotalSoldProfit() {
        double soldProfit = 0;
        for (Item item: items) {
            if (item.getSold()) {
                soldProfit += item.getProfit();
            }
        }
        return soldProfit;
    }

    public double getAverageSoldProfitPercentage() {
        double totalSoldProfitPercentage = 0;
        int numberOfSoldItems = 0;
        for (Item item: items) {
            if (item.getSold()) {
                totalSoldProfitPercentage += item.getProfitPercentage();
                numberOfSoldItems++;
            }
        }
        return totalSoldProfitPercentage / numberOfSoldItems;
    }

    public int getItemsSize() {
        return items.size();
    }

    public int getNumberOfSoldItems() {
        int numberOfSoldItems = 0;
        for (Item item : items) {
            if (item.getSold()) {
                numberOfSoldItems++;
            }
        }
        return numberOfSoldItems;
    }
}
