import java.util.ArrayList;
import java.util.Scanner;

class Seat {
    private String seatNumber;
    private String status;

    public Seat(String seatNumber, String status) {
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class Passenger {
    private static int passengerIdCounter = 1;
    private int idPassenger;
    private String name;
    private String address;
    private String seatNo;

    public Passenger(String name, String address) {
        this.idPassenger = passengerIdCounter++;
        this.name = name;
        this.address = address;
        this.seatNo = "";
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

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

public class BusTicketingSystem {
    private static ArrayList<Passenger> passengers;
    private static ArrayList<Seat> busSeats;

    public BusTicketingSystem() {
        passengers = new ArrayList<>();
        busSeats = new ArrayList<>();
        generateBusLayout();
        displayBusDetails(); // Display bus company details
    }

    private void displayBusDetails() {
        System.out.println("Bus Company Details:");
        System.out.println("--------------------");
        System.out.println("Bus name: Mayang Sari");
        System.out.println("Route: KL to Johor");
        System.out.println("Departure time: 12.45 PM");
        System.out.println("Arrival time: 4.00 PM");
        System.out.println("Price: RM75\n");
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
            String status = "[" + seatNumber + "]";
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
        Passenger passenger1 = new Passenger("Ahmad bin Abdullah", "Kuala Lumpur, Malaysia");
        Passenger passenger2 = new Passenger("Siti Aishah binti Lim", "Penang, Malaysia");

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
        int choice;

        do {
            System.out.println("\nBus Ticketing System Menu:");
            System.out.println("1. Display Booking Items");
            System.out.println("2. Book Seat for Passenger");
            System.out.println("3. Display Bus Layout");
            System.out.println("4. Edit Passenger Information");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Add Passenger");
            System.out.println("7. Delete Passenger");
            System.out.println("8. Exit\n");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ticketingSystem.displayBookingItems();
                    break;

                case 2:
                    System.out.print("Enter passenger ID: ");
                    int passengerIdToBook = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter seat number to book (e.g., A1, B2, C3): ");
                    String seatToBook = scanner.nextLine();

                    boolean seatAlreadyBooked = false;
                    boolean seatFound = false;
                    for (Seat seat : busSeats) {
                        if (seat.getSeatNumber().equals("[" + seatToBook + " - Booked]")) {
                            seatAlreadyBooked = true;
                            break;
                        } else if (seat.getSeatNumber().equals("[" + seatToBook + "]")) {
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
                        }
                    }

                    if (seatAlreadyBooked) {
                        System.out.println("Seat " + seatToBook + " is already booked.");
                    } else if (!seatFound) {
                        System.out.println("Invalid seat number. Seat not found.");
                    }

                    ticketingSystem.displayBookingItems();
                    break;

                case 3:
                    System.out.println("Bus Layout:");
                    for (Seat seat : busSeats) {
                        System.out.print(seat.getStatus() + " ");
                    }
                    System.out.println();
                    break;

                case 4:
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
                            if (seat.getSeatNumber().equals("[" + previousSeat + "]")) {
                                seat.setStatus("[" + previousSeat + "]");
                                break;
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

                        // Update display after changing seat
                        ticketingSystem.displayBookingItems();
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;

                case 5:
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
                        for (Seat seat : busSeats) {
                            if (seat.getSeatNumber().equals("[" + seatToFree + "]")) {
                                seat.setStatus("[" + seatToFree + "]");
                                // Clear the seat number of the canceled booking
                                passengerToCancel.setSeatNo("");
                                System.out.println("Booking canceled successfully.");
                                ticketingSystem.displayBookingItems(); // Update display after canceling booking
                                break;
                            }
                        }
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;

                case 6:
                    System.out.print("Enter passenger name: ");
                    String passengerNameInput = scanner.nextLine();

                    System.out.print("Enter passenger address: ");
                    String passengerAddressInput = scanner.nextLine();

                    Passenger newPassengerEntry = new Passenger(passengerNameInput, passengerAddressInput);
                    passengers.add(newPassengerEntry);
                    System.out.println("New passenger added successfully.");
                    ticketingSystem.displayBookingItems();
                    break;

                case 7:
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

                case 8:
                    System.out.println("Exiting... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }

        } while (choice != 8);

        scanner.close();
    }
}
