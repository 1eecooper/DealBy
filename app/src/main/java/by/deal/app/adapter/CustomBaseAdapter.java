package by.deal.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import by.deal.app.R;
import by.deal.app.xml.OrderItem;
import by.deal.app.xml.ProductItem;

public class CustomBaseAdapter extends BaseAdapter {

    Context mContext;
    List<OrderItem> mOrders;

    public CustomBaseAdapter(Context context, List<OrderItem> items) {
        super();
        mContext = context;
        mOrders = items;
    }

    @Override
    public int getCount() {
        return mOrders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = ((Activity)mContext).getLayoutInflater();
            view = layoutInflater.inflate(R.layout.order_item, null);
        }
        OrderItem orderItem = mOrders.get(i);
        String products = "";
        for (int j = 0; j < orderItem.getProducts().size(); j++) {
            if (j == orderItem.getProducts().size() - 1) {
                products += orderItem.getProducts().get(j).getName();
            } else {
                products += orderItem.getProducts().get(j).getName()+", ";
            }
        }
        String price = orderItem.getPriceBYR();
        price = price.substring(0, price.length() - 3);
        TextView line_one = (TextView) view.findViewById(R.id.line_one);
        TextView line_two = (TextView) view.findViewById(R.id.line_two);
        TextView time_line = (TextView) view.findViewById(R.id.time_line);
        if (line_one != null) {
            if (orderItem.getState().equals("new")) {
                line_one.setText("№"+orderItem.getId()+" — "+orderItem.getName());
            } else {
                line_one.setText(orderItem.getStateName()+" | "+"№"+orderItem.getId());
            }
        }
        if (line_two != null) {
            line_two.setText(price+" грн - "+products);
        }

        if (time_line != null) {
            time_line.setText(orderItem.getDate());
        }
        return view;
    }
}
