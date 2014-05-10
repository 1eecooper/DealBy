package by.deal.app.xml;

public class ProductItem {
    private String id;
    private String name;
    private String quantity;
    private String currency;
    private String image;
    private String url;
    private String price;
    private String sku;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "    id=" + id + "\n" +
                "    name=" + name + "\n" +
                "    quantity=" + quantity + "\n" +
                "    currency=" + currency + "\n" +
                "    image=" + image + "\n" +
                "    url=" + url + "\n" +
                "    price=" + price + "\n" +
                "    sku=" + sku + "\n\n";
    }
}
