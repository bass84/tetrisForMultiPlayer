package main;


import main.ShapeMapping.Kind;
import main.keyEvent.GameKeyType.Information;
import main.socket.PlayGameListener;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Tetris{
	
	public final int widthblock = 10;
	public final int heightblock = 15;
	
	private PApplet pApplet;
	private PFont mono;

	private Shape shape;
	private Grid grid;
	private int totalScore = 0;
	private int interval = 60;
	private int[][] usedBlock = new int[11][16];
	private int player;
	private int[][] shapeInfo;
	
	private float ratio;
	private float width;
	private float height;
	private float offsetX;
	private float offsetY;
	private int totalPerson;
	private boolean isHaveSocket;
	private PlayGameListener playGameListener;
	//private Connectable tetrisSocket;
	//private OutputStream outputStream;
	
	public Tetris(PApplet pApplet, int player, int totalPerson) {
		this.pApplet = pApplet;
		this.grid = new Grid();
		this.player = player;
		this.totalPerson = totalPerson;
		this.shapeInfo = this.shape.getShapeInfo();
		this.ratio = 1.6f;
		
		for(int i = 0; i < this.usedBlock.length; ++i) this.usedBlock[i][15] = -1;
		for(int i = 0; i < this.usedBlock[0].length; ++i) this.usedBlock[0][i] = -1;
	}
	
	public Tetris(PApplet pApplet, int player, int totalPerson, boolean isHaveSocket, PlayGameListener playGameListener) {
		if(isHaveSocket) {
			this.isHaveSocket = isHaveSocket;
			this.pApplet = pApplet;
			//this.tetrisSocket = tetrisSocket;
			//this.outputStream = this.tetrisSocket.getOutputStream();
			this.player = player;
			//this.makeNewShape();
			this.playGameListener = playGameListener;
			this.grid = new Grid();
			this.totalPerson = totalPerson;
			//this.shapeInfo = this.shape.getShapeInfo();
			this.ratio = 1.6f;
			//this.tetrisSocket.setPlayGameListener(this);
			
			for(int i = 0; i < this.usedBlock.length; ++i) this.usedBlock[i][15] = -1;
			for(int i = 0; i < this.usedBlock[0].length; ++i) this.usedBlock[0][i] = -1;
		}else {
			this.isHaveSocket = isHaveSocket;
			this.pApplet = pApplet;
			this.player = player;
			this.totalPerson = totalPerson;
			this.ratio = 1.6f;
			this.grid = new Grid();
			
			for(int i = 0; i < this.usedBlock.length; ++i) this.usedBlock[i][15] = -1;
			for(int i = 0; i < this.usedBlock[0].length; ++i) this.usedBlock[0][i] = -1;
		}
	}
	public boolean isHaveSocket() {
		return this.isHaveSocket;
	}
	
		
	public void setUsedBlock(int[][] usedBlock) {
		for(int i = 0; i < usedBlock.length; i++) {
			for(int j = 0; j < usedBlock[i].length; j++) {
				this.usedBlock[i][j] = usedBlock[i][j];
			}
		}
	}
	
	public int getPlayer() {
		return this.player;
	}

	public int getInterval() {
		return interval;
	}
	
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	public void increaseTotalScore(int addScore) {
		this.totalScore += addScore;
	}
	
	public Shape getShape() {
		return this.shape;
	}
	
	public void makeNewShape() {
		this.shape = new Shape();
		this.shapeInfo = this.shape.getShapeInfo();
		if(this.isHaveSocket) this.playGameListener.sendTetrisInfo();
	}
	
	public void makeNewShape(String shapeKind) {
		this.shape = new Shape(shapeKind);
		this.shapeInfo = this.shape.getShapeInfo();
	}
	
	public void reset() {
		this.totalScore = 0;
		for(int i = 0; i < this.usedBlock.length; ++i) {
			for(int j = 0; j < this.usedBlock[i].length; ++j) {
				if(i == 0 || j == 15) this.usedBlock[i][j] = -1;
				else this.usedBlock[i][j] = 0;
			}
		}
	}
	
	
	public void addUsedBlock(Shape shape, int[][] usedBlock) {
		int positionX = shape.getPositionX();
		int positionY = shape.getPositionY();
		int shapeColor = shape.getShapeColor();
		int shapeInfo[][] = shape.getShapeInfo();
		for(int i = 0; i < shapeInfo.length; i++) {
			usedBlock[shapeInfo[i][0] + 1 + positionX][shapeInfo[i][1] - 1  + positionY] = shapeColor;
		}
	}
	
	public void makeOffsetX() {
		if(this.player == 1)
			this.offsetX = (this.pApplet.width / this.totalPerson - width) / this.totalPerson;
		else if(this.player == 2) 
			this.offsetX = ((this.pApplet.width / this.totalPerson - width) / this.totalPerson) + (this.pApplet.width / this.totalPerson);
	}
	
	public void makeOffsetY() {
		this.offsetY = (this.pApplet.height - height) / this.totalPerson;
	}
	
	
	public void drawBackground(){
		// 현재 창의 크기
		width = this.pApplet.width / this.totalPerson;
		height = this.pApplet.height;
		
		// 창과 게임 영역의 비율 맞추기
		if(width * ratio > height) width = height / ratio;
		else height = ratio * width;
		
		this.makeOffsetX();
		this.makeOffsetY();
		
		// 창 안 게임 영역 그리기
		for(int i = 1; i < usedBlock.length; i++) {
			for(int j = 0; j < usedBlock[i].length - 1; j++) {
				pApplet.fill(255, 255, 242);
				pApplet.rect(
						(i * (width / widthblock)) - (width / widthblock) + offsetX
						, (j * (height / heightblock)) + offsetY
						, width / widthblock
						, height / heightblock);
			}
		}
	}
	
	public boolean drawTetris() {
		if(this.shape != null) {
			// 도형이 바닥에 닿는다면
			if(this.grid.isBottom(this.usedBlock, this.shape)) {
				
				if(this.checkGameOver()) {
					System.out.println("Game Over!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					this.drawGameOver();
					return false;
				}else{
					this.addUsedBlock(shape, usedBlock);
					this.increaseTotalScore(1000);
					//this.shape = new Shape();
					this.makeNewShape();
					this.usedBlock = this.grid.getNewGridLine(this.usedBlock, this.shape, this);
					this.shapeInfo = this.shape.getShapeInfo();
				}
				
			// 도형이 바닥에 닿지 않은 상태일 경우
			}else{
				if(this.player != 1) System.out.println("plyaer2");
				// 배경 그리기
				this.drawBackground();
				
				// 도형 전체 그리기
				this.drawGrid();
				
				// 현재 움직이는 도형 그리기
				this.drawShape();
				
				// draw text
				this.mono = pApplet.createFont("mono", width / 30);
				pApplet.textFont((PFont) this.mono);
				pApplet.fill(0, 0, 0);
				pApplet.textAlign(PConstants.LEFT, PConstants.CENTER);
				pApplet.text("SCORE : " + this.totalScore, offsetX + (2.0f * ratio)
						, offsetY + (8.0f * ratio));
			}
		}
		
		return true;
	}
	
	public void drawGameOver() {
		this.pApplet.fill(0);
		this.pApplet.rect(offsetX, offsetY, width, height);
	}
	
	
	public void drawGrid() {
		for(int i = 1; i < usedBlock.length; i++) {
			for(int j = 0; j < usedBlock[i].length - 1; j++) {
				if(usedBlock[i][j] == -1 || usedBlock[i][j] != 0) {
					pApplet.fill(usedBlock[i][j], 255);
					pApplet.rect(
							(i * (width / widthblock)) - (width / widthblock) + offsetX
							, (j * (height / heightblock)) + offsetY
							, width / widthblock
							, height / heightblock);
				}
			}
		}
	}
	
	
	public void drawShape() {
		int shapeColor = this.shape.getShapeColor();
		for(int i = 0; i < this.shapeInfo.length; i++) {
			pApplet.fill(shapeColor, 255);
			pApplet.rect(
					(this.shapeInfo[i][0] + this.shape.getPositionX()) * (width / widthblock) + offsetX
					, (this.shapeInfo[i][1] + this.shape.getPositionY()) * (height / heightblock) + offsetY
					, (width / widthblock)
					, (height / heightblock));
				
		}
	}
	
	public boolean checkGameOver() {
		for(int i = 0; i < this.shapeInfo.length; i++) {
			if((this.shapeInfo[i][1] + this.shape.getPositionY()) == 0) {
				// 도형 전체 그리기
				this.drawGrid();
				
				// 현재 움직이는 도형 그리기
				this.drawShape();
				return true;
			}
		}
		return false;
	}
	
	public void move(Information information) {
		switch(information) {
			case DROP_SHAPE :
				this.dropShape();
				break;
				
			case MOVE_SHAPE_LEFT :
				this.moveShapeLeft();
				break;
			
			case MOVE_SHAPE_RIGHT :
				this.moveShapeRight();
				break;
				
			case ROTATE_SHAPE :
				this.rotateShape();
				break;
				
			case MOVE_SHAPE_DOWN :
				this.moveShapeDown();
				break;
			
			case MOVE_SHAPE_DOWN_BY_TIME :
				this.moveShapeDownByTime();
				break;
		}
		if(this.isHaveSocket) this.playGameListener.sendTetrisInfo(information);
	}
	
	
	private void moveShapeDownByTime() {
		if(pApplet.frameCount % this.interval == 0) this.shape.increasePositionY();
	}
	
	private void moveShapeLeft() {
		if(!grid.isLeftEnd(usedBlock, shape)) shape.decreasePositionX();
	}
	
	private void moveShapeRight() {
		if(!grid.isRightEnd(usedBlock, shape)) shape.increasePositionX();
	}
	
	private void moveShapeDown() {
		if(!grid.isBottom(usedBlock, shape)) shape.increasePositionY();
	}
	
	private void rotateShape() {
		if(shape.getShapeKind() == Kind.O || !grid.isPossibleRotation(usedBlock, shape)) return;
		shape.rotate();
		shape.increaseRotationIdx();
		this.shapeInfo = this.shape.getShapeInfo();
	}
	
	private void dropShape() {
		while(!this.grid.isBottom(this.usedBlock, this.shape)) {
			this.shape.increasePositionY();
		}
	}

	public void setShapeInfo(int[][] shapeInfo) {
		this.shapeInfo = shapeInfo;
		
	}
	
	
//	/*로컬 사용자의 도형 정보를 보낸다.*/
//	private void sendTetrisInfo() {
//		String shapeInfo = "S:" + this.shape.getShapeKind().getValue() + "\n";
//		
//		try {
//			this.outputStream.write(shapeInfo.getBytes());
//			System.out.println("**테트리스 도형에 대한 정보를 전송하였습니다. player = " + this.player);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/*로컬 사용자의 움직임 정보를 보낸다.*/
//	private void sendTetrisInfo(Information information) {
//		String movingInfo = "M:" + Information.valueOf(information.name()) + "\n";
//		
//		try {
//			this.outputStream.write(movingInfo.getBytes());
//			System.out.println("** 테트리스 위치에 대한 정보를 전송하였습니다.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	/*네트워크 사용자의 값을 분석한다.*/
//	public void resolveNetworkUserValue(String response) {
//		if(response == null || response.equals("")) return;
//		
//		String[] responseArray = response.split(":");
//		/*'S'이면 도형이다.*/
//		if(responseArray[0].equals("S")) this.makeNewShape(responseArray[1]);
//		/*'M'이면 움직임에 관한 것이다.*/
//		else if(responseArray[0].equals("M")) this.move(Information.valueOf(responseArray[1]));
//		
//	}
	
	
}
