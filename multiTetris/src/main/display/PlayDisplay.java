package main.display;

import main.Grid;
import main.Shape;
import main.pages.PlayPageDisplay;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class PlayDisplay extends PlayPageDisplay{
	private PApplet pApplet;
	private PFont mono;
	
	private Shape shape;
	private Grid grid;
	private int totalScore = 0;
	private int term = 60;
	private int[][] usedBlock = new int[11][16];
	
	private Background background;
	
	
	public PlayDisplay(PApplet pApplet, Background background) {
		this.pApplet = pApplet;
		this.shape = new Shape(pApplet);
		this.grid = new Grid(pApplet);
		this.background = background;
		
		for(int i = 0; i < this.usedBlock.length; ++i) this.usedBlock[i][15] = -1;
		for(int i = 0; i < this.usedBlock[0].length; ++i) this.usedBlock[0][i] = -1;
	}
	
	public Shape getShape() {
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
	}
	public void setUsedBlock(int[][] usedBlock) {
		for(int i = 0; i < usedBlock.length; i++) {
			for(int j = 0; j < usedBlock[i].length; j++) {
				this.usedBlock[i][j] = usedBlock[i][j];
			}
		}
	}
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
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

	@Override
	public void drawPage() {
		
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
			this.background.drawBackground();
			
			// 도형 전체 그리기
			this.grid.drawShape(this.usedBlock, this.shape);
			
			// 현재 움직이는 도형 그리기
			this.shape.drawShape(this.usedBlock, shape);
			
			// 바닥에 닿지 않으면 계속 내려감
			if(pApplet.frameCount % this.term == 0) this.shape.increasePositionY();
			
			
			// draw text
			this.mono = pApplet.createFont("mono", Background.width / 30);
			pApplet.textFont((PFont) this.mono);
			pApplet.fill(0, 0, 0);
			pApplet.textAlign(PConstants.LEFT, PConstants.CENTER);
			pApplet.text("SCORE : " + this.totalScore, Background.offsetX + (2.0f * Background.ratio)
					, Background.offsetY + (8.0f * Background.ratio));
			
			
		}
	}
	
	
}
