package main.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import main.pages.PlayPage;
import navigator.Navigator;
import pages.IPage;
import processing.core.PApplet;

public class TetrisServerSocket implements Connectable{

	private final int PORT;
	private Navigator navigator;
	private PApplet pApplet;
	private ServerSocket serverSocket;
	private Socket socket;
	
	public TetrisServerSocket() {
		this.PORT = 0;
	}
	public TetrisServerSocket(int port, Navigator navigator, PApplet pApplet) {
		this.PORT = port;
		this.navigator = navigator;
		this.pApplet = pApplet;
		this.serverSocket = null;
		this.socket = null;
	}


	@Override
	public void connect() {
		
		try {
			this.serverSocket = new ServerSocket(PORT);
			
			System.out.println(">> 서버 소켓의 연결을 기다립니다.");
			
			this.socket = serverSocket.accept();
			
			System.out.println(">> 서버 소켓이 " + this.socket.getInetAddress() 
			+ " 클라이언트와 " + this.socket.getPort() + "포트로 연결되었습니다.");
			
			this.navigator.push(new PlayPage(this.pApplet, 2));
			this.navigator.peek();
			System.out.println("게임이 시작되었습니다.");
			
			try {
				OutputStream stream = this.socket.getOutputStream();
				stream.write("==== 안녕하세요? 테트리스 서버에 들어오신 것을 환영합니다. ====".getBytes());
				stream.write(new Date().toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//socket.close();
			}
			
			/*while(true) {
				
			}*/
			
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
