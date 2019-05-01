package clients;

import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SendMessages 
{
    public static void main(String args[]) 
    { 
    		ClientOne cOne;
    		ClientTwo cTwo;
    		ClientThree cThree;
    		ClientFour cFour;
    		AbstractClient clientOne = null;
    		AbstractClient clientTwo = null;
    		AbstractClient clientThree = null;
    		AbstractClient clientFour = null;

		try {
			cOne = new ClientOne();
			cTwo = new ClientTwo();
			cThree = new ClientThree();
			cFour = new ClientFour();
			clientOne = cOne.getClientOne();
			clientTwo = cTwo.getClientTwo();
			clientThree = cThree.getClientThree();
			clientFour = cFour.getClientFour();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scanner kb = new Scanner(System.in);
		System.out.println("Hit enter to begin sending messages...");
		String beginSending = kb.nextLine();
		// sends messages every 2 seconds
		for (int i = 0; i < 15; i++)
		{			
			System.out.println("Client 1 Sending");
			sendMessage(clientOne);
			System.out.println("Client 2 Sending");
			sendMessage(clientTwo);
			System.out.println("Client 3 Sending");
			sendMessage(clientThree);
			System.out.println("Client 4 Sending");
			sendMessage(clientFour);
		}
    }
    
    public static void sendMessage(AbstractClient client)
    {
    		client.send();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
    }
}
