package cs2103;

public class Event extends Task {

    private final String from;
    private final String to;



    public Event(String fullname, String from, String to) {
        super(fullname);
        this.from = from;
        this.to = to;
    }

    @Override
    public String typeIcon() {
        return "E";
    }

    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)",
                typeIcon(), statusIcon(), getName(), from, to);
    }
}

