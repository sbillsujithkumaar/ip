package leo;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    // Constants for magic numbers
    private static final String BYE_COMMAND = "bye";
    private static final int EXIT_DELAY_MILLIS = 300;
    
    // Image paths
    private static final String USER_IMAGE_PATH = "/images/DaUser.png";
    private static final String DUKE_IMAGE_PATH = "/images/DaDuke.png";
    
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Leo leo;

    private Image userImage = new Image(this.getClass().getResourceAsStream(USER_IMAGE_PATH));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream(DUKE_IMAGE_PATH));

    /**
     * Initializes the GUI components after FXML loading.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Leo instance and shows the welcome message.
     *
     * @param leoInstance the Leo instance to inject
     */
    public void setLeo(Leo leoInstance) {
        this.leo = leoInstance;
        String welcomeMessage = leo.getWelcomeMessage();
        DialogBox welcomeDialog = DialogBox.getLeoDialog(welcomeMessage, dukeImage);
        dialogContainer.getChildren().add(welcomeDialog);
    }

    /**
     * Handles user input by creating dialog boxes and processing the response.
     */
    @FXML
    private void handleUserInput() {
        String userInputText = userInput.getText();
        userInput.clear();

        String leoResponse = leo.getResponse(userInputText);
        
        DialogBox userDialog = DialogBox.getUserDialog(userInputText, userImage);
        DialogBox leoDialog = DialogBox.getLeoDialog(leoResponse, dukeImage);
        
        dialogContainer.getChildren().addAll(userDialog, leoDialog);

        String trimmedInput = userInputText.trim();
        boolean isExitCommand = trimmedInput.equalsIgnoreCase(BYE_COMMAND);
        
        if (isExitCommand) {
            PauseTransition exitDelay = new PauseTransition(Duration.millis(EXIT_DELAY_MILLIS));
            exitDelay.setOnFinished(e -> Platform.exit());
            exitDelay.play();
        }
    }
}
