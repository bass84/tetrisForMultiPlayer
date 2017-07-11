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
		//this.pApplet.clear();
		
		for(int i = 0; i < this.tetris.length; i++) {
			//if(i == 0) this.pApplet.clear();
			
			this.tetris[i].drawTetris();
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		
		switch(keyCode) {
			case 32 :
				this.tetris[1].dropShape();
				break;
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
				
			case 9 :
				this.tetris[0].dropShape();
				break;
			case 68 : //d
				this.tetris[0].moveShapeLeft();
				break;
			case 82 : //r
				this.tetris[0].rotateShape();
				break;
			case 71 : //g
				this.tetris[0].moveShapeRight();
				break;
			case 70 : //f
				this.tetris[0].moveShapeDown();
				break;
		}
		
		
	}
	
	

}
