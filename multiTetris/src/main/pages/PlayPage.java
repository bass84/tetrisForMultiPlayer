package main.pages;

import main.Tetris;
import pages.IPage;
import processing.core.PApplet;

public class PlayPage extends IPage{
	
	private PApplet pApplet;
	private Tetris[] tetris;
	
	public PlayPage(PApplet pApplet, int player) {
		this.pApplet = pApplet;
		this.tetris = new Tetris[player];
		for(int i = 0; i < this.tetris.length; i++) {
			this.tetris[i] = new Tetris(pApplet, i + 1, player);
		}
		this.pApplet.getSurface().setSize(this.pApplet.width * 2, this.pApplet.height);
	}
	

	@Override
	public void drawPage() {
		this.pApplet.clear();
		for(int i = 0; i < this.tetris.length; i++) {
			this.tetris[i].drawTetris();
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		
		switch(keyCode) {
			case 37 : 
				this.tetris[1].moveShapeLeft();
				break;
			case 38 : 
				this.tetris[1].rotateShape();
				break;
			case 39 : 
				this.tetris[1].moveShapeRight();
				break;
			case 40 : 
				this.tetris[1].moveShapeDown();
				break;
			case 68 :
				this.tetris[0].moveShapeLeft();
				break;
			case 82 :
				this.tetris[0].rotateShape();
				break;
			case 71 :
				this.tetris[0].moveShapeRight();
				break;
			case 70 :
				this.tetris[0].moveShapeDown();
				break;
		}
		
		
	}
	
	

}
