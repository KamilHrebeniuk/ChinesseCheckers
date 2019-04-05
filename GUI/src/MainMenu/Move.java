package MainMenu;

import javafx.scene.Group;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kamil on 2017-12-27.
 */
public class Move extends Thread {
    Group myGroup;

    protected enum Dir {LEFT, RIGHT, UP, DOWN;}
    int value = 0;
    int speed = 0;
    Dir dir;
    boolean done = false;

    public Move(Group group, Dir direction, int value, int speed){
        myGroup = group;
        dir = direction;
        this.value = value;
        this.speed = speed;
    }

    public boolean getDone(){
        return done;
    }

    public void run(){
        for(int i = 0; i < 100; i++) {
            try {
                switch (dir) {
                    case RIGHT:
                        myGroup.setLayoutX(myGroup.getLayoutX() + value / 100);
                        break;
                    case LEFT:
                        myGroup.setLayoutX(myGroup.getLayoutX() - value / 100);
                        break;
                    case DOWN:
                        myGroup.setLayoutY(myGroup.getLayoutY() + value / 100);
                        break;
                    case UP:
                        myGroup.setLayoutY(myGroup.getLayoutY() - value / 100);
                        break;
                }
                TimeUnit.MILLISECONDS.sleep(speed);
            }
            catch(InterruptedException f){

            }
        }
        done = true;
    }
}
