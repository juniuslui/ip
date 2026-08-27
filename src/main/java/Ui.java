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
}
