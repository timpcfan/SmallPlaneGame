package cn.edu.scau.cmi.glz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;


/**
 * VisHelper类是用于辅助画图的工具类
 */
public class VisHelper {
	
	// Google设计中使用的一些颜色常量
    public static final Color Red = new Color(0xF44336);
    public static final Color Pink = new Color(0xE91E63);
    public static final Color Purple = new Color(0x9C27B0);
    public static final Color DeepPurple = new Color(0x673AB7);
    public static final Color Indigo = new Color(0x3F51B5);
    public static final Color Blue = new Color(0x2196F3);
    public static final Color LightBlue = new Color(0x03A9F4);
    public static final Color Cyan = new Color(0x00BCD4);
    public static final Color Teal = new Color(0x009688);
    public static final Color Green = new Color(0x4CAF50);
    public static final Color LightGreen = new Color(0x8BC34A);
    public static final Color Lime = new Color(0xCDDC39);
    public static final Color Yellow = new Color(0xFFEB3B);
    public static final Color Amber = new Color(0xFFC107);
    public static final Color Orange = new Color(0xFF9800);
    public static final Color DeepOrange = new Color(0xFF5722);
    public static final Color Brown = new Color(0x795548);
    public static final Color Grey = new Color(0x9E9E9E);
    public static final Color BlueGrey = new Color(0x607D8B);
    public static final Color Black = new Color(0x000000);
    public static final Color White = new Color(0xFFFFFF);
    
    public static final Color Sky = new Color(0x3B2951); // 背景颜色
    
    // 图像对象
    public static final Image Bullet;
    public static final Image BulletPacket;
    public static final Image Heart;
    public static final Image Plane;
    public static final Image Stone1;
    public static final Image Stone1_1;
    public static final Image Stone1_2;
    public static final Image Stone1_3;
    public static final Image Stone1_broken;
    public static final Image Stone2;
    public static final Image Stone2_1;
    public static final Image Stone2_2;
    public static final Image Stone2_broken;
    public static final Image UFO;
    
    // 字体
    public static Font FONT;
    
	static {
		
		// 初始化图像对象
		Bullet = loadImage("resources/bullet.png");
		BulletPacket = loadImage("resources/bulletpacket.png");
		Heart = loadImage("resources/heart.png");
		Plane = loadImage("resources/plane.png");
		Stone1 = loadImage("resources/stone1.png");
		Stone1_1 = loadImage("resources/stone1_1.png");
		Stone1_2 = loadImage("resources/stone1_2.png");
		Stone1_3 = loadImage("resources/stone1_3.png");
		Stone1_broken = loadImage("resources/stone1_broken.png");
		Stone2 = loadImage("resources/stone2.png");
		Stone2_1 = loadImage("resources/stone2_1.png");
		Stone2_2 = loadImage("resources/stone2_2.png");
		Stone2_broken = loadImage("resources/stone2_broken.png");
		UFO = loadImage("resources/ufo.png");
		
		// 初始化字体
    	try {
    		File fontFile = new File("resources/Monaco.ttf");
			FONT = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (Exception e) {
			System.out.println("WARNNING: Failed to load font file");
			FONT = new Font("Serif", Font.PLAIN, 1);
		}
		
	}
    
    
    
    // 绘制图形实例
    public static void drawShapeEntity(Graphics2D g, ShapeEntity shapeEntity) {
    	setColor(g, shapeEntity.getColor());
    	if(shapeEntity.isFilled()) g.fill(shapeEntity.getShape());
    	else g.draw(shapeEntity.getShape());
    }
    
    // 绘制图形实例
    public static void drawImageEntity(Graphics2D g, ImageEntity imageEntity) {
    	g.drawImage(imageEntity.getImage(), (int)imageEntity.getX(), (int)imageEntity.getY(), null);
    }
    
    // 绘制文字实例
    public static void drawTextEntity(Graphics2D g, TextEntity textEntity) {
    	setColor(g, textEntity.getColor());
        g.setFont(FONT.deriveFont(textEntity.getFontSize()));
        FontMetrics metrics = g.getFontMetrics();
        int w = metrics.stringWidth(textEntity.getText());
        int h = metrics.getDescent();
        g.drawString(textEntity.getText(), (int)(textEntity.getCenterX() - w/2), (int)(textEntity.getCenterY() - h/2));
    }
    
    // 设置画笔颜色
    public static void setColor(Graphics2D g, Color color){
        g.setColor(color);
    }

    // 设置画笔粗细
    public static void setStrokeWidth(Graphics2D g, int w){
        int strokeWidth = w;
        g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    // 延迟t毫秒
    public static void pause(int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }
    

    // 绘制图像
    public static void putImage(Graphics2D g, int x, int y, String imageURL){

        ImageIcon icon = new ImageIcon(imageURL);
        Image image = icon.getImage();

        g.drawImage(image, x, y, null);
    }
    
    public static void putImage(Graphics2D g, int x, int y, Image image) {
    	g.drawImage(image, x, y, null);
    }

    // 绘制文字
    public static void drawText(Graphics2D g, String text, int centerx, int centery){

        if(text == null)
            throw new IllegalArgumentException("Text is null in drawText function!");

        FontMetrics metrics = g.getFontMetrics();
        int w = metrics.stringWidth(text);
        int h = metrics.getDescent();
        g.drawString(text, centerx - w/2, centery + h);
    }
    
    public static Image loadImage(String imageURL) {
    	return new ImageIcon(imageURL).getImage();
    }
}
