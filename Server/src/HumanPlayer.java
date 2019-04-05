import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.io.DataInputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Damian Borek on 27/11/2017.
 */
public class HumanPlayer extends Thread implements Player {

    private Socket socket;
    String name;
    DataInputStream input = null;
    PrintStream output = null;
    char mark;
    char oppositeMark;
    ArrayList<Piece> pieces;
    boolean onlyJump=false;
    String roomName;
    Piece recentPiece;
    boolean win = false;
    boolean admin = false;

    public HumanPlayer(Socket socket) {
        this.socket = socket;
        this.name = "Default name";
        //room = -1;

        try {
            this.input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            this.output = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public void run() {

        String line = null;

        try {
            while ((line = input.readLine()) != null) {
                System.out.println(line);

                //new room
                if (line.charAt(0) == 'N') {

                    /*int humanPlayers = line.charAt(1) - '0';
                    int AIPlayers = line.charAt(2) - '0';
                    String tableName = line.substring(3);

                    if(AIPlayers + humanPlayers > 6 || AIPlayers + humanPlayers <= 1 || humanPlayers == 0 || AIPlayers + humanPlayers == 5)
                    {
                        displayErrorMsg(output, "Wrong ammount of players!");
                        System.out.println("Wrong ammount of players!");
                        break;
                    }
                    if(tableName.equals(""))
                    {
                        displayErrorMsg(output, "Table Name can't be empty!");
                        System.out.println("Table Name can't be empty!");
                        break;
                    }
                    if(createNewGame(humanPlayers, AIPlayers, tableName) == 0)
                    {
                        displayErrorMsg(output, "Game couldn't be created!");
                        System.out.println("Game couldn't be created!");
                        break;
                    }
                    else
                        System.out.println("NEW GAME CREATED [humanPlayers] = " + humanPlayers + " [AIPlayers] = " + AIPlayers + " [Name] = " + tableName );*/
                    String tableName = line.substring(1);
                    boolean fail = false;
                    for(int i=0;i<GamesManager.getInstance().games.size();i++)
                    {
                        if(GamesManager.getInstance().games.get(i).getTableName().equals(tableName))
                        {
                            fail = true;
                            break;
                        }
                    }
                    if(fail)
                    {
                        displayErrorMsg(output,"Can't create that game!");
                        break;
                    }
                    else {
                        Game game = new Game(tableName);
                        //game.addPlayer(this);
                        admin = true;
                        GamesManager.getInstance().games.add(game);
                        this.roomName = tableName;
                    }

                }
                else if (line.charAt(0) == 'G') {
                    if(admin) {
                        if(startGame(roomName) == 0)
                        {
                            displayErrorMsg(output, "Wrong ammount of players!");
                            System.out.println("Wrong ammount of players!");
                        }
                        else
                        {
                            updateMSG("Game Started");
                        }
                    }
                    else
                        displayErrorMsg(output,"You are not admin!");

                }
                // show games with players
                else if (line.charAt(0) == 'A') {
                    System.out.println("AAAAAA!@#@!@#!@#");
                    sendOutput();
                }
                //add new bot
                else if(line.charAt(0) == 'B')
                {
                    if(admin) {
                        if (GamesManager.getInstance().getGameByName(roomName).limit <= 0) {
                            displayErrorMsg(output, "Cant add new bot!");
                            System.out.println("Cant add new bot!");
                            //break;
                        } else {
                            String botName = GamesManager.getInstance().getGameByName(roomName).addBot();
                            System.out.println("BOT ADDED");
                            updateAfterEnteringGame(botName);
                        }
                    }
                    else
                        displayErrorMsg(output, "You have no rights to do this!");
                    System.out.println("limit is = " + GamesManager.getInstance().getGameByName(roomName).limit);
                }
                else if (line.charAt(0) == 'K') {
                    onlyJump = false;
                    GamesManager.getInstance().getGameByName(roomName).turn.next();
                }
                // move(xxyyxxyy)
                else if (line.charAt(0) == 'M' && win == false) {
                    if (GamesManager.getInstance().getGameByName(roomName).turn.current() == this) {
                        int oldX, oldY, newX, newY;
                        oldX = Integer.parseInt(line.substring(1, 3));
                        oldY = Integer.parseInt(line.substring(3, 5));
                        newX = Integer.parseInt(line.substring(5, 7));
                        newY = Integer.parseInt(line.substring(7, 9));
                        int status = 0;


                            //looking for choosen piece and perform move
                            if (onlyJump) {
                                if (oldX == recentPiece.getX() && oldY == recentPiece.getY()) {
                                    status = GamesManager.getInstance().getGameByName(roomName).board.move(recentPiece, newX, newY, this.mark, onlyJump);
                                } else
                                    status = 0;
                            } else {
                                try {
                                    for (int i = 0; i < pieces.size(); i++) {
                                        if (oldX == pieces.get(i).getX() && oldY == pieces.get(i).getY()) {
                                            status = GamesManager.getInstance().getGameByName(roomName).board.move(pieces.get(i), newX, newY, this.mark, onlyJump);
                                            if (status == 2) {
                                                recentPiece = pieces.get(i);
                                                onlyJump = true;
                                            }
                                        }
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        //update if move performed
                        if (status != 0)
                            update(oldX, oldY, newX, newY);
                        else if (status == 0) {
                            displayErrorMsg(output, "Wrong Move!");
                            output.println("Your Turn!");
                        }
                        if (status == 2)
                            output.println("Your Turn!");

                        GamesManager.getInstance().getGameByName(roomName).board.displayBoard();
                        if (GamesManager.getInstance().getGameByName(roomName).board.checkWinCondition(pieces, mark)) {
                            if(GamesManager.getInstance().getGameByName(roomName).board.checkWinCondition(this.pieces, this.mark))
                            {
                                System.out.printf("BOT " + this.mark + " WYGRAL!");
                                GamesManager.getInstance().getGameByName(roomName).addWinner(this);
                                for(int i=0;i<GamesManager.getInstance().getGameByName(roomName).players.size();i++)
                                {
                                    if(GamesManager.getInstance().getGameByName(roomName).players.get(i) instanceof HumanPlayer)
                                        GamesManager.getInstance().getGameByName(roomName).players.get(i).getOutput().println("W"+name);
                                }
                                this.setWin(true);
                            }
                            if(GamesManager.getInstance().getGameByName(roomName).isOver())
                            {
                                System.out.println("THE GAME IS OVER");
                                for(int i=0;i<GamesManager.getInstance().getGameByName(roomName).players.size();i++)
                                {
                                    if(GamesManager.getInstance().getGameByName(roomName).players.get(i).getWin() == false)
                                    {
                                        GamesManager.getInstance().getGameByName(roomName).players.get(i).setWin(true);
                                        GamesManager.getInstance().getGameByName(roomName).addWinner(GamesManager.getInstance().getGameByName(roomName).players.get(i));
                                    }
                                }
                                for(int i=0;i<GamesManager.getInstance().getGameByName(roomName).players.size();i++)
                                {
                                    if(GamesManager.getInstance().getGameByName(roomName).players.get(i) instanceof HumanPlayer) {
                                        GamesManager.getInstance().getGameByName(roomName).players.get(i).getOutput().println("Game is over!");
                                        String str="";
                                        for(int j=0;j<GamesManager.getInstance().getGameByName(roomName).winners.size();j++)
                                            str = str + GamesManager.getInstance().getGameByName(roomName).winners.get(j).getName_() + "|";
                                        for(int k=0;k<GamesManager.getInstance().getGameByName(roomName).winners.size();k++)
                                            if(GamesManager.getInstance().getGameByName(roomName).players.get(k) instanceof HumanPlayer)
                                                GamesManager.getInstance().getGameByName(roomName).players.get(k).getOutput().println(str);
                                    }
                                }
                            }
                        }
                        if (status != 2 && status != 0) {
                            GamesManager.getInstance().getGameByName(roomName).turn.next();
                        }
                    } else
                        displayErrorMsg(output, "Can't Move!");

                }
                // leave room
                else if (line.charAt(0) == 'R') {
                    GamesManager.getInstance().getGameByName(roomName).removePlayer(this);
                    System.out.println(name + "removed from " + roomName + " room!");
                    updateAfterLeavingGame(this.name);

                }
                else if (line.charAt(0) == 'Z') {
                    if(admin) {
                        GamesManager.getInstance().getGameByName(roomName).limit--;
                        updateMSG("Slot Removed");
                    }
                    else
                        output.println("You have no rights!");
                }
                else if (line.charAt(0) == 'C') {
                    if(admin) {
                        GamesManager.getInstance().getGameByName(roomName).limit++;
                        updateMSG("Slot Added");
                    }
                    else
                        output.println("You have no rights!");


                }
                else if(line.charAt(0) == 'T')
                {
                    if(admin) {
                        String n = line.substring(1);
                        Player p = null;
                        int success = 0;
                        for(int i=0;i<GamesManager.getInstance().getGameByName(roomName).players.size();i++)
                        {
                            if(GamesManager.getInstance().getGameByName(roomName).players.get(i).getName_().equals(n)) {
                                p = GamesManager.getInstance().getGameByName(roomName).players.get(i);
                                success = 1;
                                break;
                            }
                        }
                        if(success == 1) {
                            GamesManager.getInstance().getGameByName(roomName).limit++;
                            GamesManager.getInstance().getGameByName(roomName).removePlayer(p);
                            updateMSG("R" + p.getName_());
                        }
                        else
                            displayErrorMsg(output,"Couldn't remove that player");
                    }
                    else
                        output.println("You have no rights!");
                }

                // DEBUGGING
                /*else if (line.charAt(0) == 'X') {
                    for (int i = 0; i < GamesManager.getInstance().games.size(); i++) {
                        System.out.println(i);
                        for (int j = 0; j < GamesManager.getInstance().games.get(i).players.size(); j++) {
                            System.out.println(GamesManager.getInstance().games.get(i).players.get(j).getName_());
                        }
                    }
                    System.out.println("Starting game: 1");
                    GamesManager.getInstance().games.get(1).start();
                    GamesManager.getInstance().games.get(1).turn.resetTurn();
                    while(GamesManager.getInstance().games.get(1).turn.next() instanceof AIPlayer) {
                        GamesManager.getInstance().games.get(1).turn.current().move();
                        GamesManager.getInstance().games.get(1).board.displayBoard();
                        if(GamesManager.getInstance().games.get(1).board.checkWinCondition(GamesManager.getInstance().games.get(1).turn.current().getPieces(),GamesManager.getInstance().games.get(1).turn.current().getMark())) {
                            System.out.printf("BOT " + GamesManager.getInstance().games.get(1).turn.current().getMark() + " WYGRAL!");
                            try {
                                Thread.sleep(50000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }*/
                // set name
                else if (line.charAt(0) == 'S') {
                    this.name = line.substring(1);
                }
                // get player name
                else if (line.charAt(0) == 'O') {
                        System.out.println("TEST123: " + this.getMark());
                        output.println("O" + this.getMark());
                }
                // enter room
                else if (line.charAt(0) == 'E') {
                    //looking for given room
                    if(GamesManager.getInstance().getGameByName(line.substring(1)) == null)
                    {
                        displayErrorMsg(output, "There is no such room!");
                        break;
                    }
                    // remember room name
                    this.roomName = line.substring(1);
                    //preventing entering twice into same room
                    if (!GamesManager.getInstance().getGameByName(roomName).players.contains(this) && GamesManager.getInstance().getGameByName(roomName).limit > 0) {
                        GamesManager.getInstance().getGameByName(roomName).addPlayer(this);
                        updateAfterEnteringGame(this.name);
                    }
                    else {
                        displayErrorMsg(output, "You can't join that room!");
                        System.out.println("fail joing room");
                       // break;
                    }

                    System.out.println("LIMIT IS = " +  GamesManager.getInstance().getGameByName(roomName).limit);
                }
                else
                {
                    System.out.println("Wrong command!");
                    displayErrorMsg(output, "Wrong command!");
                }
            }
        } catch (SocketException ex) {
            System.out.println("SOCKET = " + socket + " WYCHODZI!");
            try {
                input.close();
                output.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateMSG(String msg)
    {
        for (int i=0; i <GamesManager.getInstance().getGameByName(roomName).players.size(); i++)
        {
            if (GamesManager.getInstance().getGameByName(roomName).players.get(i) instanceof HumanPlayer) {
                GamesManager.getInstance().getGameByName(roomName).players.get(i).getOutput().println(msg);
            }
        }
    }


    private void updateAfterLeavingGame(String name)
    {
        for (int i=0; i <GamesManager.getInstance().getGameByName(roomName).players.size(); i++)
        {
            if (GamesManager.getInstance().getGameByName(roomName).players.get(i) instanceof HumanPlayer) {
                GamesManager.getInstance().getGameByName(roomName).players.get(i).getOutput().println("V" + name);
            }
        }
    }

    private void updateAfterEnteringGame(String namestr)
    {
        for (int i=0; i <GamesManager.getInstance().getGameByName(roomName).players.size(); i++)
        {
            if (GamesManager.getInstance().getGameByName(roomName).players.get(i) instanceof HumanPlayer) {
                GamesManager.getInstance().getGameByName(roomName).players.get(i).getOutput().println("P" + namestr);
            }
        }
    }

    private void sendOutput()
    {
        String str = "";
        for (int i = 0; i < GamesManager.getInstance().games.size(); i++) {
            str = str + GamesManager.getInstance().games.get(i).getTableName()+"|"+GamesManager.getInstance().games.get(i).getHumanPlayers();
            str = str + "|"+ GamesManager.getInstance().games.get(i).getAIPlayers();

            for (int j = 0; j < GamesManager.getInstance().games.get(i).players.size(); j++) {
                if (GamesManager.getInstance().games.get(i).players.get(j) instanceof HumanPlayer) {
                    str = str + "|" + GamesManager.getInstance().games.get(i).players.get(j).getName_();
                }
            }
            for (int j = 0; j < GamesManager.getInstance().games.get(i).players.size(); j++) {
                if (GamesManager.getInstance().games.get(i).players.get(j) instanceof AIPlayer) {
                    str = str + "|" + GamesManager.getInstance().games.get(i).players.get(j).getName_();
                }
            }
            str = str + "*";
        }
        System.out.println("Sys: " + str);
        output.println(str);
    }
    private void update(int oldX, int oldY, int newX, int newY)
    {
        String oX="",oY="",nX="",nY="",msg="";
        if(oldX<10)
            oX="0";
        oX = oX + Integer.toString(oldX);
        if(oldY<10)
            oY="0";
        oY=oY + Integer.toString(oldY);
        if(newX<10)
            nX="0";
        nX=nX + Integer.toString(newX);
        if(newY<10)
            nY="0";
        nY=nY + Integer.toString(newY);

        msg = msg + oX + oY + nX + nY;
        for(int i =0 ; i<GamesManager.getInstance().getGameByName(roomName).players.size(); i++)
            if(GamesManager.getInstance().getGameByName(roomName).players.get(i) instanceof HumanPlayer)
                GamesManager.getInstance().getGameByName(roomName).players.get(i).getOutput().println("U" + msg);
    }

    private int startGame(String tableName)
    {
        int size = GamesManager.getInstance().getGameByName(roomName).players.size();
        if(size <= 1 || size > 6 || size == 5)
        {
            return 0;
        }
        System.out.println("Starting game: " + roomName);
        GamesManager.getInstance().getGameByName(roomName).start();
        GamesManager.getInstance().getGameByName(roomName).turn.resetTurn();
        return 1;
    }

    private void displayErrorMsg(PrintStream output, String msg)
    {
        output.println("E" + msg);
    }

    private int createNewGame(int humanPlayers, int AIPlayers, String tableName)
    {
        for(int i=0;i<GamesManager.getInstance().games.size();i++)
        {
            // that name exists
            if(GamesManager.getInstance().games.get(i).getTableName().equals(tableName))
                return 0;
        }
        //create new table
        Game game = new Game(humanPlayers, AIPlayers, tableName);
        //add table to manager
        GamesManager.getInstance().games.add(game);
        //remember table
        this.roomName = tableName;
        return 1;
    }

    public DataInputStream getInput() {
        return input;
    }

    public PrintStream getOutput() {
        return output;
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

    @Override
    public void move() {

    }

    @Override
    public String getName_() {
        return name;
    }

    @Override
    public ArrayList<Piece> getPieces()
    {
        return this.pieces;
    }

    private void setPieces() {
        pieces = GamesManager.getInstance().getGameByName(roomName).board.setPieces(mark);
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
