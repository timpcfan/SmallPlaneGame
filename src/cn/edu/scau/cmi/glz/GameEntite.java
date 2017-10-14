package cn.edu.scau.cmi.glz;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;


/**
 * GameEntite是所有游戏实体的基类
 */
public class GameEntite {

	private int x, y; // 实体中心坐标
	private int r; // 实体的半径，用于碰撞检测
	private int vx, vy; // 实体的运动速度

	public GameEntite(int x, int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.vx = 0;
		this.vy = 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getR() {
		return r;
	}
	
	public void setSpeed(int vx, int vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public Point getPoint() {
		return new Point(x, y);
	}

	public boolean collideWith(GameEntite other) {
		return (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y) <= (r - other.r) * (r - other.r);
	}
}


class ImageEntite extends GameEntite{

	private Image image;
	
	public ImageEntite(int x, int y, int r, Image image) {
		super(x, y, r);
		this.image = image;
	}
	
	public ImageEntite(int x, int y, int r, String imageURL) {
		super(x, y, r);
		ImageIcon icon = new ImageIcon(imageURL);
        this.image = icon.getImage();
	}
	
	public Image getImage() {
		return this.image;
	}
	
	
}


class FireBall extends ImageEntite{

	public FireBall(int x, int y, int r,int vy) {
		super(x, y, r, "");
		super.setSpeed(0, vy);
	}
	
	
	
}