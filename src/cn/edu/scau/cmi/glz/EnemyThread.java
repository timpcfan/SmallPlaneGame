package cn.edu.scau.cmi.glz;

public class EnemyThread extends Thread{
	
	private GameFrame frame;
	private GameModel model;
	private int interval; // 生成敌人的时间间隔（毫秒）
	
	public EnemyThread(GameFrame frame, GameModel model, int initialInterval) {
		super();
		this.frame = frame;
		this.model = model;
		this.interval = initialInterval;
	}
	
	@Override
	public void run() {
		
		
		while(true){
			
			VisHelper.pause(interval);
			
			
			
			int x = (int)(Math.random() * frame.getCanvasWidth());
			int y = -113;
			int r = 53;
			int vy = (int)(Math.random() * 100);
			
			
			Enemy enemy = new Enemy(x, y, r);
			enemy.setSpeed(0, vy);
			
			model.addEnemy(enemy);
			
		}
		
	}
	

}
