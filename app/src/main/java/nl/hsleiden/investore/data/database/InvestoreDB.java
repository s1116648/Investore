package nl.hsleiden.investore.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nl.hsleiden.investore.data.model.Item;

public class InvestoreDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDB";
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


    public InvestoreDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    // ToDo needs testing
    public void removeItem(Item item) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, COLUMN_ITEM_ID + "=?", new String[]{item.getID()});
    }


    public void clearItems() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ TABLE_NAME);
    }
}
