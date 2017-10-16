package cn.edu.scau.cmi.glz;

public class ViewBuilder {

	/**
	 * 生成一个主界面的数据模型
	 * @return 主界面的数据模型
	 */
	public static GameModel buildMainView(GameFrame frame) {
		GameModel model = new GameModel();
		// TODO 向model中添加实体构成主界面
		TextEntity text1 = TextEntity.buildTextEntity("Play", 30,
				frame.getCanvasWidth() / 2, frame.getCanvasHeight() / 2,
				VisHelper.DeepOrange);
        model.addText(text1);
        TextEntity text2 = TextEntity.buildTextEntity("Close", 30,
        		frame.getCanvasWidth() / 2, frame.getCanvasHeight() / 2+125,
				VisHelper.Sky);
        model.addText(text2);
        TextEntity text3 = TextEntity.buildTextEntity("Welcome to Tiny Plane!", 30,
				frame.getCanvasWidth() / 2, frame.getCanvasHeight() / 2-100,
				VisHelper.Orange);
        model.addText(text3);

// 添加图形
        ShapeEntity ellipse1 = ShapeEntity.buildEllipseEntity(150, 325, 200, 110, VisHelper.Teal, true);
		model.addShape(ellipse1);
		ShapeEntity ellipse2 = ShapeEntity.buildEllipseEntity(150, 450, 200, 110, VisHelper.Teal, true);
		model.addShape(ellipse2);
// 添加图片
        model.addImage(new ImageEntity(300, 100, "resources/stone1.png"));
        model.addImage(new ImageEntity(20, 600, "resources/ufo.png"));
        model.addImage(new ImageEntity(20, 600, "resources/bullet.png"));
        model.addImage(new ImageEntity(230, 600, "resources/bullet.png"));

		
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
		GameFrame frame = new GameFrame("游戏开始界面", 500, 800);
		GameModel model = buildMainView(frame); // 这里将model赋值成需要测试的界面数据模型Model
		frame.render(model);
	}

}
