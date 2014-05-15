package by.deal.app.sql;

import android.provider.BaseColumns;

public class Contract {
    public Contract() {}

    public static abstract class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders_main";
        public static final String ID = "id";
        public static final String STATE = "state";
        public static final String DATE = "date";
        public static final String NAME = "name";
        public static final String COMPANY = "company";
        public static final String PHONE = "phone";
        public static final String EMAIL = "email";
        public static final String ADDRESS = "address";
        public static final String INDEX = "address_index";
        public static final String PAYMENT_TYPE = "paymentType";
        public static final String DELIVERY_TYPE = "deliveryType";
        public static final String DELIVERY_COST = "deliveryCost";
        public static final String PAYER_COMMENT = "payerComment";
        public static final String SALES_COMMENT = "salesComment";
        public static final String PRICE_BYR = "priceBYR";
    }

    public static abstract class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String QUANTITY = "quantity";
        public static final String CURRENCY = "currency";
        public static final String IMAGE = "image";
        public static final String URL = "url";
        public static final String PRICE = "price";
        public static final String SKU = "sku";
        public static final String ORDER_REF = "order_reference";
    }
}
