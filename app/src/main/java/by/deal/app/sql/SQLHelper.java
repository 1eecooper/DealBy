package by.deal.app.sql;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    private static SQLHelper INSTANCE;

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
        return this.getReadableDatabase().rawQuery(buildSQL, null);
    }

    public Cursor getProducts(Integer order_id) {
        String buildSQL = "SELECT * FROM " + ProductEntry.TABLE_NAME + " WHERE "+ProductEntry.ORDER_REF+"="+order_id;
        return this.getReadableDatabase().rawQuery(buildSQL, null);
    }

    public Cursor getOrdersByQuery(String query) {
        Set<String> orders = new HashSet<String>();
        Cursor cursor = getOrdersFromOrders(query);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                orders.add(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(OrderEntry.ID))));
            }
        }
        cursor = getOrdersFromProducts(query);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                orders.add(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(OrderEntry.ID))));
            }
        }
        String[] array = orders.toArray(new String[orders.size()]);
        return getOrdersById(array);
    }

    public Cursor getOrdersFromOrders(String query) {
        String buildSQL = "SELECT * FROM " + OrderEntry.TABLE_NAME + " WHERE "+
                OrderEntry.ID+"='"+query+"' OR "+
                OrderEntry.NAME+" LIKE '%"+query+"%' OR "+
                OrderEntry.PHONE+"='"+query+"'";
        return this.getReadableDatabase().rawQuery(buildSQL, null);
    }

    public Cursor getOrdersFromProducts(String str) {
        ArrayList<String> orders = new ArrayList<String>();
        String selection = ProductEntry.SKU+" = ?"+" OR "+ProductEntry.NAME+" LIKE ?";
        String[] selectionArgs = {str, "%"+str+"%"};
        Cursor cursor = this.getReadableDatabase().query(ProductEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            orders.add(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.ORDER_REF))));
        }
        String[] array = orders.toArray(new String[orders.size()]);
        return getOrdersById(array);
    }

    public Cursor getOrderBy_Id(int id) {
        String selection = OrderEntry._ID+" = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return this.getReadableDatabase().query(OrderEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    public Cursor getOrdersById(String[] ids) {
        String selection = "";
        if (ids.length == 1) {
            selection = OrderEntry.ID+" = ?";
        } else {
            for (int i = 0; i < ids.length; i++) {
                if (i == ids.length - 1) {
                    selection += OrderEntry.ID+" = ?";
                } else {
                    selection += OrderEntry.ID+" = ? OR ";
                }
            }
        }
        if (selection.equals("")) {
            return null;
        } else {
            return this.getReadableDatabase().query(OrderEntry.TABLE_NAME, null, selection, ids, null, null, null);
        }
    }

    public static SQLHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SQLHelper(context);
        }
        return INSTANCE;
    }
}
