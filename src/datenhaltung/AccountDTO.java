package datenhaltung;

public class AccountDTO {
    private final long id;
    private String email;
    private TicketDTO[] tickets;


    public AccountDTO(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTickets(TicketDTO[] tickets) {
        this.tickets = tickets;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public TicketDTO[] getTickets() {
        return tickets;
    }
}
