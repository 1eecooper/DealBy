package by.deal.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import by.deal.app.R;
import by.deal.app.sql.Contract.*;
import by.deal.app.sql.SQLHelper;

public class DetailsActivity extends Activity {

    SQLHelper mSQLHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);

        Intent intent = getIntent();
        int id = intent.getIntExtra(OrderEntry.ID, 1);

        mSQLHelper = SQLHelper.getInstance(this.getApplicationContext());
        Cursor cursor = mSQLHelper.getOrderBy_Id(id);
        cursor.moveToFirst();

        int order_id = cursor.getInt(cursor.getColumnIndexOrThrow(OrderEntry.ID));
        TextView tv_order_id = (TextView) findViewById(R.id.order_id);
        tv_order_id.setText("Номер заказа "+String.valueOf(order_id));

        String order_name = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.NAME));
        TextView tv_order_name = (TextView) findViewById(R.id.order_name);
        tv_order_name.setText("Заказчик "+order_name);

        String order_date = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.DATE));
        TextView tv_order_date = (TextView) findViewById(R.id.order_date);
        tv_order_date.setText("Дата "+order_date);

        String order_phone = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.PHONE));
        TextView tv_order_phone = (TextView) findViewById(R.id.order_phone);
        tv_order_phone.setText("Телефон "+order_phone);

        String order_email = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.EMAIL));
        if (order_email == null || order_email.equals("")) {
            order_email = "не указан";
        }
        TextView tv_order_email = (TextView) findViewById(R.id.order_email);
        tv_order_email.setText("Email "+order_email);

        String order_address = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.ADDRESS));
        TextView tv_order_address = (TextView) findViewById(R.id.order_address);
        tv_order_address.setText("Адрес доставки "+order_address);

        String order_company = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.COMPANY));
        if (order_company == null || order_company.equals("")) {
            order_company = "не указана";
        }
        TextView tv_order_company = (TextView) findViewById(R.id.order_company);
        tv_order_company.setText("Компания "+order_company);

        String order_total_price = cursor.getString(cursor.getColumnIndexOrThrow(OrderEntry.PRICE_BYR));
        order_total_price = order_total_price.substring(0, order_total_price.length() - 3);
        TextView tv_order_total_price = (TextView) findViewById(R.id.order_total_price);
        tv_order_total_price.setText(order_total_price+" грн");

        cursor.close();

        LinearLayout list = (LinearLayout) findViewById(R.id.products_list);
        Cursor cur_prod = SQLHelper.getInstance(this.getApplicationContext()).getProducts(order_id);
        while (cur_prod.moveToNext()) {
            View view = getLayoutInflater().inflate(R.layout.product_item, null);
            list.addView(view);

            TextView product_name = (TextView) view.findViewById(R.id.product_name);
            product_name.setText(cur_prod.getString(cur_prod.getColumnIndexOrThrow(ProductEntry.NAME)));

            TextView product_price = (TextView) view.findViewById(R.id.product_price);
            String price = cur_prod.getString(cur_prod.getColumnIndexOrThrow(ProductEntry.PRICE));
            price = price.substring(0, price.length() - 3);
            String quantity = cur_prod.getString(cur_prod.getColumnIndexOrThrow(ProductEntry.QUANTITY));
            quantity = quantity.substring(0, quantity.length() - 3);
            product_price.setText(price+" грн | "+quantity+" шт");

            TextView product_total_price = (TextView) view.findViewById(R.id.product_total_price);
            Integer total_price = Integer.parseInt(price) * Integer.parseInt(quantity);
            product_total_price.setText(String.valueOf(total_price)+" грн");
        };
        cur_prod.close();
    }
}
