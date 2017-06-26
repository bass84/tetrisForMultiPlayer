package main.display;

import processing.core.PApplet;
import processing.core.PFont;

public class SingleBackground {

	public static final float ratio = 7.2f / 4.0f; //창의 비율
	private PApplet pApplet;
	private PFont mono;
	public static float width;
	public static float height;
	public static float offsetX;
	public static float offsetY;
	
	public SingleBackground(PApplet pApplet) {
		this.pApplet = pApplet;
	}
	
	public void drawBackground() {
		// 현재 창의 크기
		width = this.pApplet.width;
		height = this.pApplet.height;
		
		// 창과 게임 영역의 비율 맞추기
		if(width * ratio > height) width = height / ratio;
		else height = ratio * width;

		// 맞춘 비율 게임 영역을 가운데에 그리기
		offsetX = (this.pApplet.width - width) / 2;
		offsetY = (this.pApplet.height - height) / 2;
		
		// 창 안 게임 영역 그리기
		this.pApplet.fill(255, 255, 255);
		this.pApplet.rect(offsetX, offsetY, width, height);
				
	}
	
	public void drawBeforeGameMenuText () {
		// 창 안의 텍스트도 비율에 맞추어 가운데에 있게 그린다.
		this.mono = this.pApplet.createFont("mono", width / 20);
		this.pApplet.fill(0, 0, 0);
		this.pApplet.textFont(this.mono);
		this.pApplet.text("PRESS '1' Then Play Single"
				, offsetX + (width / 4.8f)
				, offsetY + (height / 2.3f));
		this.pApplet.text("PRESS '2' Then Play Multi"
				, offsetX + (width / 4.8f)
				, offsetY + (height / 1.86f));
	}
	
}
