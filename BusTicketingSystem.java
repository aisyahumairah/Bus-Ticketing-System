import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Abstract class representing a booking item
abstract class BookingItem {
    private int idTicket;
    private String status;
    private static int idCounter = 1;

    public BookingItem(String status) {
        this.idTicket = idCounter++;
        this.status = status;
    }

    // Accessors and mutators
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdTicket() {
        return idTicket;
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
    private static int ticketIdCounter = 1000;
    private int idTicket;
    private String busname;
    private String type;
    private double rate;
    private String description;
    private String date;
    private String departure;
    private String duration;
    private String arrival;

    public Ticket(String busname, String type, double rate, String description, String status, String date,
            String departure, String duration, String arrival) {
        super(status);
        this.idTicket = ticketIdCounter++;
        this.busname = busname;
        this.type = type;
        this.rate = rate;
        this.description = description;
        this.date = date;
        this.departure = departure;
        this.duration = duration;
        this.arrival = arrival;
    }

    // Accessors and mutators
    public int getIdTicket() {
        return idTicket;
    }

    public String getBusName() {
        return busname;
    }

    public void setBusName(String busname) {
        this.busname = busname;
    }

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

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
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
        System.out.println("ID: " + getIdTicket());
        System.out.println("Bus Name: " + getBusName());
        System.out.println("Type: " + getType());
        System.out.println("Rate: RM" + getRate());
        System.out.println("Description: " + getDescription());
        System.out.println("Date (dd-MM-yyyy): " + getDate());
        System.out.println("Departure: " + getDeparture());
        System.out.println("Duration: " + getDuration());
        System.out.println("Arrival: " + getArrival());
        System.out.println("Status: " + getStatus());
    }
}

// Class representing a Passenger
class Passenger extends BookingItem {
    private static int passengerIdCounter = 1;
    private int idPassenger;
    private String name;
    private String address;

    public Passenger(String name, String address, String status) {
        super(status);
        this.idPassenger = passengerIdCounter++;
        this.name = name;
        this.address = address;
    }

    // Accessors and mutators
    public int getIdPassenger() {
        return idPassenger;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
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
        System.out.println("ID: " + getIdPassenger());
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Status: " + getStatus());
    }

    // Method to book a ticket for the passenger
    public void book(Ticket ticket, String date, String departure) {
        if (ticket.getStatus().equals("available")) {
            ticket.setStatus("booked");
            ticket.setDate(date);
            ticket.setDeparture(departure);
            // ticket.setDuration(duration);
            // ticket.setArrival(arrival);
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
    public void addPassenger(String name, String address, String status) {
        Passenger newPassenger = new Passenger(name, address, status);
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
    private static void reduceSeat(Ticket ticket) {
        int ticketId = ticket.getIdTicket();
        if (ticketId <= 10) {
            availableBusinessSeats--;
            if (availableBusinessSeats == 0) {
                System.out.println("Ticket are not available.");
            } else {
                System.out.println("Business seat booked. Remaining Business seats: " + availableBusinessSeats);
            }
        } else if (ticketId > 10 && ticketId <= 30) {
            availableEconomySeats--;
            if (availableEconomySeats == 0) {
                System.out.println("Ticket are not available.");
            } else {
                System.out.println("Economy seat booked. Remaining Economy seats: " + availableEconomySeats);
            }
        }
    }

    // Helper method to find a passenger by ID
    private static Passenger findPassengerById(int passengerId) {
        for (BookingItem item : bookingItems) {
            if (item instanceof Passenger && ((Passenger) item).getIdPassenger() == passengerId) {
                return (Passenger) item;
            }
        }
        return null;
    }

    private static Ticket findTicketById(int ticketId) {
        for (BookingItem item : bookingItems) {
            if (item instanceof Ticket && ((Ticket) item).getIdTicket() == ticketId) {
                return (Ticket) item;
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
    private static BookingItem findItemById(int itemId, String itemType) {
        for (BookingItem item : bookingItems) {
            if ("ticket".equalsIgnoreCase(itemType) && item instanceof Ticket && ((Ticket) item).getIdTicket() == itemId) {
                return item;
            } else if ("passenger".equalsIgnoreCase(itemType) && item instanceof Passenger && ((Passenger) item).getIdPassenger() == itemId) {
                return item;
            }
        }
        return null;
    }

    // private static boolean getConfirmation(Scanner scanner) {
    //     System.out.print("Do you wish to continue? (Y/N): ");
    //     String response = scanner.next().trim().toLowerCase();
    //     return response.equals("y");
    // }

    // Main method
    public static void main(String[] args) {
        BusTicketingSystem ticketingSystem = new BusTicketingSystem();
        Scanner scanner = new Scanner(System.in);

        // boolean continueExecution;

        // Adding some initial booking items
        Ticket ticket1 = new Ticket("Star Mart Express", "Economy", 25.0, "Economy class ticket", "available",
                "04-12-2001", "8.45am", "2 Hours", "10.45am");
        Ticket ticket2 = new Ticket("KKKL Express", "Business", 50.0, "Business class ticket", "available",
                "04-12-2001", "8.45am", "4 Hours", "12.45pm");
        Passenger passenger1 = new Passenger("Ahmad Abdullah", "123 Jalan Raya", "Active");
        Passenger passenger2 = new Passenger("Siti Aminah", "456 Jalan Bahagia", "Active");

        ticketingSystem.addBookingItem(ticket1);
        ticketingSystem.addBookingItem(ticket2);
        ticketingSystem.addBookingItem(passenger1);
        ticketingSystem.addBookingItem(passenger2);

        int choice;

        do {
            System.out.println("\nBus Ticketing System Menu:");
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

            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                // System.out.println("Invalid input. Please enter a valid integer.");
                // Clear the buffer to prevent an infinite loop
                scanner.nextLine();
                choice = 0; // Set a default value or handle it according to your logic
            }
            System.out.print("\n");

            switch (choice) {
                case 1: // display booking items
                    ticketingSystem.displayBookingItems();
                    break;

                case 2: // book ticket for passenger
                    System.out.print("Enter passenger ID: ");
                    int bookPassengerId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Ticket ID: ");
                    int bookTicketID = scanner.nextInt();
                    scanner.nextLine();

                    Passenger bookPassenger = findPassengerById(bookPassengerId);
                    Ticket bookTicket = findTicketById(bookTicketID);

                    if (bookPassenger != null && bookTicket != null) {
                        if (bookTicket.getStatus().equals("available")) {
                            bookPassenger.book(bookTicket, bookTicket.getDate(), bookTicket.getDeparture());
                            reduceSeat(bookTicket);
                            ticketingSystem.displayBookingItems();
                        } else {
                            System.out.println("Ticket not available for booking.");
                        }
                    } else {
                        System.out.println("Passenger or ticket not found.");
                    }
                    break;

                case 3: // pay for booked ticket
                    System.out.print("Enter passenger ID: ");
                    int payPassengerId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter ticket ID to pay for (Business/Economy): ");
                    int payTicketid = scanner.nextInt();
                    scanner.nextLine();

                    Passenger payPassenger = findPassengerById(payPassengerId);
                    Ticket payTicket = findTicketById(payTicketid);

                    if (payPassenger != null && payTicket != null) {
                        payTicket.pay();
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Passenger or ticket not found.");
                    }
                    break;
                case 4: // delete booking item
                    scanner.nextLine();
                    System.out.print("Enter item type (Ticket/Passenger): ");
                    String itemType = scanner.nextLine().toLowerCase();

                    System.out.print("Enter item ID to delete: ");
                    int deleteItemId = scanner.nextInt();
                    BookingItem itemToDelete = findItemById(deleteItemId, itemType);

                    if (itemToDelete != null) {
                        ticketingSystem.deleteBookingItem(itemToDelete);
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case 5:
                    // ... (Adding a new passenger functionality)
                    scanner.nextLine();
                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.nextLine();

                    System.out.print("Enter passenger address: ");
                    String passengerAddress = scanner.nextLine();

                    System.out.print("Passenger Status (Active/Not Active): ");
                    String passengerStatus = scanner.nextLine().trim().toLowerCase();

                    ticketingSystem.addBookingItem(new Passenger(passengerName, passengerAddress, passengerStatus));

                    ticketingSystem.displayBookingItems();
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
                        System.out.println("Passenger deleted successfully.\n");
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;

                case 8:
                    // Edit Information Passenger
                    System.out.print("Enter passenger ID to edit: ");
                    int editPassengerId = scanner.nextInt();
                    scanner.nextLine();

                    Passenger passengerToEdit = findPassengerById(editPassengerId);
                    if (passengerToEdit != null) {
                        System.out.print("Enter new passenger name: ");
                        String newName = scanner.nextLine();

                        System.out.print("Enter new passenger address: ");
                        String newAddress = scanner.nextLine();

                        System.out.print("Enter new passenger status (Active/Not Active): ");
                        String newStatus = scanner.nextLine().trim().toLowerCase();

                        passengerToEdit.setName(newName);
                        passengerToEdit.setAddress(newAddress);
                        passengerToEdit.setStatus(newStatus);

                        System.out.println("Passenger information updated successfully.\n");
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;

                case 9:
                    // Add New Ticket
                    scanner.nextLine();
                    System.out.print("Enter Bus Name: ");
                    String busName = scanner.nextLine();

                    System.out.print("Enter ticket type (Business/Economy): ");
                    String newTicketType = scanner.nextLine().trim().toLowerCase();

                    System.out.print("Enter ticket rate: ");
                    double newTicketRate = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter ticket description: ");
                    String newTicketDescription = scanner.nextLine();

                    System.out.print("Enter ticket date (dd-MM-yyyy): ");
                    String newTicketDate = scanner.nextLine();

                    System.out.print("Enter ticket time departure (HH:MM): ");
                    String newTicketDeparture = scanner.nextLine();

                    System.out.print("Enter duration (HH:MM): ");
                    String newDuration = scanner.nextLine();

                    System.out.print("Enter ticket time arrival (HH:MM): ");
                    String newTicketArrival = scanner.nextLine();

                    Ticket newTicket = new Ticket(busName, newTicketType, newTicketRate, newTicketDescription,
                            "available", newTicketDate, newTicketDeparture, newDuration, newTicketArrival);
                    ticketingSystem.addBookingItem(newTicket);

                    System.out.println("New ticket added successfully.\n");
                    ticketingSystem.displayBookingItems();
                    break;

                case 10:
                    // Display Ticket
                    scanner.nextLine();
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
                    ticketingSystem.displayBookingItems();
                    System.out.print("Enter ticket ID to delete: ");
                    int deleteTicketId = scanner.nextInt();
                    Ticket ticketToDelete = findTicketById(deleteTicketId);
                    if (ticketToDelete != null) {
                        ticketingSystem.deleteBookingItem(ticketToDelete);
                        System.out.println("Ticket deleted successfully.\n");
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;

                case 12: //edit ticket information
                    ticketingSystem.displayBookingItems();
                    System.out.print("Enter ticket ID to edit : ");
                    int editTicketId = scanner.nextInt();
                    scanner.nextLine();

                    Ticket ticketToEdit = findTicketById(editTicketId);
                    if (ticketToEdit != null) {
                        System.out.print("Enter new bus name: ");
                        String newBusName = scanner.nextLine();

                        System.out.print("Enter new ticket rate: ");
                        double newRate = 0.0;
                        if (scanner.hasNextDouble()) {
                            newRate = scanner.nextDouble();
                        } else {
                            System.out.println("Invalid rate format. Please enter a valid number.");
                            scanner.nextLine();
                        }

                        scanner.nextLine();

                        System.out.print("Enter new ticket description: ");
                        String newDescription = scanner.nextLine();

                        ticketToEdit.setBusName(newBusName);
                        ticketToEdit.setRate(newRate);
                        ticketToEdit.setDescription(newDescription);

                        System.out.println("Ticket information updated successfully.\n");
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Ticket not found.");
                    }
                break;
            }
            

        } while (choice != 13);

        scanner.close();
    }
}
