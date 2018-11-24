import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.net.Socket;

public class Receiver_bytesBlank implements Runnable{
	
	DataInputStream input_channel;
	
	Socket new_socket;
	String filename;
	
	public Receiver_bytesBlank(DataInputStream channel_one,Socket socket, String filename){
		this.input_channel = channel_one;
		
		this.new_socket = socket;
		this.filename = filename;
	}
	
	@Override
	public void run() {
		
		try {
			
			String msg;
			
			msg= input_channel.readUTF();//size
			
			/***/
			int Doc_size = Integer.valueOf(msg);
			String workingDir = System.getProperty("user.dir");
			String createfileName = workingDir + "/" + filename;
			FileOutputStream fr = new FileOutputStream(createfileName);
			
			int size = 1500;
			
			byte[] buffer = new byte[1500];
			
			int number_read;
			while ((number_read = input_channel.read(buffer, 0, size)) > 0){
				
				if(number_read <= 2) break;
				
				fr.write(buffer, 0, size);
				Doc_size -= size;
				
				if (Doc_size < 1500)
					size = Doc_size;
				
			}
			
			//System.out.println("Receiver_bytesBlank: File finished transferring");
			//System.out.println("Receiver_bytesBlank: Closing FileOutputStream");
			fr.close();
			/***/
			
			input_channel.close();
			
			new_socket.close();
			
		} 
		catch (EOFException e) {
			//Other side shutdown
		} 
		catch (Exception e) {
			//System.exit(1);
			System.err.println("Receiver_bytesBlank: Receive exception er: "+ e.getMessage() );
			//System.exit(1);
		}
	}
	

}
