package megatron.command;

import megatron.Megatron;
import megatron.exception.MegatronException;
import megatron.parser.CommandType;
import megatron.storage.Storage;
import megatron.task.Task;
import megatron.task.TaskList;
import megatron.ui.Ui;
/** An action produced by parsing one user command. */
public abstract class Command {
    public static Command exit() { return new ExitCommand(); }
    public static Command list() { return new ListCommand(); }
    public static Command add(String input, CommandType type) { return new AddCommand(input, type); }
    public static Command mark(String input, boolean mark) { return new MarkCommand(input, mark); }
    public static Command delete(String input) { return new DeleteCommand(input); }
    /** Executes this action. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws MegatronException;
    /** Returns whether this action ends the application. */
    public boolean isExit() { return false; }
}

class ExitCommand extends Command {
    public void execute(TaskList tasks, Ui ui, Storage storage) { ui.showGoodbye(); }
    public boolean isExit() { return true; }
}

class ListCommand extends Command {
    public void execute(TaskList tasks, Ui ui, Storage storage) { ui.showTasks(tasks.asList()); }
}

class AddCommand extends Command {
    private final String input;
    private final CommandType type;
    AddCommand(String input, CommandType type) { this.input = input; this.type = type; }
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MegatronException {
        Task task = Megatron.createTaskForCommand(input, type);
        tasks.add(task);
        ui.showAdded(task, tasks.size());
        storage.save(tasks.asList());
    }
}

class MarkCommand extends Command {
    private final String input;
    private final boolean mark;
    MarkCommand(String input, boolean mark) { this.input = input; this.mark = mark; }
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MegatronException {
        int index = Megatron.taskIndexForCommand(input, mark ? "mark" : "unmark", tasks.size());
        if (mark) {
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).unmark();
        }
        ui.showMarked(tasks.get(index), mark);
        storage.save(tasks.asList());
    }
}

class DeleteCommand extends Command {
    private final String input;
    DeleteCommand(String input) { this.input = input; }
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MegatronException {
        int index = Megatron.taskIndexForCommand(input, "delete", tasks.size());
        Task deleted = tasks.delete(index);
        ui.showDeleted(deleted, tasks.size());
        storage.save(tasks.asList());
    }
}

