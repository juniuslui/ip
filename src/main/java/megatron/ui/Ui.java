package megatron.ui;

import java.util.ArrayList;
import java.util.function.Consumer;

import megatron.task.Task;

/** Handles messages displayed to the user. */
public class Ui {
    private final Consumer<String> messageConsumer;

    /** Creates a UI that displays messages in the terminal. */
    public Ui() {
        this(System.out::println);
    }

    /**
     * Creates a UI that sends messages to the supplied display function.
     *
     * @param messageConsumer function that displays one chatbot message
     */
    public Ui(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    /** Displays the application greeting. */
    public void showWelcome() {
        messageConsumer.accept("Hello! I'm Megatron.\nWhat can I do for you?");
    }

    /** Displays the tasks in list order. */
    public void showTasks(ArrayList<Task> tasks) {
        StringBuilder message = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            message.append(System.lineSeparator()).append(i + 1).append('.').append(tasks.get(i));
        }
        messageConsumer.accept(message.toString());
    }

    /** Displays an error message. */
    public void showError(String message) {
        messageConsumer.accept("Sorry, " + message);
    }

    /** Displays the exit message. */
    public void showGoodbye() {
        messageConsumer.accept("Bye. Hope to see you again soon!");
    }
    /** Displays a successful addition. */
    public void showAdded(Task task, int count) {
        messageConsumer.accept("Got it. I've added this task:\n  " + task
                + "\nNow you have " + count + " tasks in the list.");
    }
    /** Displays a successful deletion. */
    public void showDeleted(Task task, int count) {
        messageConsumer.accept("Noted. I've removed this task:\n  " + task
                + "\nNow you have " + count + " tasks in the list.");
    }
    /** Displays a successful mark or unmark operation. */
    public void showMarked(Task task, boolean marked) {
        String message = marked
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:";
        messageConsumer.accept(message + "\n  " + task);
    }
    /** Displays tasks matching a search keyword. */
    public void showMatchingTasks(ArrayList<Task> tasks) {
        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            message.append(System.lineSeparator()).append(i + 1).append('.').append(tasks.get(i));
        }
        messageConsumer.accept(message.toString());
    }
}
