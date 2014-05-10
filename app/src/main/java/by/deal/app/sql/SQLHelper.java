package by.deal.app.sql;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import by.deal.app.sql.Contract.*;

public class SQLHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OrdersReader.db";

    public static final String SQL_CREATE_ORDERS_TABLE =
            "CREATE TABLE " + OrderEntry.TABLE_NAME + "( " +
            OrderEntry._ID + " INTEGER PRIMARY KEY," +
            OrderEntry.ID + " INTEGER," +
            OrderEntry.STATE + " TEXT," +
            OrderEntry.DATE + " TEXT," +
            OrderEntry.NAME + " TEXT," +
            OrderEntry.COMPANY + " TEXT," +
            OrderEntry.PHONE + " TEXT," +
            OrderEntry.EMAIL + " TEXT," +
            OrderEntry.ADDRESS + " TEXT," +
            OrderEntry.INDEX + " TEXT," +
            OrderEntry.PAYMENT_TYPE + " TEXT," +
            OrderEntry.DELIVERY_TYPE + " TEXT," +
            OrderEntry.DELIVERY_COST + " TEXT," +
            OrderEntry.PAYER_COMMENT + " TEXT," +
            OrderEntry.SALES_COMMENT + " TEXT," +
            OrderEntry.PRICE_BYR + " TEXT" + "); ";

    public static final String SQL_CREATE_PRODUCTS_TABLE =
            "CREATE TABLE " + ProductEntry.TABLE_NAME + "( " +
            ProductEntry._ID + " INTEGER PRIMARY KEY," +
            ProductEntry.ID + " TEXT," +
            ProductEntry.NAME + " TEXT," +
            ProductEntry.QUANTITY + " TEXT," +
            ProductEntry.CURRENCY + " TEXT," +
            ProductEntry.IMAGE + " TEXT," +
            ProductEntry.URL + " TEXT," +
            ProductEntry.PRICE + " TEXT," +
            ProductEntry.SKU + " TEXT," +
            ProductEntry.ORDER_REF + " INTEGER," +
            "FOREIGN KEY" + "(" + ProductEntry.ORDER_REF + ") REFERENCES " + OrderEntry.TABLE_NAME+"("+OrderEntry.ID+")"+"); ";

    private static final String SQL_DELETE_ORDERS_TABLE = "DROP TABLE IF EXISTS " + OrderEntry.TABLE_NAME;
    private static final String SQL_DELETE_PRODUCTS_TABLE = "DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME;

    public SQLHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ORDERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(SQL_DELETE_ORDERS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_PRODUCTS_TABLE);
        onCreate(sqLiteDatabase);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(false);
    }

    public void clearTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_DELETE_ORDERS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_PRODUCTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ORDERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    public Cursor getAllOrders() {
        String buildSQL = "SELECT * FROM " + OrderEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(buildSQL, null);
    }

    public Cursor getProducts(Integer order_id) {
        String buildSQL = "SELECT * FROM " + ProductEntry.TABLE_NAME + " WHERE "+ProductEntry.ORDER_REF+"="+order_id;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(buildSQL, null);
    }
}
