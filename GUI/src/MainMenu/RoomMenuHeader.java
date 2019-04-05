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
import java.util.Random;

import javafx.scene.image.ImageView;

import javafx.scene.image.Image;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;


import javafx.event.EventHandler;



public class RoomMenuHeader {

    public RoomMenuHeader(GroupContainer root, ConnectionManager connectionManager) {

        /**********DECLARATIONS**********/
        Text t_nickname;
        Text t_rooms;
        Button b_create_room;
        Button b_set_name;
        TextField tf_nickname;

        /**********TEXTS**********/
        t_nickname = new Text();
        t_rooms = new Text();

        //Setting font
        t_nickname.setFont(new Font(14));
        t_rooms.setFont(new Font(14));

        //Setting positions
        t_nickname.setX(320);
        t_nickname.setY(30);
        t_rooms.setX(320);
        t_rooms.setY(90);

        //Setting the text to be added.
        t_nickname.setText("Nickname:");
        t_rooms.setText("Rooms avaiable:");

        //Setting color
        t_nickname.setFill(Color.WHITE);
        t_rooms.setFill(Color.WHITE);

        /**********BUTTONS**********/
        b_create_room = new Button("Create room");
        b_set_name = new Button("Set");
        Random random = new Random();
        tf_nickname = new TextField("Player: " + String.valueOf(random.nextInt(10000)));

        //Setting positions
        b_create_room.setLayoutX(320);
        b_create_room.setLayoutY(360);
        b_set_name.setLayoutX(520);
        b_set_name.setLayoutY(40);
        tf_nickname.setLayoutX(320);
        tf_nickname.setLayoutY(40);

        //Setting style
        b_create_room.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 260px;");
        b_set_name.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
        tf_nickname.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 180px;");

        //Setting handlers
        connectionManager.menuData.nickname = tf_nickname.getText();
        b_create_room.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(tf_nickname.getText() != "") {
                    connectionManager.menuData.nickname = tf_nickname.getText();
                    //TO DO SINGLETON MOVE
                    Move m = new Move(root.menuElements, Move.Dir.LEFT, 300, 5);
                    m.start();
                }
            }
        });
        b_set_name.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(tf_nickname.getText() != "") {
                    connectionManager.menuData.nickname = tf_nickname.getText();
                }
            }
        });
        tf_nickname.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(tf_nickname.getText().contains("Player"))
                    tf_nickname.setText("");
            }
        });

        /**********ROOT OPERATIONS**********/
        root.roomMenuHeader = new Group(t_nickname, t_rooms, b_create_room, b_set_name, tf_nickname);
        root.menuElements.getChildren().add(root.roomMenuHeader);
    }
}