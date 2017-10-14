package cn.edu.scau.cmi.glz;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;


import sun.rmi.runtime.Log;

/**
 * GameFrame类负责视图管理（View）
 * 继承于JFrame，作为整个游戏窗口的框架
 */
public class GameFrame extends JFrame{

	private int canvasWidth;
	private int canvasHeight;
	
	public GameFrame(String title, int canvasWidth, int canvasHeight) {
		super(title);
		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		
		setContentPane(new GameCanvas());
		setResizable(false);
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	
	private GameModel model;
	public void render(GameModel model) {
		this.model = model;
		repaint();
	}
	
	
	/**
	 * GameCanvas类为GameFrame框架的内容面板
	 * 作为整个游戏的画布，游戏的实际内容会绘制在GameCanvas上
	 */
	private class GameCanvas extends JPanel{
		

		public GameCanvas() {
			super(true);
			setBackground(VisHelper.Sky);
		}
		
		/**
		 * paintComponent方法用于具体绘制
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			
			// 抗锯齿
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);
            
			
            // 具体绘制
            // TODO 具体绘制内容
            if(model != null) {
	            VisHelper.putImage(g2d, model.getPlayer().getX(), model.getPlayer().getY(), model.getPlayer().getImage());
	            
	            for(ImageEntite entite: model.getAllEntites()) {
	            	VisHelper.putImage(g2d, entite.getX(), entite.getY(), entite.getImage());
	            }
	        }
            
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(canvasWidth, canvasHeight);
		}
		
	}
}
