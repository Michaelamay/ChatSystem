import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map.Entry;

public class ChatClient {
	
	Socket sock;
	int port;
		
	public ChatClient(int port_num){
		port = port_num;
	}
	
	public DataOutputStream getOutputStream() throws IOException {
		return new DataOutputStream( sock.getOutputStream());
	}
	
	public DataInputStream getInputStream() throws  IOException {
		return new DataInputStream( sock.getInputStream() );
	}
	
	public void initialize() {
		try{
			this.sock= new Socket("localhost", port);
		}
		catch (Exception e){
			System.out.println("startClient exception: " + e.getMessage());
			System.exit(1);
		}
	}

	
	public static void main(String[] args) throws IOException {
		
		//Initialize socket
		int port = Integer.valueOf(args[3]);
		int connect = Integer.valueOf(args[1]);
		ChatClient client = new ChatClient(port);
		client.initialize();//Setup a socket
		
		//Input and Output streams
		DataOutputStream data_output= client.getOutputStream();//Get OutputStream going
		DataInputStream data_input= client.getInputStream();//Get InputStream going
				
		//Sending messages through a socket
		BufferedReader std_input= new BufferedReader(new InputStreamReader(System.in));
		String user_msg;
		System.out.println("What is your name?");
		user_msg= std_input.readLine();
				
		/***/
		//Run thread in the background
		new Thread( new Receive(data_input)).start();//Just for receiving
		/***/
		
		data_output.writeUTF(user_msg);//Write name to server
		
		System.out.println("Enter an option ('m', 'f', 'x'):\n (M)essage (send)\n (F)ile (request)\ne(X)it");
		
		while((user_msg= std_input.readLine()) != null){
			
			if(user_msg.equals("m")) {
				System.out.println("Enter your message:");
				std_input= new BufferedReader(new InputStreamReader(System.in));
				user_msg = std_input.readLine();
				data_output.writeUTF(user_msg);//Write to server
			}
			else if(user_msg.equals("f")){
				
				data_output.writeUTF("0b10");
				
				data_output.writeInt(connect);//Port_number 
				
				System.out.println("Who owns the file?");
				std_input= new BufferedReader(new InputStreamReader(System.in));
				user_msg = std_input.readLine();
				data_output.writeUTF(user_msg);//ClientX
				
				/***/
				//Write file-name
				System.out.println("Which file do you want?");
				std_input= new BufferedReader(new InputStreamReader(System.in));
				user_msg = std_input.readLine();//file being requested
				/***/
					
				
				//Ameca
				
				/***/
				
				Socket new_socket = new Socket("localhost",connect);	
				DataOutputStream data_out = new DataOutputStream(new_socket.getOutputStream());
				data_out.writeUTF(user_msg);//Write filename to filename_filebytes
				/***/
					
				/***/
				DataInputStream input_channel = new DataInputStream(new_socket.getInputStream());
				//System.out.println("ChatClient: Launching Receiver thread.");
				new Thread(new Receiver_bytesBlank(input_channel,new_socket,user_msg)).start();
				/***/
								
			}
			else if (user_msg.equals("x")) break;
			
			System.out.println("Enter an option ('m', 'f', 'x'):\n (M)essage (send)\n (F)ile (request)\ne(X)it");
		}
		System.out.println("closing your sockets...goodbye");
		System.exit(0);
	}

}
