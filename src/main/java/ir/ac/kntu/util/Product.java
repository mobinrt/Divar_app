package ir.ac.kntu.util;

public class Product {
    private final String adsCategory;
    private final String name;
    private final Seller seller;
    private final int price;
    private boolean isVisible;

    /**
     * @param adsCategory -product category
     * @param name        -product name
     * @param seller      -product seller
     * @param price       -product price
     */
    public Product(String adsCategory, String name, Seller seller, int price) {
        this.adsCategory = adsCategory;
        this.name = name;
        this.seller = seller;
        this.price = price;
        isVisible = false;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
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

    @Override
    public String toString() {
        return "Ads) " + "{ " +
                "Ads category: " + adsCategory + ", " +
                "Name: " + name + ", " +
                "Seller: " + seller.getUserName() + ", " +
                "Price: " + price + " }";
    }
}
