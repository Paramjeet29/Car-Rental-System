
package service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.CarLeaseRepositoryImpl;
import entity.Car;
import entity.Customer;
import exception.DatabaseException;

public class carRentalTesting {

    private CarLeaseRepositoryImpl carLeaseRepository;

    @BeforeEach
    public void setUp() {
        // Initialize the repository before each test
        carLeaseRepository = new CarLeaseRepositoryImpl();
    }

    // Test case for successfully adding a customer
    @Test
    public void testAddCustomer_Success() {
        try {
            Customer customer = new Customer(0, "ansh", "Singh", "ansh@example.com", "1234567890");
            carLeaseRepository.addCustomer(customer);
            assertTrue(customer.getCustomerID() > 0, "Customer ID should be greater than 0 after insertion");
        } catch (DatabaseException e) {
            fail("DatabaseException occurred: " + e.getMessage());
        }
    }

    // Test case for successfully adding a car
    @Test
    public void testAddCar_Success() {
        try {
            Car car = new Car(0, "Toyota", "Corolla", 2022, 2000.00, "available",3,2.33);
            carLeaseRepository.addCar(car);
            assertTrue(car.getVehicleID() > 0, "Car ID should be greater than 0 after insertion");
        } catch (DatabaseException e) {
            fail("DatabaseException occurred: " + e.getMessage());
        }
    }
    @Test
    public void testAddCustomer_Failure_DuplicateEmail() {
        try {
        	 Customer customer1 = new Customer(0, "ansh", "Singh", "ansh@example.com", "1234567890");
            carLeaseRepository.addCustomer(customer1);

            fail("Expected DatabaseException due to duplicate email was not thrown");
        } catch (DatabaseException e) {
            assertTrue(e.getMessage().contains("Duplicate entry for email"));
        }
    }


}
