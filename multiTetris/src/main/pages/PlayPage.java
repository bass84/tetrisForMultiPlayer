package main.pages;

import main.display.Background;
import main.display.PlayDisplay;
import main.keyEvent.PlayEventProcessor;
import navigator.Navigator;
import pages.IPage;
import processing.core.PApplet;

public class PlayPage extends IPage{
	
	
	private PApplet pApplet;
	private Background background;
	
	public PlayPage(PApplet pApplet, Navigator navigator, Background background) {
		this.pApplet = pApplet;
		this.navigator = navigator;
		this.display = new PlayDisplay(pApplet, background);
		this.eventProcessor = new PlayEventProcessor(pApplet, navigator);
		this.background = background;
		this.eventProcessor.setDisplay(this.display);
		this.navigator.push(this);
		this.navigator.peek();
		this.pApplet.clear();
	}

	@Override
	public void drawPage() throws Exception{
		this.display.drawPage();
	}
	

	@Override
	public void keyPressed(int keyCode) {
		
		this.eventProcessor.keyPressed(keyCode);
		
		
		
	}

}
