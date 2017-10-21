package cn.edu.scau.cmi.glz;

import java.util.ArrayList;

/**
 * GameModel类用于储存游戏数据（Model）
 */
public class GameModel {
	
	// 游戏的数据
	private ArrayList<ImageEntity> imageEntities; // 图像实体链表
	private ArrayList<Enemy> enemies; // 敌人实体链表
	private ArrayList<Bullet> bullets; // 子弹实体链表
	private ArrayList<TextEntity> texts; // 文字实体链表
	private ArrayList<ShapeEntity> shapes; // 形状实体链表
	private PlayerPlane player; // 玩家实体
	private ViewType viewType; // 当前Model的类型
	private int score; // 分数
	
	
	public GameModel() {
		imageEntities = new ArrayList<>();
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();
		texts = new ArrayList<>();
		shapes = new ArrayList<>();
		viewType = null;
		player = null;
		score = 0;
	}
	
	// 获取当前Model类型
	public ViewType getViewType() {
		return viewType;
	}

	// 设置Model类型
	public void setViewType(ViewType viewType) {
		this.viewType = viewType;
	}
	
	// 获取当前分数
	public int getScore() {
		return score;
	}

	// 设置分数为指定值
	public void setScore(int score) {
		this.score = score;
	}
	
	// 加指定的分数
	public void addScore(int score) {
		this.score += score;
	}

	// 获取图像实体链表
	public ArrayList<ImageEntity> getImageEntites() {
		return imageEntities;
	}
	
	// 获得图像实体链表的拷贝（用于遍历链表）
	@SuppressWarnings("unchecked")
	public ArrayList<ImageEntity> getImageEntitesCopy(){
		return (ArrayList<ImageEntity>) imageEntities.clone();
	}
	
	// 获取敌人实体链表
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
	// 获得敌人实体链表的拷贝（用于遍历链表）
	@SuppressWarnings("unchecked")
	public ArrayList<Enemy> getEnemiesCopy(){
		return (ArrayList<Enemy>) enemies.clone();
	}
	
	// 获取子弹实体链表
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	// 获得子弹实体链表的拷贝（用于遍历链表）
	@SuppressWarnings("unchecked")
	public ArrayList<Bullet> getBulletsCopy(){
		return (ArrayList<Bullet>) bullets.clone();
	}
	
	// 获取文字实体链表
	public ArrayList<TextEntity> getTexts() {
		return texts;
	}
	
	// 获得文字实体链表的拷贝（用于遍历链表）
	@SuppressWarnings("unchecked")
	public ArrayList<TextEntity> getTextsCopy(){
		return (ArrayList<TextEntity>) texts.clone();
	}
	
	// 获取形状实体链表
	public ArrayList<ShapeEntity> getShapes() {
		return shapes;
	}
	
	// 获得形状实体链表的拷贝（用于遍历链表）
	@SuppressWarnings("unchecked")
	public ArrayList<ShapeEntity> getShapesCopy(){
		return (ArrayList<ShapeEntity>) shapes.clone();
	}
	
	// 设置玩家
	public void setPlayer(PlayerPlane player) {
		this.player = player;
	}
	
	// 获取玩家
	public PlayerPlane getPlayer() {
		return player;
	}
	
	// 添加图像实体
	public void addImage(ImageEntity imageEntity) {
		imageEntities.add(imageEntity);
	}
	
	// 删除图像实体
	public void deleteImage(ImageEntity imageEntity) {
		imageEntities.remove(imageEntity);
	}
	
	// 添加敌人实体
	public void addEnemy(Enemy enemy) {
		imageEntities.add(enemy);
		enemies.add(enemy);
	}
	
	// 添加子弹实体
	public void addBullet(Bullet bullet) {
		imageEntities.add(bullet);
		bullets.add(bullet);
	}
	
	// 删除敌人实体
	public void deleteEnemy(Enemy enemy) {
		enemies.remove(enemy);
		imageEntities.remove(enemy);
	}
	
	// 删除子弹实体
	public void deleteBullet(Bullet bullet) {
		bullets.remove(bullet);
		imageEntities.remove(bullet);
	}
	
	// 添加文字实体
	public void addText(TextEntity textEntity) {
		texts.add(textEntity);
	}
	
	// 删除文字实体
	public void deleteText(TextEntity textEntity) {
		texts.remove(textEntity);
	}
	
	// 添加图形实体
	public void addShape(ShapeEntity shapeEntity) {
		shapes.add(shapeEntity);
	}
	
	// 删除图形实体
	public void deleteShape(ShapeEntity shapeEntity) {
		shapes.remove(shapeEntity);
	}
	
	// 清空Model中所有内容
	public void clearAll() {
		imageEntities.clear();
		enemies.clear();
		bullets.clear();
		texts.clear();
		shapes.clear();
		player = null;
		score = 0;
	}

}

/**
 * 指示Model的类型
 */
enum ViewType {
	MAIN, 		// 主界面
	GAMING, 	// 游戏中
	GAMEOVER	// 游戏结束
}

