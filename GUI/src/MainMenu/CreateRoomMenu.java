package MainMenu;

import Game.Game;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by Kamil on 2017-12-27.
 */
public class CreateRoomMenu {
    /**********DECLARATIONS**********/
    private Text t_players;
    private Button b_start;
    private Button b_bot[];
    private Button b_close[];
    private Button b_state[];
    private Button b_kick[];

    CreateRoomMenu(GroupContainer root, ConnectionManager connectionManager){

        /**********DECLARATIONS**********/
        Text t_room_name;
        Button b_create;
        Button b_exit;
        TextField tf_room_name;

        /**********TEXTS**********/
        t_room_name = new Text();
        t_players = new Text();

        //Setting font
        t_room_name.setFont(new Font(14));
        t_players.setFont(new Font(14));

        //Setting positions
        t_room_name.setX(620);
        t_room_name.setY(30);
        t_players.setX(920);
        t_players.setY(90);

        //Setting the text to be added.
        t_room_name.setText("Room name:");
        t_players.setText("Players:");

        //Setting color
        t_room_name.setFill(Color.WHITE);
        t_players.setFill(Color.WHITE);

        /**********BUTTONS**********/
        b_create = new Button("Create");
        b_start = new Button("Start");
        b_exit = new Button("Exit");
        b_bot = new Button[6];
        b_close = new Button[6];
        b_state = new Button[6];
        b_kick = new Button[6];
        tf_room_name = new TextField();

        //Set texts
        for(int i = 0; i < 6; i++){
            b_state[i] = new Button("Waiting...");
            b_bot[i] = new Button("Bot");
            b_close[i] = new Button("Close");
            b_kick[i] = new Button("Kick");
        }

        //Setting positions
        b_create.setLayoutX(820);
        b_create.setLayoutY(40);
        b_start.setLayoutX(1120);
        b_start.setLayoutY(360);
        b_exit.setLayoutX(620);
        b_exit.setLayoutY(360);
        for(int i = 0; i < 6; i++){
            b_state[i].setLayoutX(920);
            b_state[i].setLayoutY(100 + i * 30);
            b_bot[i].setLayoutX(1095);
            b_bot[i].setLayoutY(100 + i * 30);
            b_close[i].setLayoutX(1135);
            b_close[i].setLayoutY(100 + i * 30);
            b_kick[i].setLayoutX(1095);
            b_kick[i].setLayoutY(100 + i * 30);
        }
        tf_room_name.setLayoutX(620);
        tf_room_name.setLayoutY(40);

        //Setting style
        b_create.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
        b_start.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
        b_exit.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
        for(int i = 0; i < 6; i++){
            b_state[i].setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 170px;");
            b_bot[i].setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 35px;");
            b_close[i].setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 45px;");
            b_kick[i].setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 85px;");
        }
        tf_room_name.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 180px;");
        //Setting handlers
        Refresh refresh = new Refresh(b_state, b_bot, b_close, b_kick, connectionManager, b_start);
        b_create.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(tf_room_name.getText().length() > 1) {
                    connectionManager.createRoom(tf_room_name.getText());
                    connectionManager.changeName(connectionManager.menuData.nickname);
                    connectionManager.getRooms();
                    connectionManager.joinRoom(tf_room_name.getText());



                    connectionManager.setRefresh(refresh);

                    createRoom();
                    connectionManager.start();
                }
            }
        });
        b_start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int amount_of_players = 0;
                for(int i = 0; i < 6; i++){
                    if(!(connectionManager.menuData.players[i] == "*") || !(connectionManager.menuData.players[i] == "."))
                        amount_of_players++;
                }
                if(amount_of_players > 1 && amount_of_players != 5 && amount_of_players < 7) {
                    Game game = new Game(connectionManager);
                    refresh.updateRefresh(game);
                    connectionManager.startGame();
                }
            }
        });
        b_exit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //TO DO SINGLETON MOVE
                Move m = new Move(root.menuElements, Move.Dir.RIGHT, 300, 5);
                m.start();
            }
        });
        for(int i = 1; i < 6; i++){
            final int counter = i;
            b_bot[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    connectionManager.menuData.place = counter;
                    connectionManager.addBot();
                /*    if (connectionManager.menuData.players[counter] != "*") {
                        b_kick[counter].setLayoutX(1095);
                        b_bot[counter].setLayoutX(1395);
                        b_close[counter].setLayoutX(1435);
                    }*/
                }
            });

            b_close[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    connectionManager.menuData.place = counter;
                    connectionManager.closeSlot();
                 /*   if (connectionManager.menuData.players[counter] == "*") {
                        b_kick[counter].setLayoutX(1095);
                        b_bot[counter].setLayoutX(1395);
                        b_close[counter].setLayoutX(1435);
                    }*/
                }
            });

            b_kick[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if(b_state[counter].getText().equals("Slot Removed")){
                        connectionManager.menuData.place = counter;
                        connectionManager.addSlot();
                    }
                    else {
                        connectionManager.kickPlayer(b_state[counter].getText());
                    }
                }
            });
        }


        /**********ROOT OPERATIONS**********/
        root.createRoomMenu = new Group(t_room_name, t_players, b_create, b_start, b_exit, tf_room_name,
                b_state[0], b_state[1], b_state[2], b_state[3], b_state[4], b_state[5],
                b_bot[1], b_bot[2], b_bot[3], b_bot[4], b_bot[5],
                b_close[1], b_close[2], b_close[3], b_close[4], b_close[5],
                b_kick[0], b_kick[1], b_kick[2], b_kick[3], b_kick[4], b_kick[5]);
        root.menuElements.getChildren().add(root.createRoomMenu);
    }

    private void createRoom(){
        t_players.setLayoutX(620);
        b_start.setLayoutX(820);
        for(int i = 0; i < 6; i++){
            b_state[i].setLayoutX(620);
            b_bot[i].setLayoutX(795);
            b_close[i].setLayoutX(835);
        }
    }
}
