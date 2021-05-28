package com.yang.tank;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		TankFrame f = new TankFrame();
		
		for (int i = 0; i < 5; i++) {
			f.tanks.add(new Tank(50 + i*100, 200, Dir.DOWN, Group.BAD, f));
		}
		
		while (true) {
			Thread.sleep(50);
			// 默认调用paint方法
			f.repaint();
		}
	}

}
