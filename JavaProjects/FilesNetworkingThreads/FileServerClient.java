import java.net.*;
import java.io.*;
   
/**
 * This file relates to a Javanotes exercise. It is a client 
 * to a server that when connection is opened sends commands:
 * "INDEX" or "GET <file-name>". The server will send either 
 * the specified file or list of available files. It also sends
 * error messages. Command-line arguments respectively correspond 
 * to name of server computer, name of file to be downloaded and
 * new name for the local copy. Second and third parameters are 
 * optional with list of files sent if not included. Files save into
 * same folder as this file.
 */
public class FileServerClient {

   static final int LISTENING_PORT = 25004;


   public static void main(String[] args) {
   
      String computer;          // Name or IP address of server.
      Socket connection;        // Communicates with server.
      PrintWriter outgoing;     // Stream sending a command to server.
      BufferedReader incoming;  // Stream reading data from connection.
      String command;          
      

      // Checks legal amount of command-line arguments.
      
      if (args.length == 0 || args.length > 3) {
         System.out.println("Usage:  java ServerFileClient <server>");
         System.out.println("    or  java ServerFileClient <server> <file>");
         System.out.println(
               "    or  java ServerFileClient <server> <file> <local-file>");
         return;
      }
      
      // Gets details for connecting to sever.
      
      computer = args[0];
      
      if (args.length == 1)
         command = "INDEX";
      else
         command = "GET " + args[1];
      
      // Opens streams sending command to server handling error.
      
      try {
         connection = new Socket( computer, LISTENING_PORT );
         incoming = new BufferedReader( 
                           new InputStreamReader(connection.getInputStream()) );
         outgoing = new PrintWriter( connection.getOutputStream() );
         outgoing.println(command);
         outgoing.flush(); 
      }
      catch (Exception e) {
         System.out.println(
              "Connection to sever at \"" + args[0] + "\" failed.");
         System.out.println("Error:  " + e);
         return;
      }
      
      // Read and process server's response.
      
      try {
         if (args.length == 1) {
               // "index" :reads and displays lines.
            System.out.println("File list from server:");
            while (true) {
               String line = incoming.readLine();
               if (line == null)
                   break;
               System.out.println("   " + line);
            }
         }
         else {
               /*"get <file-name>": reads server's response message. "Attempting.." 
        	    response writes file.*/
            String message = incoming.readLine();
            if (! message.equalsIgnoreCase("Attempting..")) {
               System.out.println("File not found on server.");
               System.out.println("Message from server: " + message);
               return;
            }
            PrintWriter fileOut;  // Writes received data to file.
            if (args.length == 3) {
                  // local file name specified, will overwrite same name file.
                fileOut = new PrintWriter( new FileWriter(args[2]) );
            }
            else {
                  /* file name unchanged from second parameter,
                     won't replace an existing file.*/
                File file = new File(args[1]);
                if (file.exists()) {
                   System.out.println("File name already exists.");
                   System.out.println("The three-argument version of");
                   System.out.println("command will replace it.");
                   return;
                }
                fileOut = new PrintWriter( new FileWriter(args[1]) );
            }
            while (true) {
                   // Copy lines until end of the incoming stream is encountered.
                String line = incoming.readLine();
                if (line == null)
                    break;
                fileOut.println(line);
            }
            if (fileOut.checkError()) {
               System.out.println("There was an error when writing file.");
               System.out.println("Output file may be incomplete or empty.");
            }
         }
      }
      catch (Exception e) {
         System.out.println(
                 "Error occurred when reading data from server.");
         System.out.println("Error: " + e);
      }
      finally {
         try {
            connection.close();
         }
         catch (IOException e) {
         }
      }
      
   }  // end main()
   

} //end class FileServerClient
