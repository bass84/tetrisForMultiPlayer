package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.pages.PlayPage;
import processing.core.PApplet;

public class Grid {
	private PApplet pApplet;
	
	public Grid(PApplet pApplet) {
		this.pApplet = pApplet;
	}
	
	public boolean isBottom(int[][] usedBlock, Shape shape){
		int[][] shapeInfo = shape.getShapeInfo();
		int positionX = shape.getPositionX();
		int positionY = shape.getPositionY();
		
		for(int i = 0; i < shapeInfo.length; i++) {
			if((shapeInfo[i][0] + positionX) < 10 
					&& (usedBlock[shapeInfo[i][0] + 1 + positionX][shapeInfo[i][1] + positionY] == -1
					|| usedBlock[shapeInfo[i][0] + 1 + positionX][shapeInfo[i][1] + positionY] != 0)) return true;
		}
		
		return false;
	}
	
	public boolean checkGameOver(int[][] usedBlock, Shape shape) {
		int[][] shapeInfo = shape.getShapeInfo();
		int positionY = shape.getPositionY();
		
		for(int i = 0; i < shapeInfo.length; i++) {
			if((shapeInfo[i][1] + positionY) < 1) return true;
		}
		
		return false;
	}
	
	public boolean isLeftEnd(int[][] usedBlock, Shape shape) {
		int[][] shapeInfo = shape.getShapeInfo();
		int positionX = shape.getPositionX();
		int positionY = shape.getPositionY();
		
		for(int i = 0; i < shapeInfo.length; i++) {
			if(usedBlock[shapeInfo[i][0] + positionX][shapeInfo[i][1] + positionY] == -1
					|| usedBlock[shapeInfo[i][0] + positionX][shapeInfo[i][1] + positionY] != 0) return true;
		}
		return false;
	}
	
	public boolean isRightEnd(int[][] usedBlock, Shape shape) {
		int[][] shapeInfo = shape.getShapeInfo();
		int positionX = shape.getPositionX();
		int positionY = shape.getPositionY();
		
		for(int i = 0; i < shapeInfo.length; i++) {
			if((shapeInfo[i][0] + positionX) == 9 
					|| usedBlock[shapeInfo[i][0] + 2 + positionX][shapeInfo[i][1] + positionY] == -1
					|| usedBlock[shapeInfo[i][0] + 2 + positionX][shapeInfo[i][1] + positionY] != 0) return true;
		}
		return false;
	}
	
	public boolean isPossibleRotation(int[][] usedBlock, Shape shape) {
		
		int positionX = shape.getPositionX();
		int positionY = shape.getPositionY();
		
		int newX = 0;
		int newY = 0;
		int[][] nextShapeInfo = shape.getNextShapeInfo();
		
		for(int i = 0; i < nextShapeInfo.length; i++) {
			newX = nextShapeInfo[i][0] + positionX + 1;
			newY = nextShapeInfo[i][1] + positionY;
			if(newX < 1 || newX > 10 || newY < 0 || newY > 14 ) return false;
			if(usedBlock[newX][newY] == -1) return false;
		}
		return true;
	}

	
	/*@Override
	public void drawShape(int[][] usedBlock, Shape shape, int player) {
		for(int i = 1; i < usedBlock.length; i++) {
			for(int j = 0; j < usedBlock[i].length - 1; j++) {
				if(usedBlock[i][j] == -1 || usedBlock[i][j] != 0) {
					pApplet.fill(usedBlock[i][j], 255);
					if(player == 1) {
						pApplet.rect(
								(i * (Tetris.width / Tetris.widthblock)) - (Tetris.width / Tetris.widthblock) + Tetris.offsetX
								, (j * (Tetris.height / Tetris.heightblock)) + Tetris.offsetY
								, Tetris.width / Tetris.widthblock
								, Tetris.height / Tetris.heightblock);
						
					}else{
						pApplet.rect(
								(i * (Tetris.width / Tetris.widthblock)) - (Tetris.width / Tetris.widthblock) + Tetris.offsetX2
								, (j * (Tetris.height / Tetris.heightblock)) + Tetris.offsetY
								, Tetris.width / Tetris.widthblock
								, Tetris.height / Tetris.heightblock);
						
					}
					
				}
			}
		}
	}*/

	public int[][] getNewGridLine(int[][] usedBlock, Shape shape, Tetris playingPage) {
		List<Integer> removeLines = new ArrayList<Integer>();
		
		for(int i = usedBlock[0].length - 2; i >= 0; i--) {	// 행 안에 비어있는 블록이 있는지 체크
			boolean blockEmpty = false;
			for(int j = 1; j < usedBlock.length; j++) {	//만약 한 행 안에 블록이 비어있으면 지워질 행이 없기 때문에 break를 하고 다음 행으로 넘어가서 체크한다.
				if(usedBlock[j][i] == 0) {
					blockEmpty = true;
					break;
				}
			}
			if(!blockEmpty)removeLines.add(i); //꽉 차있는 행이 있으면 그 행 번호를 추가한다.
		}
		if(removeLines.size() > 0) this.removeLines(usedBlock, removeLines, playingPage);
		
		return usedBlock;
	}


	private int[][] removeLines(int[][] usedBlock, List<Integer> removeLines, Tetris PlayingPage) {	//행을 지우는 메서드
		Collections.sort(removeLines);
		
		for(int i = 0; i < removeLines.size(); i++) {
			int heighestLine = this.getHeighestLine(usedBlock, removeLines.get(i));	//지울 행 중 가장 높은 곳에 있는 행 번호를 찾아오는 메서드
			usedBlock = this.moveLines(removeLines.get(i) - 1, heighestLine, usedBlock);
			PlayingPage.increaseTotalScore(2500);
		}
		return usedBlock;
	}
	

	private int[][] moveLines(int lowestLine, int heighestLine, int[][] usedBlock) {
		for(int i = lowestLine; i >= heighestLine; i--) {
			for(int j = 1; j < usedBlock.length; j++) {
				usedBlock[j][i + 1] = usedBlock[j][i];
			}
		}
		
		for(int i = 0; i < usedBlock.length; i++) usedBlock[i][heighestLine] = 0;
		
		return usedBlock;
	}

	private int getHeighestLine(int[][] usedBlock, int heightRemovingLine) {
		int currentHeightestLine = heightRemovingLine;
		
		for(int i = heightRemovingLine - 1; i >= 0 ; i--) {
			boolean blockEmpty = false;
			for(int j = 1; j < usedBlock.length; j++) {
				if(usedBlock[j][i] == -1 || usedBlock[j][i] != 0) {
					currentHeightestLine = i;
					blockEmpty = true;
					break;
				}
			}
			if(!blockEmpty) break;
		}
		
		return currentHeightestLine;
	}

	

	
}
