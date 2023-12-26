import java.util.ArrayList;
import java.util.Scanner;

// Abstract class representing a booking item
abstract class BookingItem {
    private String status;

    public BookingItem(String status) {
        this.status = status;
    }

    // Accessors and mutators
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Abstract method to be overridden by subclasses
    public abstract void displayDetails();
}

// Interface for payment process
interface PaymentProcess {
    void pay();
}

// Class representing a Ticket
class Ticket extends BookingItem implements PaymentProcess {
    private String type;
    private double rate;
    private String description;
    private String date;
    private String time;

    public Ticket(String type, double rate, String description, String status) {
        super(status);
        this.type = type;
        this.rate = rate;
        this.description = description;
    }

    // Accessors and mutators
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Implementing interface method
    public void pay() {
        if (getStatus().equals("booked")) {
            setStatus("paid");
            System.out.println("Payment successful for the ticket.");
        } else {
            System.out.println("Ticket not booked yet.");
        }
    }

    // Overriding abstract method
    public void displayDetails() {
        System.out.println("Ticket Details:");
        System.out.println("Type: " + type);
        System.out.println("Rate: RM" + rate);
        System.out.println("Description: " + description);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Status: " + getStatus());
    }
}

// Class representing a Passenger
class Passenger extends BookingItem {
    private int id;
    private String name;
    private String address;

    public Passenger(int id, String name, String address, String status) {
        super(status);
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // Accessors and mutators
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Method to display passenger details
    @Override
    public void displayDetails() {
        System.out.println("Passenger Details:");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Status: " + getStatus());
    }

    // Method to book a ticket for the passenger
    public void book(Ticket ticket, String date, String time) {
        if (ticket.getStatus().equals("available")) {
            ticket.setStatus("booked");
            ticket.setDate(date);
            ticket.setTime(time);
            System.out.println("Ticket booked successfully for " + name);
        } else {
            System.out.println("Ticket not available for booking.");
        }
    }
}

// Class representing the Bus Ticketing System
public class BusTicketingSystem {
    private static ArrayList<BookingItem> bookingItems;
    private static ArrayList<Passenger> passengers;
    private static int availableBusinessSeats = 10;
    private static int availableEconomySeats = 20;

    public BusTicketingSystem() {
        bookingItems = new ArrayList<>();
        passengers = new ArrayList<>();
    }

    // Method to add a new booking item
    public void addBookingItem(BookingItem item) {
        bookingItems.add(item);
        System.out.println("New booking item added.");
    }

    // Method to add new passenger
    public void addPassenger(int id, String name, String address, String status) {
        Passenger newPassenger = new Passenger(id, name, address, status);
        passengers.add(newPassenger);
        System.out.println("New passenger added successfully");
    }

    // Method to display all booking items
    public void displayBookingItems() {
        System.out.println("Booking Items:");
        System.out.println("-------------");
        for (BookingItem item : bookingItems) {
            item.displayDetails();
            System.out.println("-------------");
        }
    }

    // Method to delete a booking item
    public void deleteBookingItem(BookingItem item) {
        bookingItems.remove(item);
        System.out.println("Booking item deleted.");
    }

    // Method to reduce seat availability after booking
    private static void reduceSeat(String seatType) {
        if (seatType.equalsIgnoreCase("Business")) {
            availableBusinessSeats--;
            System.out.println("Business seat booked. Remaining Business seats: " + availableBusinessSeats);
        } else if (seatType.equalsIgnoreCase("Economy")) {
            availableEconomySeats--;
            System.out.println("Economy seat booked. Remaining Economy seats: " + availableEconomySeats);
        }
    }

    // Helper method to find a passenger by ID
    private static Passenger findPassengerById(int passengerId) {
        for (BookingItem item : bookingItems) {
            if (item instanceof Passenger && ((Passenger) item).getId() == passengerId) {
                return (Passenger) item;
            }
        }
        return null;
    }

    // Helper method to find a ticket by type
    private static Ticket findTicketByType(String ticketType) {
        for (BookingItem item : bookingItems) {
            if (item instanceof Ticket && ((Ticket) item).getType().equalsIgnoreCase(ticketType)) {
                return (Ticket) item;
            }
        }
        return null;
    }

    // Helper method to find an item by ID
    private static BookingItem findItemById(int itemId) {
        for (BookingItem item : bookingItems) {
            if (item instanceof Passenger && ((Passenger) item).getId() == itemId) {
                return (Passenger) item;
            } else if (item instanceof Ticket && ((Ticket) item).getRate() == itemId) {
                return (Ticket) item;
            }
        }
        return null;
    }

    // Main method
    public static void main(String[] args) {
        BusTicketingSystem ticketingSystem = new BusTicketingSystem();
        Scanner scanner = new Scanner(System.in);

        // Adding some initial booking items
        Ticket ticket1 = new Ticket("Economy", 25.0, "Economy class ticket", "available");
        Ticket ticket2 = new Ticket("Business", 50.0, "Business class ticket", "available");
        Passenger passenger1 = new Passenger(1, "Ahmad Abdullah", "123 Jalan Raya", "Active");
        Passenger passenger2 = new Passenger(2, "Siti Aminah", "456 Jalan Bahagia", "Active");

        ticketingSystem.addBookingItem(ticket1);
        ticketingSystem.addBookingItem(ticket2);
        ticketingSystem.addBookingItem(passenger1);
        ticketingSystem.addBookingItem(passenger2);

        int choice;

        do {
            System.out.println("\nBas Lada Putih Ticketing System Menu:");
            System.out.println("1. Display Booking Items (Tickets/Passenger)");
            System.out.println("2. Book Ticket for Passenger");
            System.out.println("3. Pay for Booked Ticket");
            System.out.println("4. Delete Booking Item");
            System.out.println("5. Add Passenger");
            System.out.println("6. Display Passenger Information");
            System.out.println("7. Delete Passenger");
            System.out.println("8. Edit Passenger Information");
            System.out.println("9. Add New Ticket");
            System.out.println("10. Display Ticket");
            System.out.println("11. Delete Ticket");
            System.out.println("12. Edit Ticket Information");
            System.out.println("13. Exit\n");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            System.out.print("\n");

            switch (choice) {
                case 1:
                    ticketingSystem.displayBookingItems();
                    break;
                case 2:
                    System.out.print("Enter passenger ID: ");
                    int bookPassengerId = scanner.nextInt();
                    System.out.print("Enter ticket type to book (Business/Economy): ");
                    String bookTicketType = scanner.next();
                    System.out.print("Enter booking date (yyyy-MM-dd): ");
                    String bookDate = scanner.next();
                    System.out.print("Enter booking time: ");
                    String bookTime = scanner.next();
                    Passenger bookPassenger = findPassengerById(bookPassengerId);
                    Ticket bookTicket = findTicketByType(bookTicketType);
                    if (bookPassenger != null && bookTicket != null) {
                        bookPassenger.book(bookTicket, bookDate, bookTime);
                        reduceSeat(bookTicketType);
                    } else {
                        System.out.println("Passenger or ticket not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter passenger ID: ");
                    int payPassengerId = scanner.nextInt();
                    System.out.print("Enter ticket type to pay for (Business/Economy): ");
                    String payTicketType = scanner.next();
                    Passenger payPassenger = findPassengerById(payPassengerId);
                    Ticket payTicket = findTicketByType(payTicketType);
                    if (payPassenger != null && payTicket != null) {
                        payTicket.pay();
                    } else {
                        System.out.println("Passenger or ticket not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter item ID to delete: ");
                    int deleteItemId = scanner.nextInt();
                    BookingItem itemToDelete = findItemById(deleteItemId);
                    if (itemToDelete != null) {
                        ticketingSystem.deleteBookingItem(itemToDelete);
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case 5:
                    // ... (Adding a new passenger functionality)
                    System.out.print("Enter ID Passenger: ");
                    int passengerId = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character
                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.nextLine();
                    System.out.print("Enter passenger address: ");
                    String passengerAddress = scanner.nextLine();
                    System.out.print("Passenger Status (Active/Not Active): ");
                    String passengerStatus = scanner.nextLine();
                    ticketingSystem.addBookingItem(
                            new Passenger(passengerId, passengerName, passengerAddress, passengerStatus));
                    break;

                case 6:
                    // Display passenger details
                    System.out.print("Enter passenger ID: ");
                    int displayPassengerId = scanner.nextInt();
                    Passenger displayPassenger = findPassengerById(displayPassengerId);
                    if (displayPassenger != null) {
                        displayPassenger.displayDetails();
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;

                case 7:
                    // Delete Passenger
                    System.out.print("Enter passenger ID to delete: ");
                    int deletePassengerId = scanner.nextInt();
                    Passenger passengerToDelete = findPassengerById(deletePassengerId);
                    if (passengerToDelete != null) {
                        ticketingSystem.deleteBookingItem(passengerToDelete);
                        System.out.println("Passenger deleted successfully.");
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;

                case 8:
                    // Edit Information Passenger
                    System.out.print("Enter passenger ID to edit: ");
                    int editPassengerId = scanner.nextInt();
                    Passenger passengerToEdit = findPassengerById(editPassengerId);
                    if (passengerToEdit != null) {
                        System.out.print("Enter new passenger name: ");
                        String newName = scanner.next();
                        System.out.print("Enter new passenger address: ");
                        String newAddress = scanner.next();
                        System.out.print("Enter new passenger status (Active/Not Active): ");
                        String newStatus = scanner.next();

                        passengerToEdit.setName(newName);
                        passengerToEdit.setAddress(newAddress);
                        passengerToEdit.setStatus(newStatus);

                        System.out.println("Passenger information updated successfully.");
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;

                case 9:
                    // Add New Ticket
                    System.out.print("Enter ticket type: ");
                    String newTicketType = scanner.next();
                    System.out.print("Enter ticket rate: ");
                    double newTicketRate = scanner.nextDouble();
                    System.out.print("Enter ticket description: ");
                    String newTicketDescription = scanner.next();

                    Ticket newTicket = new Ticket(newTicketType, newTicketRate, newTicketDescription, "available");
                    ticketingSystem.addBookingItem(newTicket);

                    System.out.println("New ticket added successfully.");
                    break;

                case 10:
                    // Display Ticket
                    System.out.print("Enter ticket type to display (Business/Economy): ");
                    String displayTicketType = scanner.next();
                    Ticket displayTicket = findTicketByType(displayTicketType);
                    if (displayTicket != null) {
                        displayTicket.displayDetails();
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;

                case 11:
                    // Delete Ticket
                    System.out.print("Enter ticket type to delete (Business/Economy): ");
                    String deleteTicketType = scanner.next();
                    Ticket ticketToDelete = findTicketByType(deleteTicketType);
                    if (ticketToDelete != null) {
                        ticketingSystem.deleteBookingItem(ticketToDelete);
                        System.out.println("Ticket deleted successfully.");
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;

                case 12:
                    // Edit Information Ticket
                    System.out.print("Enter ticket type to edit (Business/Economy): ");
                    String editTicketType = scanner.next();
                    Ticket ticketToEdit = findTicketByType(editTicketType);
                    if (ticketToEdit != null) {
                        System.out.print("Enter new ticket rate: ");
                        double newRate = scanner.nextDouble();
                        System.out.print("Enter new ticket description: ");
                        String newDescription = scanner.next();

                        ticketToEdit.setRate(newRate);
                        ticketToEdit.setDescription(newDescription);

                        System.out.println("Ticket information updated successfully.");
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;
            }

        } while (choice != 13);

        // Closing scanner to prevent resource leak
        scanner.close();
    }
}
