
package dao;

import entity.Car;
import entity.Customer;
import entity.Lease;
import entity.Payment;
import exception.CarNotFoundException;
import exception.CustomerNotFoundException;
import exception.DatabaseException;
import exception.LeaseNotFoundException;
import util.DBConnUtil;
import util.DBPropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarLeaseRepositoryImpl implements ICarLeaseRepository {

    private static final String PROP_FILE_NAME = "src/main/java/util/db.properties";
    private String connString;

    public CarLeaseRepositoryImpl() {
        this.connString = DBPropertyUtil.getConnString(PROP_FILE_NAME);
    }

    private Connection getConnection() throws DatabaseException {
        return DBConnUtil.getConnection(connString);
    }

    @Override
    public void addCar(Car car) throws DatabaseException {
        String sql = "INSERT INTO car (make, model, year, dailyRate, status, passengerCapacity, engineCapacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, car.getMake());
            pstmt.setString(2, car.getModel());
            pstmt.setInt(3, car.getYear());
            pstmt.setDouble(4, car.getDailyRate());
            pstmt.setString(5, car.getStatus());
            pstmt.setInt(6, car.getPassengerCapacity());
            pstmt.setDouble(7, car.getEngineCapacity());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setVehicleID(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while adding car", e);
        }
    }

    @Override
    public void removeCar(int carID) throws DatabaseException, CarNotFoundException {
        String sql = "DELETE FROM car WHERE vehicleID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, carID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new CarNotFoundException("Car with ID " + carID + " not found.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while removing car", e);
        }
    }

    @Override
    public List<Car> listAvailableCars() throws DatabaseException {
        List<Car> availableCars = new ArrayList<>();
        String sql = "SELECT * FROM car WHERE status = 'available'";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                availableCars.add(extractCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while listing available cars", e);
        }
        return availableCars;
    }

    @Override
    public List<Car> listRentedCars() throws DatabaseException {
        List<Car> rentedCars = new ArrayList<>();
        String sql = "SELECT * FROM car WHERE status = 'rented'";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rentedCars.add(extractCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while listing rented cars", e);
        }
        return rentedCars;
    }

    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        return new Car(
            rs.getInt("vehicleid"),
            rs.getString("make"),
            rs.getString("model"),
            rs.getInt("year"),
            rs.getDouble("dailyRate"),
            rs.getString("status"),
            rs.getInt("passengercapacity"),
            rs.getDouble("enginecapacity")
        );
    }

    @Override
    public void addCustomer(Customer customer) throws DatabaseException {
        String sql = "INSERT INTO customer (firstName, lastName, email, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhone());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setCustomerID(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while adding customer", e);
        }
    }

    @Override
    public void removeCustomer(int customerID) throws DatabaseException, CustomerNotFoundException {
        String sql = "DELETE FROM customer WHERE customerID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while removing customer", e);
        }
    }


    @Override
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate) 
            throws DatabaseException, CarNotFoundException, CustomerNotFoundException, LeaseNotFoundException {

        String checkCarSql = "SELECT 1 FROM car WHERE vehicleID = ? AND status = 'available'";
        String insertLeaseSql = "INSERT INTO lease (vehicleID, customerID, startDate, endDate, status) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            // Get connection and start transaction
            conn = getConnection();
            conn.setAutoCommit(false); // Disable auto-commit for transaction management

            // Check if the car is available
            try (PreparedStatement checkStmt = conn.prepareStatement(checkCarSql)) {
                checkStmt.setInt(1, carID);
                ResultSet resultSet = checkStmt.executeQuery();
                if (!resultSet.next()) {
                    throw new CarNotFoundException("Car with ID " + carID + " not found or not available.");
                }
            }

            // Insert the lease record
            try (PreparedStatement insertStmt = conn.prepareStatement(insertLeaseSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, carID);
                insertStmt.setInt(2, customerID);
                insertStmt.setDate(3, new java.sql.Date(startDate.getTime()));
                insertStmt.setDate(4, new java.sql.Date(endDate.getTime()));
                insertStmt.setString(5, "active"); // Set status to active

                int affectedRows = insertStmt.executeUpdate();

                if (affectedRows == 0) {
                    conn.rollback(); // Roll back transaction if insertion fails
                    throw new DatabaseException("Creating lease failed, no rows affected.", null);
                }

                try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int leaseId = generatedKeys.getInt(1);

                        // Update the car status to "rented" after successful lease insertion
                        updateCarStatus( carID, "rented");

                        // Commit the transaction
                        conn.commit();

                        return findLeaseById(leaseId); // Return the lease object
                    } else {
                        conn.rollback(); // Roll back transaction if no lease ID is generated
                        throw new DatabaseException("Creating lease failed, no ID obtained.", null);
                    }
                }
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Roll back transaction in case of exception
                } catch (SQLException ex) {
                    throw new DatabaseException("Error rolling back transaction", ex);
                }
            }
            throw new DatabaseException("Error while creating lease", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Re-enable auto-commit
                    conn.close();
                } catch (SQLException e) {
                    throw new DatabaseException("Error closing connection", e);
                }
            }
        }
    }

    private void updateCarStatus(int carID, String status) throws DatabaseException {
        String sql = "UPDATE car SET status = ? WHERE vehicleID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, carID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error while updating car status", e);
        }
    }

    @Override
    public void returnCar(int leaseID) throws DatabaseException, LeaseNotFoundException {
        String sql = "SELECT vehicleid FROM lease WHERE leaseID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, leaseID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int carID = rs.getInt("vehicleID");
                    updateCarStatus(carID, "available");
                    deleteLease(leaseID);
                } else {
                    throw new LeaseNotFoundException("Lease with ID " + leaseID + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while returning car", e);
        }
    }

    private void deleteLease(int leaseID) throws DatabaseException {
        String sql = "DELETE FROM lease WHERE leaseID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, leaseID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error while deleting lease", e);
        }
    }

    @Override
    public void recordPayment(Lease lease, double amount) throws DatabaseException {
        String sql = "INSERT INTO payment (leaseID, amount, paymentDate) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, lease.getLeaseID());
            pstmt.setDouble(2, amount);
            pstmt.setDate(3, new java.sql.Date(new Date().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error while recording payment", e);
        }
    }


    @Override
    public Lease findLeaseById(int leaseId) throws LeaseNotFoundException, DatabaseException {
        String query = "SELECT * FROM lease WHERE leaseID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, leaseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create and return Lease object with data from the result set
                    Lease lease = new Lease(
                        rs.getInt("leaseID"),
                        rs.getInt("customerID"),
                        rs.getInt("vehicleID"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("status")
                    );
                    return lease;
                } else {
                    throw new LeaseNotFoundException("Lease with ID " + leaseId + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new LeaseNotFoundException("Error finding lease by ID", e);
        }
    }

}
