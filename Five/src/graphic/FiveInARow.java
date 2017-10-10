package graphic;

import javafx.application.Application;
import javafx.stage.Stage;

public class FiveInARow extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Five In A Row");
		stage.setHeight(600);
		stage.setWidth(800);
		ControllerCollector controllers = new ControllerCollector();
		Controller welcomeController = new WelcomeSceneController(stage, controllers);
		Controller gameController = new GameSceneController(stage, controllers);
		Controller multiController = new MultiplayerSceneController(stage, controllers);
		controllers.addController("GAME", gameController);
		controllers.addController("WELCOME", welcomeController);
		controllers.addController("MULTI", multiController);
		welcomeController.show();
	}


}
