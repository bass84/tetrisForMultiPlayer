package main.pages;

import pages.IGamePageDisplay;

public abstract class PlayPageDisplay implements IGamePageDisplay{

	public final static int widthblock = 10;
	public final static int heightblock = 15;
	
	public abstract void increaseTotalScore(int score);
	
	
}
