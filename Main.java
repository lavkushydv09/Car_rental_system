import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Rent a Car: Make your Journey Comfortable =====");
            System.out.println("1. Rent Car");
            System.out.println("2. Return Car");
            System.out.println("3. Exit");
            System.out.println("Choose from the above options(1-3): ");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your Name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                System.out.println("\nCar ID - Brand Model");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: ₹%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }

}
public class Main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C111", "Mahindra", "Scorpio", 60.0); // Different base price per day for each car
        Car car2 = new Car("C112", "Honda", "City", 70.0);
        Car car3 = new Car("C113", "Mahindra", "Thar", 150.0);
        Car car4  = new Car("C114", "Maruti", "Swift", 55.0);
        Car car5  = new Car("C115", "Maruti", "Dzire", 60.0);
        Car car6  = new Car("C116", "Hyundai", "i20", 65.0);
        Car car7  = new Car("C117", "Hyundai", "Creta", 120.0);
        Car car8  = new Car("C118", "Tata", "Nexon", 110.0);
        Car car9  = new Car("C119", "Tata", "Punch", 85.0);
        Car car10 = new Car("C120", "Kia", "Seltos", 130.0);
        Car car11 = new Car("C121", "Kia", "Sonet", 90.0);
        Car car12 = new Car("C122", "Toyota", "Innova", 150.0);
        Car car13 = new Car("C123", "Toyota", "Fortuner", 220.0);
        Car car14 = new Car("C124", "Mahindra", "Bolero", 100.0);
        Car car15 = new Car("C125", "Mahindra", "XUV700", 180.0);
        Car car16 = new Car("C126", "Honda", "Amaze", 65.0);
        Car car17 = new Car("C127", "Renault", "Kwid", 50.0);
        Car car18 = new Car("C128", "Volkswagen", "Virtus", 125.0);
        Car car19 = new Car("C129", "Skoda", "Slavia", 130.0);
        Car car20 = new Car("C130", "MG", "Hector", 170.0);
        Car car21 = new Car("C131", "Maruti", "Brezza", 105.0);
        Car car22 = new Car("C132", "Hyundai", "Venue", 95.0);
        Car car23 = new Car("C133", "Citroen", "C3", 85.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);
        rentalSystem.addCar(car6);
        rentalSystem.addCar(car7);
        rentalSystem.addCar(car8);
        rentalSystem.addCar(car9);
        rentalSystem.addCar(car10);
        rentalSystem.addCar(car11);
        rentalSystem.addCar(car12);
        rentalSystem.addCar(car13);
        rentalSystem.addCar(car14);
        rentalSystem.addCar(car15);
        rentalSystem.addCar(car16);
        rentalSystem.addCar(car17);
        rentalSystem.addCar(car18);
        rentalSystem.addCar(car19);
        rentalSystem.addCar(car20);
        rentalSystem.addCar(car21);
        rentalSystem.addCar(car22);
        rentalSystem.addCar(car23);


        rentalSystem.menu();
    }
}