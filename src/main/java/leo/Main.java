package leo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A GUI for Leo using FXML.
 */
public class Main extends Application {

    private Leo leo = new Leo("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLeo(leo);  // inject the Duke instance
            stage.show();
        } catch (IOException e) {
            // FXML loading failed - show error dialog and exit gracefully
            System.err.println("Failed to load GUI: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
