import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Damian Borek on 27/11/2017.
 */
//TODO close sockets
public class ChineseCheckersServerConnection {

    static final int PORT = 1982;

    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server is running!");

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new HumanPlayer(socket).start();
        }

    }

}

























