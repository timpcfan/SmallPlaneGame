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

	/**
	 * 碰撞检测方法
	 * @param other 另一个对象
	 * @param ratio 碰撞检测的百分比
	 * @return 是否碰撞
	 */
	public boolean collideWith(GameEntity other, double ratio) {

		return Math.abs(this.getCenterX() - other.getCenterX()) < (this.getW() / 2 + other.getW() / 2) * ratio
				&& Math.abs(this.getCenterY() - other.getCenterY()) < (this.getH() / 2 + other.getH() / 2) * ratio;
	}

	/**
	 * 实体移动方法
	 * @param passedSeconds 距离上一帧经过的时间（秒）
	 */
	public void move(double passedSeconds) {
		// 距离 ＝ 速度 × 时间
		x += vx * passedSeconds;
		y += vy * passedSeconds;
	}

	// 判断对象是否死亡
	public boolean isDead() {
		return isDead;
	}
	
	// 设置对象死亡
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	// 判断对象是否出界
	public boolean isOutOfFrame(GameFrame frame) {
		return false;
	}
}

/**
 * 图像实体类，继承于GameEntity
 * 增加了图像的数据域
 */
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

	// 设置对象的图像
	public void setImage(Image image) {
		this.image = image;
		this.setW(this.image.getWidth(null));
		this.setH(this.image.getHeight(null));
	}

	// 获得对象的图像
	public Image getImage() {
		return this.image;
	}

}

/**
 * 玩家飞机类，继承于ImageEntity类
 */
class PlayerPlane extends ImageEntity {

	private int life; // 生命数
	private int fireDelay; // 开火的延迟

	public PlayerPlane(double x, double y, int fireDelay) {
		super(x, y, VisHelper.Plane);
		this.fireDelay = fireDelay;
		this.life = 3;
	}

	public int getFireDelay() {
		return fireDelay;
	}

	public void setFireDelay(int fireDelay) {
		this.fireDelay = fireDelay;
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
}

/**
 * 敌人实体类，继承与ImageEntity类
 */
class Enemy extends ImageEntity {

	private int life = 1; // 生命数
	private int score = 100; // 击毁获得的分数

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

	// 重写死亡判断方法，用于垃圾回收
	@Override
	public boolean isDead() {
		return life == 0;
	}
	
	// 重写出界判定方法，用于垃圾回收
	@Override
	public boolean isOutOfFrame(GameFrame frame) {
		return getY() > frame.getCanvasHeight();
	}
}

/**
 * 大陨石类，继承于Enemy类
 * 拥有大陨石的图像
 * 有4条命
 * 击毁获得300分
 */
class Stone1 extends Enemy {

	public Stone1() {
		this(0, 0);
	}

	public Stone1(int x, int y) {
		super(x, y, VisHelper.Stone1);
		setLife(4);
		setScore(300);
	}
	
	// 被击中时会调用该方法，更换陨石的图像
	// 使其产生裂纹
	@Override
	public void beShot() {
		super.beShot();
		if(getLife() == 3) setImage(VisHelper.Stone1_1);
		else if(getLife() == 2) setImage(VisHelper.Stone1_2);
		else if(getLife() == 1) setImage(VisHelper.Stone1_3);
		else if(getLife() == 0) setImage(VisHelper.Stone1_broken);
	}
	
	// 撞击到玩家会调用这个方法，更换石头的图像，并设置生命为0
	@Override
	public void crash() {
		super.crash();
		setImage(VisHelper.Stone1_broken);
	}
}

/**
 * 小陨石类，继承于Enemy类
 * 拥有小陨石的图像
 * 有2条命
 * 击毁获得100分
 */
class Stone2 extends Enemy {

	public Stone2() {
		this(0, 0);
	}

	public Stone2(int x, int y) {
		super(x, y, VisHelper.Stone2);
		setLife(2);
		setScore(100);
	}
	
	// 被击中时会调用该方法，更换陨石的图像
	// 使其产生裂纹
	@Override
	public void beShot() {
		super.beShot();
		if(getLife() == 1) setImage(VisHelper.Stone2_1);
		else if(getLife() == 0) setImage(VisHelper.Stone2_broken);
	}
	
	// 撞击到玩家会调用这个方法，更换石头的图像，并设置生命为0
	@Override
	public void crash() {
		super.crash();
		setImage(VisHelper.Stone2_broken);
	}
}

/**
 * 子弹类，继承于ImageEntity类
 */
class Bullet extends ImageEntity {

	public Bullet() {
		super(0, 0, VisHelper.Bullet);
	}

	public Bullet(double x, double y, double speed) {
		super(x, y, VisHelper.Bullet);
		setBulletSpeed(speed);
	}

	// 设置子弹速度
	public void setBulletSpeed(double speed) {
		setSpeed(0, -speed);
	}
	
	// 重写出界判定方法，用于垃圾回收
	@Override
	public boolean isOutOfFrame(GameFrame frame) {
		return getY() + getH() < 0;
	}
}

/**
 * 文字实体类，继承于GameEntity类
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
 * 图形实体类，继承于GameEntity类
 */
class ShapeEntity extends GameEntity {

	private Shape shape; // 储存的形状对象
	private Color color; // 颜色
	private boolean isFilled = false; // 是否填充

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