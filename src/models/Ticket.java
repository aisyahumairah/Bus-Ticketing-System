package src.models;

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
