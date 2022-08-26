package ua.com.andromeda.shop.dto;

public record PaymentInfo(long amount, String currency, String receiptEmail) {
}
