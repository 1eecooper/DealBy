package by.deal.app.xml;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public List<OrderItem> readXml(InputStream inputStream) {
        List<OrderItem> items = new ArrayList<OrderItem>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            OrderItem orderItem = null;

            outer:
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    if (parser.getName().equals(ORDER_ITEM)) {
                        orderItem = new OrderItem();
                        orderItem.setId(parser.getAttributeValue(null,ORDER_ID));
                        orderItem.setState(parser.getAttributeValue(null,ORDER_STATE));
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_NAME) && orderItem.getName() == null) {
                        orderItem.setName(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_COMPANY)) {
                        orderItem.setCompany(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_PHONE)) {
                        orderItem.setPhone(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_EMAIL)) {
                        orderItem.setEmail(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_DATE)) {
                        orderItem.setDate(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_ADDRESS)) {
                        orderItem.setAddress(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_INDEX)) {
                        orderItem.setIndex(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_PAYMENTTYPE)) {
                        orderItem.setPaymentType(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_DELIVERYTYPE)) {
                        orderItem.setDeliveryType(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_DELIVERYCOST)) {
                        orderItem.setDeliveryCost(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_PAYERCOMMENT)) {
                        orderItem.setPayerComment(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_SALESCOMMENT)) {
                        orderItem.setSalesComment(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_PRICE)) {
                        orderItem.setPriceBYR(parser.nextText());
                        continue outer;
                    }
                    if (parser.getName().equals(ORDER_ITEMS)) {
                        ProductItem productItem = null;
                        if (parser.next() != XmlPullParser.END_TAG) {

                            inner:
                            while (!(parser.next() == XmlPullParser.END_TAG && parser.getName().equals(ORDER_ITEMS))) {
                                if (parser.getEventType() == XmlPullParser.START_TAG) {
                                    if (parser.getName().equals(PRODUCT_ITEM)) {
                                        productItem = new ProductItem();
                                        productItem.setId(parser.getAttributeValue(null,PRODUCT_ID));
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_NAME)) {
                                        productItem.setName(parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_QUANTITY)) {
                                        productItem.setQuantity(parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_CURRENCY)) {
                                        productItem.setCurrency(parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_IMAGE)) {
                                        productItem.setImage(parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_URL)) {
                                        productItem.setUrl(parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_PRICE)) {
                                        productItem.setPrice(parser.nextText());
                                        continue inner;
                                    }
                                    if (parser.getName().equals(PRODUCT_SKU)) {
                                        productItem.setSku(parser.nextText());
                                        continue inner;
                                    }
                                }
                                if (parser.getEventType() == XmlPullParser.END_TAG) {
                                    if (parser.getName().equals(PRODUCT_ITEM)) {
                                        orderItem.addProduct(productItem);
                                    }
                                }
                                //parser.next();
                            }
                        }
                    }
                }
                if (parser.getEventType() == XmlPullParser.END_TAG) {
                    if (parser.getName().equals(ORDER_ITEM)) {
                        items.add(orderItem);
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<OrderItem> search(String str, List<OrderItem> items) {
        List<OrderItem> filterList = new ArrayList<OrderItem>();

        label:
        for (OrderItem item: items) {
            if (str.equals(item.getId()) || str.equals(item.getName()) || str.equals(item.getPhone())) {
                filterList.add(item);
                continue label;
            }
            for (ProductItem prod: item.getProducts()) {
                if (str.equals(prod.getSku()) || str.equals(prod.getName())) {
                    filterList.add(item);
                    continue label;
                }
            }
        }
        return filterList;
    }
}
