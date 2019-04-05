import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Damian Borek on 27/11/2017.
 */
public class TurnManager {
    private ArrayList<Player> players;
    private int playerTurn = 0;
    Random generator;

    public TurnManager(ArrayList<Player> players)
    {
        this.players = players;
        generator = new Random(System.currentTimeMillis());
    }

    public Player current ()
    {
        return players.get(playerTurn);
    }

    public Player next ()
    {
        playerTurn = (playerTurn + 1)%(players.size());
        while(players.get(playerTurn).getWin() == true ) {
            playerTurn = (playerTurn + 1) % (players.size());
        }
        update(playerTurn);
        System.out.println(players.get(playerTurn).getName_() + "'S TURN!");
        while(players.get(playerTurn) instanceof AIPlayer)
        {
            botTurn((AIPlayer)players.get(playerTurn));
            playerTurn = (playerTurn + 1)%(players.size());
            while(players.get(playerTurn).getWin() == true ) {
                playerTurn = (playerTurn + 1) % (players.size());
            }
            update(playerTurn);
        }

        return players.get(playerTurn);
    }


    public void resetTurn ()
    {
        playerTurn = generator.nextInt(players.size());
        while(players.get(playerTurn).getWin() == true ) {
            playerTurn = (playerTurn + 1) % (players.size());
        }
        System.out.println(players.get(playerTurn).getName_() + "'S TURN! " + "MARK " + players.get(playerTurn).getMark());
        update(playerTurn);
        while(players.get(playerTurn) instanceof AIPlayer)
        {
            botTurn((AIPlayer) players.get(playerTurn));
            playerTurn = (playerTurn + 1) % (players.size());
            while(players.get(playerTurn).getWin() == true ) {
                playerTurn = (playerTurn + 1) % (players.size());
            }
            update(playerTurn);
        }
    }

    private void update(int playerTurn)
    {
        for(int i=0;i<players.size();i++)
        {
            if(i==playerTurn && players.get(i) instanceof HumanPlayer)
                players.get(i).getOutput().println("Your Turn!");
            else if(players.get(i) instanceof HumanPlayer)
                players.get(i).getOutput().println(players.get(playerTurn).getName_() + "'S TURN!");
        }
    }

    private void botTurn(AIPlayer player)
    {
        player.move();
        if(GamesManager.getInstance().getGameByName(player.getRoom()).board.checkWinCondition(player.getPieces(), player.getMark()))
        {
            System.out.printf("BOT " + player.getMark() + " WYGRAL!");
            GamesManager.getInstance().getGameByName(player.getRoom()).addWinner(player);
            for(int i=0;i<GamesManager.getInstance().getGameByName(player.getRoom()).players.size();i++)
            {
                if(GamesManager.getInstance().getGameByName(player.getRoom()).players.get(i) instanceof HumanPlayer)
                    GamesManager.getInstance().getGameByName(player.getRoom()).players.get(i).getOutput().println("W"+player.getName_());
            }
            player.setWin(true);
        }
        if(GamesManager.getInstance().getGameByName(player.getRoom()).isOver())
        {
            System.out.println("THE GAME IS OVER");
            for(int i=0;i<GamesManager.getInstance().getGameByName(player.getRoom()).players.size();i++)
            {
                if(GamesManager.getInstance().getGameByName(player.getRoom()).players.get(i).getWin() == false)
                {
                    GamesManager.getInstance().getGameByName(player.getRoom()).players.get(i).setWin(true);
                    GamesManager.getInstance().getGameByName(player.getRoom()).addWinner(GamesManager.getInstance().getGameByName(player.getRoom()).players.get(i));
                }
            }
            for(int i=0;i<GamesManager.getInstance().getGameByName(player.getRoom()).players.size();i++)
            {
                if(GamesManager.getInstance().getGameByName(player.getRoom()).players.get(i) instanceof HumanPlayer) {
                    GamesManager.getInstance().getGameByName(player.getRoom()).players.get(i).getOutput().println("Game is over!");
                    String str="";
                    for(int j=0;j<GamesManager.getInstance().getGameByName(player.getRoom()).winners.size();j++)
                        str = str + GamesManager.getInstance().getGameByName(player.getRoom()).winners.get(j).getName_() + "|";
                    for(int k=0;k<GamesManager.getInstance().getGameByName(player.getRoom()).winners.size();k++)
                        if(GamesManager.getInstance().getGameByName(player.getRoom()).players.get(k) instanceof HumanPlayer)
                            GamesManager.getInstance().getGameByName(player.getRoom()).players.get(k).getOutput().println(str);
                }
            }
        }
    }
}
