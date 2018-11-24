import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map.Entry;

public class Connect extends Thread {
    
	Socket socket;
	ServerSocket serversocket;
	DataInputStream din;
	DataOutputStream dout;
	String userName;
	
	public Connect(Socket socket, ServerSocket ServerSocketName, DataInputStream Data_InputName, String UserName) {
		this.socket = socket;
		this.serversocket = ServerSocketName;
		this.din = Data_InputName;
		this.userName = UserName;
		
	}
    

	public void WriteData(String text){
		try {
			dout.writeUTF(text);
			dout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void WriteInt(int num){
		try{
			dout.writeInt(num);
			dout.flush();
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	public void WritetoAllClients(String text){
		
		for(Entry<Connect, String> m:ChatServer.hm.entrySet()){
			Connect client = m.getKey();
			if( !(m.getValue().equals(userName)) ) client.WriteData(userName + ": "+ text);//Don't write to myself!
			
		} 
	}
		
	public void WritetoOneClientProtocols(int port,String user){
		
		boolean found = false;
		
		for(Entry<Connect, String> m:ChatServer.hm.entrySet()){
			Connect client = m.getKey();
			if(m.getValue().equals(user)){
				found = true;
				client.WriteData("0b10");
				client.WriteInt(port);
				
				
			}
			
		}
		if(!(found)){
			
			//System.out.println("User not found.");
			
			
			try {
				
				ServerSocket dummySocket = new ServerSocket(port);
			
				Socket dummyListener = dummySocket.accept();
			
				DataInputStream connect_input= new DataInputStream(dummyListener.getInputStream());
				String msg = connect_input.readUTF();//filename
				
				String workingDir = System.getProperty("user.dir");
				String createfileName = workingDir+"/"+ msg;
				File file = new File(createfileName);
			
				DataOutputStream connect_output = new DataOutputStream(dummyListener.getOutputStream());
				
				//System.out.println("Connect: file exist");
				
				int file_size = 0;
				String string_size = String.valueOf(file_size);
			
				connect_output.writeUTF(string_size);
				
				connect_output.writeUTF("");
		
				connect_input.close();
			
				connect_output.close();
		
				dummyListener.close();
		
				dummySocket.close();
				
				
			} catch (EOFException e) {
				// TODO Auto-generated catch block
				
			}
			catch(Exception e){
				System.err.println("Connect: Normal message - ss_secret closed in main program "+ e.getMessage() );
			}
			
		}
	}
			
	public Connect grabMeClient(){
		
		for(Entry<Connect, String> m:ChatServer.hm.entrySet()){
			Connect client = m.getKey();
			if(m.getValue().equals(userName)) return client;
		}
		return null;
		
		
	}
	
	@Override
	public void run() {
		try {
			
			dout = new DataOutputStream(socket.getOutputStream());
			
			while(true){
				
				String textIn = din.readUTF();//All threads sit on this
				
				if(textIn.equals("0b10")){
					
					int port_Num = din.readInt();//Portnum from ChatClient
					
					String username = din.readUTF();//Username from ChatClient
					
					WritetoOneClientProtocols(port_Num,username);
					
					
				}
				else{
					WritetoAllClients(textIn);
				}
				
				
			}
		} catch(EOFException eofx){
			//Other side shutdown
			ChatServer.hm.remove(grabMeClient(), userName);//Remove client and userName from HashMap
			
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
