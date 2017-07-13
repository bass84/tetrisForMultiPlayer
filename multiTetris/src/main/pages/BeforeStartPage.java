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
		this.keyValue = "Type something ";
	}

	@Override
	public void drawPage() {
		// background draw text
		this.mono = this.pApplet.createFont("mono", pApplet.width / 20);
		this.pApplet.fill(0, 0, 0);
		this.pApplet.textFont(this.mono);
		this.pApplet.text("PRESS '1' Then Play Single"
				, 0 + (pApplet.width / 4.8f)
				, 0 + (pApplet.height / 2.3f));
		this.pApplet.text("PRESS '2' Then Play Multi"
				, 0 + (pApplet.width / 4.8f)
				, 3 + (pApplet.height / 1.86f));
		
		this.pApplet.text(this.keyValue
				, 0 + (pApplet.width / 4.8f)
				, 20 + (pApplet.height / 1.86f));
		
		PApplet.println(this.keyValue);
		
		
		
	}

	@Override
	public void keyPressed(int keyCode) {
		
		switch(keyCode) {
			case 77 :	// press '1'
				System.out.println("pressed m");
				this.navigator.push(new PlayPage(this.pApplet, 1));
				this.navigator.peek();
				break;
			case 83 :	// press '2'
				System.out.println("pressed s");
				this.navigator.push(new PlayPage(this.pApplet, 2));
				this.navigator.peek();
				break;
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
				this.keyValue += (keyCode - 48);
				break;
			case 8 :
				this.keyValue = this.keyValue.substring(0, (this.keyValue.length() - 1) >= 0 ? this.keyValue.length() - 1 : 0);
				this.pApplet.clear();
				break;
			
				
		}
	}
	

}
