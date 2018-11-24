import java.io.DataInputStream;
import java.io.EOFException;
import java.net.ServerSocket;

public class Receive implements Runnable {
	
	DataInputStream data_input;
	
	public Receive(DataInputStream data_input){
		this.data_input= data_input;
		
	}
	
	@Override
	public void run() {
		
		try{			
			String msg;
			
			while(true){
				
				msg= data_input.readUTF();
				
				if(msg.equals("0b10")){
					
					int portNum = data_input.readInt();//PortNum from Connect method
					
					//System.out.println("Receive: I'm being given portNum:" + portNum);
					
					//System.out.println("Receive: Creating 'serverSocket'");
					
					ServerSocket serverSocket = new ServerSocket(portNum);//Gets closed inside the filename_filebytes2 thread 
					
					//System.out.println("Receive: Launching filename_filebytes2 thread.");
					
					new Thread(new filename_filebytes2(serverSocket)).start();
					
					//System.out.println();
					
				}
				else{
					
					System.out.println(msg);
				}
				
			}
		}
		catch(EOFException e){
			//Other side shutdown
		}
		catch(Exception e){
			System.err.println("Receive exception: "+ e.getMessage() );
		}
		
		System.exit(0);
		
	}

}
