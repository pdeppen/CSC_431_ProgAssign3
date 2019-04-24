
package routers;

import java.util.HashMap;

/**
 * Philip Deppen, Tyler Major
 */
public class RouterTwo extends routers.AbstractRouter
{        
    public RouterTwo()
    {
    		this.myClientPort = 4447;
    		this.myClient = 2;
		this.initializeRouterMap();
		this.listen();
    }
    
	public void initializeRouterMap() 
	{
		// router 3
		routingMap.put(3, "157.160.37.179");
		// router 4
		routingMap.put(4, "157.160.37.179");
		// router 1
		routingMap.put(1, "157.160.37.181");
		// client 2 - philips mac
		routingMap.put(2, "157.160.77.19");
	}
	
	public static void main(String[] args) 
	{
		System.out.println("ROUTER 2 MESSAGES");
		RouterTwo r2 = new RouterTwo();
	}
}
