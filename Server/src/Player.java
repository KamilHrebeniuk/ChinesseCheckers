import java.io.DataInputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by Damian Borek on 27/11/2017.
 */
public interface Player {
    public void move();
    public String getName_();
    public DataInputStream getInput();
    public PrintStream getOutput();
    public char getMark();
    public char getOppositeMark();
    public void setMark(char mark);
    public void setOppositeMark(char mark);
    public ArrayList<Piece> getPieces();
    public boolean getWin();
    public void setWin(boolean win);
}
