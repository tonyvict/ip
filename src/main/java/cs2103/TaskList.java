package cs2103;

public class TaskList {
    private Task[] tasks;
    private int size;
    private static final int MAX_TASKS = 100;

    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.size = 0;
    }

    public TaskList(Task[] tasks, int size) {
        this.tasks = tasks;
        this.size = size;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void markTask(String input, boolean mark) throws TommyException {
        Integer no = Parser.parseNo(input);
        if (no == null || no < 1 || no > size) {
            throw new TommyException("Invalid task number");
        }
        Task t = tasks[no - 1];
        if (mark) {
            t.mark();
        } else {
            t.unmark();
        }
    }

    public Task getTask(int index) {
        if (index >= 0 && index < size) {
            return tasks[index];
        }
        return null;
    }

    public int deleteTask(String input) throws TommyException {
        Integer no = Parser.parseNo(input);
        if (no == null || no < 1 || no > size) {
            throw new TommyException("Invalid task number");
        }
        
        Task deletedTask = tasks[no - 1];
        for (int i = no - 1; i < size - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[size - 1] = null;
        size--;
        
        return size;
    }

    public void addTask(Task task) throws TommyException {
        if (size >= MAX_TASKS) {
            throw new TommyException("Task list is full");
        }
        
        for (int i = 0; i < MAX_TASKS; i++) {
            if (tasks[i] == null) {
                tasks[i] = task;
                size++;
                break;
            }
        }
    }

    public void addTodo(String input) throws TommyException {
        String description = Parser.splitter(input, 1);
        Todo todo = new Todo(description);
        addTask(todo);
    }

    public void addDeadline(String input) throws TommyException {
        String name = Parser.splitter(input, 2);
        String by = Parser.deadlineRaw(Parser.splitter(input, 3));
        Deadline deadline = new Deadline(name, by);
        addTask(deadline);
    }

    public void addEvent(String input) throws TommyException {
        String name = Parser.splitter(input, 2);
        String from = Parser.eventtiming(Parser.splitter(input, 3), 0);
        String to = Parser.eventtiming(Parser.splitter(input, 3), 1);
        Event event = new Event(name, from, to);
        addTask(event);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size >= MAX_TASKS;
    }
}

