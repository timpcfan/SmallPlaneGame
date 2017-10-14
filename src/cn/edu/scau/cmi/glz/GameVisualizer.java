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
 * GameVisualizer类是控制器（Controller）
 * 处理用户的输入，管理和连接模型和视图
 */
public class GameVisualizer {

	private GameFrame frame;
	private GameModel model;
	
	private boolean[] keys = new boolean[5];
	
	public static final int PAUSE = 40; // 控制动画刷新间隔
	
	public GameVisualizer(String title, int width, int height) {
		// TODO 数据初始化
		model = new GameModel();
		model.setPlayer(new ImageEntite(300, 600, 30, "resources/plane.png"));
		
		
		EventQueue.invokeLater(()->{
			frame = new GameFrame(title, width, height);
			
			frame.addKeyListener(new GameKeyListener());
			frame.addMouseListener(new GameMouseListener());
			
			new Thread(()->{
				run();
			}).start();
		});
		
	}
	
	/**
	 * GameKeyListener类为键盘监听器
	 * 监听处理键盘事件
	 */
	private class GameKeyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
				keys[0] = true;
			if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
				keys[1] = true;
			if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
				keys[2] = true;
			if(e.getKeyChar() == 's' || e.getKeyChar() == 'S')
				keys[3] = true;
			if(e.getKeyChar() == ' ')
				keys[4] = true;
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			
			if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
				keys[0] = false;
			if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
				keys[1] = false;
			if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
				keys[2] = false;
			if(e.getKeyChar() == 's' || e.getKeyChar() == 'W')
				keys[3] = false;
			if(e.getKeyChar() == ' ')
				keys[4] = false;
		}
	}
	
	/**
	 * GameMouseListener类为鼠标监听器
	 * 监听处理鼠标事件
	 */
	private class GameMouseListener extends MouseAdapter{
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
		while(true) {
			VisHelper.pause(PAUSE);
			// 绘制数据
			frame.render(model);
			
			
			// 更新状态
			int d = 5;//移动的距离
			
			if(keys[0] && model.getPlayer().getX() >= 0)
				model.getPlayer().setX(model.getPlayer().getX() - d);
			if(keys[1] && model.getPlayer().getX() <= frame.getCanvasWidth())
				model.getPlayer().setX(model.getPlayer().getX() + d);
			if(keys[2] && model.getPlayer().getY() >= 0)
				model.getPlayer().setY(model.getPlayer().getY() - d);
			if(keys[3] && model.getPlayer().getY() <= frame.getCanvasHeight())
				model.getPlayer().setY(model.getPlayer().getY() + d);
		}		
	}
	
	
	/**
	 * 程序入口
	 */
	public static void main(String[] args) {
		GameVisualizer visualizer = new GameVisualizer("PlaneGame", 500, 800);
	}

}
