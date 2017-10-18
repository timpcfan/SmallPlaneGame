package cn.edu.scau.cmi.glz;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;


/**
 * GameEntite是所有游戏实体的基类
 */
public class GameEntity {

	private double x, y; // 实体左上角坐标
	private double vx, vy; // 实体的运动速度（像素/秒）
	private double w, h; // 实体的宽和高
	private boolean isDead; // 该变量用于指示是否需要被删除

	public GameEntity() {
		this(0, 0);
	}

	public GameEntity(double x, double y) {
		this(x, y, 0, 0);
	}

	public GameEntity(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.vx = 0;
		this.vy = 0;
		this.isDead = false;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
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

	public double getCenterX() {
		return x + w / 2;
	}

	public double getCenterY() {
		return y + h / 2;
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
		return new Point((int) x, (int) y);
	}

	public boolean collideWith(GameEntity other, double ratio) {

		return Math.abs(this.getCenterX() - other.getCenterX()) < (this.getW() / 2 + other.getW() / 2) * ratio
				&& Math.abs(this.getCenterY() - other.getCenterY()) < (this.getH() / 2 + other.getH() / 2) * ratio;
	}

	public void move(double passedSeconds) {
		x += vx * passedSeconds;
		y += vy * passedSeconds;
	}

	public boolean isDead() {
		return isDead;
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public boolean isOutOfFrame(GameFrame frame) {
		return false;
	}
}

class ImageEntity extends GameEntity {

	private Image image;

	public ImageEntity() {
		super();
		this.image = null;
	}

	public ImageEntity(double x, double y) {
		super(x, y);
		this.image = null;
	}

	public ImageEntity(double x, double y, Image image) {
		super(x, y);
		setImage(image);
	}

	public ImageEntity(double x, double y, String imageURL) {
		super(x, y);
		ImageIcon icon = new ImageIcon(imageURL);
		setImage(icon.getImage());
	}

	public void setImage(Image image) {
		this.image = image;
		this.setW(this.image.getWidth(null));
		this.setH(this.image.getHeight(null));
	}

	public Image getImage() {
		return this.image;
	}

}

class PlayerPlane extends ImageEntity {

	private int life;
	private int bullet;
	private int fireDelay;

	public PlayerPlane(double x, double y, int fireDelay) {
		super(x, y, VisHelper.Plane);
		this.fireDelay = fireDelay;
		this.life = 3;
		this.bullet = 0;
	}

	public int getFireDelay() {
		return fireDelay;
	}

	public void setFireDelay(int fireDelay) {
		this.fireDelay = fireDelay;
	}

	public void fire() {
		bullet--;
	}

	public boolean hasBullet() {
		return bullet > 0;
	}

	public void crash() {
		life--;
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

class Enemy extends ImageEntity {

	private int life = 1;
	private int score = 100;

	public Enemy() {
		super();
	}

	public Enemy(double x, double y, Image image) {
		super(x, y, image);
	}

	public Enemy(double x, double y, String imageURL) {
		super(x, y, imageURL);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean hasLife() {
		return life > 0;
	}

	public void beShot() {
		life--;
	}
	
	public void crash() {
		setLife(0);
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public boolean isDead() {
		return life == 0;
	}
	
	@Override
	public boolean isOutOfFrame(GameFrame frame) {
		return getY() > frame.getCanvasHeight();
	}
}

class Stone1 extends Enemy {

	public Stone1() {
		this(0, 0);
	}

	public Stone1(int x, int y) {
		super(x, y, VisHelper.Stone1);
		setLife(4);
		setScore(300);
	}
	
	@Override
	public void beShot() {
		super.beShot();
		if(getLife() == 3) setImage(VisHelper.Stone1_1);
		else if(getLife() == 2) setImage(VisHelper.Stone1_2);
		else if(getLife() == 1) setImage(VisHelper.Stone1_3);
		else if(getLife() == 0) setImage(VisHelper.Stone1_broken);
	}
	
	@Override
	public void crash() {
		super.crash();
		setImage(VisHelper.Stone1_broken);
	}
}

class Stone2 extends Enemy {

	public Stone2() {
		this(0, 0);
	}

	public Stone2(int x, int y) {
		super(x, y, VisHelper.Stone2);
		setLife(2);
		setScore(100);
	}
	
	@Override
	public void beShot() {
		super.beShot();
		if(getLife() == 1) setImage(VisHelper.Stone2_1);
		else if(getLife() == 0) setImage(VisHelper.Stone2_broken);
	}
	
	@Override
	public void crash() {
		super.crash();
		setImage(VisHelper.Stone2_broken);
	}
}

class Bullet extends ImageEntity {

	public Bullet() {
		super(0, 0, VisHelper.Bullet);
	}

	public Bullet(double x, double y, double speed) {
		super(x, y, VisHelper.Bullet);
		setBulletSpeed(speed);
	}

	public void setBulletSpeed(double speed) {
		setSpeed(0, -speed);
	}
	
	@Override
	public boolean isOutOfFrame(GameFrame frame) {
		return getY() + getH() < 0;
	}
}

/**
 * 文字实体类
 */
class TextEntity extends GameEntity {

	private String text; // 储存的文字
	private float size; // 字号大小
	private Color color; // 颜色

	private TextEntity() {
		super(0, 0);
		this.text = "";
		this.size = 40;
	}

	private TextEntity(String text, float size) {
		super(0, 0);
		this.text = text;
		this.size = size;
	}

	private TextEntity(String text, float size, int centerx, int centery, Color color) {
		super(centerx, centery);
		this.text = text;
		this.size = size;
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setCenterX(double x) {
		setX(x);
	}

	public void setCenterY(double y) {
		setY(y);
	}

	public double getCenterX() {
		return getX();
	}

	public double getCenterY() {
		return getY();
	}

	public float getFontSize() {
		return size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setFontSize(float size) {
		this.size = size;
	}

	/**
	 * 构建一个文字TextEntity对象
	 * @param text 文本内容
	 * @param size 字号大小
	 * @param centerx 中心x坐标
	 * @param centery 中心y坐标
	 * @param color 颜色
	 * @return 文字实例TextEntity对象
	 */
	public static TextEntity buildTextEntity(String text, int size, int centerx, int centery, Color color) {
		return new TextEntity(text, size, centerx, centery, color);
	}

}

/**
 * 图形实体类
 */
class ShapeEntity extends GameEntity {

	private Shape shape;
	private Color color;
	private boolean isFilled = false;

	private ShapeEntity() {
		super(0, 0);
	}

	private ShapeEntity(Shape shape) {
		super(0, 0);
		this.shape = shape;
	}

	private ShapeEntity(Shape shape, double x, double y) {
		super(x, y);
		this.shape = shape;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isFilled() {
		return isFilled;
	}

	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}

	/**
	 * 构建一个圆形ShapeEntity对象
	 * @param centerx 圆心x坐标
	 * @param centery 圆心y坐标
	 * @param r 圆的半径
	 * @param color 颜色
	 * @param isFilled 是否填充
	 * @return 圆形ShapeEntity对象
	 */
	public static ShapeEntity buildCircleEntity(double centerx, double centery, double r, Color color,
			boolean isFilled) {
		Ellipse2D circle = new Ellipse2D.Double(centerx - r, centery - r, 2 * r, 2 * r);
		ShapeEntity circleEntity = new ShapeEntity(circle, circle.getX(), circle.getY());
		circleEntity.setColor(color);
		circleEntity.setFilled(isFilled);
		circleEntity.setW(2 * r);
		circleEntity.setH(2 * r);
		return circleEntity;
	}

	/**
	 * 构建一个椭圆形ShapeEntity对象
	 * @param x 左上角x坐标
	 * @param y 左上角y坐标
	 * @param w 椭圆的宽度
	 * @param h 椭圆的高度
	 * @param color 颜色
	 * @param isFilled 是否填充
	 * @return 椭圆形ShapeEntity对象
	 */
	public static ShapeEntity buildEllipseEntity(double x, double y, double w, double h, Color color,
			boolean isFilled) {
		Ellipse2D ellipse = new Ellipse2D.Double(x, y, w, h);
		ShapeEntity ellipseEntity = new ShapeEntity(ellipse, x, y);
		ellipseEntity.setColor(color);
		ellipseEntity.setFilled(isFilled);
		ellipseEntity.setW(w);
		ellipseEntity.setH(h);
		return ellipseEntity;
	}

	/**
	 * 构建一个矩形ShapeEntity对象
	 * @param x 左上角x坐标
	 * @param y 左上角y坐标
	 * @param w 矩形的宽度
	 * @param h 矩形的高度
	 * @param color 颜色
	 * @param isFilled 是否填充
	 * @return 矩形ShapeEntity对象
	 */
	public static ShapeEntity buildRectangleEntity(double x, double y, double w, double h, Color color,
			boolean isFilled) {
		Rectangle2D rectangle = new Rectangle2D.Double(x, y, w, h);
		ShapeEntity rectEntity = new ShapeEntity(rectangle, x, y);
		rectEntity.setColor(color);
		rectEntity.setFilled(isFilled);
		rectEntity.setW(w);
		rectEntity.setH(h);
		return rectEntity;
	}

}