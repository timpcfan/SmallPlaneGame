package cn.edu.scau.cmi.glz;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * GameVisualizer类是控制器（Controller） 处理用户的输入，管理和连接模型和视图
 */
public class GameVisualizer {

	private GameFrame frame; // 视图层
	private GameModel model; // 数据层

	public static final int DEFAULT_FPS = 60; // 控制动画帧率

	private boolean[] keys = new boolean[5]; // 储存 a d w s space 的按键状态
	private Queue<MouseEvent> mouseEvents = new ArrayDeque<>(); // 储存鼠标事件
	private int enemySpawnInterval = 1000; // 敌人生成时间间隔
	private boolean isRunning; // 是否正在进行游戏

	public GameVisualizer(String title, int width, int height) {

		EventQueue.invokeLater(() -> {
			// 初始化界面
			frame = new GameFrame(title, width, height);
			
			// 添加键盘鼠标监听器
			frame.addKeyListener(new GameKeyListener());
			frame.addMouseListener(new GameMouseListener());

			// 加载主界面
			model = ViewBuilder.buildMainView(frame);
			isRunning = false;

			// 绘制与游戏逻辑线程
			new Thread(() -> {
				run();
			}).start();

			// 添加敌人线程
			new Thread(() -> {
				enemyTask();
			}).start();

			// 添加子弹线程
			new Thread(() -> {
				bulletTask();
			}).start();

			// 垃圾处理线程
			new Thread(() -> {
				gcTask();
			}).start();
		});
	}

	/**
	 * GameKeyListener类为键盘监听器 监听处理键盘事件
	 */
	private class GameKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			
			if (e.getKeyChar() == 'a' || e.getKeyCode() == 37 || e.getKeyChar() == 'A')
				keys[0] = true;
			if (e.getKeyChar() == 'd' || e.getKeyCode() == 39 || e.getKeyChar() == 'D')
				keys[1] = true;
			if (e.getKeyChar() == 'w' || e.getKeyCode() == 38 || e.getKeyChar() == 'W')
				keys[2] = true;
			if (e.getKeyChar() == 's' || e.getKeyCode() == 40 || e.getKeyChar() == 'S')
				keys[3] = true;
			if (e.getKeyChar() == ' ')
				keys[4] = true;
		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyChar() == 'a' || e.getKeyCode() == 37 || e.getKeyChar() == 'A')
				keys[0] = false;
			if (e.getKeyChar() == 'd' || e.getKeyCode() == 39 || e.getKeyChar() == 'D')
				keys[1] = false;
			if (e.getKeyChar() == 'w' || e.getKeyCode() == 38 || e.getKeyChar() == 'W')
				keys[2] = false;
			if (e.getKeyChar() == 's' || e.getKeyCode() == 40 || e.getKeyChar() == 'W')
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
			mouseEvents.add(e); // 将鼠标事件添加进鼠标事件队列
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
		
		// 游戏主循环
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

			
			// 在游戏界面处理的逻辑
			if (model.getViewType() == ViewType.GAMING) {
				
				mouseEvents.clear(); // 清空鼠标事件队列

				// 玩家移动
				double d = 400 * passedSeconds; // 玩家此帧的位移
				double px = model.getPlayer().getX();
				double py = model.getPlayer().getY();
				if (keys[0]) px -= d;
				if (keys[1]) px += d;
				if (keys[2]) py -= d;
				if (keys[3]) py += d;
				// 更新玩家位置
				model.getPlayer().setX(Math.max(0, Math.min(frame.getCanvasWidth() - model.getPlayer().getW(), px)));
				model.getPlayer().setY(Math.max(0, Math.min(frame.getCanvasHeight() - model.getPlayer().getH(), py)));

				// 检测玩家与敌人碰撞
				for (Enemy enemy : model.getEnemiesCopy()) {
					if (enemy.collideWith(model.getPlayer(), 0.85)) {
						enemy.crash(); // 敌人被撞击
						model.getPlayer().crash(); // 玩家被撞击
						break;
					}
				}

				// 检测敌人与子弹碰撞
				for (Bullet bullet : model.getBulletsCopy()) {
					for (Enemy enemy : model.getEnemiesCopy()) {
						if (bullet.collideWith(enemy, 0.85)) {
							enemy.beShot(); // 敌人扣血
							if(enemy.isDead()) model.addScore(enemy.getScore()); // 消灭敌人，加相应分数
							model.deleteBullet(bullet); // 重从链表中删除子弹
							break;
						}
					}
				}

				// 玩家死亡检测
				if (!model.getPlayer().hasLife()) {
					model = ViewBuilder.buildGameoverView(frame, model.getScore());
					isRunning = false;
				}

			} // end if GAMING

			if (model.getViewType() == ViewType.MAIN) {
				
				MouseEvent event = mouseEvents.poll();
				if (event != null && VisHelper.isPointInImage(event.getX(), event.getY(), VisHelper.PlayButton, 255, 666)) {
					model = ViewBuilder.buildGamingView(frame);
					isRunning = true;				
				}

			} // end if MAIN

			
			// 在游戏结束界面处理的逻辑
			if (model.getViewType() == ViewType.GAMEOVER) {

				// 从鼠标事件队列中获取一个事件
				MouseEvent event = mouseEvents.poll();
				
				// 如果鼠标点的位置在“AGAIN”上，则重新开始游戏
				if(event != null && VisHelper.isPointInImage(event.getX(), event.getY(), VisHelper.ImageForCheck, 178, 586)) {
					model = ViewBuilder.buildGamingView(frame);
					isRunning = true;
				}
				
				// 如果鼠标点的位置在“MAIN”上，则回到主界面
				if(event != null && VisHelper.isPointInImage(event.getX(), event.getY(), VisHelper.ImageForCheck, 178, 650)) {
					model = ViewBuilder.buildMainView(frame);
					isRunning = false;
				}
				
				// 如果鼠标点的位置在“EXIT”上，则退出游戏
				if(event != null && VisHelper.isPointInImage(event.getX(), event.getY(), VisHelper.ImageForCheck, 178, 710)) {
					System.exit(0);
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

				Enemy enemy;
				if (Math.random() > 0.8)
					enemy = new Stone1(); // 20%几率生成大陨石
				else
					enemy = new Stone2(); // 80%几率生成小陨石

				// 随机生成坐标
				int x = (int) (Math.random() * (frame.getCanvasWidth() - enemy.getW()));
				int y = -(int) enemy.getH();
				enemy.setX(x);
				enemy.setY(y);
				
				// 随机生成速度
				int vx = (int) (Math.random() * 50);
				int vy = (int) (Math.random() * 400 + 200);

				// 若果敌人生成在屏幕左边，让它有个向右水平方向的速度，否者向左
				if (x < frame.getCanvasWidth() / 2) {
					enemy.setSpeed(vx, vy);
				} else {
					enemy.setSpeed(-vx, vy);
				}

				// 将敌人加入链表
				model.addEnemy(enemy);
				
				// 等待一定的时间继续生成下一个敌人
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

				// 如果按下了空格，则生成子弹
				if (keys[4]) {
					
					Bullet bullet = new Bullet();
					bullet.setCenterX(model.getPlayer().getCenterX());
					bullet.setY(model.getPlayer().getY());
					bullet.setBulletSpeed(700);
					
					// 将子弹加入链表
					model.addBullet(bullet);

					// 等待一定的时间
					VisHelper.pause(model.getPlayer().getFireDelay());
				}
			}
		}
	}

	/**
	 * 垃圾回收任务
	 */
	private void gcTask() {
		while (true) {
			VisHelper.pause(10);
			
			// 删除死亡和出界的敌人
			for(Enemy enemy: model.getEnemiesCopy())
				if (enemy.isDead() || enemy.isOutOfFrame(frame))
					model.deleteEnemy(enemy);

			// 删除出界的子弹
			for(Bullet bullet: model.getBulletsCopy())
				if(bullet.isOutOfFrame(frame))
					model.deleteBullet(bullet);
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
