package ir.ac.kntu.util;

public class Product {
    private final String adsCategory;
    private final String name;
    private final Seller seller;
    private final double price;
    private Customer customer;
    private boolean isVisible;
    private boolean readyToSend;
    private boolean sold;
    private boolean isSending;
    private boolean waitingToSend;

    /**
     * @param adsCategory -product category
     * @param name        -product name
     * @param seller      -product seller
     * @param price       -product price
     */
    public Product(String adsCategory, String name, Seller seller, double price) {
        this.adsCategory = adsCategory;
        this.name = name;
        this.seller = seller;
        this.price = price;
        customer = null;
        isVisible = false;
        readyToSend = false;
        sold = false;
        waitingToSend = false;
    }

    public String productStatus(Product product) {
        if (product.isReadyToSend())
            return "Ready to send";
        if (product.isSending())
            return "Sending product";
        if (product.isSold())
            return "sold";
        if (product.isWaitingToSend())
            return "Waiting to send";
        return "";
    }

    @Override
    public String toString() {
        return "Ads) " + "{ " +
                "Ads category: " + adsCategory + ", " +
                "Name: " + name + ", " +
                "Seller: " + seller.getUserName() + ", " +
                "Price: " + price + " }";
    }

    public double getPrice() {
        return price;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public String getAdsCategory() {
        return adsCategory;
    }

    public boolean isReadyToSend() {
        return readyToSend;
    }

    public void setReadyToSend(boolean readyToSend) {
        this.readyToSend = readyToSend;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public boolean isWaitingToSend() {
        return waitingToSend;
    }

    public void setWaitingToSend(boolean waitingToSend) {
        this.waitingToSend = waitingToSend;
    }

    public boolean isSending() {
        return isSending;
    }

    public void setSending(boolean sending) {
        isSending = sending;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
