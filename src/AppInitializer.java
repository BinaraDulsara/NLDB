import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
       // primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/LoginForm.fxml"))));
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("views/LoginForm.fxml"))));
        primaryStage.setTitle("Farm system v.1.0  ");
        primaryStage.centerOnScreen();
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
