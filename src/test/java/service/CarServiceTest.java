package service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.CarLeaseRepositoryImpl;
import entity.Car;
import exception.DatabaseException;

public class CarServiceTest {

    private CarLeaseRepositoryImpl carLeaseRepository;

    @BeforeEach
    public void setUp() {
        // Initialize the repository before each test
        carLeaseRepository = new CarLeaseRepositoryImpl();
    }

    // Test case for successfully adding a car
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
