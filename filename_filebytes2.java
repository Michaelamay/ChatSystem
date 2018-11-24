import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class filename_filebytes2 implements Runnable {
	ServerSocket ss_secret;

	
	public filename_filebytes2(ServerSocket ss) {
		this.ss_secret = ss;		
	}
	
	@Override
	public void run() {
		
		try{
			
			String msg;
			
			
				
				//System.out.println("filename_filebytes2: Creating 'listen_socket'");
				Socket listen_socket = ss_secret.accept();
				//System.out.println("filename_filebytes2: A socket connected to 'serverSocket'!");
				
				//System.out.println("filename_filebytes2: opening 'input_channel'");
				DataInputStream input_channel= new DataInputStream(listen_socket.getInputStream());
				
				msg = input_channel.readUTF();//filename
				//System.out.println("filename_filebytes2: Received file requested: "+ msg);
								
				
				String workingDir = System.getProperty("user.dir");
				String createfileName = workingDir+"/"+ msg;
				File file = new File(createfileName);
				
				//System.out.println("filename_filebytes2: opening 'output_channel'");
				DataOutputStream output_channel = new DataOutputStream(listen_socket.getOutputStream());
				
				if(file.exists()) {
					//System.out.println("filename_filebytes: file exist");
					long bytes_size = file.length();
					int file_size = (int) bytes_size;
					String string_size = String.valueOf(file_size);
					
					//System.out.println("filename_filebytes: Sending file size");
					output_channel.writeUTF(string_size);//size
					
					FileInputStream fr = new FileInputStream(createfileName);
					
					int number_read = 0;
					byte b[] = new byte[1500];
					while( (number_read = fr.read(b, 0, b.length)) != -1){
						//System.out.println("filename_filebytes: number_read: "+ number_read);
						output_channel.write(b,0,b.length);//bytes
					}
					
					//System.out.println("filename_filebytes2: File Transmitted Successfully");

					//System.out.println("filename_filebytes2: closing FileInputStream of the file that exist");
					fr.close();

				}
				
				//System.out.println("filename_filebytes2: closing 'input_channel'");
				input_channel.close();
				//System.out.println("filename_filebytes: closing 'output_channel'");
				output_channel.close();
				//System.out.println("filename_filebytes2: closing 'listen_socket'");
				listen_socket.close();
				//System.out.println("filename_filebytes2: closing 'serverSocket'");
				ss_secret.close();
				//System.out.println();
			
		}
		catch(EOFException e){
			//Other side shutdown
			
		}
		
		catch(Exception e){
			//Getting an exception here is perfectly mind because the ss_secret socket gets closed in the main program while this thread remains running in the background therefore exiting the system with parameter one is appropriate
			System.err.println("filename_filebytes: Normal message - ss_secret closed in main program "+ e.getMessage() );
			//System.exit(1);
		}
		
	}

}
