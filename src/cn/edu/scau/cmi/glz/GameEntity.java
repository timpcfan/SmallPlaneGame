package cn.edu.scau.cmi.glz;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;


/**
 * GameEntite是所有游戏实体的基类
 */
public class GameEntity {

	private double x, y; // 实体左上角坐标
	private double vx, vy; // 实体的运动速度（像素/秒）
	private double w, h; // 实体的宽和高

	public GameEntity(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.vx = 0;
		this.vy = 0;
	}
	
	public GameEntity(double x, double y) {
		this.x = x;
		this.y = y;
		this.w = 0;
		this.h = 0;
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

	public void setCenterX(double centerX) {
		x = centerX - w / 2;
	}
	
	public void setCenterY(double centerY) {
		y = centerY - h / 2;
	}
	
	public int getCenterX() {
		return (int) (x + w / 2);
	}
	
	public int getCenterY() {
		return (int) (y + h / 2);
	}
	
	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	
	public void setSpeed(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public Point getPoint() {
		return new Point((int)x, (int)y);
	}

	public boolean collideWith(GameEntity other, double ratio) {
		if(Math.abs(this.getX() - (1 - ratio) / 2 * this.w - other.getX() + 0.1 * other.w) < ratio * this.w
				&& Math.abs(this.getY() - (1 - ratio) / 2 * this.h - other.getY() + 0.1 * other.h) < ratio * this.h)
			return true;
		else
			return false;
	}
	
	public void move(double passedSeconds) {
		x += vx * passedSeconds;
		y += vy * passedSeconds;
	}
}


class ImageEntity extends GameEntity{

	private Image image;
	
	public ImageEntity(double x, double y, Image image) {
		super(x, y);
		this.image = image;
		this.setW(this.image.getWidth(null));
		this.setH(this.image.getHeight(null));
	}
	
	public ImageEntity(int x, int y, String imageURL) {
		super(x, y);
		ImageIcon icon = new ImageIcon(imageURL);
        this.image = icon.getImage();
		this.setW(this.image.getWidth(null));
		this.setH(this.image.getHeight(null));
	}
	
	public Image getImage() {
		return this.image;
	}
	
	
}

class PlayerPlane extends ImageEntity{
	
	private int life;
	private int bullet;
	
	public PlayerPlane(int x, int y) {
		super(x, y, "resources/plane.png");
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

class Enemy extends ImageEntity{
	
	private int life = 1;
	
	public Enemy() {
		super(0, 0, "resources/stone1.png");
	}
	
	public Enemy(int x, int y) {
		super(x, y, "resources/stone1.png");
	}
	
	public boolean isDead() {
		return (life == 0);
	}
}

class Bullet extends ImageEntity{

	public Bullet(int x, int y) {
		super(x, y, "resources/bullet.png");
		setSpeed(0, 20);
	}
	
}