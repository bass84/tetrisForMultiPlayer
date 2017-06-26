package main;

import java.io.Serializable;

public class ShapeMapping implements Serializable{

	private static final long serialVersionUID = 2002873879363676629L;
	
	public enum Kind implements Serializable{
		I
		, J
		, L
		, O
		, S
		, T
		, Z
	}
	
	public int getShapeColor(Kind kind) {
		switch(kind) {
			case I : return 0xff00ffff;
			case S : return 0xff0000ff;
			case Z : return 0xffffa500;
			case J : return 0xffffff00;
			case L : return 0xff00ff00;
			case T : return 0xff800080;
			case O : return 0xffff0000;
		}
		return 0;
	}
	
	public int getRotationLimit(Kind kind) {
		switch(kind) {
			case I :
			case S :
			case Z :
				return 1;
			case J :
			case L :
			case T :
				return 3;
			case O :
				return 0;
		}
		return 0;
	}
	public int[] getMovingValue(Kind kind) {
		int movingValue[];
		
		switch(kind) {
			case I :
				movingValue = new int[]{5, 0};
				return movingValue;
			case J :
				movingValue = new int[]{5, 1};
				return movingValue;
			case L :
				movingValue = new int[]{5, 1};
				return movingValue;
			case O :
				return new int[]{0, 0};
			case S :
				movingValue = new int[]{5, 1};
				return movingValue;
			case T :
				movingValue = new int[]{5, 1};
				return movingValue;
			case Z :
				movingValue = new int[]{5, 1};
				return movingValue;
		}
		
		return null;
	}
	public int[][][] getShapeInfo(Kind kind) {
		int[][][] shapeInfo;
		
		switch(kind) {
			case I : 
				shapeInfo = new int[][][]{
					{{-2, 0}, {-1, 0}, {0, 0}, {1, 0}} 
					, {{0, -1}, {0, 0}, {0, 1}, {0, 2}}
				};
				return shapeInfo;
			case J :
				shapeInfo = new int[][][]{
					{{-1, -1}, {0, -1}, {1, -1}, {1, 0}}
					, {{1, -2}, {1, -1}, {1, 0}, {0, 0}}
					, {{-1, -1}, {-1, 0}, {0, 0}, {1, 0}}
					, {{0, -2}, {-1, -2}, {-1, -1}, {-1, 0}}
				};
				return shapeInfo;
			case L :
				shapeInfo = new int[][][]{
					{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}}
					, {{0, -2}, {1, -2}, {1, -1}, {1, 0}}
					, {{1, -1}, {1, 0}, {0, 0}, {-1, 0}}
					, {{-1, -2}, {-1, -1}, {-1, 0}, {0, 0}}
				};
				return shapeInfo;
			case O :
				shapeInfo = new int[][][]{
					{{4, 0}, {5, 0}, {4, 1}, {5, 1}}};
				return shapeInfo;
			case S :
				shapeInfo = new int[][][]{
					{{0, -1}, {1, -1}, {-1, 0}, {0, 0}}
					, {{0, -1}, {0, 0}, {1, 0}, {1, 1}}
				};
				return shapeInfo;
			case T :
				shapeInfo = new int[][][]{
					{{-1, 0}, {0, -1}, {0, 0}, {1, 0}}
					, {{0, -1}, {0, 0}, {0, 1}, {1, 0}}
					, {{-1, 0}, {0, 0}, {1, 0}, {0, 1}}
					, {{-1, 0}, {0, -1}, {0, 0}, {0, 1}}
					
				};
				return shapeInfo;
			case Z :
				shapeInfo = new int[][][]{
					{{-1, -1}, {0, -1}, {0, 0}, {1, 0}}
					, {{1, -1}, {1, 0}, {0, 0}, {0, 1}}
				};
				return shapeInfo;
		}
		
		return null;
	}
	
	
}