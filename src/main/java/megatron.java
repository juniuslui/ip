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

        Task[] tasks = new Task[100];
        int taskCount = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }
                if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                    continue;
                }
                if (command.startsWith("mark ")) {
                    int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                    tasks[taskIndex].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  [X] " + tasks[taskIndex].getDescription());
                    continue;
                }
                if (command.startsWith("unmark ")) {
                    int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                    tasks[taskIndex].unmark();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  [ ] " + tasks[taskIndex].getDescription());
                    continue;
                }

                if (command.startsWith("todo ")) {
                    tasks[taskCount] = new Todo(command.substring(5));
                } else if (command.startsWith("deadline ")) {
                    int byIndex = command.indexOf(" /by ");
                    String description = command.substring(9, byIndex);
                    String by = command.substring(byIndex + 5);
                    tasks[taskCount] = new Deadline(description, by);
                } else if (command.startsWith("event ")) {
                    int fromIndex = command.indexOf(" /from ");
                    int toIndex = command.indexOf(" /to ");
                    String description = command.substring(6, fromIndex);
                    String from = command.substring(fromIndex + 7, toIndex);
                    String to = command.substring(toIndex + 5);
                    tasks[taskCount] = new Event(description, from, to);
                } else {
                    tasks[taskCount] = new Todo(command);
                }
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            }
        }
    }
}
