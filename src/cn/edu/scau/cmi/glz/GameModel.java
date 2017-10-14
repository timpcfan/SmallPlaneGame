package cn.edu.scau.cmi.glz;

import java.util.ArrayList;

/**
 * GameModel类用于储存游戏数据（Model）
 */
public class GameModel {
	
	private ArrayList<ImageEntite> entiteList;
	private ArrayList<ImageEntite> enemyList;
	private ArrayList<ImageEntite> bulletList;
	private ImageEntite player;
	
	
	public GameModel() {
		entiteList = new ArrayList<>();
		enemyList = new ArrayList<>();
		bulletList = new ArrayList<>();
		player = null;
	}
	
	public ArrayList<ImageEntite> getAllEntites() {
		return entiteList;
	}
	
	public ArrayList<ImageEntite> getEnemies(){
		return enemyList;
	}
	
	public ArrayList<ImageEntite> getBullets(){
		return bulletList;
	}
	
	public void setPlayer(ImageEntite player) {
		this.player = player;
	}
	
	public ImageEntite getPlayer() {
		return player;
	}
	
	public void addEnemy(ImageEntite enemy) {
		entiteList.add(enemy);
		enemyList.add(enemy);
	}
	
	public void addBullet(ImageEntite bullet) {
		entiteList.add(bullet);
		bulletList.add(bullet);
	}

}
