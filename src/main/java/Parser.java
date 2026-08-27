/** Converts user commands into command types and tasks. */
public class Parser {
    public CommandType getCommandType(String command) {
        String[] words = {"todo", "deadline", "event", "mark", "unmark", "delete"};
        CommandType[] types = {CommandType.TODO, CommandType.DEADLINE, CommandType.EVENT,
            CommandType.MARK, CommandType.UNMARK, CommandType.DELETE};
        for (int i = 0; i < words.length; i++) if (command.equals(words[i]) || command.startsWith(words[i] + " ")) return types[i];
        if (command.equals("list")) return CommandType.LIST;
        if (command.equals("bye")) return CommandType.BYE;
        return CommandType.UNKNOWN;
    }
}
