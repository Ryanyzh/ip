package bobby.gui;

import bobby.Bobby;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for Bobby's main GUI window.
 */
public class MainWindow extends AnchorPane {
    private final Image userImage = new Image(getClass().getResourceAsStream("/images/heh.png"));
    private final Image bobbyImage = new Image(getClass().getResourceAsStream("/images/huh.gif"));

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Bobby bobby;

    /**
     * Initializes FXML-linked controls after the view has been loaded.
     */
    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));
    }

    /**
     * Injects the Bobby instance that generates chatbot responses.
     *
     * @param bobby Bobby instance to use for command handling.
     */
    public void setBobby(Bobby bobby) {
        this.bobby = bobby;
        dialogContainer.getChildren().add(DialogBox.getBobbyDialog(bobby.getWelcomeMessage(), bobbyImage));
    }

    /**
     * Creates dialog boxes for the user's input and Bobby's response.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText().trim();
        if (userText.isEmpty()) {
            return;
        }

        String bobbyText = bobby.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getBobbyDialog(bobbyText, bobbyImage));
        userInput.clear();

        if (bobby.isExitCommand(userText)) {
            Platform.exit();
        }
    }
}
