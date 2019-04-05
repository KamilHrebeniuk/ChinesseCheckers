import java.io.DataInputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by Damian Borek on 27/11/2017.
 */
public class AIPlayer implements Player {
    char mark;
    char oppositeMark;
    public ArrayList<Piece> pieces;
    String tableName;
    String name;
    boolean win = false;

    AIPlayer(String name, String tableName)
    {
        this.name = name;
        this.tableName = tableName;
    }

    @Override
    public void move() {
        String outcome;
        System.out.println ("[NAME] = " + name + " [MARK] = " + mark + " [ROOM] = " + tableName +  " WYKONUJE RUCH");
        outcome = GamesManager.getInstance().getGameByName(tableName).board.AIMove(pieces, mark);
        update(outcome);
    }

    private void update(String msg)
    {
        String[] splitStr = msg.split("\\s+");
        for(int i=0;i<splitStr.length ; i++)
        {
            for(int j =0 ; j<GamesManager.getInstance().getGameByName(tableName).players.size(); j++)
                if(GamesManager.getInstance().getGameByName(tableName).players.get(j) instanceof HumanPlayer)
                    GamesManager.getInstance().getGameByName(tableName).players.get(j).getOutput().println("U" + splitStr[i]);
        }

    }

    @Override
    public String getName_() {
        return this.name;
    }

    public void setName_(String name) {
        this.name = name;
    }

    @Override
    public DataInputStream getInput() {
        return null;
    }

    @Override
    public PrintStream getOutput() {
        return null;
    }

    @Override
    public char getMark() {
        return this.mark;
    }

    @Override
    public char getOppositeMark() {
        return this.oppositeMark;
    }

    @Override
    public void setMark(char mark) {

        this.mark = mark;
        setPieces();
    }

    @Override
    public void setOppositeMark(char mark) {
        this.oppositeMark = mark;
    }

    public void setPieces() {
        pieces = GamesManager.getInstance().getGameByName(tableName).board.setPieces(mark);
    }

    public ArrayList<Piece> getPieces()
    {
        return this.pieces;
    }

    public String getRoom()
    {
        return this.tableName;
    }

    public void setRoom(String room)
    {
        this.tableName = room;
    }

    @Override
    public boolean getWin()
    {
        return this.win;
    }

    @Override
    public void setWin(boolean win)
    {
        this.win = win;
    }
}
