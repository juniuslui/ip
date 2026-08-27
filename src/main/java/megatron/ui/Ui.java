package megatron.ui;

import megatron.task.Task;
import java.util.ArrayList;

/** Handles messages displayed to the user. */
public class Ui {
    /** Displays the application greeting. */
    public void showWelcome() {
        System.out.println("Hello! I'm megatron.");
        System.out.println("What can I do for you?");
    }

    /** Displays the tasks in list order. */
    public void showTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Displays an error message. */
    public void showError(String message) {
        System.out.println("Sorry, " + message);
    }

    /** Displays the exit message. */
    public void showGoodbye() { System.out.println("Bye. Hope to see you again soon!"); }
    /** Displays a successful addition. */
    public void showAdded(Task task, int count) {
        System.out.println("Got it. I've added this task:\n  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }
    /** Displays a successful deletion. */
    public void showDeleted(Task task, int count) {
        System.out.println("Noted. I've removed this task:\n  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }
    /** Displays a successful mark or unmark operation. */
    public void showMarked(Task task, boolean marked) {
        System.out.println(marked ? "Nice! I've marked this task as done:" : "OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }
}
