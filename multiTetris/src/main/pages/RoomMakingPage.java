package main.pages;

import main.RoomConnect.ConnectType;
import navigator.Navigator;
import pages.IPage;
import processing.core.PApplet;
import processing.core.PFont;

public class RoomMakingPage extends IPage{
	private PApplet pApplet;
	private PFont mono;
	private String keyValue;
	
	public RoomMakingPage(PApplet pApplet, Navigator navigator) {
		this.pApplet = pApplet;
		this.navigator = navigator;
		
	}

	@Override
	public void drawPage() {
		this.pApplet.background(255);
		this.mono = this.pApplet.createFont("mono", pApplet.width / 20);
		this.pApplet.fill(0, 0, 0);
		this.pApplet.textFont(this.mono);
		this.pApplet.text("1을 누르시면 다른 사용자의 방에 연결합니다."
				, 0 + (pApplet.width / 9.8f)
				, 0 + (pApplet.height / 2.3f));
		this.pApplet.text("2를 누르시면 새로운 방을 만듭니다."
				, 0 + (pApplet.width / 9.8f)
				, 3 + (pApplet.height / 1.86f));
		this.pApplet.text("이전으로 돌아가시려면 q를 눌러주세요."
				, 0 + (pApplet.width / 9.8f)
				, 4 + (pApplet.height / 1.2f));
	}

	@Override
	public void keyPressed(int keyCode) {
		
		switch(keyCode) {
			case 49 :	// press '1'
				System.out.println("pressed 1");
				//this.navigator.push(new PlayPage(this.pApplet, 1));
				//this.navigator.peek();
				break;
			case 50 :	// press '2'
				System.out.println("pressed 2");
				this.navigator.push(new RoomConnectPage(this.pApplet, ConnectType.ROOM_MAKING, navigator));
				this.navigator.peek();
				break;
				
			case 81 :
				//System.out.println("pressed q");
				this.navigator.pop();
				break;
		}
	}


}
