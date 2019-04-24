package routers;

import java.util.HashMap;

public class RouterFour extends routers.AbstractRouter
{    
    public RouterFour()
    {
    		this.myClient = 4;
    		this.myClientPort = 4449;
		this.initializeRouterMap();
		this.listen();
    }
    
	public void initializeRouterMap() 
	{
		// router 3
		routingMap.put(3, "157.160.37.179");
		// router 2
		routingMap.put(2, "157.160.37.179");
		// router 1
		routingMap.put(1, "157.160.37.181");
		// client 4 - philips mac
		routingMap.put(4, "157.160.77.19");
	}
	
	public static void main(String[] args) 
	{
		System.out.println("ROUTER 4 MESSAGES");
		RouterFour r4 = new RouterFour();
	}
}
