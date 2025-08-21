package cs2103;

public class Deadline extends Task {


    public Deadline(String fullname) {
        super(fullname);
    }

    @Override
    public String typeIcon() {
        return "D";
    }
}

