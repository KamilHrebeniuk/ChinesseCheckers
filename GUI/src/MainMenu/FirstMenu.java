package MainMenu;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.ImageView;

import javafx.scene.image.Image;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;


import javafx.event.EventHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;


public class FirstMenu {

    public FirstMenu(GroupContainer root, ConnectionManager connectionManager) throws FileNotFoundException {

        /**********DECLARATIONS**********/
        Text t_header;
        Text t_IPadres;
        Text t_create;
        Text t_error;
        Button b_connect;
        Button b_create;
        TextField tf_connect;
        Image background;
        ImageView imageView;

        /**********BACKGROUND**********/
        background = new Image(new FileInputStream("GUI\\resources\\MenuBackground.png"));
        imageView = new ImageView(background);
        imageView.setX(0);
        imageView.setY(100);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        /**********TEXTS**********/
        t_header = new Text();
        t_IPadres = new Text();
        t_create = new Text();
        t_error = new Text();

        //Setting font
        t_header.setFont(new Font(25));
        t_IPadres.setFont(new Font(14));
        t_create.setFont(new Font(14));
        t_error.setFont(new Font(20));

        //Setting positions
        t_header.setX(50);
        t_header.setY(40);
        t_IPadres.setX(20);
        t_IPadres.setY(110);
        t_create.setX(20);
        t_create.setY(170);
        t_error.setX(72);
        t_error.setY(80);

        //Setting the text to be added.
        t_header.setText("Chinese Checkers");
        t_IPadres.setText("Join server");
        t_create.setText("Create server");

        //Setting color
        t_header.setFill(Color.WHITE);
        t_IPadres.setFill(Color.WHITE);
        t_create.setFill(Color.WHITE);
        t_error.setFill(Color.RED);

        /**********BUTTONS**********/
        b_connect = new Button("Connect");
        b_create = new Button("Create");
        tf_connect = new TextField();

        //Setting positions
        b_connect.setLayoutX(220);
        b_connect.setLayoutY(120);
        b_create.setLayoutX(20);
        b_create.setLayoutY(180);
        tf_connect.setLayoutX(20);
        tf_connect.setLayoutY(120);

        //Setting style
        b_connect.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
        b_create.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
        tf_connect.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 180px;");

        //Setting handlers
        b_connect.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                int tmp = connectionManager.makeConnection(tf_connect.getText());

                if (tmp == 0) {
                //    connectionManager.test();
                    t_error.setText("");
                    //TO DO SINGLETON MOVE
                    Move m = new Move(root.menuElements, Move.Dir.LEFT, 300, 5);
                    m.start();
                    RoomMenuHeader roomMenuHeader = new RoomMenuHeader(root, connectionManager);
                    RoomMenuSlider roomMenuSlider = new RoomMenuSlider(root, connectionManager);
                    CreateRoomMenu createRoomMenu = new CreateRoomMenu(root, connectionManager);
                }
                else if (tmp == 1) {
                    t_error.setText("Connection failed");
                }
            }
        });
        b_create.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Start the server");
            }
        });

        /**********ROOT OPERATIONS**********/
        root.firstMenu = new Group(t_header, t_IPadres, t_create, t_error, b_connect, b_create, tf_connect);
        root.menuBackground.getChildren().add(imageView);
        root.menuElements.getChildren().add(root.firstMenu);
        root.mainRoot.getChildren().add(root.menuBackground);
        root.mainRoot.getChildren().add(root.menuElements);
    }
}