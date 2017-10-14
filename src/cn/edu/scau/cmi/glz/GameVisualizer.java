package cn.edu.scau.cmi.glz;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

/**
 * GameVisualizer类是控制器（Controller） 处理用户的输入，管理和连接模型和视图
 */
public class GameVisualizer {

	private GameFrame frame;
	private GameModel model;

	public static final int DEFAULT_FPS = 60; // 控制动画帧率

	public GameVisualizer(String title, int width, int height) {
		// TODO 数据初始化
		model = new GameModel();
		model.setPlayer(new ImageEntite(300, 600, 30, "resources/plane.png"));

		EventQueue.invokeLater(() -> {
			frame = new GameFrame(title, width, height);

			frame.addKeyListener(new GameKeyListener());
			frame.addMouseListener(new GameMouseListener());

			new Thread(() -> {
				run();
			}).start();
		});

	}

	/**
	 * GameKeyListener类为键盘监听器 监听处理键盘事件
	 */
	private class GameKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			int d = 5;

			// 按下键向左移动
			if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
				if (model.getPlayer().getX() != 0)
					model.getPlayer().setX(model.getPlayer().getX() - d);
			}
			// 按下d键向右移动
			if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
				if (model.getPlayer().getX() != frame.getCanvasWidth())
					model.getPlayer().setX(model.getPlayer().getX() + d);
			}
			// 按下s键向上移动
			if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
				if (model.getPlayer().getY() != 0)
					model.getPlayer().setY(model.getPlayer().getY() + d);
			}
			// 按下w键向下移动
			if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
				if (model.getPlayer().getY() != frame.getCanvasHeight())
					model.getPlayer().setY(model.getPlayer().getY() - d);
			}

		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyReleased(e);
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
			Point pos = e.getPoint();

		}
	}

	/**
	 * 动画逻辑
	 */
	private void run() {

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
			
			
			// 数据更新
			System.out.println(total);
			
			
			
			
			
		}
	}

	/**
	 * 程序入口
	 */
	public static void main(String[] args) {
		GameVisualizer visualizer = new GameVisualizer("PlaneGame", 500, 800);
	}

}
