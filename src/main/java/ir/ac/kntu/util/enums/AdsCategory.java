package ir.ac.kntu.util.enums;

public enum AdsCategory {

    PHONE(2000), HOME_STUFF(4000), STATIONARY(2000),
    CLOTHE(2000), CAR(0);
    private final int baseCharge;

    AdsCategory(int baseCharge) {
        this.baseCharge = baseCharge;
    }

    public int getBaseCharge() {
        return baseCharge;
    }
}
