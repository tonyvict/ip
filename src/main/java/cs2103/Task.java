package cs2103;

public abstract class Task {

    private final String fullname;
    private boolean status;

    Task(String fullname) {
        this.fullname = fullname;
        this.status = false;
    }

    public void mark() {
        this.status = true;
    }

    public void unmark() {
        this.status = false;
    }

    public boolean isMarked() {
        return this.status;
    }

    public String getName() {
        return this.fullname;
    }

    public String statusIcon() {
        return status ? "X" : " ";
    }

    public abstract String typeIcon();

    @Override
    public String toString() {
        return String.format("[%s][%s]%s", typeIcon(), statusIcon(), fullname);
    }
}



