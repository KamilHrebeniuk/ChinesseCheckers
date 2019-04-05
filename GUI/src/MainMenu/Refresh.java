package MainMenu;

import Game.Game;
import Game.MapDisplay;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Button;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kamil on 2017-12-27.
 */
public class Refresh extends Thread {
    Button b_nick[];
    Button b_bot[];
    Button b_close[];
    Button b_kick[];
    Button b_start;
    ConnectionManager connectionManager;
    Game game;

    public Refresh(Button b_nick[], Button b_bot[], Button b_close[], Button b_kick[], ConnectionManager connectionManager, Button start) {
        this.b_nick = b_nick;
        this.b_bot = b_bot;
        this.b_close = b_close;
        this.b_kick = b_kick;
        this.connectionManager = connectionManager;
        this.b_start = start;
    }

    public void makeRefresh() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                for(int i = 0; i < 6; i++){
                    b_nick[i].setText(connectionManager.menuData.players[i]);
                    if(connectionManager.menuData.players[i] != "*" && i > 0){
                        b_kick[i].setLayoutX(795);
                        b_bot[i].setLayoutX(1095);
                        b_close[i].setLayoutX(1135);
                    }
                    if(connectionManager.menuData.players[i] == "*" && i > 0){
                        b_kick[i].setLayoutX(1095);
                        b_bot[i].setLayoutX(795);
                        b_close[i].setLayoutX(835);
                    }

                    if(b_nick[i].getText() == "."){
                        b_nick[i].setText("Slot Removed");
                        b_nick[i].setStyle("-fx-background-color: rgba(200,0,0,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 170px;");
                        b_kick[i].setText("Open");
                    }
                    if(b_nick[i].getText() == "*"){
                        b_nick[i].setText("Waiting...");
                        b_nick[i].setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 170px;");
                    }
                    if(b_nick[i].getText().contains("Bot")){
                        b_nick[i].setStyle("-fx-background-color: rgba(0,100,245,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 170px;");
                    }
                    int amount_of_players = 0;
                    for(int j = 0; j < 6; j++){
                        if(connectionManager.menuData.players[j] != "*" && connectionManager.menuData.players[j] != ".")
                            amount_of_players++;
                    }
                    if(amount_of_players < 2 || amount_of_players == 5 || amount_of_players > 6) {
                        b_start.setStyle("-fx-background-color: rgba(100,100,100,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
                    }
                    else{
                        b_start.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 60px;");
                    }
                }
            }
        });
    }

    public void updateRefresh(Game game){
        this.game = game;
    }

    public void createGame(){
        Game game = new Game(connectionManager);
        updateRefresh(game);
    }

    public void makeMapRefresh(String pos){
        int posX1 = Integer.valueOf(pos.substring(0,2));
        int posY1 = Integer.valueOf(pos.substring(2,4));
        int posX2 = Integer.valueOf(pos.substring(4,6));
        int posY2 = Integer.valueOf(pos.substring(6,8));

        char color = this.game.mapDisplay.pawn[posX1][posY1].getType();
        this.game.mapDisplay.pawn[posX1][posY1].setType('#');
        this.game.mapDisplay.pawn[posX2][posY2].setType(color);
    }
}
