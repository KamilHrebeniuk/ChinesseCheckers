package Game;

import MainMenu.ConnectionManager;
import MainMenu.Move;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kamil on 2017-12-28.
 */
public class MapDisplay extends Thread {

    public Pawn pawn[][];
    Board board;
    int posX1 = 0;
    int posY1 = 0;
    int posX2 = 0;
    int posY2 = 0;
    char color = '#';
    ConnectionManager connectionManager;
    Button b_turn = new Button("Next Turn");

    MapDisplay(GroupContainer root, ConnectionManager connectionManager){

        board = new Board();
        pawn = new Pawn[19][19];
        this.connectionManager = connectionManager;

        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(board.mainArray[j][i] != '.') {
                    pawn[j][i] = new Pawn();
                    pawn[j][i].setType(board.mainArray[j][i]);
                    pawn[j][i].symbol.setLayoutX(-165 + i * 34 + j * 17);
                    pawn[j][i].symbol.setLayoutY(-15 + j * 34);
                    root.pawns.getChildren().add(pawn[j][i].symbol);
                }
            }
        }
        b_turn.setLayoutX(480);
        b_turn.setLayoutY(520);
        //Setting style
        b_turn.setStyle("-fx-background-color: rgba(200,200,200,0.6); -fx-text-fill: rgba(0,0,0,0.8); -fx-pref-width: 100px; -fx-pref-height: 60px;");
        //Setting handlers
        b_turn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                connectionManager.nextTurn();
            }
        });

        /**********ROOT OPERATIONS**********/
        root.mainRoot.getChildren().add(b_turn);
        root.mainRoot.getChildren().add(root.board);
        root.mainRoot.getChildren().add(root.pawns);
    }

    public void run(){
        try {
            while(connectionManager.getColor() == '#' || Character.getNumericValue(connectionManager.getColor()) == -1){
                connectionManager.requestColor();
                this.sleep(100);
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        final char color2 = connectionManager.getColor();
        System.out.println("MAM:" + color2 + "X");
        System.out.println(Character.getNumericValue(color2));
        this.connectionManager.waiter = true;
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                final int counterX = i;
                final int counterY = j;
                if(board.mainArray[j][i] != '.') {
                    pawn[j][i].symbol.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            if(posX1 == 0 && posY1 == 0 && pawn[counterY][counterX].getType() == color2){
                                posX1 = counterX;
                                posY1 = counterY;
                                color = pawn[counterY][counterX].getType();
                                System.out.println("TO JEST: " + color2);
                            }
                            else{
                                System.out.println(String.valueOf(posX1) + " " + String.valueOf(posY1) + " " + String.valueOf(counterX) + " " + String.valueOf(counterY));
                                color = '#';
                                String output = "";

                                if(String.valueOf(posY1).length() < 2){
                                    output = "0" + String.valueOf(posY1);
                                }
                                else{
                                    output = String.valueOf(posY1);
                                }
                                if(String.valueOf(posX1).length() < 2){
                                    output += "0" + String.valueOf(posX1);
                                }
                                else{
                                    output += String.valueOf(posX1);
                                }
                                if(String.valueOf(counterY).length() < 2){
                                    output += "0" + String.valueOf(counterY);
                                }
                                else{
                                    output += String.valueOf(counterY);
                                }
                                if(String.valueOf(counterX).length() < 2){
                                    output += "0" + String.valueOf(counterX);
                                }
                                else{
                                    output += String.valueOf(counterX);
                                }

                                posX1 = 0;
                                posY1 = 0;

                                connectionManager.makeMove(output);
                            }
                        }
                    });
                }
            }
        }
    }
}
