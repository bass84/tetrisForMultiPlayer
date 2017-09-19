package main.pages;


import main.socket.ConnectListener;
import main.socket.Connectable;
import main.socket.SocketThread;
import main.socket.TetrisClientSocket;
import main.socket.TetrisServerSocket;
import navigator.Navigator;
import pages.IPage;
import processing.core.PApplet;
import processing.core.PFont;

public class WaitingGamePage extends IPage implements ConnectListener{
	
	private PApplet pApplet;
	private PFont mono;
	private int interval;
	private boolean isDisplay;
	private SocketThread socketThread;
	private Thread thread;
	private Connectable tetrisSocket;
	
	public WaitingGamePage(int port, Navigator navigator, PApplet pApplet, int socketCode) {
		this.navigator = navigator;
		this.pApplet = pApplet;
		this.interval = 0;
		this.isDisplay = true;
		this.tetrisSocket = socketCode == 1 ? new TetrisClientSocket(port) : new TetrisServerSocket(port);
		this.tetrisSocket.setConnectListener(this);
		this.socketThread = new SocketThread(this.tetrisSocket);
		this.thread = new Thread(this.socketThread);
		this.thread.start();
	}
	
	public Navigator getNavigator() {
		return this.navigator;
	}
	
	public PApplet getPApplet() {
		return this.pApplet;
	}
	
	
	@Override
	public void drawPage() {
		this.increaseInterval();
		
		this.pApplet.background(255);
		this.mono = this.pApplet.createFont("mono", pApplet.width / 20);
		this.pApplet.fill(0, 0, 0);
		this.pApplet.textFont(this.mono);
		this.pApplet.text("이전으로 돌아가시려면 q를 눌러주세요."
				, 0 + (pApplet.width / 9.8f)
				, 4 + (pApplet.height / 1.2f));
		
		if(this.isDisplay) {
			this.pApplet.text("상대방의 연결을 기다리는 중입니다..."
					, 0 + (pApplet.width / 9.8f)
					, 0 + (pApplet.height / 2.3f));
		}
	}
	
	private void increaseInterval() {
		if(this.interval >= 60) {
			this.isDisplay = this.isDisplay ? false : true;
			this.interval = 0;
		}
		else this.interval += 1;
	}
	
	@Override
	public void keyPressed(int keyCode) {
		switch(keyCode) {
			case 81 :
				this.tetrisSocket.disConnect();
				this.navigator.pop();
				break;
		}
		
	}

	@Override
	public void onConnected() {
		PlayPage page = new PlayPage(this.pApplet, 2, this.tetrisSocket);
		this.navigator.push(page);
		this.navigator.peek();
		System.out.println("게임이 시작되었습니다.");
	}


}
