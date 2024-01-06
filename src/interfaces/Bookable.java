package src.interfaces;

public interface Bookable {
    boolean bookTicket(String ticketNumber);

    boolean cancelBooking(String ticketNumber);

    boolean makePayment();
}
