package Game;

import javafx.scene.Group;

/**
 * Created by Kamil on 2017-12-28.
 */
public class GroupContainer {
    Group mainRoot;
    Group pawns;
    Group board;
    Group boardBackground;


    GroupContainer(){
        mainRoot = new Group();
        pawns = new Group();
        board = new Group();
        boardBackground = new Group();
    }
}
