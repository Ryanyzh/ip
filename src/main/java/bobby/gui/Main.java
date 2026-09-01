package bobby.gui;

import bobby.Bobby;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Sets up Bobby's JavaFX window and handles user input from the GUI.
 */
public class Main extends Application {
    private static final double WINDOW_WIDTH = 400.0;
    private static final double WINDOW_HEIGHT = 600.0;

    private final Bobby bobby = new Bobby();
    private final Image userImage = new Image(getClass().getResourceAsStream("/images/heh.png"));
    private final Image bobbyImage = new Image(getClass().getResourceAsStream("/images/huh.gif"));

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    /**
     * Starts the JavaFX GUI.
     *
     * @param stage main application window.
     */
    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        dialogContainer.getStyleClass().add("dialog-container");
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);
        scene.getStylesheets().add(getClass().getResource("/view/Main.css").toExternalForm());

        stage.setTitle("Bobby");
        stage.setResizable(false);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setMinWidth(WINDOW_WIDTH);

        mainLayout.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));

        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        dialogContainer.getChildren().add(DialogBox.getBobbyDialog(bobby.getWelcomeMessage(), bobbyImage));
        sendButton.setOnMouseClicked(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the JavaFX GUI.
     *
     * @param args command-line arguments passed to JavaFX.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

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
