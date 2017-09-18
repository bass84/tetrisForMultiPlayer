package main.pages;

import java.io.OutputStream;

import main.ShapeMapping.Kind;
import main.Tetris;
import main.keyEvent.GameKeyType.Information;
import main.socket.Connectable;
import main.socket.PlayGameListener;
import main.socket.TetrisServerSocket;
import pages.IPage;
import processing.core.PApplet;

public class PlayPage extends IPage implements PlayGameListener{
	
	private PApplet pApplet;
	private Tetris[] tetris;
	private int localUserNo = 0;
	private int networkUserNo = 0;
	private Connectable tetrisSocket;
	private OutputStream outputStream;
	
	public PlayPage(PApplet pApplet, int totalPerson, Connectable tetrisSocket) {
		this.pApplet = pApplet;
		this.tetris = new Tetris[totalPerson];
		this.tetrisSocket = tetrisSocket;
		this.outputStream = tetrisSocket.getOutputStream();
		this.tetrisSocket.setPlayGameListener(this);
			
		/*서버 소켓으로 접속하면 1P이다.*/
		if(tetrisSocket instanceof TetrisServerSocket) {
			this.tetris[0] = new Tetris(pApplet, 1, totalPerson, true, this);
			this.tetris[0].makeNewShape();
			this.tetris[1] = new Tetris(pApplet, 2, totalPerson, false, null);
			this.localUserNo = 0;
			this.networkUserNo = 1;
		}
		/*클라이언트 소켓으로 접속하면 2P이다.*/
		else {
			this.tetris[0] = new Tetris(pApplet, 1, totalPerson, false, null);
			this.tetris[1] = new Tetris(pApplet, 2, totalPerson, true, this);
			this.tetris[1].makeNewShape();
			this.localUserNo = 1;
			this.networkUserNo = 0;
		}
		this.pApplet.getSurface().setSize(this.pApplet.width * 2, this.pApplet.height);
	}
	

	@Override
	public void drawPage() {
			this.tetris[this.localUserNo].drawTetris();
			/*시간이 되면 도형이 내려가는 프로세스*/
			if(this.pApplet.frameCount % this.tetris[this.localUserNo].getInterval() == 0) {
				this.tetris[this.localUserNo].move(Information.MOVE_SHAPE_DOWN_BY_TIME);
			}
			
			this.tetris[this.networkUserNo].drawTetris();
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
	
	@Override
	public void keyPressed(int keyCode) {
		switch(keyCode) {
			case 32 :
				this.tetris[this.localUserNo].move(Information.DROP_SHAPE);
				break;
			case 37 :
				this.tetris[this.localUserNo].move(Information.MOVE_SHAPE_LEFT);
				break;
			case 38 :
				this.tetris[this.localUserNo].move(Information.ROTATE_SHAPE);
				break;
			case 39 :
				this.tetris[this.localUserNo].move(Information.MOVE_SHAPE_RIGHT);
				break;
			case 40 : 
				this.tetris[this.localUserNo].move(Information.MOVE_SHAPE_DOWN);
				break;
		}
		
		
	}
	
	
	/*로컬 사용자의 도형 정보를 보낸다.*/
	@Override
	public void sendTetrisInfo() {
		String shapeInfo = "S:" + this.tetris[this.localUserNo].getShape().getShapeKind().getValue() + "\n";
		
		try {
			this.outputStream.write(shapeInfo.getBytes());
			System.out.println("**테트리스 도형에 대한 정보를 전송하였습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*로컬 사용자의 움직임 정보를 보낸다.*/
	@Override
	public void sendTetrisInfo(Information information) {
		String movingInfo = "M:" + Information.valueOf(information.name()) + "\n";
		
		try {
			this.outputStream.write(movingInfo.getBytes());
			System.out.println("** 테트리스 위치에 대한 정보를 전송하였습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	@Override
	/*클라이언트 사용자가 보내온 정보를 분석하여 도형을 그리거나 위치를 조정한다.*/
	public void resolveNetworkUserValue(String response) {
		if(response == null || response.equals("")) return;
		
		String[] responseArray = response.split(":");
		/*'S'이면 도형이다.*/
		if(responseArray[0].equals("S")) this.tetris[this.networkUserNo].makeNewShape(responseArray[1]);
		/*'M'이면 움직임에 관한 것이다.*/
		else if(responseArray[0].equals("M")) this.tetris[this.networkUserNo].move(Information.valueOf(responseArray[1]));
		
	}
	
	

}
