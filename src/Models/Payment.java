package Models;

import java.sql.Timestamp;

public class Payment {

    private int userID;
    private int paymentId;
    private int carID;
    private double amount;
    private String method;
    private String cardDetails;
    private String cardType;
    private Timestamp timestamp;

    public Payment(int paymentId, int userID, int carID, double amount, String method, String cardDetails,
            String cardType,
            java.sql.Timestamp timestamp) {
        this.paymentId = paymentId;
        this.userID = userID;
        this.carID = carID;
        this.amount = amount;
        this.method = method;
        this.cardDetails = cardDetails;
        this.cardType = cardType;
        this.timestamp = timestamp;
    }

    public Payment(int userID, int carID, double amount, String method, String cardDetails, String cardType,
            java.sql.Timestamp timestamp) {
        this.userID = userID;
        this.carID = carID;
        this.amount = amount;
        this.method = method;
        this.cardDetails = cardDetails;
        this.cardType = cardType;
        this.timestamp = timestamp;
    }

    public Payment(int userID, int carID, double amount, String method,
            java.sql.Timestamp timestamp) {
        this.userID = userID;
        this.carID = carID;
        this.amount = amount;
        this.method = method;
    
        this.timestamp = timestamp;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getcarID() {
        return carID;
    }

    public void setcarID(int carID) {
        this.carID = carID;
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

    public String getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(String cardDetails) {
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

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", userID=" + userID +
                ", carID=" + carID +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                ", cardDetails=" + cardDetails +
                ", cardType='" + cardType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}