package com.yang.tank;

import java.awt.Graphics;
import java.util.Random;

public class Tank {

	private int x;
	private int y;
	private Dir dir = Dir.DOWN;
	// 坦克速度
	private static final int SPEED = 2;
	public static final int WIDTH = ResourceMgr.tankD.getWidth();
	public static final int HEIGHT = ResourceMgr.tankD.getHeight();
	
	private boolean moving = true;
	
	private TankFrame tf = null;
	private boolean living = true;
	private Group group = Group.BAD;
	
	//
	private Random random = new Random();
	
	public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
		this.group = group;
	}
	
	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Group getGroup() {
		return group;
	}

	public void paint(Graphics g) {
//		Color c = g.getColor();
//		g.setColor(Color.YELLOW);
//		g.fillRect(x, y, 60, 60);
//		g.setColor(c);
		// 死了就不用画了
		if (!living) {
			tf.tanks.remove(this);
		}
		
		// 画坦克图片
		switch (dir) {
			case LEFT:
				g.drawImage(ResourceMgr.tankL, x, y, null);
				break;
			case RIGHT:
				g.drawImage(ResourceMgr.tankR, x, y, null);
				break;
			case UP:
				g.drawImage(ResourceMgr.tankU, x, y, null);
				break;
			case DOWN:
				g.drawImage(ResourceMgr.tankD, x, y, null);
				break;
		}
		
		move();
	}

	private void move() {
		if (!moving) return;
		
		switch (dir) {
			case LEFT:
				x -= SPEED;
				break;
			case RIGHT:
				x += SPEED;
				break;
			case UP:
				y -= SPEED;
				break;
			case DOWN:
				y += SPEED;
				break;
		}
		
		if (random.nextInt(10) > 7) {
			this.fire();
		}
	}

	public void fire() {
		int bX = this.x + Tank.WIDTH/2 -Bullet.WIDTH/2;
		int bY = this.y + Tank.HEIGHT/2 -Bullet.HEIGHT/2;
		tf.bullets.add(new Bullet(bX, bY, this.dir, this.group, this.tf));	
	}

	public void die() {
		this.living = false;	
	}
}
