package misc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Philip Deppen
 * Tyler Major
 */
public class Server extends Thread {
	
    //initialize socket and input stream 
    private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       = null; 
    private DataOutputStream out 	= null;
    public static int threadNumber = 1;
    
    /**
     * Constructor
     * @param socket
     */
    public Server(Socket socket) 
    { 
    		this.socket = socket;
    }
    
    /**
     * Contains thread logic
     */
	@Override
	public void run() {
      try
      { 
          // takes input from the client socket 
          in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
          
          // sends message back to client
          out = new DataOutputStream(socket.getOutputStream());
          out.flush();
         
          // reads message from client 
          String userInput = "";
          try
          { 
            //  System.out.println(in.readUTF());
              userInput = in.readUTF();
          } 
          catch(IOException i) 
          { 
              System.out.println(i); 
          } 
          
          // reverse string
          String reversedString = "";          
          for (int i = userInput.length() - 1; i >= 0; i--)
        	  	reversedString += userInput.charAt(i);
          
          // sends to client
          out.writeChars("RESULT: " + reversedString);

          System.out.println("Closing connection with client thread #: " + Server.threadNumber); 
          Server.threadNumber++;
          
          // close connection 
          socket.close(); 
          in.close(); 
      } 
      catch(IOException i) 
      { 
          System.out.println(i); 
      } 
	} 
} 

