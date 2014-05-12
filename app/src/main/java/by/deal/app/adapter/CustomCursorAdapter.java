package by.deal.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import by.deal.app.sql.Contract.*;

import by.deal.app.R;
import by.deal.app.sql.SQLHelper;
import by.deal.app.ui.OrdersActivity;

public class CustomCursorAdapter extends CursorAdapter {

    Context mContext;

    public CustomCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        return layoutInflater.inflate(R.layout.order_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView line_one = (TextView) view.findViewById(R.id.line_one);
        TextView line_two = (TextView) view.findViewById(R.id.line_two);
        TextView time_line = (TextView) view.findViewById(R.id.time_line);
        int order_id = cursor.getInt(cursor.getColumnIndexOrThrow(OrderEntry.ID));
        String order_state = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.STATE));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.PRICE_BYR));
        price = price.substring(0, price.length() - 3);
        if (line_one != null) {
            if (order_state.equals("new")) {
                line_one.setText("№"+order_id+" — "+
                cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.NAME)));
            } else {
                line_one.setText(getStateName(order_state)+" | "+"№"+order_id);
            }
        }
        String products = "";
        Cursor cur = ((OrdersActivity)mContext).getSQLHelper().getProducts(order_id);
        while (cur.moveToNext()) {
            products += cur.getString(cur.getColumnIndexOrThrow(ProductEntry.NAME))+" ";
        }
        if (line_two != null) {
            line_two.setText(price+" грн - "+products);
        }

        if (time_line != null) {
            time_line.setText(cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.DATE)));
        }
    }

    public String getStateName(String str) {
        if (str.equals("new")) {
            return "НОВЫЙ";
        } else if (str.equals("accepted")) {
            return "ПРИНЯТ";
        } else if (str.equals("declined")) {
            return "ОТМЕНЕН";
        } else if (str.equals("draft")) {
            return "ЧЕРНОВИК";
        } else if (str.equals("closed")) {
            return "ЗАКРЫТ";
        }
        return "";
    }
}
