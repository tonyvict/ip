package cs2103;

class Event extends Task {


    public Event(String fullname) {
        super(fullname);
    }

    @Override
    public String typeIcon() {
        return "E";
    }
}