package clients;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AbstractClient {
	// initialize socket and input output streams 
    private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataOutputStream out     = null; 
    private Scanner sc = null;
    private int startingValue;
    private Random rand = new Random();
    private boolean success;
    protected byte[] message;
    private byte source;
    private InetAddress address;
    private int port;
    private int clientPort;
    
    // used for testing
    public AbstractClient()
    {
    		this.setStartingValue();
    }
    
    // constructor to put ip address and port 
	public AbstractClient(InetAddress address, int port, byte source) 
    { 
		this.setSource(source);
		this.setStartingValue();
		this.address = address;
		this.port = port;				
    }

	public void send()
	{	
		this.sendMessage(address, port);
	}
	
	
	public void sendMessage(InetAddress address, int port)
	{
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
//            input  = new DataInputStream(System.in); 
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
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

        		this.message = this.generateMessage();
        		this.displayMessage(message);
        		out.write(message);
        }
        
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
        
//        this.receivedMessage();
        
	}
	
	public void setClientPort(int port)
	{
		this.clientPort = port;
	}
	
	public void listen()
	{
		/* creates socket */
		ServerSocket ssock = null;
		try {
			ssock = new ServerSocket(this.clientPort);
			System.out.println("Client listening on port " + this.clientPort + "...");
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
			this.receivedMessage(sock);
		
		} 
	}
	
	public void receivedMessage(Socket socket)
	{
		
		try
		{ 
			// takes input from the client socket 
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
	  
			// sends message back to client
			out = new DataOutputStream(socket.getOutputStream());
			out.flush();
	    }
		catch(IOException i) 
      	{ 
			System.out.println(i); 
      	} 
 		
        byte[] messageRecevied = new byte[10];
        // receives message from server
        try {
        		this.success = input.readBoolean();
        		if (success && input.available() > 0) {
        			System.out.println("\nMessage Recevied");
        			input.readFully(messageRecevied);
        			// verify checksum before displaying message
        			if (this.verifyMessage(messageRecevied))
        				this.displayMessage(messageRecevied);
        		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public boolean verifyMessage(byte[] message)
	{
		boolean result = false;
		
		byte sum = 0;
		byte numReqBytes = 0;
		byte length = 10;
		
        byte[] newData = new byte[length];
        for (int i = 0; i < length; i++)
        {
        		newData[i] = message[i];
        }
        
        byte[] c1_data = new byte[length];
    
        for(int i = 5; i< newData.length; i++) 
        {                  
        		
            // Complementing the data being received 
            numReqBytes = (byte) ((Math.floor(Math.log(newData[i]) / Math.log(2))) + 1);  
            c1_data[i] = (byte) (((1 << numReqBytes) - 1) ^ newData[i]); 
            

            // Adding the complemented data 
            sum += c1_data[i]; 
        } 
        
        // Complementing the sum 
        numReqBytes = (byte) ((Math.floor(Math.log(sum) / Math.log(2))) + 1);  
        sum = (byte) (((1 << numReqBytes) - 1) ^ sum);  
          
        // Checking whether final result is 0 or something else 
        // and sending feedback accordingly  
        if(sum == 0) 
        {    
            result = true;
        }      
        else
        {    
        		result = false;
            System.out.println("checksum failure"); 
        } 
				
		return result;
	}
	
	public void displayMessage(byte[] message)
	{
		System.out.println("Client Source: " + message[0]);
		System.out.println("Client Dest: " + message[1]);
		System.out.println("CheckSum: " + message[2]);
		System.out.println("LAN Source: " + message[3]);
		System.out.println("LAN Dest: " + message[4]);
		System.out.println("Data");
		for (int i = 5; i < message.length; i++)
			System.out.print(message[i] + " ");
		System.out.println("");
	}
	
	public boolean getSuccess()
	{
		return this.success;
	}
	
	public void setSource(byte source)
	{
		this.source = source;
	}
	
	public byte getSource()
	{
		return this.source;
	}
	
	public byte[] generateMessage()
	{
		byte sum = 0;
		byte complementedData = 0;
		byte numReqBytes = 0;
		byte length = 10;
		// array to hold the complement of each data
		byte c_data[] = new byte[length];
		
		byte[] message = new byte[length];
		/* TODO: message[0] = this.clientNumber when abstract is made */
		message[0] = this.source;
//		System.out.println("this source: " + message[0]);
		
		// random destination client
		message[1] = (byte) (this.rand.nextInt(4) + 1);
		
		// this NAT source
		message[3] = (byte) 1;
		
		// random NAT dest
		message[4] = (byte) (this.rand.nextInt(7) + 1);
		
		// start with starting value
		message[5] = (byte) this.startingValue;
		numReqBytes = (byte) ((Math.floor(Math.log(message[3]) / Math.log(2))) + 1);
		complementedData = (byte) (((1 << numReqBytes) - 1) ^ message[3]);
		sum += complementedData;
				
		// random data
//		System.out.println("C_DATA");
        for (int i = 6; i < length; i++)  
        {               
    			// random byte
        		message[i] = (byte) (rand.nextInt(100000) + 1);
        		
            // Complementing the entered data 
            // Here we find the number of bits required to represent 
            // the data, like say 8 requires 1000, i.e 4 bits 
            numReqBytes = (byte) ((Math.floor(Math.log(message[i]) / Math.log(2))) + 1); 
              
            // Here we do a XOR of the data with the number 2^n -1, 
            // where n is the nob calculated in previous step 
            c_data[i] = (byte) (((1 << numReqBytes) - 1) ^ message[i]); 
             
//            System.out.println(c_data[i]);
            // Adding the complemented data and storing in sum 
            sum += c_data[i]; 
        } 
		this.startingValue++;

        System.out.println("Checksum Calculated is : " + sum); 
        byte checkSum = sum;
        message[2] = checkSum;
        return message;
      		
	}
	
	public void setStartingValue()
	{
		this.startingValue = rand.nextInt(100000) + 1;
	}
}
