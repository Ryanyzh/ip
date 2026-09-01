package bobby.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Shows one chat message with an avatar.
 */
public class DialogBox extends HBox {
    private static final double AVATAR_SIZE = 80.0;

    private final Label text;
    private final ImageView displayPicture;

    /**
     * Creates a dialog box with the given text and display picture.
     *
     * @param text message to show.
     * @param image avatar image to show next to the message.
     */
    public DialogBox(String text, Image image) {
        this.text = new Label(text);
        displayPicture = new ImageView(image);

        this.text.setWrapText(true);
        displayPicture.setFitWidth(AVATAR_SIZE);
        displayPicture.setFitHeight(AVATAR_SIZE);
        setAlignment(Pos.TOP_RIGHT);
        getChildren().addAll(this.text, displayPicture);
    }

    /**
     * Returns a dialog box for the user's message.
     *
     * @param text message entered by the user.
     * @param image user's avatar image.
     * @return user dialog box.
     */
    public static DialogBox getUserDialog(String text, Image image) {
        DialogBox dialogBox = new DialogBox(text, image);
        dialogBox.getStyleClass().add("user-dialog");
        return dialogBox;
    }

    /**
     * Returns a dialog box for Bobby's response.
     *
     * @param text response from Bobby.
     * @param image Bobby's avatar image.
     * @return Bobby dialog box.
     */
    public static DialogBox getBobbyDialog(String text, Image image) {
        DialogBox dialogBox = new DialogBox(text, image);
        dialogBox.flip();
        dialogBox.getStyleClass().add("bobby-dialog");
        return dialogBox;
    }

    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> nodes = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(nodes);
        getChildren().setAll(nodes);
    }
}
