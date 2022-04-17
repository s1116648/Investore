package nl.hsleiden.investore.data.database;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

import nl.hsleiden.investore.R;
import nl.hsleiden.investore.data.model.Item;

public class InvestoreDB extends SQLiteOpenHelper {

//    private static final String DATABASE_NAME = "myDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Items";
    private static final String COLUMN_ITEM_ID = "itemID"; // Datatype: varchar(36)
    private static final String COLUMN_ITEM_NAME = "itemName"; // Datatype: varchar(255)
    private static final String COLUMN_ITEM_NOTES = "itemNotes"; // Datatype: text
    private static final String COLUMN_ITEM_ENTRY_DATE = "itemEntryDate"; // Datatype: text
    private static final String COLUMN_ITEM_SELL_DATE = "itemSellDate"; // Datatype: text
    private static final String COLUMN_ITEM_SOLD = "itemSold"; // Datatype: NUMERIC
    private static final String COLUMN_ITEM_BUY_PRICE = "itemBuyPrice"; // Datatype: REAL
    private static final String COLUMN_ITEM_SELL_PRICE = "itemSellPrice"; // Datatype: REAL

    private String dataBaseName;


    public InvestoreDB(Context context, String accountMail) {
        super(context, accountMail, null, DATABASE_VERSION);
        dataBaseName = accountMail;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Queries, initialisation
        sqLiteDatabase.execSQL("" +
                "CREATE TABLE " + TABLE_NAME + " \n(" +
                "    " + COLUMN_ITEM_ID + " varchar(36) PRIMARY KEY,\n" +
                "    " + COLUMN_ITEM_NAME + " varchar(255),\n" +
                "    " + COLUMN_ITEM_NOTES + " text,\n" +
                "    " + COLUMN_ITEM_ENTRY_DATE + " text,\n" +
                "    " + COLUMN_ITEM_SELL_DATE + " text,\n" +
                "    " + COLUMN_ITEM_SOLD + " boolean,\n" +
                "    " + COLUMN_ITEM_BUY_PRICE + " double,\n" +
                "    " + COLUMN_ITEM_SELL_PRICE + " double" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    public boolean addItem(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_ID, item.getID());
        contentValues.put(COLUMN_ITEM_NAME, item.getName());
        contentValues.put(COLUMN_ITEM_NOTES, item.getNotes());
        contentValues.put(COLUMN_ITEM_ENTRY_DATE, item.getEntryDate());
        contentValues.put(COLUMN_ITEM_SELL_DATE, item.getSellDate());
        contentValues.put(COLUMN_ITEM_SOLD, item.getSold());
        contentValues.put(COLUMN_ITEM_BUY_PRICE, item.getBuyPrice());
        contentValues.put(COLUMN_ITEM_SELL_PRICE, item.getSellPrice());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public void removeItem(Item item) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, COLUMN_ITEM_ID + "=?", new String[]{item.getID()});
    }

    public void clearItems() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ TABLE_NAME);
    }

    /**
     *
     * @param id
     * @return returns null if no item with id is found.
     */
    public Item getItemById(String id) {
        ArrayList<Item> items = getAllItems();
        for (Item item: items) {
            if (item.getID().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> itemsList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            itemsList.add(getItemFromCursor(cursor));
        }

        return itemsList;
    }

    private Item getItemFromCursor(Cursor cursor) {
        String id = cursor.getString(0);
        String name = cursor.getString(1);
        String notes = cursor.getString(2);
        String entryDate = cursor.getString(3);
        
        double buyPrice;
        String buyPriceString = cursor.getString(6);
        buyPrice = Double.parseDouble(buyPriceString);
        
        boolean sold;
        String soldString = cursor.getString(5);
        int soldInt = Integer.parseInt(soldString);
        if (soldInt > 0) {
            sold = true;
        } else {
            sold = false;
        }

        if (sold) {
            String itemSellDate = cursor.getString(4);
            double itemSellPrice;
            String itemSellPriceString = cursor.getString(7);
            itemSellPrice = Double.parseDouble(itemSellPriceString);
            
            return new Item(id, name, notes, entryDate, itemSellDate, true, buyPrice, itemSellPrice);
        } else {
            return new Item(id, name, notes, entryDate, buyPrice);
        }
    }

    public void updateItem(Item item) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME, item.getName());
        contentValues.put(COLUMN_ITEM_NOTES, item.getNotes());
        contentValues.put(COLUMN_ITEM_ENTRY_DATE, item.getEntryDate());
        contentValues.put(COLUMN_ITEM_SELL_DATE, item.getSellDate());
        contentValues.put(COLUMN_ITEM_SOLD, item.getSold());
        contentValues.put(COLUMN_ITEM_BUY_PRICE, item.getBuyPrice());
        contentValues.put(COLUMN_ITEM_SELL_PRICE, item.getSellPrice());
        String whereClause = COLUMN_ITEM_ID + "=?";
        String[] whereArgs = new String[] {item.getID()};

        sqLiteDatabase.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }
}
