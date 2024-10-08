package service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.CarLeaseRepositoryImpl;
import entity.Car;
import entity.Customer;
import exception.DatabaseException;

public class CarLeaseRepositoryTest {

    private CarLeaseRepositoryImpl carLeaseRepository;

    @BeforeEach
    public void setUp() {
        // Initialize the repository before each test
        carLeaseRepository = new CarLeaseRepositoryImpl();
    }

    @Test
    public void testAddCustomer_Success() {
        try {
            Customer customer = new Customer(0, "An", "Singh", "an@example.com", "1234567890");
            carLeaseRepository.addCustomer(customer);
            assertTrue(customer.getCustomerID() > 0, "Customer ID should be greater than 0 after insertion");
        } catch (DatabaseException e) {
            fail("DatabaseException occurred: " + e.getMessage());
        }
    }

    @Test
    public void testAddCar_Success() {
        try {
            Car car = new Car(0, "Toyota", "Corolla", 2022, 2000.00, "available", 3, 2.33);
            carLeaseRepository.addCar(car);
            assertTrue(car.getVehicleID() > 0, "Car ID should be greater than 0 after insertion");
        } catch (DatabaseException e) {
            fail("DatabaseException occurred: " + e.getMessage());
        }
    }

}
