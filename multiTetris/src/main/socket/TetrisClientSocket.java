package main.socket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TetrisClientSocket {
	
	private int port;
	private Socket socket;
	
	public TetrisClientSocket(int port) {
		this.port = port;
	}
	
	public void connect() {
		
		try {
			this.socket = new Socket("localhost", port);
			
			System.out.println(">> PORT(" + port + ")로 접속을 시도합니다.");
			
			InputStream stream = this.socket.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String response = null;
			
			while(true) {
				response = br.readLine();
				
				System.out.println(">> 수신 내용 : " + response);
				
			}
			
			//socket.close();
			
			//System.exit(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
