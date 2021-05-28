package com.yang.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TankFrame extends Frame {
	
	Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
	List<Bullet> bullets = new ArrayList<>();
//	Bullet myBullet = new Bullet(220, 260, Dir.DOWN, this);
	List<Tank> tanks = new ArrayList<>();
	
	Explode explode = new Explode(100, 100, this);
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;

	public TankFrame() {
		// 窗口大小
		setSize(GAME_WIDTH, GAME_HEIGHT);
		// 不能放大缩小
		setResizable(false);
		// 题目
		setTitle("̹坦克大战");
		setVisible(true);
		// 按键监听
		addKeyListener(new MyKeyListener());
		// 窗口监听器
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	// 双缓冲解决闪烁现象
	Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			// 内存里先创建一张图片
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		// 把坦克、子弹画到内存里面
		paint(gOffScreen);
		// 把整张图片放入屏幕里
		g.drawImage(offScreenImage, 0, 0, null);
	}

	
	@Override
	public void paint(Graphics g) {
//		System.out.println("重绘");
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("子弹数量：" + bullets.size(), 10, 60);
		g.drawString("敌人数量：" + tanks.size(), 10, 80);
		g.setColor(c);
		
		myTank.paint(g);
		// 画子弹
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint(g);
		}
		
//		for (Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
//			Bullet b = it.next();
//			if (!b.isLive()) it.remove();
//		}
		
// 		Exception in thread "AWT-EventQueue-0" java.util.ConcurrentModificationException		
//		for (Bullet b : bullets) {
//			b.paint(g);
//		}
		
		// 画敌人坦克
		for (int i = 0; i < tanks.size(); i++) {
			tanks.get(i).paint(g);
		}
		
		// 碰撞监测
		for (int i = 0; i < bullets.size(); i++) {
			for (int j = 0; j < tanks.size(); j++) {
				bullets.get(i).collideWith(tanks.get(j));
			}
		}
		
		explode.paint(g);
	}
	

	/**
	 * 处理按键
	 * @author yang
	 *
	 */
	class MyKeyListener extends KeyAdapter {
		
		boolean bL = false;
		boolean bR = false;
		boolean bU = false;
		boolean bD = false;

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			System.out.println("按下:" + key);
			switch (key) {
				case KeyEvent.VK_LEFT:
					bL = true;
					break;
				case KeyEvent.VK_RIGHT: 
					bR = true;
					break;
				case KeyEvent.VK_UP: 
					bU = true;
					break;
				case KeyEvent.VK_DOWN: 
					bD = true;
					break;
				default : 
					break;
			}
			setMainTankDir();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			System.out.println("释放:" + key);
			switch (key) {
				case KeyEvent.VK_LEFT:
					bL = false;
					break;
				case KeyEvent.VK_RIGHT: 
					bR = false;
					break;
				case KeyEvent.VK_UP: 
					bU = false;
					break;
				case KeyEvent.VK_DOWN: 
					bD = false;
					break;				
				case KeyEvent.VK_CONTROL:	// 打子弹
					myTank.fire();
					break;
				default : 
					break;
			}
			
			setMainTankDir();
		}
		
		private void setMainTankDir() {
			
			if (!bL && !bR && !bU && !bD) {
				myTank.setMoving(false);
			} else {
				myTank.setMoving(true);
				
				if (bL) myTank.setDir(Dir.LEFT);
				if (bR) myTank.setDir(Dir.RIGHT);
				if (bU) myTank.setDir(Dir.UP);
				if (bD) myTank.setDir(Dir.DOWN);
			}
		}
		
	}
	



}
