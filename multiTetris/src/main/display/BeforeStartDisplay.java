package main.display;

import pages.IGamePageDisplay;
import processing.core.PApplet;
import processing.core.PFont;

public class BeforeStartDisplay implements IGamePageDisplay{

	private PApplet pApplet;
	private Background background;
	
	public BeforeStartDisplay(PApplet pApplet) {
		this.pApplet = pApplet;
		this.background = new Background(pApplet);
	}
	
	public Background getBackground(int playerPerson) {
		if(playerPerson != 1) {
			Background.playerPerson = playerPerson;
			this.background.setSize();
		}
		
		return this.background;
	}
	
	@Override
	public void drawPage() {
		
		this.background.drawBackground();
		
		this.background.drawBeforeGameMenuText();
		
	}
}
