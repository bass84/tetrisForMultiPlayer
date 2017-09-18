package main.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TetrisServerSocket implements Connectable{
	private final int PORT;
	private ServerSocket serverSocket;
	private Socket socket;
	private OutputStream outputStream;
	private ConnectListener connectListener;
	private PlayGameListener playGameListener;
	
	private InputStream inputStream = null;
	private BufferedReader bufferedReader = null;
	private String response = null;
	
	public TetrisServerSocket() {
		this.PORT = 0;
	}
	
	public TetrisServerSocket(int port) {
		this.PORT = port;
		this.serverSocket = null;
		this.socket = null;
		
	}

	@Override
	public void setConnectListener(ConnectListener listener) {
		this.connectListener = listener;
	}
	
	@Override
	public void setPlayGameListener(PlayGameListener playGameListener) {
		this.playGameListener = playGameListener;
	}
	
	
	public OutputStream getOutputStream(){
		return this.outputStream;
	}

	@Override
	public void connect() {
		
		try {
			this.serverSocket = new ServerSocket(PORT);
			
			System.out.println(">> 서버 소켓의 연결을 기다립니다.");
			
			this.socket = serverSocket.accept();
			
			System.out.println(">> 서버 소켓이 " + this.socket.getInetAddress() 
			+ " 클라이언트와 " + this.socket.getPort() + "포트로 연결되었습니다.");
			
			
			try {
				this.outputStream = this.socket.getOutputStream();
				
				this.outputStream.write("==== 안녕하세요? 테트리스 서버에 들어오신 것을 환영합니다. ====".getBytes());
				//stream.write(new Date().toString().getBytes());
				this.outputStream.write("\n".getBytes());
				
				this.connectListener.onConnected();
				
				inputStream = this.socket.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				
				while(true) {
					response = bufferedReader.readLine();
					System.out.println(">> 클라이언트로부터 온 수신 내용 : " + response);
					this.playGameListener.resolveNetworkUserValue(response);
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public void disConnect() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
}
