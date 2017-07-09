package main;

import main.ShapeMapping.Kind;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Tetris {
	
	public final int widthblock = 10;
	public final int heightblock = 15;
	
	private PApplet pApplet;
	private PFont mono;

	private Shape shape;
	private Grid grid;
	private int totalScore = 0;
	private int term = 60;
	private int[][] usedBlock = new int[11][16];
	private int player;
	private int[][] shapeInfo;
	
	private float ratio;
	private float width;
	private float height;
	private float offsetX;
	private float offsetY;
	private float offsetX2;
	
	public Tetris(PApplet pApplet, int player) {
		this.pApplet = pApplet;
		this.shape = new Shape(pApplet);
		this.grid = new Grid(pApplet);
		this.player = player;
		this.shapeInfo = this.shape.getShapeInfo();
		
		for(int i = 0; i < this.usedBlock.length; ++i) this.usedBlock[i][15] = -1;
		for(int i = 0; i < this.usedBlock[0].length; ++i) this.usedBlock[0][i] = -1;
	}
	

	/*public Shape getShape() {
		return this.shape;
	}
	public int[][] getUsedBlock() {
		return this.usedBlock;
	}
	public Grid getGrid() {
		return this.grid;
	}
	public int getTotalScore() {
		return this.totalScore;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}*/
	public void setUsedBlock(int[][] usedBlock) {
		for(int i = 0; i < usedBlock.length; i++) {
			for(int j = 0; j < usedBlock[i].length; j++) {
				this.usedBlock[i][j] = usedBlock[i][j];
			}
		}
	}
	
	
	/*public void setGrid(Grid grid) {
		this.grid = grid;
	}*/
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	public void increaseTotalScore(int addScore) {
		this.totalScore += addScore;
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
	
	
	public void drawBackground(){
		
		// 현재 창의 크기
		width = this.pApplet.width / this.player;
		height = this.pApplet.height;
		
		ratio = heightblock / widthblock;
		
		// 창과 게임 영역의 비율 맞추기
		if(width * ratio > height) width = height / ratio;
		else height = ratio * width;
		
		offsetX = (this.pApplet.width / player - width) / 2;
		if(player == 2)
			offsetX2 = ((this.pApplet.width / player - width) / 2) + (this.pApplet.width / player);
		offsetY = (this.pApplet.height - height) / 2;
		
		// 창 안 게임 영역 그리기
		this.pApplet.fill(255, 255, 255);
		this.pApplet.rect(offsetX, offsetY, width, height);
		if(player == 2) {
			this.pApplet.rect(offsetX2, offsetY, width, height);
		}
		
	}
	
	public void drawTetris() {
		// 도형이 바닥에 닿는다면
		if(this.grid.isBottom(this.usedBlock, this.shape)) {
			
			if(this.grid.checkGameOver(this.usedBlock, this.shape)) {
				return;
			}else{
				this.addUsedBlock(shape, usedBlock);
				this.increaseTotalScore(1000);
				this.shape = new Shape(this.pApplet);
				this.usedBlock = this.grid.getNewGridLine(this.usedBlock, this.shape, this);
			}
			
			// 도형이 바닥에 닿지 않은 상태일 경우
		}else{
			pApplet.clear();
			
			// 배경 그리기
			//this.drawBackground();
			
			// 도형 전체 그리기
			this.drawGrid();
			
			// 현재 움직이는 도형 그리기
			this.drawShape();
			
			// 바닥에 닿지 않으면 계속 내려감
			if(pApplet.frameCount % this.term == 0) this.shape.increasePositionY();
			
			
			// draw text
			this.mono = pApplet.createFont("mono", width / 30);
			pApplet.textFont((PFont) this.mono);
			pApplet.fill(0, 0, 0);
			pApplet.textAlign(PConstants.LEFT, PConstants.CENTER);
			pApplet.text("SCORE : " + this.totalScore, offsetX + (2.0f * ratio)
					, offsetY + (8.0f * ratio));
			
			
		}
		
	}
	
	
	public void drawGrid() {
		for(int i = 1; i < usedBlock.length; i++) {
			for(int j = 0; j < usedBlock[i].length - 1; j++) {
				if(usedBlock[i][j] == -1 || usedBlock[i][j] != 0) {
					pApplet.fill(usedBlock[i][j], 255);
					if(this.player == 1) {
						System.out.println("$$$$$$$$$$$$$$$$$$$$$$$ grid");
						pApplet.rect(
								(i * (width / widthblock)) - (width / widthblock) + offsetX
								, (j * (height / heightblock)) + offsetY
								, width / widthblock
								, height / heightblock);
						
					}else{
						System.out.println("$$$$$$$$$$$$$$$$$$$$$$$ grid2");
						pApplet.rect(
								(i * (width / widthblock)) - (width / widthblock) + offsetX2
								, (j * (height / heightblock)) + offsetY
								, width / widthblock
								, height / heightblock);
						
					}
					
				}
			}
		}
	}
	
	
	public void drawShape() {
		int shapeColor = this.shape.getShapeColor();
		for(int i = 0; i < this.shapeInfo.length; i++) {
			pApplet.fill(shapeColor, 255);
			
			if(player == 1) {
				pApplet.rect(
						(this.shapeInfo[i][0] + this.shape.getPositionX()) * (width / widthblock) + offsetX
						, (this.shapeInfo[i][1] + this.shape.getPositionY()) * (height / heightblock) + offsetY
						, (width / widthblock)
						, (height/ heightblock));
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$ shape1");
			}
			else {
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$ shape2");
				pApplet.rect(
						(this.shapeInfo[i][0] + this.shape.getPositionX()) * (width / widthblock) + offsetX2
						, (this.shapeInfo[i][1] + this.shape.getPositionY()) * (height / heightblock) + offsetY
						, (width / widthblock)
						, (height/ heightblock));
				
			}
				
		}
	}
	
	
	
	
	
public void keyPressed(int keyCode) {
		
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
