package cs2103;

class Todo extends Task {


    public Todo(String fullname) {
        super(fullname);
    }

    @Override
    public String typeIcon() {
        return "T";
    }
}
