package routers;

import java.util.HashMap;


/**
 * Philip Deppen, Tyler Major
 */
public class RouterOne extends routers.AbstractRouter
{
        
    public RouterOne()
    {
    		this.myClient = 1;
    		this.myClientPort = 4446;
		this.initializeRouterMap();
		this.listen();
    }
    
    @Override
	public void initializeRouterMap() 
	{
    		// router 4
		routingMap.put(4, "157.160.37.182");
		// router 3
		routingMap.put(3, "157.160.37.182");
		// router 2
		routingMap.put(2, "157.160.37.180");
		// client 1 - philips mac
		routingMap.put(1, "157.160.77.19");
	}
		
	public static void main(String[] args) 
	{
		System.out.println("ROUTER 1 MESSAGES");
		RouterOne r1 = new RouterOne();
	}
}
