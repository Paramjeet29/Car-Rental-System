package entity;

public class Payment {
    private int paymentID;
    private int leaseID;
    private double amount;
    private String paymentDate;

    // Constructor
    public Payment(int paymentID, int leaseID, double amount, String paymentDate) {
        this.paymentID = paymentID;
        this.leaseID = leaseID;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment [paymentID=" + paymentID + ", leaseID=" + leaseID + ", amount=" + amount + ", paymentDate=" + paymentDate + "]";
    }
}
