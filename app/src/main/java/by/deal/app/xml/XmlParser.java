package by.deal.app.xml;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;

import by.deal.app.sql.SQLHelper;
import by.deal.app.sql.Contract.*;

public class XmlParser {
    static final String ORDER_ITEM = "order";
    static final String ORDER_ID = "id";
    static final String ORDER_STATE = "state";
    static final String ORDER_DATE = "date";
    static final String ORDER_NAME = "name";
    static final String ORDER_COMPANY = "company";
    static final String ORDER_PHONE = "phone";
    static final String ORDER_EMAIL = "email";
    static final String ORDER_ADDRESS = "address";
    static final String ORDER_INDEX = "index";
    static final String ORDER_PAYMENTTYPE = "paymentType";
    static final String ORDER_DELIVERYTYPE = "deliveryType";
    static final String ORDER_DELIVERYCOST = "deliveryCost";
    static final String ORDER_PAYERCOMMENT = "payercomment";
    static final String ORDER_SALESCOMMENT = "salescomment";
    static final String ORDER_PRICE = "priceBYR";
    static final String ORDER_ITEMS = "items";

    static final String PRODUCT_ITEM = "item";
    static final String PRODUCT_ID = "id";
    static final String PRODUCT_NAME = "name";
    static final String PRODUCT_QUANTITY = "quantity";
    static final String PRODUCT_CURRENCY = "currency";
    static final String PRODUCT_IMAGE = "image";
    static final String PRODUCT_URL = "url";
    static final String PRODUCT_PRICE = "price";
    static final String PRODUCT_SKU = "sku";

    public SQLHelper readXml(InputStream inputStream, SQLHelper sql) {
        SQLHelper sqlHelper = sql;
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        sqlHelper.clearTables(db);
        ContentValues orderValues = new ContentValues();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            long newRowId;
            Integer currentOrderId = -1;

            outer:
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    if (parser.getName().equals(ORDER_ITEM)) {
                        currentOrderId = Integer.parseInt(parser.getAttributeValue(null, ORDER_ID));
                        orderValues.put(OrderEntry.ID, currentOrderId);
                        orderValues.put(OrderEntry.STATE, parser.getAttributeValue(null, ORDER_STATE));
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_NAME) && orderValues.get(OrderEntry.NAME) == null) {
                        orderValues.put(OrderEntry.NAME, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_COMPANY)) {
                        orderValues.put(OrderEntry.COMPANY, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_PHONE)) {
                        orderValues.put(OrderEntry.PHONE, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_EMAIL)) {
                        orderValues.put(OrderEntry.EMAIL, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_DATE)) {
                        orderValues.put(OrderEntry.DATE, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_ADDRESS)) {
                        orderValues.put(OrderEntry.ADDRESS, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_INDEX)) {
                        orderValues.put(OrderEntry.INDEX, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_PAYMENTTYPE)) {
                        orderValues.put(OrderEntry.PAYMENT_TYPE, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_DELIVERYTYPE)) {
                        orderValues.put(OrderEntry.DELIVERY_TYPE, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_DELIVERYCOST)) {
                        orderValues.put(OrderEntry.DELIVERY_COST, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_PAYERCOMMENT)) {
                        orderValues.put(OrderEntry.PAYER_COMMENT, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_SALESCOMMENT)) {
                        orderValues.put(OrderEntry.SALES_COMMENT, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_PRICE)) {
                        orderValues.put(OrderEntry.PRICE_BYR, parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_ITEMS)) {
                        if (parser.next() != XmlPullParser.END_TAG) {
                            ContentValues productValues = null;

                            inner:
                            while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals(ORDER_ITEMS))) {
                                if (parser.getEventType() == XmlPullParser.START_TAG) {
                                    if (parser.getName().equals(PRODUCT_ITEM)) {
                                        productValues =  new ContentValues();
                                        productValues.put(ProductEntry.ID, parser.getAttributeValue(null, PRODUCT_ID));
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_NAME)) {
                                        productValues.put(ProductEntry.NAME, parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_QUANTITY)) {
                                        productValues.put(ProductEntry.QUANTITY, parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_CURRENCY)) {
                                        productValues.put(ProductEntry.CURRENCY, parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_IMAGE)) {
                                        productValues.put(ProductEntry.IMAGE, parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_URL)) {
                                        productValues.put(ProductEntry.URL, parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_PRICE)) {
                                        productValues.put(ProductEntry.PRICE, parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_SKU)) {
                                        productValues.put(ProductEntry.SKU, parser.nextText());
                                        continue inner;
                                    }
                                }
                                if (parser.getEventType() == XmlPullParser.END_TAG) {
                                    if (parser.getName().equals(PRODUCT_ITEM)) {
                                        productValues.put(ProductEntry.ORDER_REF, currentOrderId);
                                        db.insert(ProductEntry.TABLE_NAME, null, productValues);
                                    }
                                }
                            }
                        }
                    }
                }
                if (parser.getEventType() == XmlPullParser.END_TAG) {
                    if (parser.getName().equals(ORDER_ITEM)) {
                        db.insert(OrderEntry.TABLE_NAME, null, orderValues);
                        orderValues.clear();
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return sqlHelper;
    }
}
