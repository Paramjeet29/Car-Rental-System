//package entity;
//
//import java.util.Date;
//
//public class Lease {
//    private int leaseID;
//    private int carID;
//    private int customerID;
//    private Date startDate;
//    private Date endDate;
//    private String status;
//    private double dailyRate;
//    private double amountPaid; // Tracks total amount paid for the lease
//
//    // Constructor (fixing the undefined constructor issue)
//    public Lease(int leaseID, int carID, int customerID, Date startDate, Date endDate, String status) {
//        this.leaseID = leaseID;
//        this.carID = carID;
//        this.customerID = customerID;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.status = status;
////        this.amountPaid = 0; // Default payment is 0 when lease is created
//    }
//
//    // Getters and Setters
//    public int getLeaseID() {
//        return leaseID;
//    }
//
//    public void setLeaseID(int leaseID) {
//        this.leaseID = leaseID;
//    }
//
//    public int getCarID() {
//        return carID;
//    }
//
//    public void setCarID(int carID) {
//        this.carID = carID;
//    }
//
//    public int getCustomerID() {
//        return customerID;
//    }
//
//    public void setCustomerID(int customerID) {
//        this.customerID = customerID;
//    }
//
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
//
//    public double getDailyRate() {
//        return dailyRate;
//    }
//
//    public void setDailyRate(double dailyRate) {
//        this.dailyRate = dailyRate;
//    }
//
//    public double getAmountPaid() {
//        return amountPaid;
//    }
//
//    public void setAmountPaid(double amountPaid) {
//        this.amountPaid = amountPaid;
//    }
//
//    @Override
//    public String toString() {
//        return "Lease [leaseID=" + leaseID + ", carID=" + carID + ", customerID=" + customerID + ", startDate=" 
//                + startDate + ", endDate=" + endDate + ", dailyRate=" + dailyRate + ", amountPaid=" + amountPaid + "]";
//    }
//}
package entity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Lease {
    private int leaseID;
    private int carID;
    private int customerID;
    private Date startDate;
    private Date endDate;
    private String status;
    private double dailyRate;
    private double amountPaid; // Tracks total amount paid for the lease

    // Constructor
    public Lease(int leaseID, int carID, int customerID, Date startDate, Date endDate, String status) {
        this.leaseID = leaseID;
        this.carID = carID;
        this.customerID = customerID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.amountPaid = 0; // Default payment is 0 when lease is created
    }

    // Getters and Setters
    public int getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    // Helper method to calculate the duration of the lease in days
    public long getLeaseDurationInDays() {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    // Helper method to calculate the total amount to be paid
    public double calculateTotalAmount() {
        return getLeaseDurationInDays() * dailyRate;
    }

    @Override
    public String toString() {
        return "Lease [leaseID=" + leaseID + ", carID=" + carID + ", customerID=" + customerID + ", startDate=" 
                + startDate + ", endDate=" + endDate + ", status=" + status + ", dailyRate=" + dailyRate 
                + ", amountPaid=" + amountPaid + "]";
    }
}
