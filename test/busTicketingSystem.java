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

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
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
            System.out.println("Ticket booked successfully for " + name);
        } else {
            System.out.println("Ticket not available for booking.");
        }
    }
}

// Class representing the Bus Ticketing System
class BusTicketingSystem {
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
        if (ticket.getType().equalsIgnoreCase("Business")) {
            availableBusinessSeats--;
            if (availableBusinessSeats == 0) {
                System.out.println("Business seats are not available.");
            } else {
                System.out.println("Business seat booked. Remaining Business seats: " + availableBusinessSeats);
            }
        } else if (ticket.getType().equalsIgnoreCase("Economy")) {
            availableEconomySeats--;
            if (availableEconomySeats == 0) {
                System.out.println("Economy seats are not available.");
            } else {
                System.out.println("Economy seat booked. Remaining Economy seats: " + availableEconomySeats);
            }
        }
    }

    // Helper method to find a passenger by ID
    private static Passenger findPassengerById(int passengerId) {
        for (BookingItem item : bookingItems) {
            if (item instanceof Passenger && ((Passenger) item).getIdTicket() == passengerId) {
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
    private static BookingItem findItemById(int itemId) {
        for (BookingItem item : bookingItems) {
            if (item instanceof Passenger && ((Passenger) item).getIdTicket() == itemId) {
                return (Passenger) item;
            } else if (item instanceof Ticket && ((Ticket) item).getIdTicket() == itemId) {
                return (Ticket) item;
            }
        }
        return null;
    }

    // Method to display available tickets based on destination
    private static void displayAvailableTickets(String destination) {
        System.out.println("Available Tickets for Destination: " + destination);
        System.out.println("----------------------------------");

        for (BookingItem item : bookingItems) {
            if (item instanceof Ticket && item.getStatus().equalsIgnoreCase("available")) {
                Ticket ticket = (Ticket) item;
                if (ticket.getArrival().equalsIgnoreCase(destination)) {
                    System.out.println("Ticket ID: " + ticket.getIdTicket());
                    System.out.println("Bus Name: " + ticket.getBusName());
                    System.out.println("Type: " + ticket.getType());
                    System.out.println("Rate: RM" + ticket.getRate());
                    System.out.println("Departure: " + ticket.getDeparture());
                    System.out.println("Duration: " + ticket.getDuration());
                    System.out.println("Arrival: " + ticket.getArrival());
                    System.out.println("Availability: " + item.getStatus());
                    System.out.println("----------------------------------");
                }
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        BusTicketingSystem ticketingSystem = new BusTicketingSystem();
        Scanner scanner = new Scanner(System.in);

        //add some initial booking items
        Ticket t = new Ticket("Mayang Sari", "Business", availableBusinessSeats, "Business class ticket", "Available", "27-12-2023", "9:00am", "2 Hourse", "11:00am");
        Ticket t2 = new Ticket("Orkid Malaysia", "Economy", availableEconomySeats, "Economy class ticket", "Available", "27-12-2023", "9:00am", "2 Hourse", "11:00am");

        Passenger p = new Passenger("Aisyah", "Batu Kawan", "Active");
        Passenger p2 = new Passenger("Siti Aminah", "Sungai Bakap", "Active");

        ticketingSystem.addBookingItem(t);
        ticketingSystem.addBookingItem(t2);
        ticketingSystem.addBookingItem(p);
        ticketingSystem.addBookingItem(p2);

        int choice;

        do {
            System.out.println("\nBus Ticketing System Menu:");
            System.out.println("1. Display Booking Items (Tickets/Passenger)");
            System.out.println("2. Display Available Tickets for Destination");
            System.out.println("3. Book Ticket for Passenger");
            System.out.println("4. Pay for Booked Ticket");
            System.out.println("5. Delete Booking Item");
            System.out.println("6. Add Passenger");
            System.out.println("7. Display Passenger Information");
            System.out.println("8. Delete Passenger");
            System.out.println("9. Edit Passenger Information");
            System.out.println("10. Add New Ticket");
            System.out.println("11. Display Ticket");
            System.out.println("12. Delete Ticket");
            System.out.println("13. Edit Ticket Information");
            System.out.println("14. Display Available Seats");
            System.out.println("15. Book Tickets for Multiple Passengers");
            System.out.println("16. Exit\n");
            // System.out.print("Enter your choice: ");
            // choice = scanner.nextInt();
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
                case 1:
                    ticketingSystem.displayBookingItems();
                    break;
                case 2:
                    System.out.print("Enter destination: ");
                    String destination = scanner.next();
                    ticketingSystem.displayAvailableTickets(destination);
                    break;
                case 3: //book ticket for passenger
                    ticketingSystem.displayBookingItems();
                    System.out.print("\nEnter passenger ID: ");
                    int bookPassengerId = scanner.nextInt();
                    System.out.print("Enter Ticket ID: ");
                    int bookTicketID = scanner.nextInt();
                    System.out.print("Enter ticket type to book (Business/Economy): ");
                    String bookTicketType = scanner.next();
                    System.out.print("Enter booking date (dd-MM-yyyy): ");
                    String bookDate = scanner.next();
                    System.out.print("Enter booking departure (HH-MM): ");
                    String bookDeparture = scanner.next();

                    Passenger bookPassenger = findPassengerById(bookPassengerId);
                    Ticket bookTicket = findTicketById(bookTicketID);

                    if (bookPassenger != null && bookTicket != null
                            && bookTicket.getStatus().equalsIgnoreCase("available")) {
                        bookPassenger.book(bookTicket, bookDate, bookDeparture);
                        reduceSeat(bookTicket);
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Passenger or ticket not found or ticket not available.");
                    }
                    break;
                case 4: //pay for booked ticket
                    ticketingSystem.displayBookingItems();
                    System.out.print("\nEnter passenger ID: ");
                    int payPassengerId = scanner.nextInt();
                    System.out.print("Enter ticket ID to pay for: ");
                    int payTicketid = scanner.nextInt();
                    Passenger payPassenger = findPassengerById(payPassengerId);
                    Ticket payTicket = findTicketById(payTicketid);
                    if (payPassenger != null && payTicket != null) {
                        payTicket.pay();
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Passenger or ticket not found.");
                    }
                    break;
                case 5: //delete booking item
                    ticketingSystem.displayBookingItems();
                    System.out.print("Enter item ID to delete: ");
                    int deleteItemId = scanner.nextInt();
                    BookingItem itemToDelete = findItemById(deleteItemId);
                    if (itemToDelete != null) {
                        ticketingSystem.deleteBookingItem(itemToDelete);
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case 6: // add passenger
                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.next();
                    System.out.print("Enter passenger address: ");
                    String passengerAddress = scanner.next();
                    System.out.print("Passenger Status (Active/Not Active): ");
                    String passengerStatus = scanner.next();

                    // Explicitly set the status during passenger creation
                    Passenger newPassenger = new Passenger(passengerName, passengerAddress, passengerStatus);

                    ticketingSystem.addBookingItem(newPassenger);
                    break;
                case 7: //display passenger information
                    System.out.print("Enter passenger ID: ");
                    int displayPassengerId = scanner.nextInt();
                    Passenger displayPassenger = findPassengerById(displayPassengerId);
                    if (displayPassenger != null) {
                        displayPassenger.displayDetails();
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;
                case 8: //delete passenger
                    System.out.print("Enter passenger ID to delete: ");
                    int deletePassengerId = scanner.nextInt();
                    Passenger passengerToDelete = findPassengerById(deletePassengerId);
                    if (passengerToDelete != null) {
                        ticketingSystem.deleteBookingItem(passengerToDelete);
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;
                case 9: //edit passenger information
                    System.out.print("Enter passenger ID to edit: ");
                    int editPassengerId = scanner.nextInt();
                    Passenger passengerToEdit = findPassengerById(editPassengerId);
                    if (passengerToEdit != null) {
                        System.out.print("Enter new name: ");
                        String newName = scanner.next();
                        System.out.print("Enter new address: ");
                        String newAddress = scanner.next();
                        System.out.print("Enter new status (Active/Not Active): ");
                        String newStatus = scanner.next();
                        passengerToEdit.setName(newName);
                        passengerToEdit.setAddress(newAddress);
                        passengerToEdit.setStatus(newStatus);
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;
                case 10: //add new ticket
                    System.out.print("Enter bus name: ");
                    String newBusName = scanner.next();
                    System.out.print("Enter ticket type (Business/Economy): ");
                    String newTicketType = scanner.next();
                    System.out.print("Enter ticket rate: ");
                    double newRate = scanner.nextDouble();
                    System.out.print("Enter ticket description: ");
                    String newDescription = scanner.next();
                    System.out.print("Enter ticket status (Available/Booked): ");
                    String newStatus = scanner.next();
                    System.out.print("Enter ticket date (dd-MM-yyyy): ");
                    String newDate = scanner.next();
                    System.out.print("Enter ticket departure (HH-MM): ");
                    String newDeparture = scanner.next();
                    System.out.print("Enter ticket duration: ");
                    String newDuration = scanner.next();
                    System.out.print("Enter ticket arrival: ");
                    String newArrival = scanner.next();
                    ticketingSystem.addBookingItem(new Ticket(newBusName, newTicketType, newRate, newDescription,
                            newStatus, newDate, newDeparture, newDuration, newArrival));
                    break;
                case 11: //display ticket
                    System.out.print("Enter ticket ID: ");
                    int displayTicketId = scanner.nextInt();
                    Ticket displayTicket = findTicketById(displayTicketId);
                    if (displayTicket != null) {
                        displayTicket.displayDetails();
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;
                case 12: //delete ticket
                    System.out.print("Enter ticket ID to delete: ");
                    int deleteTicketId = scanner.nextInt();
                    Ticket ticketToDelete = findTicketById(deleteTicketId);
                    if (ticketToDelete != null) {
                        ticketingSystem.deleteBookingItem(ticketToDelete);
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;
                case 13: //edit ticket information
                    System.out.print("Enter ticket ID to edit: ");
                    int editTicketId = scanner.nextInt();
                    Ticket ticketToEdit = findTicketById(editTicketId);
                    if (ticketToEdit != null) {
                        System.out.print("Enter new bus name: ");
                        String newBus = scanner.next();
                        System.out.print("Enter new ticket type (Business/Economy): ");
                        String newType = scanner.next();
                        System.out.print("Enter new ticket rate: ");
                        double newRateTicket = scanner.nextDouble();
                        System.out.print("Enter new ticket description: ");
                        String newDescriptionTicket = scanner.next();
                        System.out.print("Enter new ticket status (Available/Booked): ");
                        String newStatusTicket = scanner.next();
                        System.out.print("Enter new ticket date (dd-MM-yyyy): ");
                        String newDateTicket = scanner.next();
                        System.out.print("Enter new ticket departure (HH-MM): ");
                        String newDepartureTicket = scanner.next();
                        System.out.print("Enter new ticket duration: ");
                        String newDurationTicket = scanner.next();
                        System.out.print("Enter new ticket arrival: ");
                        String newArrivalTicket = scanner.next();

                        ticketToEdit.setBusName(newBus);
                        ticketToEdit.setType(newType);
                        ticketToEdit.setRate(newRateTicket);
                        ticketToEdit.setDescription(newDescriptionTicket);
                        ticketToEdit.setStatus(newStatusTicket);
                        ticketToEdit.setDate(newDateTicket);
                        ticketToEdit.setDeparture(newDepartureTicket);
                        ticketToEdit.setDuration(newDurationTicket);
                        ticketToEdit.setArrival(newArrivalTicket);

                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Ticket not found.");
                    }
                    break;
                case 14: //display available seats
                    System.out.println("Available Seats:");
                    System.out.println("Business Seats: " + availableBusinessSeats);
                    System.out.println("Economy Seats: " + availableEconomySeats);
                    break;
                case 15: //book ticket for multiple passenger
                    System.out.print("Enter the number of passengers: ");
                    int numberOfPassengers = scanner.nextInt();

                    for (int i = 0; i < numberOfPassengers; i++) {
                        System.out.println("\nPassenger " + (i + 1) + ":");
                        System.out.print("Enter passenger name: ");
                        String passengerNameMulti = scanner.next();
                        System.out.print("Enter passenger address: ");
                        String passengerAddressMulti = scanner.next();
                        System.out.print("Passenger Status (Active/Not Active): ");
                        String passengerStatusMulti = scanner.next();
                        ticketingSystem.addBookingItem(
                                new Passenger(passengerNameMulti, passengerAddressMulti, passengerStatusMulti));
                    }

                    System.out.print("\nEnter Ticket ID: ");
                    int multiBookTicketID = scanner.nextInt();
                    System.out.print("Enter ticket type to book (Business/Economy): ");
                    String multiBookTicketType = scanner.next();
                    System.out.print("Enter booking date (dd-MM-yyyy): ");
                    String multiBookDate = scanner.next();
                    System.out.print("Enter booking departure (HH-MM): ");
                    String multiBookDeparture = scanner.next();

                    Ticket multiBookTicket = findTicketById(multiBookTicketID);

                    if (multiBookTicket != null && multiBookTicket.getStatus().equalsIgnoreCase("available")) {
                        for (int i = 0; i < numberOfPassengers; i++) {
                            Passenger multiBookPassenger = (Passenger) bookingItems
                                    .get(bookingItems.size() - numberOfPassengers + i);
                            multiBookPassenger.book(multiBookTicket, multiBookDate, multiBookDeparture);
                        }
                        reduceSeat(multiBookTicket);
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Ticket not found or not available.");
                    }
                    break;
                case 16:
                    System.out.println("Exiting Bus Ticketing System. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }

        } while (choice != 16);

        scanner.close();
    }
}
