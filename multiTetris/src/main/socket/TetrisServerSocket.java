package main.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import main.pages.PlayPage;
import main.pages.WaitingGamePage;
import navigator.Navigator;
import processing.core.PApplet;

public class TetrisServerSocket implements Connectable{
	private final int PORT;
	private ServerSocket serverSocket;
	private Socket socket;
	private OutputStream stream;
	private ConnectListener connectListener;
	private PlayGameListener playGameListener;
	private boolean isGameOver;
	
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
		return this.stream;
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
				this.stream = this.socket.getOutputStream();
				this.connectListener.onConnected();
				
				this.stream.write("==== 안녕하세요? 테트리스 서버에 들어오신 것을 환영합니다. ====".getBytes());
				//stream.write(new Date().toString().getBytes());
				this.stream.write("\n".getBytes());
				
				while(!isGameOver) {
					
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//socket.close();
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void send() {
		
	}
	
	@Override
	public void receive() {
		
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
