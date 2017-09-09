package main.pages;

import main.ShapeMapping.Kind;
import main.Tetris;
import main.keyEvent.GameKeyType.Information;
import main.socket.Connectable;
import main.socket.PlayGameListener;
import pages.IPage;
import processing.core.PApplet;

public class PlayPage extends IPage implements PlayGameListener{
	
	private PApplet pApplet;
	private Tetris[] tetris;
	
	public PlayPage(PApplet pApplet, int player, Connectable tetrisServerSocket) {
		this.pApplet = pApplet;
		this.tetris = new Tetris[player];
		for(int i = 0; i < this.tetris.length; i++) {
			this.tetris[0] = new Tetris(pApplet, i, player, tetrisServerSocket);
			this.tetris[1] = new Tetris(pApplet, i + 1, player, tetrisServerSocket);
		}
		this.pApplet.getSurface().setSize(this.pApplet.width * 2, this.pApplet.height);
	}
	

	@Override
	public void drawPage() {
		for(int i = 0; i < this.tetris.length; i++) {
			this.tetris[i].drawTetris();
			
			/*시간이 되면 도형이 내려가는 프로세스*/
			if(this.pApplet.frameCount % this.tetris[i].getInterval() == 0) {
				this.tetris[i].move(Information.MOVE_SHAPE_DOWN_BY_TIME);
			}
		}
	}
	
	public int getShapeValue(Kind kind) {
		//I(7), J(8), L(9), O(10), S(11), T(12), Z(13)
		switch(kind) {
			case I :
				return 7;
			case J :
				return 8;
			case L :
				return 9;
			case O :
				return 10;
			case S :
				return 11;
			case T :
				return 12;
			case Z :
				return 13;
			default :
				return 0;
		}
	}
	public int add(int value) {
		return value * 2;
	}
	
	interface A{
		int d(int i);
	}
	@Override
	public void keyPressed(int keyCode) {
		switch(keyCode) {
			case 32 :
				this.tetris[1].move(Information.DROP_SHAPE);
				break;
			case 37 :
				this.tetris[1].move(Information.MOVE_SHAPE_LEFT);
				break;
			case 38 :
				this.tetris[1].move(Information.ROTATE_SHAPE);
				break;
			case 39 :
				this.tetris[1].move(Information.MOVE_SHAPE_RIGHT);
				break;
			case 40 : 
				this.tetris[1].move(Information.MOVE_SHAPE_DOWN);
				break;
				
			case 9 :
				this.tetris[0].move(Information.DROP_SHAPE);
				// send..
				break;
			case 68 : //d
				this.tetris[0].move(Information.MOVE_SHAPE_LEFT);
				break;
			case 82 : //r
				this.tetris[0].move(Information.ROTATE_SHAPE);
				break;
			case 71 : //g
				this.tetris[0].move(Information.MOVE_SHAPE_RIGHT);
				break;
			case 70 : //f
				this.tetris[0].move(Information.MOVE_SHAPE_DOWN);
				break;
		}
		
		
	}
	
	

}
