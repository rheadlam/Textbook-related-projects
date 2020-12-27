import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This file relates to a Javanotes exercise. It is a simple 
 * network file server. The client downloads text files from 
 * the server sending either command: "index" (server sends list
 * of files) or "get <file-name>" (server sends specific file
 * or error message if file doesn't exist). If illegal 
 * command is entered server sends message "unknown command".
 * The server program requires a command-line parameter specifying 
 * the directory containing text files with permission to read them.
 * It uses a threadpool handling communication with clients and queues
 * connections.
 */
public class ThreadedFileServer {

    static final int LISTENING_PORT = 25004;
    
    private static final int THREAD_POOL_SIZE = 10;
    
    private static final int CONNECTION_QUEUE_SIZE = 5;
    
    private static ArrayBlockingQueue<Socket> connectionQueue;
    
    
    /**
     * Creates thread pool and opens sockets.
     */
    public static void main(String[] args) {

        File directory;        // Directory from which the server serves.

        ServerSocket listener; // Listens for connection requests.

        Socket connection;     // Socket for communicating with a client.


        // prints usage message and ends if no cmd argument. 

        if (args.length == 0) {
            System.out.println("Usage:  java ThreadedFileServer <directory>");
            return;
        }

        // Creates file object from directory specified from cmd.

        directory = new File(args[0]);
        if ( ! directory.exists() ) {
            System.out.println("Specified directory doesn't exist.");
            return;
        }
        if (! directory.isDirectory() ) {
            System.out.println("Specified file is not a directory.");
            return;
        }
        
        // Create the connection queue to be used by threads. 
        
        connectionQueue = new ArrayBlockingQueue<Socket>(CONNECTION_QUEUE_SIZE);
        
        // Creates thread pool with directory as parameter in each threads constructor.
        
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            ConnectionHandler worker = new ConnectionHandler(directory);
            worker.start();
        }

        // Listens and queues until terminated.

        try {
            listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
            while (true) {
                connection = listener.accept();
                connectionQueue.add(connection);
            }
        }
        catch (Exception e) {
            System.out.println("Server shut down.");
            System.out.println("Error:  " + e);
            return;
        }

    } // end main()


    /**
     * This class defines connection-handling threads running
     * in infinite loop removing connected sockets from queue 
     * and calling handleConnection().
     */
    private static class ConnectionHandler extends Thread {
        File directory;  // Directory from which the server serves.
        ConnectionHandler(File directory) {
            this.directory = directory;
            setDaemon(true);
        }
        public void run() {
            while (true) {
                try {
                    Socket connection = connectionQueue.take();
                    handleConnection(directory,connection);
                }
                catch (Exception e) {
                }
            }
        }
    }
    
    
    /**
     * Processes connection with client creating streams, logging
     * to standard output. Commands from client are read and carried
     * out with any error shown in output.
     */
    private static void handleConnection(File directory, Socket connection) {
        Scanner incoming;       // Reads data from client.
        PrintWriter outgoing;   // Sends data to client.
        String command = "Command has not been read";
        try {
            incoming = new Scanner( connection.getInputStream() );
            outgoing = new PrintWriter( connection.getOutputStream() );
            command = incoming.nextLine();
            if (command.equals("INDEX")) {
                sendIndex(directory, outgoing);
            }
            else if (command.startsWith("GET")){
                String fileName = command.substring(3).trim();
                sendFile(fileName, directory, outgoing);
            }
            else {
                outgoing.println("Illegal command");
                outgoing.flush();
            }
            System.out.println("Attempting.." + connection.getInetAddress()
                    + " " + command);
        }
        catch (Exception e) {
            System.out.println("ERROR " + connection.getInetAddress()
                    + " " + command + " " + e);
        }
        finally {
            try {
                connection.close();
            }
            catch (IOException e) {
            }
        }
    }

    /**
     * "index" command from client causes this to be called by run() sending list of files.
     */
    private static void sendIndex(File directory, PrintWriter outgoing) throws Exception {
        String[] fileList = directory.list();
        for (int i = 0; i < fileList.length; i++)
            outgoing.println(fileList[i]);
        outgoing.flush();
        outgoing.close();
        if (outgoing.checkError())
            throw new Exception("Error transmitting data.");
    }

    /**
     * "get <fileName>" command from client causes this to be called by run() 
     * sending contents of file or error message.
     */
    private static void sendFile(String fileName, File directory, PrintWriter outgoing) throws Exception {
        File file = new File(directory,fileName);
        if ( (! file.exists()) || file.isDirectory() ) {
        	outgoing.println("Error");
        }
        else {
            outgoing.println("Attempting..");
            BufferedReader fileIn = new BufferedReader( new FileReader(file) );
            while (true) {
                String line = fileIn.readLine();
                if (line == null)
                    break;
                outgoing.println(line);
            }
        }
        outgoing.flush(); 
        outgoing.close();
        if (outgoing.checkError())
            throw new Exception("Error transmitting data.");
    }


} //end class ThreadedFileServer
