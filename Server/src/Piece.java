/**
 * Created by Damian Borek on 08/12/2017.
 */
public class Piece {
    int x;
    int y;
    boolean done;
    boolean inZone;


    Piece(int x,int y)
    {
        this.x = x;
        this.y = y;
        this.done = false;
        this.inZone = false;
    }

    void setX(int x)
    {
        this.x = x;
    }

    void setY(int y)
    {
        this.y = y;
    }

    Piece getByPosition(int x, int y)
    {
        if (this.x == x && this.y ==y)
            return this;
        else return null;
    }

    int getX()
    {
        return this.x;
    }

    int getY()
    {
        return this.y;
    }

    boolean getDone()
    {
        return this.done;
    }

    void setDone(boolean done)
    {
        this.done = done;
    }

    boolean getInZone()
    {
        return this.inZone;
    }

    void setInZone(boolean inZone)
    {
        this.inZone=inZone;
    }
}
