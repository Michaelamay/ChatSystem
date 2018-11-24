import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
/*
 * The server side of the program. It takes any incomming connections and adds the user's name and server port
 * number to a hashmap.
 */


public class ChatServer {
	
	static HashMap<Connect,String> hm =new HashMap<Connect,String>();
	
	public static void main(String[] args) throws IOException{
		
		//Initialize ServerSocket
		int port = Integer.valueOf(args[0]);
		ServerSocket Main_ServerSocket = new ServerSocket(port);
		
		//new Thread(new Kill(hm)).start();
		
		
		System.out.println("waiting for connections on port "+port+" ...");
		while(true){
			
			Socket established_sock1 = Main_ServerSocket.accept();//Accept new clients
			
			DataInputStream Input = new DataInputStream(established_sock1.getInputStream());
			String Name = Input.readUTF();//Receive UserName from first client
			
			Connect client = new Connect(established_sock1,Main_ServerSocket,Input,Name);//Set him up.
			client.start();
	
			hm.put(client,Name);
			
			
		}
		
		
	}


}
