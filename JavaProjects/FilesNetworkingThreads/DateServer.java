import java.net.*;
import java.io.*;
import java.util.Date;

/**
 * This file relates to a Javanotes example. It is a program server 
 * taking connection requests on the port specified by the constant 
 * LISTENING_PORT. When connection is opened, program sends current 
 * time. The program runs until killed.
 */
public class DateServer {

    public static final int LISTENING_PORT = 25004;

    public static void main(String[] args) {

        ServerSocket listener;  // Listens for incoming connections.
        Socket connection;      // For communication with connecting program.

        // Accept and process connections until an error occurs.
        try {
            listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
            while (true) {
                    // Accept next connection request then handle it.
                connection = listener.accept(); 
                sendDate(connection);
            }
        }
        catch (Exception e) {
            System.out.println("Server shut down.");
            System.out.println("Error:  " + e);
            return;
        }

    }  // end main()


    /**
     * Client is a socket already connected to another program.  
     * Sends the current time, and close the connection.
     */
    private static void sendDate(Socket client) {
        try {
            System.out.println("Connection from " +  
                    client.getInetAddress().toString() );
            Date now = new Date();  // Current date and time.
            PrintWriter outgoing;   // Stream sending data.
            outgoing = new PrintWriter( client.getOutputStream() );
            outgoing.println( now.toString() );
            outgoing.flush();  // Definitely sends data!
            client.close();
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    } // end sendDate()


} //end class DateServer