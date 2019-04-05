package Game;

import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Created by Kamil on 2017-12-28.
 */
public class Pawn {
    char type = '.';
    Button symbol = new Button();

    Pawn(){
    //    symbol.setText("" + type);
    }

    public char getType(){
        return type;
    }

    public void setType (char type){
        this.type = type;
    //    symbol.setText("" + type);
        String tmp = "-fx-background-radius: 5em; " +
        "-fx-min-width: 30px; " +
                "-fx-min-height: 30px; " +
                "-fx-max-width: 30px; " +
                "-fx-max-height: 30px; -fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"
                ;

        switch (type){
            case '#' : tmp += "-fx-background-color: rgba(255,255,255,0.8);"; break;
            case 'Y' : tmp += "-fx-background-color: rgba(255,255,0,0.8);"; break;
            case 'L' : tmp += "-fx-background-color: rgba(0,255,255,0.8);"; break;
            case 'G' : tmp += "-fx-background-color: rgba(0,255,0,0.8);"; break;
            case 'B' : tmp += "-fx-background-color: rgba(0,0,255,0.8);"; break;
            case 'P' : tmp += "-fx-background-color: rgba(255,0,255,0.8);"; break;
            case 'R' : tmp += "-fx-background-color: rgba(255,0,0,0.8);"; break;
        }

        symbol.setStyle(tmp);
    }
}
