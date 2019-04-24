package clients;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Philip Deppen
 * Tyler Major
 */
public class ClientTwo extends AbstractClient{
	
	InetAddress addr = null;
	AbstractClient client;

	
	public ClientTwo() throws UnknownHostException
	{
		addr = InetAddress.getByName("157.160.37.180");
        this.client = new AbstractClient(addr, 4446, (byte)2); 
        this.client.setClientPort(4447);
	}
	
	public AbstractClient getClientTwo()
	{
		return this.client;
	}

    public static void main(String args[]) 
    { 
		ClientTwo cTwo;
		AbstractClient clientTwo = null;
		try {
			cTwo = new ClientTwo();
			clientTwo = cTwo.getClientTwo();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("CLIENT 2 MESSAGES");
		clientTwo.listen();
    }
}


