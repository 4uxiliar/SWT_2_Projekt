package model;

public class Account {
    private final long id;
    private String email;
    private Ticket[] tickets;


    public Account(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Ticket[] getTickets() {
        return tickets;
    }


}
