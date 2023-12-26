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