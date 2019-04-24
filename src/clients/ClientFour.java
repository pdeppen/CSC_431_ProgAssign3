package clients;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientFour 
{
	InetAddress addr = null;
	AbstractClient client;
	
	public ClientFour() throws UnknownHostException
	{
		addr = InetAddress.getByName("157.160.37.182");
		this.client = new AbstractClient(addr, 4446, (byte)4); 
        this.client.setClientPort(4449);
	}

	public AbstractClient getClientFour()
	{
		return this.client;
	}

    public static void main(String args[]) 
    { 
		ClientFour cFour;
		AbstractClient clientFour = null;
		try {
			cFour = new ClientFour();
			clientFour = cFour.getClientFour();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("CLIENT 4 MESSAGES");
		clientFour.listen();
    }
}
