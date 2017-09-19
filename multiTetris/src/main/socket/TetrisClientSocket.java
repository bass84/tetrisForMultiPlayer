package main.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TetrisClientSocket implements Connectable{
	
	private final int port;
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private BufferedReader bufferedReader;
	private String response;
	private ConnectListener connectListener;
	private PlayGameListener playGameListener;
	
	
	public TetrisClientSocket(int port) {
		this.port = port;
	}
	
	public void connect() {
		
		try {
			this.socket = new Socket("localhost", this.port);
			
			System.out.println(">> PORT(" + this.port + ")로 접속을 시도합니다.");
			
			this.inputStream = this.socket.getInputStream();
			this.outputStream = this.socket.getOutputStream();
			
			this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			while(true) {
				this.response = this.bufferedReader.readLine();
				System.out.println(">> 서버로부터 온 수신 내용 : " + response);
				this.playGameListener.resolveNetworkUserValue(response);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disConnect() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void setConnectListener(ConnectListener listener) {
		this.connectListener = listener;
	}
	
	@Override
	public void setPlayGameListener(PlayGameListener playGameListener) {
		this.playGameListener = playGameListener;
	}
	
	@Override
	public OutputStream getOutputStream(){
		return this.outputStream;
	}
}
