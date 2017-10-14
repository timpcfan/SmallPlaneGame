package cn.edu.scau.cmi.glz;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

import sun.reflect.generics.tree.VoidDescriptor;


/**
 * GameEntite是所有游戏实体的基类
 */
public class GameEntite {

	private double x, y; // 实体左上角坐标
	private double r; // 实体的半径，用于碰撞检测
	private double vx, vy; // 实体的运动速度（像素/秒）

	public GameEntite(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.vx = 0;
		this.vy = 0;
	}

	public int getX() {
		return (int)x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getR() {
		return r;
	}
	
	public void setSpeed(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public Point getPoint() {
		return new Point((int)x, (int)y);
	}

	public boolean collideWith(GameEntite other) {
		return (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y) <= (r - other.r) * (r - other.r);
	}
	
	public void move(double passedSeconds) {
		x += vx * passedSeconds;
		y += vy * passedSeconds;
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

class PlayerPlane extends ImageEntite{
	
	private int life;
	private int bullet;
	
	public PlayerPlane(int x, int y, int r) {
		super(x, y, r, "resources/plane.png");
	}
	
	public void fire() {
		bullet --;
	}
	
	public boolean hasBullet() {
		return bullet > 0;
	}
	
	public void crash() {
		life --;
	}
	
	public boolean hasLife() {
		return life > 0;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getBullet() {
		return bullet;
	}

	public void setBullet(int bullet) {
		this.bullet = bullet;
	}
	
	
}

class Enemy extends ImageEntite{
	
	private int life = 1;
	
	public Enemy(int x, int y, int r) {
		super(x, y, r, "resources/enemy1.png");
	}
	
	public boolean isDead() {
		return (life == 0);
	}
}

class Bullet extends ImageEntite{

	public Bullet(int x, int y, int r) {
		super(x, y, r, "resources/bullet.png");
		setSpeed(0, 20);
	}
	
}