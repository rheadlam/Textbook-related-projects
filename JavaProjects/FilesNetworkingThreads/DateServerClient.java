import java.net.*;
import java.util.Scanner;
import java.io.*;


/**
 * This file relates to a Javanotes example. It opens
 * connection to a computer specified as the first command-line 
 * argument otherwise prompts user for computer to connect to.  
 * The port is specified by LISTENING_PORT. It displays the date
 * and time read on a standard output.
 */

public class DateServerClient {

    public static final int LISTENING_PORT = 25004;

    public static void main(String[] args) {

        String hostName;         // Name of server.
        Socket connection;       // Socket for communicating with server.
        BufferedReader incoming; // reads data from connection.

        // Get computer name from command line. 
        if (args.length > 0)
            hostName = args[0];
        else {
            Scanner stdin = new Scanner(System.in);
            System.out.print("Enter computer name or IP address: ");
            hostName = stdin.nextLine();
        }

        // Make connection, reads and displays line of text. 
        try {
            connection = new Socket( hostName, LISTENING_PORT );
            incoming = new BufferedReader( 
                                new InputStreamReader(connection.getInputStream()) );
            String lineFromServer = incoming.readLine();
            if (lineFromServer == null) {
                    // end of stream encountered.
                throw new IOException("Connection was opened, " + 
                        "but server did not send any data.");
            }
            System.out.println();
            System.out.println(lineFromServer);
            System.out.println();
            incoming.close();
        }
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }

    }  // end main()


} //end class DateServerClient
