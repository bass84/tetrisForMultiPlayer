package main.pages;

import main.Shape;
import main.ShapeMapping.Kind;
import main.Tetris;
import pages.IPage;
import processing.core.PApplet;
import processing.core.PFont;

public class PlayPage extends IPage{
	
	private PApplet pApplet;
	private PFont mono;
	//private int player;
	private Tetris[] tetris;
	

	
	public PlayPage(PApplet pApplet, int player) {
		this.pApplet = pApplet;
		//this.player = player;
		this.tetris = new Tetris[player];
		for(int i = 0; i < this.tetris.length; i++) {
			this.tetris[i] = new Tetris(pApplet, i + 1);
		}
	}
	
	

	@Override
	public void drawPage() {
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
