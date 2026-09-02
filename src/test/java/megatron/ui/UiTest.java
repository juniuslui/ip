package megatron.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import megatron.task.Task;
import megatron.task.Todo;

/** Tests messages emitted by the UI abstraction used by both front ends. */
class UiTest {
    private final List<String> messages = new ArrayList<>();
    private final Ui ui = new Ui(messages::add);

    @Test
    void showWelcome_noArguments_emitsGreeting() {
        ui.showWelcome();

        assertEquals(1, messages.size());
        assertTrue(messages.get(0).contains("Hello! I'm Megatron."));
    }

    @Test
    void showTasks_twoTasks_emitsNumberedList() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write notes"));

        ui.showTasks(tasks);

        assertEquals(1, messages.size());
        assertTrue(messages.get(0).contains("1.[T][ ] read book"));
        assertTrue(messages.get(0).contains("2.[T][ ] write notes"));
    }
}
