package main.pages;

import main.Tetris;
import main.keyEvent.GameKeyType.GameKey;
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
		for(int i = 0; i < this.tetris.length; i++) {
			this.tetris[i].drawTetris();
			
			/*시간이 되면 도형이 내려가는 프로세스*/
			if(this.pApplet.frameCount % this.tetris[i].getInterval() == 0) {
				this.tetris[i].move(GameKey.MOVE_SHAPE_DOWN_BY_TIME);
			}
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		
		
		switch(keyCode) {
			case 32 :
				this.tetris[1].move(GameKey.DROP_SHAPE);
				break;
			case 37 :
				this.tetris[1].move(GameKey.MOVE_SHAPE_LEFT);
				break;
			case 38 :
				this.tetris[1].move(GameKey.ROTATE_SHAPE);
				break;
			case 39 :
				this.tetris[1].move(GameKey.MOVE_SHAPE_RIGHT);
				break;
			case 40 : 
				this.tetris[1].move(GameKey.MOVE_SHAPE_DOWN);
				break;
				
			case 9 :
				this.tetris[0].move(GameKey.DROP_SHAPE);
				break;
			case 68 : //d
				this.tetris[0].move(GameKey.MOVE_SHAPE_LEFT);
				break;
			case 82 : //r
				this.tetris[0].move(GameKey.ROTATE_SHAPE);
				break;
			case 71 : //g
				this.tetris[0].move(GameKey.MOVE_SHAPE_RIGHT);
				break;
			case 70 : //f
				this.tetris[0].move(GameKey.MOVE_SHAPE_DOWN);
				break;
		}
		
		
	}
	
	

}
