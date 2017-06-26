package main.keyEvent;

import main.display.BeforeStartDisplay;
import main.pages.PlayPage;
import navigator.Navigator;
import pages.IGamePageDisplay;
import pages.IGamePageEventProcessor;
import processing.core.PApplet;

public class BeforeStartEventProcessor implements IGamePageEventProcessor{

	private PApplet pApplet;
	private Navigator navigator;
	private BeforeStartDisplay display;
	
	public BeforeStartEventProcessor(PApplet pApplet, Navigator navigator) {
		this.pApplet = pApplet;
		this.navigator = navigator;
	}
	
	@Override
	public void setDisplay(IGamePageDisplay display) {
		this.display = (BeforeStartDisplay) display;
	}
	
	@Override
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case 49 :	// press '1'
			System.out.println("pressed 1");
			this.navigator.push(new PlayPage(this.pApplet, this.navigator, this.display.getBackground(1)));
			break;
		case 50 :	// press '2'
			System.out.println("pressed 2");
			this.navigator.push(new PlayPage(this.pApplet, this.navigator, this.display.getBackground(2)));
			break;
	}
		
	}

	

}
