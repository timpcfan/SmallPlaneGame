package cn.edu.scau.cmi.glz;

import java.util.ArrayList;

import com.sun.xml.internal.fastinfoset.algorithm.IEEE754FloatingPointEncodingAlgorithm;

/**
 * GameModel类用于储存游戏数据（Model）
 */
public class GameModel {
	
	private ArrayList<ImageEntity> entityList;
	private ArrayList<ImageEntity> enemyList;
	private ArrayList<ImageEntity> bulletList;
	private ArrayList<TextEntity> textList;
	private PlayerPlane player;
	
	
	public GameModel() {
		entityList = new ArrayList<>();
		enemyList = new ArrayList<>();
		bulletList = new ArrayList<>();
		textList = new ArrayList<>();
		player = null;
	}
	
	public ArrayList<ImageEntity> getAllEntites() {
		return entityList;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ImageEntity> getAllEntitesCopy(){
		return (ArrayList<ImageEntity>) entityList.clone();
	}
	
	public ArrayList<ImageEntity> getEnemies(){
		return enemyList;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ImageEntity> getEnemiesCopy(){
		return (ArrayList<ImageEntity>) enemyList.clone();
	}
	
	public ArrayList<ImageEntity> getBullets(){
		return bulletList;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ImageEntity> getBulletsCopy(){
		return (ArrayList<ImageEntity>) bulletList.clone();
	}
	
	public ArrayList<TextEntity> getTexts() {
		return textList;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TextEntity> getTextsCopy(){
		return (ArrayList<TextEntity>) textList.clone();
	}
	
	public void setPlayer(PlayerPlane player) {
		this.player = player;
	}
	
	public PlayerPlane getPlayer() {
		return player;
	}
	
	public void addEnemy(ImageEntity enemy) {
		entityList.add(enemy);
		enemyList.add(enemy);
	}
	
	public void addBullet(ImageEntity bullet) {
		entityList.add(bullet);
		bulletList.add(bullet);
	}
	
	public void deleteEnemy(ImageEntity enemy) {
		enemyList.remove(enemy);
		entityList.remove(enemy);
	}
	
	public void deleteBullet(ImageEntity bullet) {
		bulletList.remove(bullet);
		entityList.remove(bullet);
	}

}

