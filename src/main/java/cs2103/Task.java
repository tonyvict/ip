package cs2103;

public class Task {

    private String name;
    private boolean status;

    public Task(String name) {
        this.name = name;
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
        return this.name;
    }

  @Override
    public String toString() {
        if (this.isMarked()) {
            return "[X] " + this.name;
        } else {
            return "[ ] " + this.name;
        }
    }
}



