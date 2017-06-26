package main.keyEvent;

import main.Grid;
import main.Shape;
import main.ShapeMapping.Kind;
import main.display.PlayDisplay;
import navigator.Navigator;
import pages.IGamePageDisplay;
import pages.IGamePageEventProcessor;
import processing.core.PApplet;

public class PlayEventProcessor implements IGamePageEventProcessor{

	private PApplet pApplet;
	private Navigator navigator;
	private PlayDisplay display;
	
	public PlayEventProcessor(PApplet pApplet, Navigator navigator) {
		this.pApplet = pApplet;
		this.navigator = navigator;
	}
	
	@Override
	public void setDisplay(IGamePageDisplay display) {
		this.display = (PlayDisplay) display;
		
	}
	@Override
	public void keyPressed(int keyCode) {
		
		Grid grid = this.display.getGrid();
		Shape shape = this.display.getShape();
		int[][] usedBlock = this.display.getUsedBlock();
		
		
		if(grid.isBottom(usedBlock, shape)) return;
		switch(keyCode) {
			case(37) :	//left
				if(!grid.isLeftEnd(usedBlock, shape)) shape.decreasePositionX();
				break;
			
			case(38) :	//up
				if(shape.getShapeKind() == Kind.O || !grid.isPossibleRotation(usedBlock, shape)) return;
				shape.rotate();
				shape.increaseRotationIdx();
				break;
			
			case(39) :	//right
				if(!grid.isRightEnd(usedBlock, shape)) shape.increasePositionX();
				break;
			
			case(40) :	//down
				if(!grid.isBottom(usedBlock, shape)) shape.increasePositionY();
				break;
			
			case(80) :	// 'p' - pause
				//this.navigator.push(new MenuPage(this.navigator));
				//this.navigator.peek();
				break;
		}
	
		
	}
	

}
