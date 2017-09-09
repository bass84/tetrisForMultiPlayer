package main.pages;

import navigator.Navigator;
import pages.IPage;
import processing.core.PApplet;
import processing.core.PFont;

public class BeforeStartPage extends IPage{
	
	public static final float ratio = 7.2f / 4.0f; //창의 비율
	private PFont mono;
	private PApplet pApplet;
	private String keyValue;
	
	public BeforeStartPage(PApplet pApplet, Navigator navigator) {
		this.pApplet = pApplet;
		this.navigator = navigator;
		this.navigator.push(this);
		this.navigator.peek();
		this.keyValue = "";
	}

	@Override
	public void drawPage() {
		// background draw text
		this.pApplet.background(255);
		this.mono = this.pApplet.createFont("mono", pApplet.width / 20);
		this.pApplet.fill(0, 0, 0);
		this.pApplet.textFont(this.mono);
		this.pApplet.text("1를 누르시면 1인용 게임을 시작합니다."
				, 0 + (pApplet.width / 6.8f)
				, 0 + (pApplet.height / 2.3f));
		this.pApplet.text("2를 누르시면 2인용 게임을 시작합니다."
				, 0 + (pApplet.width / 6.8f)
				, 3 + (pApplet.height / 1.86f));
		
		this.pApplet.text(this.keyValue
				, 0 + (pApplet.width / 5.8f)
				, 5 + (pApplet.height / 3.86f));
		
	}

	
	@Override
	public void keyPressed(int keyCode) {
		
		switch(keyCode) {
			case 49 :	// press 's'
				System.out.println("pressed 1");
				//this.navigator.push(new PlayPage(this.pApplet, 1));
				this.navigator.peek();
				break;
			case 50 :	// press 'm'
				System.out.println("pressed 2");
				this.navigator.push(new RoomMakingPage(this.pApplet, this.navigator));
				this.navigator.peek();
				break;
			case 57 :
				this.keyValue += (keyCode - 48);
				break;
			case 8 :
				this.keyValue = this.keyValue.substring(0, (this.keyValue.length() - 1) >= 0 ? this.keyValue.length() - 1 : 0);
				break;
			
				
		}
	}
	

}
