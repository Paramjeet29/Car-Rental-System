package main;

import dao.*;
import entity.*;
import exception.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CarRentalApplication {

    public static void main(String[] args) throws DatabaseException, CarNotFoundException, CustomerNotFoundException, LeaseNotFoundException {
        ICarLeaseRepository repo = new CarLeaseRepositoryImpl();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Car Rental System Menu ---");
            System.out.println("1. Add Car");
            System.out.println("2. Remove Car");
            System.out.println("3. List Available Cars");
            System.out.println("4. List Rented Cars");
            System.out.println("5. Add Customer");
            System.out.println("6. Remove Customer");
            System.out.println("7. Create Lease");
            System.out.println("8. Return Car");
            System.out.println("9. Record Payment");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    // Add Car
                    System.out.println("Enter Car Details:");
                    System.out.print("Make: ");
                    String make = scanner.nextLine();
                    System.out.print("Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Year: ");
                    int year = scanner.nextInt();
                    System.out.print("Daily Rate: ");
                    double dailyRate = scanner.nextDouble();
                    System.out.print("Passenger Capacity: ");
                    int passengerCapacity = scanner.nextInt();
                    System.out.print("Engine Capacity: ");
                    double engineCapacity = scanner.nextDouble();
                    scanner.nextLine(); // clear buffer
                    Car car = new Car(0, make, model, year, dailyRate, "available", passengerCapacity, engineCapacity);
                    repo.addCar(car);
                    System.out.println("Car added successfully.");
                    break;

                case 2:
                    // Remove Car
                    System.out.print("Enter Car ID to remove: ");
                    int carID = scanner.nextInt();
                    repo.removeCar(carID);
                    System.out.println("Car removed successfully.");
                    break;

                case 3:
                    // List Available Cars
                    List<Car> availableCars = repo.listAvailableCars();
                    if (availableCars.isEmpty()) {
                        System.out.println("No cars available.");
                    } else {
                        System.out.println("Available Cars:");
                        availableCars.forEach(c -> System.out.println(c.getVehicleID() + ": " + c.getMake() + " " + c.getModel() + " (" + c.getYear() + ") - " + c.getDailyRate() + "/day"));
                    }
                    break;

                case 4:
                    // List Rented Cars
                    List<Car> rentedCars = repo.listRentedCars();
                    if (rentedCars.isEmpty()) {
                        System.out.println("No cars currently rented.");
                    } else {
                        System.out.println("Rented Cars:");
                        rentedCars.forEach(c -> System.out.println(c.getVehicleID() + ": " + c.getMake() + " " + c.getModel() + " (" + c.getYear() + ")"));
                    }
                    break;

                case 5:
                    // Add Customer
                    System.out.println("Enter Customer Details:");
                    System.out.print("First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone Number: ");
                    String phone = scanner.nextLine();
                    Customer customer = new Customer(0, firstName, lastName, email, phone);
                    repo.addCustomer(customer);
                    System.out.println("Customer added successfully.");
                    break;

                case 6:
                    // Remove Customer
                    System.out.print("Enter Customer ID to remove: ");
                    int customerID = scanner.nextInt();
                    repo.removeCustomer(customerID);
                    System.out.println("Customer removed successfully.");
                    break;

                case 7:
                    // Create Lease
                    System.out.print("Enter Customer ID: ");
                    int custID = scanner.nextInt();
                    System.out.print("Enter Car ID: ");
                    int vID = scanner.nextInt();
                    System.out.print("Enter Lease Start Date (yyyy-mm-dd): ");
                    String startDateInput = scanner.next();
                    System.out.print("Enter Lease End Date (yyyy-mm-dd): ");
                    String endDateInput = scanner.next();
                    Date startDate = java.sql.Date.valueOf(startDateInput);
                    Date endDate = java.sql.Date.valueOf(endDateInput);
                    Lease lease = repo.createLease(custID, vID, startDate, endDate);
                    System.out.println("Lease created successfully. Lease ID: " + lease.getLeaseID());
                    break;

                case 8:
                    // Return Car
                    System.out.print("Enter Lease ID: ");
                    int leaseID = scanner.nextInt();
                    try {
                        repo.returnCar(leaseID);
                        System.out.println("Car returned successfully.");
                    } catch (LeaseNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 9:
                    // Record Payment
                    System.out.print("Enter Lease ID: ");
                    int payLeaseID = scanner.nextInt();
                    System.out.print("Enter Payment Amount: ");
                    double amount = scanner.nextDouble();
                    try {
                        Lease leaseForPayment = repo.findLeaseById(payLeaseID);
                        repo.recordPayment(leaseForPayment, amount);
                        System.out.println("Payment recorded successfully.");
                    } catch (LeaseNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 10:
                    // Exit
                    exit = true;
                    System.out.println("Exiting... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }
}
