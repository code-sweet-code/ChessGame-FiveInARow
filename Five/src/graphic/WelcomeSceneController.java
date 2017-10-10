package graphic;

import game.GameLauncher;
import game.GameObserver;
import javafx.stage.Stage;

public class WelcomeSceneController implements Controller{
	private WelcomeScene scene = null;
	private Stage stage = null;
	private ControllerCollector controllers = null;
	
	public WelcomeSceneController(Stage stage, ControllerCollector controllers) {
		this.scene = new WelcomeScene(this);
		this.stage = stage;
		this.controllers = controllers;
	}

	public void singleModeSelected() {
		Controller gameController = controllers.getController("GAME");
		gameController.show();
		GameLauncher launcher = new GameLauncher();
		GameObserver observer = (GameObserver) gameController;
		launcher.singleGame(observer);
	}
	public void multiModeSelected() {
		Controller multiController = controllers.getController("MULTI");
		multiController.show();
	}
	
	@Override
	public void show(){
		stage.setScene(scene.getScene());
		stage.show();
	}

	
}
