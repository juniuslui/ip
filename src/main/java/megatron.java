import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the Megatron chatbot.
 */
public class megatron {
    public static void main(String[] args) {
        String banner = " __  __ _____ ____    _  _____ ____   ___  _   _\n"
                + "|  \\/  | ____/ ___|  / \\|_   _|  _ \\ / _ \\| \\ | |\n"
                + "| |\\/| |  _|| |  _  / _ \\ | | | |_) | | | |  \\| |\n"
                + "| |  | | |__| |_| |/ ___ \\| | |  _ <| |_| | |\\  |\n"
                + "|_|  |_|_____\\____/_/   \\_\\|_| |_| \\_\\___/|_| \\_|\n";
        System.out.println(banner);
        System.out.println("Hello! I'm megatron.");
        System.out.println("What can I do for you?");

        ArrayList<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                try {
                    if (command.equals("bye")) {
                        System.out.println("Bye. Hope to see you again soon!");
                        break;
                    }
                    if (command.equals("list")) {
                        printTasks(tasks);
                        continue;
                    }
                    if (isCommand(command, "mark")) {
                        int taskIndex = getTaskIndex(command, "mark", tasks.size());
                        tasks.get(taskIndex).markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  [X] " + tasks.get(taskIndex).getDescription());
                        continue;
                    }
                    if (isCommand(command, "unmark")) {
                        int taskIndex = getTaskIndex(command, "unmark", tasks.size());
                        tasks.get(taskIndex).unmark();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  [ ] " + tasks.get(taskIndex).getDescription());
                        continue;
                    }
                    if (isCommand(command, "delete")) {
                        int taskIndex = getTaskIndex(command, "delete", tasks.size());
                        Task deletedTask = tasks.remove(taskIndex);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + deletedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        continue;
                    }

                    tasks.add(createTask(command));
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } catch (MegatronException exception) {
                    System.out.println("Sorry, " + exception.getMessage());
                }
            }
        }
    }

    /**
     * Returns whether a command begins with a complete command keyword.
     *
     * @param command the user input
     * @param keyword the command keyword to check
     * @return whether the input uses the keyword
     */
    private static boolean isCommand(String command, String keyword) {
        return command.equals(keyword) || command.startsWith(keyword + " ");
    }

    /**
     * Creates a task from a valid task-creation command.
     *
     * @param command the user input
     * @return the corresponding task
     * @throws MegatronException if the command is unknown or incomplete
     */
    private static Task createTask(String command) throws MegatronException {
        if (isCommand(command, "todo")) {
            String description = command.substring(4).trim();
            if (description.isEmpty()) {
                throw new MegatronException("a to-do needs a description after 'todo'.");
            }
            return new Todo(description);
        }
        if (isCommand(command, "deadline")) {
            String remainder = command.substring(8).trim();
            int byIndex = remainder.indexOf(" /by ");
            if (byIndex <= 0 || byIndex == remainder.length() - 5) {
                throw new MegatronException("use 'deadline <description> /by <time>'.");
            }
            String description = remainder.substring(0, byIndex).trim();
            String by = remainder.substring(byIndex + 5).trim();
            if (description.isEmpty() || by.isEmpty()) {
                throw new MegatronException("a deadline needs both a description and a time.");
            }
            return new Deadline(description, by);
        }
        if (isCommand(command, "event")) {
            String remainder = command.substring(5).trim();
            int fromIndex = remainder.indexOf(" /from ");
            int toIndex = remainder.indexOf(" /to ");
            if (fromIndex <= 0 || toIndex <= fromIndex + 7 || toIndex == remainder.length() - 4) {
                throw new MegatronException("use 'event <description> /from <start> /to <end>'.");
            }
            String description = remainder.substring(0, fromIndex).trim();
            String from = remainder.substring(fromIndex + 7, toIndex).trim();
            String to = remainder.substring(toIndex + 4).trim();
            if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new MegatronException("an event needs a description, start, and end time.");
            }
            return new Event(description, from, to);
        }
        throw new MegatronException("I don't recognise that command. Try todo, deadline, event, list, mark, delete, or bye.");
    }

    /**
     * Parses and validates the task number supplied to mark or unmark.
     *
     * @param command the complete user input
     * @param action the command keyword
     * @param taskCount the number of stored tasks
     * @return the zero-based task index
     * @throws MegatronException if the task number is missing, invalid, or out of range
     */
    private static int getTaskIndex(String command, String action, int taskCount) throws MegatronException {
        String numberText = command.substring(action.length()).trim();
        if (numberText.isEmpty()) {
            throw new MegatronException("provide a task number after '" + action + "'.");
        }
        try {
            int taskIndex = Integer.parseInt(numberText) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw new MegatronException("task numbers run from 1 to " + taskCount + ".");
            }
            return taskIndex;
        } catch (NumberFormatException exception) {
            throw new MegatronException("the task number after '" + action + "' must be a whole number.");
        }
    }

    /**
     * Prints the stored tasks in list order.
     *
     * @param tasks the stored tasks
     */
    private static void printTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }
}
