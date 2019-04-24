package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.UnknownHostException;
import java.util.Random;

import org.junit.jupiter.api.Test;

import clients.ClientOne;

class TestClient {
	
//	@Test
//	void testProtocol() 
//	{
//		for (int i = 0; i < 10; i++)
//		{
//			System.out.println("\nNew Message\n");
//			c.generateMessage();
//			assertEquals(true, c.getSuccess());
//		}
//		
//	}
	
	@Test
	void testMessageGenerated() throws UnknownHostException
	{
		ClientOne c = new ClientOne();

		byte[] message = c.generateMessage();
		for (int i = 0; i < message.length; i++)
			System.out.println("Message: " + message[i]);
	}
	
//	@Test
//	void testChecksum()
//	{
//		int i, l, sum = 0, nob; 
//        l = 4; 
//          
//        // Array to hold the data being entered 
//        int data[] = {67, 43, 0, 30}; 
//          
//        // Array to hold the complement of each data 
//        int c_data[] = new int[l]; 
//          
//        System.out.println("Enter data to send"); 
//          
//        for (i = 0; i < l; i++)  
//        {               
//            // Complementing the entered data 
//            // Here we find the number of bits required to represent 
//            // the data, like say 8 requires 1000, i.e 4 bits 
//        		System.out.println(data[i]);
//            nob = (int)(Math.floor(Math.log(data[i]) / Math.log(2))) + 1; 
//              
//            // Here we do a XOR of the data with the number 2^n -1, 
//            // where n is the nob calculated in previous step 
//            c_data[i] = ((1 << nob) - 1) ^ data[i]; 
//              
//            // Adding the complemented data and storing in sum 
//            sum += c_data[i]; 
//        } 
//        System.out.println("Checksum Calculated is : " + sum); 
//        int checkSum = sum;
//        
//        // SERVER
//        sum = 0;
//        nob = 0;
//        int[] newData = new int[5];
//        for (i = 0; i < l; i++)
//        		newData[i] = data[i];
//        
//        newData[4] = checkSum;
//        int[] c1_data = new int[5];
//        for(i = 0; i< newData.length; i++) 
//        {                  
//            // Complementing the data being received 
//            nob = (int)(Math.floor(Math.log(newData[i]) / Math.log(2))) + 1;  
//            c1_data[i] = ((1 << nob) - 1) ^ newData[i]; 
//              
//            // Adding the complemented data 
//            sum += c1_data[i]; 
//        } 
//        System.out.println("Sum(in ones complement) is : "+sum);
//        
//     // Complementing the sum 
//        nob = (int)(Math.floor(Math.log(sum) / Math.log(2))) + 1;  
//        sum = ((1 << nob) - 1) ^ sum; 
//        System.out.println("Calculated Checksum is : "+sum); 
//          
//        // Checking whether final result is 0 or something else 
//        // and sending feedback accordingly  
//        if(sum == 0) 
//        {    
//            System.out.println("success"); 
//        }      
//        else
//        {    
//            System.out.println("failure"); 
//        } 
//	}
//	
///****************************************************************************************/
//	
//	@Test
//	void testCheckSumBytes()
//	{
//		byte i, l, sum = 0, nob; 
//        l = 4; 
//          
//        // Array to hold the data being entered 
//        byte data[] = {67, 44, 10, 90}; 
//          
//        // Array to hold the complement of each data 
//        byte c_data[] = new byte[l]; 
//          
//        System.out.println("Enter data to send"); 
//          
//        for (i = 0; i < l; i++)  
//        {               
//            // Complementing the entered data 
//            // Here we find the number of bits required to represent 
//            // the data, like say 8 requires 1000, i.e 4 bits 
//        		System.out.println(data[i]);
//            nob = (byte) ((Math.floor(Math.log(data[i]) / Math.log(2))) + 1); 
//              
//            // Here we do a XOR of the data with the number 2^n -1, 
//            // where n is the nob calculated in previous step 
//            c_data[i] = (byte) (((1 << nob) - 1) ^ data[i]); 
//              
//            // Adding the complemented data and storing in sum 
//            sum += c_data[i]; 
//        } 
//        System.out.println("Checksum Calculated is : " + sum); 
//        byte checkSum = sum;
//        
//        /**** SERVER ****/
//        sum = 0;
//        nob = 0;
//        byte[] newData = new byte[5];
//        for (i = 0; i < l; i++)
//        		newData[i] = data[i];
//        
//        newData[4] = checkSum;
//        byte[] c1_data = new byte[5];
//        for(i = 0; i< newData.length; i++) 
//        {                  
//            // Complementing the data being received 
//            nob = (byte) ((Math.floor(Math.log(newData[i]) / Math.log(2))) + 1);  
//            c1_data[i] = (byte) (((1 << nob) - 1) ^ newData[i]); 
//              
//            // Adding the complemented data 
//            sum += c1_data[i]; 
//        } 
//        System.out.println("Sum(in ones complement) is : "+sum);
//        
//     // Complementing the sum 
//        nob = (byte) ((Math.floor(Math.log(sum) / Math.log(2))) + 1);  
//        sum = (byte) (((1 << nob) - 1) ^ sum); 
//        System.out.println("Calculated Checksum is : "+sum); 
//          
//        // Checking whether final result is 0 or something else 
//        // and sending feedback accordingly  
//        if(sum == 0) 
//        {    
//            System.out.println("success"); 
//        }      
//        else
//        {    
//            System.out.println("failure"); 
//        } 
//	}

}
