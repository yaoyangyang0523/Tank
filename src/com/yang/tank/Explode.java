package com.yang.tank;

import java.awt.Graphics;

public class Explode {
	public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
	
	private int x;
	private int y;
	
	private boolean living = true;
	private TankFrame tf;
	
	private int step = 0;
	
	public Explode(int x, int y, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		
		new Audio("audio/explode.wav").play();
	}
	
	public void paint(Graphics g) {		
		g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		if (step >= ResourceMgr.explodes.length) {
			step = 0;
		}
	}
}
