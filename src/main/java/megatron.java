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

        String[] tasks = new String[100];
        int taskCount = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }
                if (command.equals("list")) {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                    continue;
                }

                tasks[taskCount] = command;
                taskCount++;
                System.out.println("added: " + command);
            }
        }
    }
}
