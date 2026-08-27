/** Converts user commands into command types and tasks. */
public class Parser {
    /** Parses a complete user input into an executable command. */
    public Command parse(String input) throws MegatronException {
        CommandType type = getCommandType(input);
        if (type == CommandType.BYE) return new ExitCommand();
        if (type == CommandType.LIST) return new ListCommand();
        if (type == CommandType.TODO || type == CommandType.DEADLINE || type == CommandType.EVENT) {
            return new AddCommand(input, type);
        }
        if (type == CommandType.MARK || type == CommandType.UNMARK) {
            return new MarkCommand(input, type == CommandType.MARK);
        }
        if (type == CommandType.DELETE) return new DeleteCommand(input);
        throw new MegatronException("I don't recognise that command. Try todo, deadline, event, list, mark, delete, or bye.");
    }
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
