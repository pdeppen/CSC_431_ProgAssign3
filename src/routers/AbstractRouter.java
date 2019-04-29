package routers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Philip Deppen, Tyler Major
 */
public abstract class AbstractRouter 
{
	private Socket socket            = null;
    private DataOutputStream out     = null; 
    private InetAddress sendDest = null;
    private DataInputStream in       = null; 
    private Scanner sc = null;
    private byte[] failureMessage = {0,0,0,0,0,0,0,0,0,0};
    protected int dest = 0;
    protected int source = 0;
    protected byte[] message;
    protected byte checksum;
    protected boolean success;
    protected int myClient;
    protected int myClientPort;
    
    HashMap<Integer, String> routingMap = new HashMap<>();
    
	public AbstractRouter() 
	{
//		this.listen();
	}
	
	public abstract void initializeRouterMap();
	
	public void listen()
	{
		/* creates socket */
		ServerSocket ssock = null;
		try {
			ssock = new ServerSocket(4446);
			System.out.println("Router listening on port 4446...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**
		 * Accepts connections and creates new thread for each Client request
		 */
		while (true) {
			
			/* listens for client */
			Socket sock = null;
			try {
				sock = ssock.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			/* creates new client thread */
			this.connectionReceived(sock);
		
		} 
	}
	
	public void connectionReceived(Socket socket) 
	{
		this.socket = socket;
		System.out.println("Connection Received...");
		this.processMessage();
		System.out.println("");
	}
	
	public void processMessage()
	{
		
		try
		{ 
			// takes input from the client socket 
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
	  
			// sends message back to client
			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
	    }
		catch(IOException i) 
      	{ 
			System.out.println(i); 
      	} 
 		
		message = new byte[10];
		
		try {
			in.readFully(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
  		
		this.source = message[0];
		this.dest = message[1];
		System.out.println("Client Source: " + message[0]);
		System.out.println("Client Dest: " + message[1]);
		System.out.println("CheckSum: " + message[2]);
		System.out.println("LAN Source: " + message[3]);
		System.out.println("LAN Dest: " + message[4]);
		System.out.println("Data");
		for (int i = 5; i < message.length; i++)
			System.out.print(message[i] + " ");
		System.out.println("");
		
  		this.checksum = this.computeCheckSum();
  		
//		String message = "From: " + this.source;
		// TODO: remove this when using other clients
		
		// decide where to send message
  		if (message[4] != 1)
  		{
  			System.out.println("Sending to Router");
  			if (this.success)
  				this.sendToInternetRouter();
  			else
  				System.out.println("Message did not send: checksum failed");
  		}
  		else if (this.dest == this.myClient) 
		{
			this.sendToClient();
		}
		else 
		{
			if (this.success)
				this.sendToRouter(dest, this.message);
			else 
				System.out.println("Message did not send: checksum failed");
		}
		// close connection
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendToInternetRouter() 
	{
		System.out.println("sending to: " + dest);
		try {
			sendDest = InetAddress.getByName("157.160.37.178");
//			out.writeBoolean(false);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
				
		// establish a connection 
        try
        { 
            socket = new Socket(sendDest, 2555); 
            System.out.println("Connected");  
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
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
    			out.write(message);
        }
        
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
        
        // close the connection 
        try
        { 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
	}

	public byte computeCheckSum()
	{
		byte sum = 0;
		byte numReqBytes = 0;
		byte length = 10;
		
        byte[] newData = new byte[length];
        for (int i = 0; i < length; i++)
        {
        		newData[i] = message[i];
        }
        
        byte[] c1_data = new byte[length];
    
        numReqBytes = (byte) ((Math.floor(Math.log(newData[2]) / Math.log(2))) + 1);  
        c1_data[2] = (byte) (((1 << numReqBytes) - 1) ^ newData[2]); 
        
//        System.out.println(c1_data[i]);
        // Adding the complemented data 
        sum += c1_data[2]; 
        
//        System.out.println("C1_DATA");
        for(int i = 5; i< newData.length; i++) 
        {                  
        		
            // Complementing the data being received 
            numReqBytes = (byte) ((Math.floor(Math.log(newData[i]) / Math.log(2))) + 1);  
            c1_data[i] = (byte) (((1 << numReqBytes) - 1) ^ newData[i]); 
            
//            System.out.println(c1_data[i]);
            // Adding the complemented data 
            sum += c1_data[i]; 
        } 
//        System.out.println("Sum(in ones complement) is : "+sum);
        
     // Complementing the sum 
        numReqBytes = (byte) ((Math.floor(Math.log(sum) / Math.log(2))) + 1);  
        sum = (byte) (((1 << numReqBytes) - 1) ^ sum); 
        System.out.println("Calculated Checksum is : "+sum); 
          
        // Checking whether final result is 0 or something else 
        // and sending feedback accordingly  
        if(sum == 0) 
        {    
            System.out.println("success"); 
            this.success = true;
        }      
        else
        {    
        		this.success = false;
            System.out.println("failure"); 
        } 
		
		return sum;
	}
	
	/**
	 * Send the message that was received to the Client connected to the router (i.e. sends message to Client 1)
	 */
	public void sendToClient() 
	{		              
		System.out.println("sending to: " + dest);
		try {
			sendDest = InetAddress.getByName("157.160.37.183");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// establish a connection 
        try
        { 
            socket = new Socket(sendDest, this.myClientPort); 
            System.out.println("Connected");  
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
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
        		out.writeBoolean(this.success);
			if (!this.success) {
				System.out.println("Message did not send: checksum failed");
			}
			else
				out.write(message);			
        }
        
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
        
        // close the connection 
        try
        { 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
	}
	
	public void sendToRouter(int dest, byte[] message) 
	{
		System.out.println("sending to: " + dest);
		try {
			sendDest = InetAddress.getByName(routingMap.get(dest));
//			out.writeBoolean(false);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println(routingMap.get(dest));
		
		// establish a connection 
        try
        { 
            socket = new Socket(sendDest, 4446); 
            System.out.println("Connected");  
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
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
    			out.write(message);
        }
        
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
        
        // close the connection 
        try
        { 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
	}
}
