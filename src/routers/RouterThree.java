package routers;

import java.util.HashMap;

public class RouterThree extends routers.AbstractRouter
{    
    public RouterThree()
    {
    		this.myClient = 3;
    		this.myClientPort = 4448;
		this.initializeRouterMap();
		this.listen();
    }
    
    @Override
	public void initializeRouterMap() 
	{
    		// router 4
		routingMap.put(4, "157.160.37.182");
		// router 1
		routingMap.put(1, "157.160.37.182");
		// router 2
		routingMap.put(2, "157.160.37.180");
		// client 3 - philips mac
		routingMap.put(3, "157.160.77.19");
	}
			
	public static void main(String[] args) 
	{
		System.out.println("ROUTER 3 MESSAGES");
		RouterThree r3 = new RouterThree();
	}
}
