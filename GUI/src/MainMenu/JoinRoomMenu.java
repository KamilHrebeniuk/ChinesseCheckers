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
public class JoinRoomMenu {
    /**********DECLARATIONS**********/
    private Text t_players;
    private Button b_start;
    private Button b_create;
    private Button b_bot[];
    private Button b_close[];
    private Button b_state[];
    private Button b_kick[];

    JoinRoomMenu(GroupContainer root, ConnectionManager connectionManager, String list){

        /**********DECLARATIONS**********/
        Text t_room_name;
        Button b_exit;
        TextField tf_room_name;
        String[] list_tmp = list.split("\\|", -1);
        int i_amount = list.split("\\|", -1).length - 3;
        int i_p_amount = 6;

        /**********TEXTS**********/
        t_room_name = new Text();
        t_players = new Text();

        //Setting font
        t_room_name.setFont(new Font(14));
        t_players.setFont(new Font(14));

        //Setting positions
        t_room_name.setX(320);
        t_room_name.setY(430);
        t_players.setX(320);
        t_players.setY(490);

        //Setting the text to be added.
        t_room_name.setText("Room name:");
        t_players.setText("Players:");

        //Setting color
        t_room_name.setFill(Color.WHITE);
        t_players.setFill(Color.WHITE);

        /**********BUTTONS**********/
        b_exit = new Button("Exit");
        b_state = new Button[6];
        tf_room_name = new TextField();

        //Set texts
        for(int i = 0; i < 6; i++){
            b_state[i] = new Button("Waiting...");
        }

        for(int i = 0; i < i_amount; i++){
            b_state[i].setText(list_tmp[i + 3]);
        }

        //Setting positions
        b_exit.setLayoutX(320);
        b_exit.setLayoutY(750);
        for(int i = 0; i < i_p_amount; i++){
            b_state[i].setLayoutX(320);
            b_state[i].setLayoutY(500 + i * 30);
        }
        tf_room_name.setLayoutX(320);
        tf_room_name.setLayoutY(440);

        //Setting style
        b_exit.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
        for(int i = 0; i < i_p_amount; i++){
            b_state[i].setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 170px;");
        }
        tf_room_name.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 180px;");
        //Setting handlers
        Refresh refresh = new Refresh(b_state, b_bot, b_close, b_kick, connectionManager, b_start);
        b_exit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                connectionManager.exitRoom();
                //TO DO SINGLETON MOVE
                Move m = new Move(root.menuElements, Move.Dir.DOWN, 400, 5);
                m.start();
            }
        });

        /**********ROOT OPERATIONS**********/
        root.joinRoomMenu = new Group(t_room_name, t_players, b_exit, tf_room_name,
                b_state[0], b_state[1], b_state[2], b_state[3], b_state[4], b_state[5]);
        root.menuElements.getChildren().add(root.joinRoomMenu);
    }

    private void createRoom(){
        t_players.setLayoutX(620);
        for(int i = 0; i < 6; i++){
            b_state[i].setLayoutX(620);
        }
    }
}
