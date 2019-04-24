package misc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Philip Deppen
 * Tyler Major
 */
public class Client {
	// initialize socket and input output streams 
    private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataOutputStream out     = null; 
    private Scanner sc = null;
    
    // constructor to put ip address and port 
	public Client(InetAddress address, int port) 
    { 
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
            input  = new DataInputStream(System.in); 
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
            
            sc = new Scanner(socket.getInputStream());
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
        
        // send user input to server
        try
        { 	
        		String userInput = input.readLine();
        		out.writeUTF(userInput);
        }
        
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
        
        // receives message from server
        String message = sc.nextLine();
        System.out.println(message);

        // close the connection 
        try
        { 
            input.close(); 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
    		InetAddress addr = null;
    		
		try {
			addr = InetAddress.getByName("157.160.37.181");
	        Client client = new Client(addr, 4446); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
}
