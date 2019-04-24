package misc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Philip Deppen
 * Tyler Major
 */
public class RunServer {
    public static void main(String args[]) 
    { 
    		/* creates socket */
    		ServerSocket ssock = null;
    		try {
    			ssock = new ServerSocket(4446);
    			System.out.println("Server listening on port 4446...");
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
			new Server(sock).start();
			
			// stop server after 10 client connections
			if (Server.threadNumber == 10)
				break;
    		}
    		
    		System.out.println("Server stopped...");    		
    		
    }
}
