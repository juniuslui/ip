package megatron;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import megatron.command.Command;
import megatron.exception.MegatronException;
import megatron.parser.Parser;
import megatron.storage.Storage;
import megatron.task.TaskList;
import megatron.ui.Ui;

/** Provides the JavaFX front end for the Megatron chatbot. */
public class Main extends Application {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 500;

    private final Parser parser = new Parser();
    private final Storage storage = new Storage(Megatron.FILE_PATH);
    private final TaskList tasks = new TaskList(storage.load());
    private final TextArea conversation = new TextArea();
    private final TextField userInput = new TextField();
    private final Button sendButton = new Button("Send");
    private final Ui ui = new Ui(this::showMegatronMessage);

    @Override
    public void start(Stage stage) {
        configureConversation();
        configureInput();

        HBox inputArea = new HBox(10, userInput, sendButton);
        HBox.setHgrow(userInput, javafx.scene.layout.Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setCenter(conversation);
        root.setBottom(inputArea);
        BorderPane.setMargin(inputArea, new Insets(10, 0, 0, 0));

        stage.setTitle("Megatron");
        stage.setMinWidth(420);
        stage.setMinHeight(320);
        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.show();

        ui.showWelcome();
        userInput.requestFocus();
    }

    private void configureConversation() {
        conversation.setEditable(false);
        conversation.setWrapText(true);
        conversation.setFocusTraversable(false);
    }

    private void configureInput() {
        userInput.setPromptText("Enter a command...");
        userInput.setOnAction(event -> handleUserInput());
        sendButton.setDefaultButton(true);
        sendButton.setOnAction(event -> handleUserInput());
    }

    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        showMessage("You", input);
        userInput.clear();
        try {
            Command command = parser.parse(input);
            command.execute(tasks, ui, storage);
            if (command.isExit()) {
                userInput.setDisable(true);
                sendButton.setDisable(true);
            }
        } catch (MegatronException exception) {
            ui.showError(exception.getMessage());
        }
    }

    private void showMegatronMessage(String message) {
        showMessage("Megatron", message);
    }

    private void showMessage(String speaker, String message) {
        if (!conversation.getText().isEmpty()) {
            conversation.appendText(System.lineSeparator() + System.lineSeparator());
        }
        conversation.appendText(speaker + ":" + System.lineSeparator() + message);
        conversation.positionCaret(conversation.getLength());
    }
}
