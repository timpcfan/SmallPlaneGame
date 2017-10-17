package cn.edu.scau.cmi.glz;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * GameFrame类负责视图管理（View）
 * 继承于JFrame，作为整个游戏窗口的框架
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame{

	private int canvasWidth;
	private int canvasHeight;
	
	// GUI绘制素材
	private static final ImageEntity guiHeart = new ImageEntity(10, 10, "resources/heart.png");
	
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
	
	
	
	public int getCanvasWidth() {
		return canvasWidth;
	}


	public int getCanvasHeight() {
		return canvasHeight;
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
            if(model != null) {
            	
            	// 玩家实体的绘制
            	if(model.getPlayer() != null)
            		VisHelper.drawImageEntity(g2d, model.getPlayer());
	            
	            // 图形实体的绘制
	            for(ImageEntity entity: model.getImageEntitesCopy())
	            	VisHelper.drawImageEntity(g2d, entity);
	           
	            // 形状实体的绘制
	            for(ShapeEntity shape: model.getShapesCopy())
	            	VisHelper.drawShapeEntity(g2d, shape);
	            
	            // 文字实体的绘制
	            for(TextEntity text: model.getTextsCopy())
	            	VisHelper.drawTextEntity(g2d, text);
	            
	            // 顶层图像绘制
	            for(ImageEntity image: model.getTopLevelImagesCopy())
	            	VisHelper.drawImageEntity(g2d, image);
	            
	            // GUI绘制
	            if(model.getViewType() == ViewType.GAMING) {
	            	VisHelper.drawImageEntity(g2d, guiHeart);
	            	VisHelper.drawTextEntity(g2d, TextEntity.buildTextEntity(
	            			Integer.toString(model.getPlayer().getLife()),
	            			56, 83, 64, VisHelper.Red));
	            }
	            
	            
            }
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(canvasWidth, canvasHeight);
		}
		
	}
}
