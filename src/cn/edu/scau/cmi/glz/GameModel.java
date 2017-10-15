package cn.edu.scau.cmi.glz;

import java.util.ArrayList;

import com.sun.xml.internal.fastinfoset.algorithm.IEEE754FloatingPointEncodingAlgorithm;

/**
 * GameModel类用于储存游戏数据（Model）
 */
public class GameModel {
	
	private ArrayList<ImageEntity> entiteList;
	private ArrayList<ImageEntity> enemyList;
	private ArrayList<ImageEntity> bulletList;
	private PlayerPlane player;
	
	
	public GameModel() {
		entiteList = new ArrayList<>();
		enemyList = new ArrayList<>();
		bulletList = new ArrayList<>();
		player = null;
	}
	
	public ArrayList<ImageEntity> getAllEntites() {
		return entiteList;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ImageEntity> getAllEntitesCopy(){
		return (ArrayList<ImageEntity>) entiteList.clone();
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
	
	public void setPlayer(PlayerPlane player) {
		this.player = player;
	}
	
	public ImageEntity getPlayer() {
		return player;
	}
	
	public void addEnemy(ImageEntity enemy) {
		entiteList.add(enemy);
		enemyList.add(enemy);
	}
	
	public void addBullet(ImageEntity bullet) {
		entiteList.add(bullet);
		bulletList.add(bullet);
	}

}