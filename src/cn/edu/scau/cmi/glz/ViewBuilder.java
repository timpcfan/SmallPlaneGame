package cn.edu.scau.cmi.glz;

public class ViewBuilder {

	/**
	 * 生成一个主界面的数据模型
	 * @return 主界面的数据模型
	 */
	public static GameModel buildMainView(GameFrame frame) {
		GameModel model = new GameModel();
		// TODO 向model中添加实体构成主界面
		
		return model;
	}
	
	/**
	 * 生成游戏结束界面的数据模型
	 * @return 游戏结束界面的数据模型
	 */
	public static GameModel buildGameoverView(GameFrame frame) {
		GameModel model = new GameModel();
		// TODO 向model中添加实体构成游戏结束界面
		
		return model;
	}
	
	/**
	 * 生成一个示例界面
	 * @return 一个示例界面数据模型Model
	 */
	public static GameModel buildDemoView(GameFrame frame) {
		GameModel model = new GameModel();
		
		// 添加文字
		// 注：文字只使用英文
		TextEntity text1 = TextEntity.buildTextEntity("Hello, World!", 50,
									frame.getCanvasWidth() / 2, frame.getCanvasHeight() / 3,
									VisHelper.Cyan);
		model.addText(text1);
		TextEntity text2 = TextEntity.buildTextEntity("_(:3LZ)_", 30,
									300, 600,
									VisHelper.LightBlue);
		model.addText(text2);
		
		// 添加图形
		ShapeEntity circle = ShapeEntity.buildCircleEntity(300, 400, 30, VisHelper.Amber, true);
		model.addShape(circle);
		ShapeEntity rect = ShapeEntity.buildRectangleEntity(100, 500, 30, 60, VisHelper.Brown, true);
		model.addShape(rect);
		ShapeEntity ellipse = ShapeEntity.buildEllipseEntity(200, 20, 260, 120, VisHelper.Red, false);
		model.addShape(ellipse);
		
		// 添加图片
		model.addImage(new ImageEntity(20, 600, "resources/ufo.png"));

		
		return model;
	}
	
	/**
	 * 此main方法用于测试界面
	 */
	public static void main(String[] args) {
		GameFrame frame = new GameFrame("测试用界面", 500, 800);
		GameModel model = buildDemoView(frame); // 这里将model赋值成需要测试的界面数据模型Model
		frame.render(model);
	}

}
