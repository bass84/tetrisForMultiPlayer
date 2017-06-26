package main.pages;

import main.display.BeforeStartDisplay;
import main.keyEvent.BeforeStartEventProcessor;
import navigator.Navigator;
import pages.IPage;
import processing.core.PApplet;

public class BeforeStartPage extends IPage{
	
	private static BeforeStartPage beforeStartPage = getBeforeStartPage();
	
	public static BeforeStartPage getBeforeStartPage() {
		if(beforeStartPage == null) beforeStartPage = new BeforeStartPage();
		return beforeStartPage;
	}
	
	public void init(PApplet pApplet, Navigator navigator) {
		this.pApplet = pApplet;
		this.display = new BeforeStartDisplay(pApplet);
		this.eventProcessor = new BeforeStartEventProcessor(pApplet, navigator);
		this.eventProcessor.setDisplay(this.display);
		this.navigator = navigator;
		this.navigator.push(this);
		this.navigator.peek();
	}

	@Override
	public void drawPage() throws Exception {
		this.display.drawPage();
	}

	@Override
	public void keyPressed(int keyCode) {
		this.eventProcessor.keyPressed(keyCode);
	}

}
