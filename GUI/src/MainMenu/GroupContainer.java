package MainMenu;

import javafx.scene.Group;

/**
 * Created by Kamil on 2017-12-27.
 */
public class GroupContainer {
    Group mainRoot;
    Group menuElements;
    Group menuBackground;
    Group firstMenu;
    Group roomMenuHeader;
    Group roomMenuSlider;
    Group createRoomMenu;
    Group joinRoomMenu;

    GroupContainer(){
        mainRoot = new Group();
        menuElements = new Group();
        menuBackground = new Group();
        firstMenu = new Group();
        roomMenuHeader = new Group();
        roomMenuSlider = new Group();
        createRoomMenu = new Group();
        joinRoomMenu = new Group();
    }
}
