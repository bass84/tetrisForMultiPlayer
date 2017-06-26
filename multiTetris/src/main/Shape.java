package main;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import main.ShapeMapping.Kind;
import main.display.Background;
import main.display.SingleBackground;
import main.pages.PlayPageDisplay;
import processing.core.PApplet;

public class Shape implements BlockDraw, Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7231162489919433626L;
	private transient PApplet pApplet;
	private int[][] shapeInfo;
	private ShapeMapping shapeMapping;
	private Kind shapeKind;
	private int positionX;
	private int positionY;
	private int rotationLimit;
	private int curRotationIdx;
	private int shapeColor;
	
	public Shape(PApplet pApplet) {
		this.pApplet = pApplet;
		int kindIndex = new Random().nextInt(Kind.values().length);
		for(Kind kind : Kind.values()) {
			if(kind.ordinal() == kindIndex) this.shapeKind = kind; 
		}
		this.shapeMapping = new ShapeMapping();
		this.shapeInfo = this.shapeMapping.getShapeInfo(this.shapeKind)[0];
		this.positionX = this.shapeMapping.getMovingValue(this.shapeKind)[0];
		this.positionY = this.shapeMapping.getMovingValue(this.shapeKind)[1];
		this.rotationLimit = this.shapeMapping.getRotationLimit(this.shapeKind);
		this.shapeColor = this.shapeMapping.getShapeColor(this.shapeKind);
		this.curRotationIdx = 0;
	}
	
	public int[][] getShapeInfo() {return this.shapeInfo;}
	public Kind getShapeKind() {return this.shapeKind;}
	public int getPositionX() {return this.positionX;}
	public int getPositionY() {return this.positionY;}
	public void setPositionX(int positionX) {this.positionX = positionX;}
	public void setPositionY(int positionY) {this.positionY = positionY;}
	public void increasePositionX() {++this.positionX;}
	public void decreasePositionX() {--this.positionX;}
	public void increasePositionY() {++this.positionY;}
	public int getNextRotationIdx() {return this.curRotationIdx == this.rotationLimit ? 0 : this.curRotationIdx + 1;}
	public void increaseRotationIdx() {this.curRotationIdx = this.curRotationIdx == this.rotationLimit ? 0 : ++this.curRotationIdx;}
	public void rotate() {this.shapeInfo = this.getNextShapeInfo();}
	public int getShapeColor() {return this.shapeColor;}
	public void setShapeColor(int shapeColor) {this.shapeColor = shapeColor;}
	public void setShapeInfo(int[][] shapeInfo) {
		for(int i = 0; i < shapeInfo.length; i++) {
			for(int j = 0; j < shapeInfo[i].length; j++) {
				this.shapeInfo[i][j] = shapeInfo[i][j];
				}
			}
		}
	
	
	public int[][] getNextShapeInfo() {
		switch(this.shapeKind) {
			case I :
			case S :
			case Z :
			case J :
			case L :
			case T :
				return this.shapeMapping.getShapeInfo(this.shapeKind)[this.getNextRotationIdx()];
		}
		return null;
	}

	@Override
	public void drawShape(int[][] usedBlock, Shape shape) {
		int shapeColor = shape.getShapeColor();
		for(int i = 0; i < this.shapeInfo.length; i++) {
			pApplet.fill(shapeColor, 255);
			
			//draw 1 player
			pApplet.rect(
					(this.shapeInfo[i][0] + this.positionX) * (Background.width / PlayPageDisplay.widthblock) + Background.offsetX
					, (this.shapeInfo[i][1] + this.positionY) * (Background.height / PlayPageDisplay.heightblock) + Background.offsetY
					, (Background.width / PlayPageDisplay.widthblock)
					, (Background.height/ PlayPageDisplay.heightblock));
			//draw 2 player
			pApplet.rect(
					(this.shapeInfo[i][0] + this.positionX) * (Background.width / PlayPageDisplay.widthblock) + Background.offsetX2
					, (this.shapeInfo[i][1] + this.positionY) * (Background.height / PlayPageDisplay.heightblock) + Background.offsetY
					, (Background.width / PlayPageDisplay.widthblock)
					, (Background.height/ PlayPageDisplay.heightblock));
		}
		
	}

	@Override
	public String toString() {
		return "Shape [shapeInfo=" + Arrays.toString(shapeInfo) + ", shapeMapping=" + shapeMapping + ", shapeKind="
				+ shapeKind + ", positionX=" + positionX + ", positionY=" + positionY + ", rotationLimit="
				+ rotationLimit + ", curRotationIdx=" + curRotationIdx + ", shapeColor=" + shapeColor + "]";
	}
	
	
	
}
