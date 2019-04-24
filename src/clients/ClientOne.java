package clients;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Philip Deppen
 * Tyler Major
 */
public class ClientOne extends AbstractClient{
  
	
	InetAddress addr = null;
	AbstractClient client;
	
	public ClientOne() throws UnknownHostException
	{
		addr = InetAddress.getByName("157.160.37.181");
        this.client = new AbstractClient(addr, 4446, (byte)1); 
        this.client.setClientPort(4446);
	}
	
	public AbstractClient getClientOne()
	{
		return this.client;
	}
	
    public static void main(String args[]) 
    { 
		ClientOne cOne;
		AbstractClient clientOne = null;
		try {
			cOne = new ClientOne();
			clientOne = cOne.getClientOne();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("CLIENT 1 MESSAGES");
		clientOne.listen();
    }
}

