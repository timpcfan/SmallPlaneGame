package cn.edu.scau.cmi.glz;

import java.util.ArrayList;



/**
 * GameModel类用于储存游戏数据（Model）
 */
public class GameModel {
	
	private ArrayList<ImageEntity> topLevelImages;
	private ArrayList<ImageEntity> imageEntities;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	private ArrayList<TextEntity> texts;
	private ArrayList<ShapeEntity> shapes;
	private PlayerPlane player;
	private ViewType viewType;
	
	
	public GameModel() {
		topLevelImages = new ArrayList<>();
		imageEntities = new ArrayList<>();
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();
		texts = new ArrayList<>();
		shapes = new ArrayList<>();
		viewType = null;
		player = null;
	}
	
	public ViewType getViewType() {
		return viewType;
	}

	public void setViewType(ViewType viewType) {
		this.viewType = viewType;
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
	
	public ArrayList<ImageEntity> getTopLevelImages() {
		return topLevelImages;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ImageEntity> getTopLevelImagesCopy(){
		return (ArrayList<ImageEntity>) topLevelImages.clone();
	}
	
	public void setPlayer(PlayerPlane player) {
		this.player = player;
	}
	
	public PlayerPlane getPlayer() {
		return player;
	}
	
	public void addImage(ImageEntity imageEntity) {
		imageEntities.add(imageEntity);
	}
	
	public void deleteImage(ImageEntity imageEntity) {
		imageEntities.remove(imageEntity);
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
	
	public void addText(TextEntity textEntity) {
		texts.add(textEntity);
	}
	
	public void deleteText(TextEntity textEntity) {
		texts.remove(textEntity);
	}
	
	public void addShape(ShapeEntity shapeEntity) {
		shapes.add(shapeEntity);
	}
	
	public void deleteShape(ShapeEntity shapeEntity) {
		shapes.remove(shapeEntity);
	}
	
	public void addImageToTopLevel(ImageEntity imageEntity) {
		topLevelImages.add(imageEntity);
	}
	
	public void deleteImageFromTopLevel(ImageEntity imageEntity) {
		topLevelImages.remove(imageEntity);
	}
	
	public void clearAll() {
		imageEntities.clear();
		enemies.clear();
		bullets.clear();
		texts.clear();
		shapes.clear();
		player = null;
	}

}

enum ViewType {
	MAIN, GAMING, GAMEOVER
}

