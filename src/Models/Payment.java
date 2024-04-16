package Models;

import java.sql.Timestamp;

public class Payment {

    private int paymentId;
    private int bookingId;
    private double amount;
    private String method;
    private int cardDetails;
    private String cardType;
    private java.sql.Timestamp timestamp;

    public Payment(int paymentId, int bookingId, double amount, String method, int cardDetails, String cardType,
            java.sql.Timestamp timestamp2) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.cardDetails = cardDetails;
        this.cardType = cardType;
        this.timestamp = timestamp2;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(int cardDetails) {
        this.cardDetails = cardDetails;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}