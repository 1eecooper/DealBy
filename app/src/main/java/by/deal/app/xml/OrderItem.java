package by.deal.app.xml;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    private String id;
    private String state;
    private String date;
    private String name;
    private String company;
    private String phone;
    private String email;
    private String address;
    private String index;
    private String paymentType;
    private String deliveryType;
    private String deliveryCost;
    private String payerComment;
    private String salesComment;
    private String priceBYR;

    private ArrayList<ProductItem> productList;

    public OrderItem() {
        productList = new ArrayList<ProductItem>();
    }

    public void addProduct(ProductItem item) {
        productList.add(item);
    }

    public List<ProductItem> getProducts() {
        return productList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public String getStateName() {
        if (state.equals("new")) {
            return "НОВЫЙ";
        } else if (state.equals("accepted")) {
            return "ПРИНЯТ";
        } else if (state.equals("declined")) {
            return "ОТМЕНЕН";
        } else if (state.equals("draft")) {
            return "ЧЕРНОВИК";
        } else if (state.equals("closed")) {
            return "ЗАКРЫТ";
        }
        return "";
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getPayerComment() {
        return payerComment;
    }

    public void setPayerComment(String payerComment) {
        this.payerComment = payerComment;
    }

    public String getSalesComment() {
        return salesComment;
    }

    public void setSalesComment(String salesComment) {
        this.salesComment = salesComment;
    }

    public String getPriceBYR() {
        return priceBYR;
    }

    public void setPriceBYR(String priceBYR) {
        this.priceBYR = priceBYR;
    }

    @Override
    public String toString() {
        String str = "id=" + id + "\n" +
                "state=" + state + "\n" +
                "date=" + date + "\n" +
                "name=" + name + "\n" +
                "company=" + company + "\n" +
                "phone=" + phone + "\n" +
                "email=" + email + "\n" +
                "address=" + address + "\n" +
                "index=" + index + "\n" +
                "paymentType=" + paymentType + "\n" +
                "deliveryType=" + deliveryType + "\n" +
                "deliveryCost=" + deliveryCost + "\n" +
                "payerComment=" + payerComment + "\n" +
                "salesComment=" + salesComment + "\n" +
                "priceBYR=" + priceBYR + "\n\n";
        for (ProductItem item: productList) {
            str += item.toString();
        }
        return str;
    }
}
