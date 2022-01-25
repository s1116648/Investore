package nl.hsleiden.investore.data.model;

import java.text.DecimalFormat;
import java.util.UUID;

public class Item {

    private final String ID; // can be used as a key

    private String name;
    private String notes;
    private String entryDate;
    private String sellDate;
    private boolean sold;
    private double buyPrice;
    private double sellPrice;

    public Item(String name, String notes, String entryDate, double buyPrice) {
        this.ID = UUID.randomUUID().toString(); // generates an unique id
        this.name = name;
        this.notes = notes;
        this.entryDate = entryDate;
        this.sold = false;
        this.buyPrice = buyPrice;
    }

    public Item(String id, String name, String notes, String entryDate, double buyPrice) {
        this.ID = id;
        this.name = name;
        this.notes = notes;
        this.entryDate = entryDate;
        this.sold = false;
        this.buyPrice = buyPrice;
    }

    public Item(String name, String notes, String entryDate, String sellDate, boolean sold, double buyPrice, double sellPrice) {
        this.ID = UUID.randomUUID().toString(); // generates an unique id
        this.name = name;
        this.notes = notes;
        this.entryDate = entryDate;
        this.sellDate = sellDate;
        this.sold = sold;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Item(String ID, String name, String notes, String entryDate, String sellDate, boolean sold, double buyPrice, double sellPrice) {
        this.ID = ID;
        this.name = name;
        this.notes = notes;
        this.entryDate = entryDate;
        this.sellDate = sellDate;
        this.sold = sold;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public boolean getSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }


    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getProfit() {
        return sellPrice - buyPrice;
    }

    public double getProfitPercentage() {
        double part = getProfit();
        double whole = buyPrice;
        return (part / whole) * 100;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", sellDate='" + sellDate + '\'' +
                ", sold=" + sold +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                '}';
    }
}
