package cn.edu.scau.cmi.glz;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GameVisualizer类是控制器（Controller） 处理用户的输入，管理和连接模型和视图
 */
public class GameVisualizer {

	private GameFrame frame;
	private GameModel model;

	public static final int DEFAULT_FPS = 60; // 控制动画帧率

	private boolean[] keys = new boolean[5];
	private int enemySpawnInterval = 1000;
	private boolean isRunning;
	private ViewType currentView; // 指示当前

	public GameVisualizer(String title, int width, int height) {

		EventQueue.invokeLater(() -> {
			frame = new GameFrame(title, width, height);
			frame.addKeyListener(new GameKeyListener());
			frame.addMouseListener(new GameMouseListener());
		
			// 加载主界面
			model = ViewBuilder.buildMainView(frame);
			currentView = ViewType.MAIN;
			isRunning = false;
			
			// 绘制与游戏逻辑线程
			new Thread(() -> { run(); }).start();

			// 添加敌人线程
			new Thread(() -> { enemyTask(); }).start();

			// 添加子弹线程
			new Thread(() -> { bulletTask(); }).start();

		});
	}

	/**
	 * GameKeyListener类为键盘监听器 监听处理键盘事件
	 */
	private class GameKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
				keys[0] = true;
			if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
				keys[1] = true;
			if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
				keys[2] = true;
			if (e.getKeyChar() == 's' || e.getKeyChar() == 'S')
				keys[3] = true;
			if (e.getKeyChar() == ' ')
				keys[4] = true;
		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
				keys[0] = false;
			if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
				keys[1] = false;
			if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
				keys[2] = false;
			if (e.getKeyChar() == 's' || e.getKeyChar() == 'W')
				keys[3] = false;
			if (e.getKeyChar() == ' ')
				keys[4] = false;
		}
	}

	/**
	 * GameMouseListener类为鼠标监听器 监听处理鼠标事件
	 */
	private class GameMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}
	}

	/**
	 * 动画逻辑
	 */
	private void run() {

		long oldtime = 0;

		long fpsTime = (long) ((Double.valueOf(1000) / Double.valueOf(DEFAULT_FPS)) * 1000000);
		long now = 0; // 绘制图像前的时间戳
		long total = 0; // 每次绘制图像耗时（毫秒）
		while (true) {
			now = System.nanoTime();
			frame.render(model); // 绘制图像
			try {
				// 除去绘制之后还需要休眠的时间
				total = System.nanoTime() - now;
				if (total > fpsTime) {
					continue; // 如果本次绘制时间超过每帧需要绘制的时间，则直接继续绘制
				}
				Thread.sleep((fpsTime - (System.nanoTime() - now)) / 1000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while ((System.nanoTime() - now) < fpsTime) {
				System.nanoTime(); // 使用循环，精确控制每帧绘制时长
			}

			// 计算上一帧到这一帧所花的时间（秒）
			double passedSeconds = (System.nanoTime() - oldtime) / 1000000000.;
			oldtime = System.nanoTime();

			// 更新数据 //

			// 实体移动
			for (GameEntity entity : model.getImageEntitesCopy()) {
				entity.move(passedSeconds);
			}
			
			
			if(currentView == ViewType.GAMING) {
				// 玩家移动
				int d = 7;
				if (keys[0] && model.getPlayer().getX() >= 0)
					model.getPlayer().setX(model.getPlayer().getX() - d);
				if (keys[1] && (model.getPlayer().getX() + model.getPlayer().getW()) <= frame.getCanvasWidth())
					model.getPlayer().setX(model.getPlayer().getX() + d);
				if (keys[2] && model.getPlayer().getY() >= 0)
					model.getPlayer().setY(model.getPlayer().getY() - d);
				if (keys[3] && (model.getPlayer().getY() + model.getPlayer().getH() <= frame.getCanvasHeight()))
					model.getPlayer().setY(model.getPlayer().getY() + d);
	
				
				// 检测玩家与敌人碰撞
				for (Enemy enemy : model.getEnemiesCopy()) {
					if (enemy.collideWith(model.getPlayer(), 0.85)) {
						model.deleteEnemy(enemy);
						model = ViewBuilder.buildGameoverView(frame);
						currentView = ViewType.GAMEOVER;
						isRunning = false;
						break;
					}
				}
	
				// 检测玩家与子弹碰撞
				for (Bullet bullet : model.getBulletsCopy()) {
					for (Enemy enemy : model.getEnemiesCopy()) {
						if (bullet.collideWith(enemy, 0.85)) {
							model.deleteEnemy(enemy);
							model.deleteBullet(bullet);
							break;
						}
					}
				}
			} // end if GAMING
			
			if(currentView == ViewType.MAIN) {
				if(keys[4]) {
					model = ViewBuilder.buildGamingView(frame);
					currentView = ViewType.GAMING;
					isRunning = true;
				}
				
			} // end if MAIN
			
			if(currentView == ViewType.GAMEOVER) {
				if(keys[4]) {
					model = ViewBuilder.buildGamingView(frame);
					currentView = ViewType.GAMING;
					isRunning = true;
				}
				
			} // end if GAMEOVER

			
		}
	}
	
	/**
	 * 敌人生成任务
	 */
	private void enemyTask() {
		while (true) {
			VisHelper.pause(10);
			if (isRunning) {
				
				Enemy enemy = new Stone1();

				int x = (int) (Math.random() * (frame.getCanvasWidth() - enemy.getW()));
				int y = -(int) enemy.getH();
				int vx = (int) (Math.random() * 50);
				int vy = (int) (Math.random() * 400 + 200);

				enemy.setX(x);
				enemy.setY(y);

				if (x < frame.getCanvasWidth() / 2) {
					enemy.setSpeed(vx, vy);
				} else {
					enemy.setSpeed(-vx, vy);
				}
				
				model.addEnemy(enemy);
				VisHelper.pause(enemySpawnInterval);
			}
		}
	}
	
	/**
	 * 子弹生成任务
	 */
	private void bulletTask() {
		while (true) {
			VisHelper.pause(10);
			if (isRunning) {		
				
				if (keys[4]) {
					Bullet bullet = new Bullet();
					bullet.setCenterX(model.getPlayer().getCenterX());
					bullet.setY(model.getPlayer().getY() - bullet.getH());
					bullet.setBulletSpeed(500);
					model.addBullet(bullet);

					VisHelper.pause(model.getPlayer().getFireDelay());
				}
			}
		}
	}

	/**
	 * 程序入口
	 */
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		GameVisualizer visualizer = new GameVisualizer("PlaneGame", 500, 800);
	}

}

enum ViewType{
	MAIN, GAMING, GAMEOVER
}
