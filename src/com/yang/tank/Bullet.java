package com.yang.tank;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {

	private static final int SPEED = 10;
	public static final int WIDTH = ResourceMgr.bulletD.getWidth();
	public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
	
	private int x;
	private int y;
	private Dir dir;
	
	private boolean living = true;
	
	private TankFrame tf;
	
	private Group group = Group.BAD;
	
	public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
		this.group = group;
	}
	
	public boolean isLiving() {
		return living;
	}
	
	public Group getGroup() {
		return group;
	}

	public void paint(Graphics g) {
		// 这种方式存在bug
		// Exception in thread "AWT-EventQueue-0" java.util.ConcurrentModificationException
		if (!living) {
			tf.bullets.remove(this);
		}
		
//		Color c = g.getColor();
//		g.setColor(Color.RED);
//		g.fillOval(x, y, WIDTH, HEIGHT);
//		g.setColor(c);
		
		switch (dir) {
			case LEFT:
				g.drawImage(ResourceMgr.bulletL, x, y, null);
				break;
			case RIGHT:
				g.drawImage(ResourceMgr.bulletR, x, y, null);
				break;
			case UP:
				g.drawImage(ResourceMgr.bulletU, x, y, null);
				break;
			case DOWN:
				g.drawImage(ResourceMgr.bulletD, x, y, null);
				break;
		}
		
		move();
	}
	
	private void move() {
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
		
		if (this.x < 0 || this.y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
			living = false;
		}
	}

	
	public void collideWith(Tank tank) {
		// 区分双方子弹
		if (this.group == tank.getGroup()) {
			return;
		}
		
		Rectangle b = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
		Rectangle t = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
		// 判断相交
		if (b.intersects(t)) {
			tank.die();
			this.die();
		}
	}

	private void die() {
		this.living = false;	
	}
}
