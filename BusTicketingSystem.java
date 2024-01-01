import java.util.ArrayList;
import java.util.Scanner;

abstract class Ticket {
    protected String ticketNumber;
    protected String status;

    public Ticket(String ticketNumber, String status) {
        this.ticketNumber = ticketNumber;
        this.status = status;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

interface Bookable {
    boolean bookTicket(String ticketNumber);

    boolean cancelBooking(String ticketNumber);

    boolean makePayment();
}

class Seat extends Ticket {
    private String seatNumber;

    public Seat(String seatNumber, String status) {
        super(seatNumber, status);
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}

class Passenger extends Ticket implements Bookable {
    private static int passengerIdCounter = 1;
    private int idPassenger;
    private String name;
    private String address;
    private String seatNo;
    private boolean isPaid;

    public Passenger(String name, String address) {
        super("", ""); // Using empty string as placeholders for passenger's ticketNumber and status
        this.idPassenger = passengerIdCounter++;
        this.name = name;
        this.address = address;
        this.seatNo = "";
        this.isPaid = false;
    }

    public int getIdPassenger() {
        return idPassenger;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public boolean isPaid() {
        return isPaid;
    }

    @Override
    public boolean bookTicket(String ticketNumber) {
        if (this.status.equals("")) {
            this.setStatus("Booked");
            this.ticketNumber = ticketNumber;
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelBooking(String ticketNumber) {
        if (!this.isPaid && this.ticketNumber.equals(ticketNumber)) {
            this.setStatus("");
            this.ticketNumber = "";
            return true;
        }
        return false;
    }

    @Override
    public boolean makePayment() {
        if (this.status.equals("Booked")) {
            this.setStatus("Paid");
            this.isPaid = true;
            return true;
        }
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }
}

public class BusTicketingSystem {
    private static ArrayList<Passenger> passengers;
    private static ArrayList<Seat> busSeats;

    public BusTicketingSystem() {
        passengers = new ArrayList<>();
        busSeats = new ArrayList<>();
        generateBusLayout();
    }

    public void displayBookingItems() {
        System.out.println("Booking Items:");
        System.out.println("-------------");

        // Display Passenger details
        System.out.println("Passenger Details:");
        for (Passenger passenger : passengers) {
            System.out.println("Passenger ID: " + passenger.getIdPassenger());
            System.out.println("Name: " + passenger.getName());
            System.out.println("Address: " + passenger.getAddress());
            System.out.println("Seat Number: " + passenger.getSeatNo());
            System.out.println("-------------");
        }

        // Display Bus layout with seat status
        System.out.println("Bus Layout:");
        for (int i = 0; i < busSeats.size(); i += 3) {
            for (int j = i; j < i + 3 && j < busSeats.size(); j++) {
                System.out.print(busSeats.get(j).getStatus() + " ");
            }
            System.out.println();
        }

    }

    private void generateBusLayout() {
        char row = 'A';
        int column = 1;

        for (int i = 1; i <= 18; i++) {
            String seatNumber = "[" + row + column + "]";
            String status = "[" + seatNumber + " - Available]";
            busSeats.add(new Seat(seatNumber, status));

            if (i % 6 == 0) {
                row++;
                column = 1;
            } else {
                column++;
            }
        }
    }

    public static void main(String[] args) {
        BusTicketingSystem ticketingSystem = new BusTicketingSystem();

        // Creating two initial passengers
        Passenger passenger1 = new Passenger("John Doe", "123 Main St");
        Passenger passenger2 = new Passenger("Jane Smith", "456 Elm St");

        // Booking a seat for passenger1 (A1 as an example)
        passenger1.setSeatNo("A1");

        // Marking seat A1 as booked
        for (Seat seat : busSeats) {
            if (seat.getSeatNumber().equals("[A1]")) {
                seat.setStatus("[A1 - Booked]");
                break;
            }
        }

        // Adding passenger1 with a booked seat and passenger2 without a booked seat
        passengers.add(passenger1);
        passengers.add(passenger2);
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\nBus Ticketing System Menu:");
            System.out.println("1. Display Booking Items");
            System.out.println("2. Book Seat for Passenger");
            System.out.println("3. Display Bus Layout");
            System.out.println("4. Edit Passenger Information");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Add Passenger");
            System.out.println("7. Delete Passenger");
            System.out.println("8. Make Payment for Passenger");
            System.out.println("9. Exit\n");
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1: // Display Booking Items
                        ticketingSystem.displayBookingItems();
                        break;

                    case 2: // Book Seat for Passenger
                        System.out.print("Enter passenger ID: ");
                        int passengerIdToBook = scanner.nextInt();
                        scanner.nextLine();

                        // Check if the passenger with the specified ID exists
                        boolean passengerFound = false;
                        for (Passenger passenger : passengers) {
                            if (passenger.getIdPassenger() == passengerIdToBook) {
                                passengerFound = true;
                                break;
                            }
                        }

                        if (!passengerFound) {
                            System.out.println(
                                    "Passenger with ID " + passengerIdToBook + " not found. Booking cannot be made.");
                            break; // Exit the case
                        }

                        System.out.print("Enter seat number to book (e.g., A1, B2, C3): ");
                        String seatToBook = scanner.nextLine();

                        boolean seatAlreadyBooked = false;
                        boolean seatFound = false;
                        for (Seat seat : busSeats) {
                            if (seat.getSeatNumber().equals("[" + seatToBook + " - Booked]")) {
                                seatAlreadyBooked = true;
                                break;
                            } else if (seat.getSeatNumber().equals("[" + seatToBook + "]")) {
                                if (seat.getStatus().contains("Available")) {
                                    seat.setStatus("[" + seatToBook + " - Booked]");
                                    for (Passenger passenger : passengers) {
                                        if (passenger.getIdPassenger() == passengerIdToBook) {
                                            passenger.setSeatNo(seatToBook);
                                            break;
                                        }
                                    }
                                    seatFound = true;
                                    System.out.println("Seat " + seatToBook + " booked successfully for passenger ID "
                                            + passengerIdToBook + ".");
                                    break;
                                } else {
                                    System.out.println("Seat " + seatToBook + " is already booked.");
                                    seatAlreadyBooked = true;
                                    break;
                                }
                            }
                        }

                        if (!seatFound && !seatAlreadyBooked) {
                            System.out.println("Invalid seat number. Seat not found.");
                        }

                        ticketingSystem.displayBookingItems();
                        break;

                    case 3: // Display Bus Layout
                        System.out.println("Bus Layout:");
                        for (Seat seat : busSeats) {
                            System.out.print(seat.getStatus() + " ");
                        }
                        System.out.println();
                        break;

                    case 4: // Edit Passenger Information
                        System.out.print("Enter passenger ID to edit: ");
                        int editPassengerId = scanner.nextInt();
                        scanner.nextLine();

                        Passenger passengerToEdit = null;
                        for (Passenger passenger : passengers) {
                            if (passenger.getIdPassenger() == editPassengerId) {
                                passengerToEdit = passenger;
                                break;
                            }
                        }

                        if (passengerToEdit != null) {
                            System.out.print("Enter new passenger name: ");
                            String newName = scanner.nextLine();

                            System.out.print("Enter new passenger address: ");
                            String newAddress = scanner.nextLine();

                            System.out.print("Enter new seat number: ");
                            String newSeatNumber = scanner.nextLine();

                            String previousSeat = passengerToEdit.getSeatNo();
                            for (Seat seat : busSeats) {
                                if (seat.getSeatNumber().equals("[" + previousSeat + " - Booked]")) {
                                    seat.setStatus("[" + previousSeat + " - Available]");
                                    break;
                                }
                                if (seat.getSeatNumber().equals("[" + previousSeat + "]")) {
                                    if (seat.getStatus().equals("[" + previousSeat + " - Booked]")) {
                                        seat.setStatus("[" + previousSeat + " - Available]");
                                        break;
                                    }
                                }
                            }

                            passengerToEdit.setName(newName);
                            passengerToEdit.setAddress(newAddress);
                            passengerToEdit.setSeatNo(newSeatNumber);

                            for (Seat seat : busSeats) {
                                if (seat.getSeatNumber().equals("[" + newSeatNumber + "]")) {
                                    seat.setStatus("[" + newSeatNumber + " - Booked]");
                                    break;
                                }
                            }

                            System.out.println("Passenger information updated successfully.");
                            ticketingSystem.displayBookingItems();
                        } else {
                            System.out.println("Passenger not found.");
                        }
                        break;

                    case 5: // Cancel Booking
                        System.out.print("Enter passenger ID to cancel booking: ");
                        int cancelPassengerId = scanner.nextInt();
                        scanner.nextLine();

                        Passenger passengerToCancel = null;
                        for (Passenger passenger : passengers) {
                            if (passenger.getIdPassenger() == cancelPassengerId) {
                                passengerToCancel = passenger;
                                break;
                            }
                        }

                        if (passengerToCancel != null) {
                            String seatToFree = passengerToCancel.getSeatNo();
                            boolean foundSeat = false;
                            for (Seat seat : busSeats) {
                                if (seat.getSeatNumber().equals("[" + seatToFree + "]")) {
                                    if (seat.getStatus().equals("[" + seatToFree + " - Booked]")) {
                                        seat.setStatus("[" + seatToFree + " - Available]");
                                        passengerToCancel.setSeatNo("");
                                        System.out.println("Booking canceled successfully.");
                                    } else {
                                        System.out.println("The seat is not currently booked.");
                                    }
                                    foundSeat = true;
                                    break;
                                }
                            }
                            if (!foundSeat) {
                                System.out.println("Seat not found.");
                            }
                            ticketingSystem.displayBookingItems();
                        } else {
                            System.out.println("Passenger not found.");
                        }
                        break;

                    case 6: // Add Passenger
                        System.out.print("Enter passenger name: ");
                        String passengerNameInput = scanner.nextLine();

                        System.out.print("Enter passenger address: ");
                        String passengerAddressInput = scanner.nextLine();

                        Passenger newPassengerEntry = new Passenger(passengerNameInput, passengerAddressInput);
                        passengers.add(newPassengerEntry);
                        System.out.println("New passenger added successfully.");
                        ticketingSystem.displayBookingItems();
                        break;

                    case 7: // Delete Passenger
                        System.out.print("Enter passenger ID to delete: ");
                        int deletePassengerId = scanner.nextInt();
                        scanner.nextLine();

                        Passenger passengerDelete = null;
                        for (Passenger passenger : passengers) {
                            if (passenger.getIdPassenger() == deletePassengerId) {
                                passengerDelete = passenger;
                                break;
                            }
                        }

                        if (passengerDelete != null) {
                            passengers.remove(passengerDelete);

                            String seatToFree = passengerDelete.getSeatNo();
                            for (Seat seat : busSeats) {
                                if (seat.getSeatNumber().equals("[" + seatToFree + "]")) {
                                    seat.setStatus("[" + seatToFree + "]");
                                    break;
                                }
                            }

                            System.out.println("Passenger deleted successfully.");
                            ticketingSystem.displayBookingItems();
                        } else {
                            System.out.println("Passenger not found.");
                        }
                        break;

                    case 8: // Make Payment for Passenger
                        System.out.print("Enter passenger ID to make payment: ");
                        int paymentPassengerId = scanner.nextInt();
                        scanner.nextLine();

                        Passenger passengerToPay = null;
                        for (Passenger passenger : passengers) {
                            if (passenger.getIdPassenger() == paymentPassengerId) {
                                passengerToPay = passenger;
                                break;
                            }
                        }

                        if (passengerToPay != null) {
                            String seatToPay = passengerToPay.getSeatNo();
                            boolean seatStatus = false;

                            for (Seat seat : busSeats) {
                                if (seat.getSeatNumber().equals("[" + seatToPay + "]")) {
                                    if (seat.getStatus().equals("[" + seatToPay + " - Booked]")) {
                                        seat.setStatus("[" + seatToPay + " - Paid]");
                                        System.out.println(
                                                "Payment made successfully for passenger ID: " + paymentPassengerId);
                                    } else {
                                        System.out.println("Payment cannot be made for this passenger.");
                                    }
                                    seatStatus = true;
                                    break;
                                }
                            }

                            if (!seatStatus) {
                                System.out.println("Invalid seat or payment already processed.");
                            }

                            ticketingSystem.displayBookingItems();
                        } else {
                            System.out.println("Passenger not found.");
                        }
                        break;

                    case 9:
                        System.out.println("Exiting... Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid option.");
                scanner.nextLine(); // Clear input buffer
            }

        } while (choice != 9);
        scanner.close();
    }
}
