package src.models;

public class Seat extends Ticket {
    private String seatNumber;

    public Seat(String seatNumber, String status) {
        super(seatNumber, status);
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}