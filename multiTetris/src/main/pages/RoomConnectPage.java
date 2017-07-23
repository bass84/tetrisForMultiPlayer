package main.pages;

import main.RoomConnect.ConnectType;
import main.socket.SocketThread;
import main.socket.TetrisServerSocket;
import navigator.Navigator;
import pages.IPage;
import processing.core.PApplet;
import processing.core.PFont;

public class RoomConnectPage extends IPage{
	private PApplet pApplet;
	private PFont mono;
	private String port;
	private ConnectType connectType; // 1 : 다른 방에 연결, 2 : 방 만들기
	private SocketThread socketThread;
	private Thread thread;
	
	public RoomConnectPage(PApplet pApplet, ConnectType connectType, Navigator navigator) {
		this.pApplet = pApplet;
		this.connectType = connectType;
		this.navigator = navigator;
		this.port = new String();
	}

	@Override
	public void drawPage() {
		// background draw text
		this.pApplet.background(255);
		this.mono = this.pApplet.createFont("mono", pApplet.width / 20);
		this.pApplet.fill(0, 0, 0);
		this.pApplet.textFont(this.mono);
		
		if(this.connectType == ConnectType.ROOM_CONNECT) {
			
			
		}else if(this.connectType == ConnectType.ROOM_MAKING) {
			this.pApplet.text("port 번호를 입력하고 enter를 눌러주세요."
					, 0 + (pApplet.width / 9.8f)
					, 0 + (pApplet.height / 2.3f));
			this.pApplet.text(this.port
					, 0 + (pApplet.width / 6.8f)
					, 2 + (pApplet.height / 2.1f));
		}
		
	}

	@Override
	public void keyPressed(int keyCode) {
		System.out.println(keyCode);
		switch(keyCode) {
			case 48 :
			case 49 :
			case 50 :
			case 51 :
			case 52 :
			case 53 :
			case 54 :
			case 55 :
			case 56 :
			case 57 :
				if(this.connectType == ConnectType.ROOM_CONNECT) {
					//this.port += port.length() > 3 ? "" : (keyCode - 48);
				}else if(this.connectType == ConnectType.ROOM_MAKING) {
					this.port += port.length() > 3 ? "" : (keyCode - 48);
				}
				break;
			case 8 : // pressed 'backspace"
				this.port = this.port.substring(0, (this.port.length() - 1) >= 0 ? this.port.length() - 1 : 0);
				break;
			case 10 : //pressed 'enter'
				if(this.connectType == ConnectType.ROOM_CONNECT) {
					//this.port += port.length() > 3 ? "" : (keyCode - 48);
				}else if(this.connectType == ConnectType.ROOM_MAKING) {
					if(this.port.length() != 4) return;
					this.navigator.push(new WaitingGamePage(Integer.parseInt(this.port), this.navigator, this.pApplet));
					this.navigator.peek();
					//this.socketThread = new SocketThread(new TetrisServerSocket(Integer.parseInt(this.port), this.navigator, this.pApplet));
					//this.thread = new Thread(this.socketThread);
					//this.thread.start();
					
					
				}
				break;
				
		}
		
	}

}
