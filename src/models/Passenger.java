package src.models;

import src.interfaces.Bookable;

public class Passenger extends Ticket implements Bookable {
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

    public void setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
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
