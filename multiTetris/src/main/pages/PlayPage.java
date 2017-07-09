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
		for(int i = 0; i < this.tetris.length; i++) {
			this.tetris[i].keyPressed(keyCode);
		}
		
	}
	
	

}
