package cn.edu.scau.cmi.glz;

import java.util.ArrayList;

/**
 * GameModel类用于储存游戏数据（Model）
 */
public class GameModel {
	
	private ArrayList<ImageEntity> imageEntities;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	private ArrayList<TextEntity> texts;
	private ArrayList<ShapeEntity> shapes;
	private PlayerPlane player;
	
	
	public GameModel() {
		imageEntities = new ArrayList<>();
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();
		texts = new ArrayList<>();
		shapes = new ArrayList<>();
		player = null;
	}
	
	public ArrayList<ImageEntity> getImageEntites() {
		return imageEntities;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ImageEntity> getImageEntitesCopy(){
		return (ArrayList<ImageEntity>) imageEntities.clone();
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Enemy> getEnemiesCopy(){
		return (ArrayList<Enemy>) enemies.clone();
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Bullet> getBulletsCopy(){
		return (ArrayList<Bullet>) bullets.clone();
	}
	
	public ArrayList<TextEntity> getTexts() {
		return texts;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TextEntity> getTextsCopy(){
		return (ArrayList<TextEntity>) texts.clone();
	}
	
	public ArrayList<ShapeEntity> getShapes() {
		return shapes;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ShapeEntity> getShapesCopy(){
		return (ArrayList<ShapeEntity>) shapes.clone();
	}
	
	public void setPlayer(PlayerPlane player) {
		this.player = player;
	}
	
	public PlayerPlane getPlayer() {
		return player;
	}
	
	public void addEnemy(Enemy enemy) {
		imageEntities.add(enemy);
		enemies.add(enemy);
	}
	
	public void addBullet(Bullet bullet) {
		imageEntities.add(bullet);
		bullets.add(bullet);
	}
	
	public void deleteEnemy(Enemy enemy) {
		enemies.remove(enemy);
		imageEntities.remove(enemy);
	}
	
	public void deleteBullet(Bullet bullet) {
		bullets.remove(bullet);
		imageEntities.remove(bullet);
	}

}

