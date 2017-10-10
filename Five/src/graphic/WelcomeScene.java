package graphic;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class WelcomeScene {
	private WelcomeSceneController controller = null;
	private Scene scene = null;
	
	public WelcomeScene(WelcomeSceneController controller){
		this.controller = controller;
		this.scene = generateScene();
	}
	
	public Scene getScene(){
		return scene;
	}
	
	private Scene generateScene(){
		BorderPane border = new BorderPane();
		border.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		TilePane tile = new TilePane(Orientation.VERTICAL);
		tile.setAlignment(Pos.TOP_CENTER);
		tile.setVgap(30);
		
		Label title = new Label("Five In a Row");
		title.setStyle("-fx-font-family: 'Pacifico', cursive; -fx-font-size: 80;");
		title.setTextFill(Color.DEEPSKYBLUE);
		title.setMaxWidth(Double.MAX_VALUE);
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(100,0,100,0));
		
		
		Button singleMode = new Button("Single-Player Mode");
		Button multiMode = new Button("Multi-Player Mode");
		singleMode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		singleMode.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		singleMode.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			
			@Override
			public void handle(MouseEvent event) {
				controller.singleModeSelected();
			}
			
		});
		multiMode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		multiMode.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		multiMode.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				controller.multiModeSelected();
			}
			
		});
		
		tile.getChildren().addAll(singleMode, multiMode);
		border.setTop(title);
		border.setCenter(tile);
		
		
		Scene newScene = new Scene(border);
		newScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Abril+Fatface");
		newScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Pacifico");
		return newScene;
	}
}
