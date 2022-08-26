package ua.com.andromeda.ecommerce_shop.dto;

public final class PaymentInfo {
    private final long amount;
    private final String currency;
    private final String receiptEmail;

    public PaymentInfo(long amount, String currency, String receiptEmail) {
        this.amount = amount;
        this.currency = currency;
        this.receiptEmail = receiptEmail;
    }

    public long getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getReceiptEmail() {
        return receiptEmail;
    }
}
