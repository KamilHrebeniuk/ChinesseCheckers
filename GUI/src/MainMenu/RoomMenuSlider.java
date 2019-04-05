package MainMenu;

import com.sun.deploy.util.StringUtils;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.ImageView;

import javafx.scene.image.Image;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;


import javafx.event.EventHandler;

/**
 * Created by Kamil on 2017-12-27.
 */

public class RoomMenuSlider {
    public RoomMenuSlider(GroupContainer root, ConnectionManager connectionManager) {

        /**********DECLARATIONS**********/
        Button b_rooms[];
        Button l_rooms[];
        int amount;

        /**********BUTTONS**********/
        String tmp = connectionManager.getRooms();
        amount = tmp.split("\\*",-1).length-1;
        if(amount > 8)
            amount = 8;

        b_rooms = new Button[amount];
        l_rooms = new Button[amount];

        String[] list = tmp.split("\\*",-1);



        //Set texts
        for(int i = 0; i < amount; i++){
            String[] list_tmp = list[i].split("\\|", -1);
            int in_game = Integer.valueOf(list_tmp[2]) - ((list[i].split("\\|",-1).length - 3));
            b_rooms[i] = new Button(list_tmp[0]);
            b_rooms[i].setLayoutX(320);
            b_rooms[i].setLayoutY(100 + i * 30);
            b_rooms[i].setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 220px;");

            l_rooms[i] = new Button(String.valueOf(Integer.valueOf(list_tmp[1]) + Integer.valueOf(list_tmp[2])) + "/6");
            l_rooms[i].setLayoutX(540);
            l_rooms[i].setLayoutY(100 + i * 30);
            l_rooms[i].setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 40px;");
        }

        //Setting handlers
        for(int i = 0; i < amount; i++) {
            final int counter = i;
            final int amo = amount;
            b_rooms[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    connectionManager.changeName(connectionManager.menuData.nickname);
                    connectionManager.joinRoom(b_rooms[counter].getText());
                    connectionManager.start();
                    JoinRoomMenu joinRoomMenu = new JoinRoomMenu(root, connectionManager, list[counter]);
                    //TO DO SINGLETON MOVE
                    Move m = new Move(root.menuElements, Move.Dir.UP, 400, 5);
                    m.start();
                }
            });
        }

        /**********ROOT OPERATIONS**********/
        root.roomMenuSlider = new Group();
        for(int i = 0; i < amount; i++) {
            root.roomMenuSlider.getChildren().add(b_rooms[i]);
            root.roomMenuSlider.getChildren().add(l_rooms[i]);
        }
        root.menuElements.getChildren().add(root.roomMenuSlider);

    }
}
