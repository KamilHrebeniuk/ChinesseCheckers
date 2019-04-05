package MainMenu;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.FileNotFoundException;




public class StageManager extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        //Set stage properties
        stage.setResizable(false);
        stage.setTitle("Chinese Checkers");


        //Create groups container
        //TO DO Singleton
        GroupContainer root = new GroupContainer();


        //Creating a scene object
        Scene mainScene = new Scene(root.mainRoot, 300, 390);
        mainScene.setFill(Color.BLACK);


        //Create menu objects
        ConnectionManager connectionManager = new ConnectionManager();
        FirstMenu firstMenu = new FirstMenu(root, connectionManager);


        //Adding scene to the stage
        stage.setScene(mainScene);
        stage.show();
    }
    public static void launcher() {
        launch();
    }
}