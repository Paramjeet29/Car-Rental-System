package service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.CarLeaseRepositoryImpl;
import entity.Customer;
import exception.DatabaseException;

public class CustomerServiceTest {

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
            Customer customer = new Customer(0, "Ansh", "Singh", "ansh@example.com", "1234567890");
            carLeaseRepository.addCustomer(customer);
            assertTrue(customer.getCustomerID() > 0, "Customer ID should be greater than 0 after insertion");
        } catch (DatabaseException e) {
            fail("DatabaseException occurred: " + e.getMessage());
        }
    }

    // Test case for handling duplicate email when adding a customer
    @Test
    public void testAddCustomer_Failure_DuplicateEmail() {
        try {
            Customer customer1 = new Customer(0, "Ansh", "Singh", "ansh@example.com", "1234567890");
            carLeaseRepository.addCustomer(customer1);

            Customer customer2 = new Customer(0, "Another", "Singh", "ansh@example.com", "0987654321");
            carLeaseRepository.addCustomer(customer2);
            fail("Expected DatabaseException due to duplicate email was not thrown");
        } catch (DatabaseException e) {
            assertTrue(e.getMessage().contains("Duplicate entry for email"));
        }
    }
}
