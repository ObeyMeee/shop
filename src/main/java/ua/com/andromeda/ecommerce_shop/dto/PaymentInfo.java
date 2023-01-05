package ua.com.andromeda.ecommerce_shop.dto;

public record PaymentInfo(long amount, String currency, String receiptEmail) {
}