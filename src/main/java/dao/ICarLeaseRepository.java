package dao;

import entity.Car;
import entity.Customer;
import entity.Lease;
import exception.LeaseNotFoundException;
import exception.CarNotFoundException;
import exception.CustomerNotFoundException;
import exception.DatabaseException;

import java.util.Date;
import java.util.List;

public interface ICarLeaseRepository {
    void addCar(Car car) throws DatabaseException;                    // Add a car to the inventory
    void removeCar(int carID) throws DatabaseException, CarNotFoundException;               // Remove a car from the inventory
    List<Car> listAvailableCars() throws DatabaseException;           // List all available cars
    List<Car> listRentedCars() throws DatabaseException;              // List all rented cars
    void addCustomer(Customer customer) throws DatabaseException;     // Add a new customer
    void removeCustomer(int customerID) throws DatabaseException, CustomerNotFoundException;     // Remove an existing customer
    Lease createLease(int customerID, int vehicleID, Date startDate, Date endDate) throws DatabaseException, CarNotFoundException, CustomerNotFoundException, LeaseNotFoundException;  // Create a lease for a car
    void returnCar(int leaseID) throws LeaseNotFoundException, DatabaseException;                   // Return a rented car
    void recordPayment(Lease lease, double amount) throws DatabaseException;  // Record payment for a lease
    Lease findLeaseById(int leaseID) throws LeaseNotFoundException, DatabaseException; // Find lease by ID
}
