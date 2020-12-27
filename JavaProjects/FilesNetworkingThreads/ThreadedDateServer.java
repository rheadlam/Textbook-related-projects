import java.net.*;
import java.io.*;
import java.util.Date;

/**
 * This file is related to a Javanotes exercise. It is a server 
 * connecting to requests on the port specified by the constant
 * LISTENING_PORT. It sends the current time to the connected socket 
 * until it is killed, creating a new thread for every connection request.
 */
public class ThreadedDateServer {

    public static final int LISTENING_PORT = 25004;

    public static void main(String[] args) {

        ServerSocket listener;  // Listens for incoming connections.
        Socket connection;      

        /* Accept and processes connections until error. */

        try {
            listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
            while (true) {
                    // Accepts connection requests, handling it.
                connection = listener.accept(); 
                ConnectionHandler handler = new ConnectionHandler(connection);
                handler.start();
            }
        }
        catch (Exception e) {
            System.out.println("Sorry, the server has shut down.");
            System.out.println("Error:  " + e);
            return;
        }

    }  // end main()


    /**
     *  Thread that handles the connection with client.
     */
    private static class ConnectionHandler extends Thread {
        Socket client;
        ConnectionHandler(Socket socket) {
            client = socket;
        }
        public void run() {
            String clientAddress = client.getInetAddress().toString();
            try {
                System.out.println("Connection from " + clientAddress );
                Date now = new Date();  //Current date and time.
                PrintWriter outgoing;   // Stream.
                outgoing = new PrintWriter( client.getOutputStream() );
                outgoing.println( now.toString() );
                outgoing.flush(); 
                client.close();
            }
            catch (Exception e){
                System.out.println("Error on connection with: " 
                        + clientAddress + ": " + e);
            }
        }
    }


} //end class ThreadedDateServer