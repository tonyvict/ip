package cs2103;

public class Deadline extends Task {

    private final String by;


    public Deadline(String fullname, String by) {
        super(fullname);
        this.by =by;
    }

    @Override
    public String typeIcon() {
        return "D";
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (by: %s)", typeIcon(), statusIcon(), this.getName(), by);
    }
}

