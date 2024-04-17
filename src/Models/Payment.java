package Models;

import java.sql.Timestamp;

public class Payment {

    private int userID;
    private int paymentId;
    private int bookingId;
    private double amount;
    private String method;
    private int cardDetails;
    private String cardType;
    private Timestamp timestamp;

    public Payment(int paymentId, int userID, int bookingId, double amount, String method, int cardDetails, String cardType,
            java.sql.Timestamp timestamp) {
        this.paymentId = paymentId;
        this.userID = userID;
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.cardDetails = cardDetails;
        this.cardType = cardType;
        this.timestamp = timestamp;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


}