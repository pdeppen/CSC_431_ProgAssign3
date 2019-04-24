package clients;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientThree 
{    
	InetAddress addr = null;
	AbstractClient client;

	public ClientThree() throws UnknownHostException
	{
		addr = InetAddress.getByName("157.160.37.179");
		this.client = new AbstractClient(addr, 4446, (byte)3); 
        this.client.setClientPort(4448);
	}
	
	public AbstractClient getClientThree()
	{
		return this.client;
	}

    public static void main(String args[]) 
    { 
		ClientThree cThree;
		AbstractClient clientThree = null;
		try {
			cThree = new ClientThree();
			clientThree = cThree.getClientThree();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("CLIENT 3 MESSAGES");
		clientThree.listen();
    }
}
