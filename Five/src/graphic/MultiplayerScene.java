package graphic;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import network.NetWork;

public class MultiplayerScene {
	private MultiplayerSceneController controller = null;
	private Scene scene = null;
	private TextField targetIP = null;
	private Label state = null;
	
	public MultiplayerScene(MultiplayerSceneController controller){
		this.controller = controller;
		this.scene = generateScene();
	}

	private Scene generateScene() {
		Label subnetIPLabel = new Label();
		subnetIPLabel.setText("Subnet IP: "+ NetWork.getMySubnetIP());
		subnetIPLabel.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		Label publicIPLabel = new Label();
	//	publicIPLabel.setText("Public IP: "+ NetWork.getMyPublicIP());
		publicIPLabel.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		
		Label targetIPLabel = new Label("Play with:");
		targetIPLabel.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		targetIP = new TextField();
		targetIP.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		HBox ipHBox = new HBox();
		ipHBox.getChildren().addAll(targetIPLabel, targetIP);
		ipHBox.setSpacing(10);
		ipHBox.setAlignment(Pos.CENTER);
		
		state = new Label();
		state.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 15;");
		
		Button hostBtn = new Button("Host A Game");
		Button joinBtn = new Button("Join A Game");
		Button backBtn = new Button("Go Back");
		hostBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			
			@Override
			public void handle(MouseEvent event) {
				controller.hostGame();
			}
			
		});
		joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
					
			@Override
			public void handle(MouseEvent event) {
				controller.joinGame();
			}
			
		});
		backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			
			@Override
			public void handle(MouseEvent event) {
				controller.goBack();
			}
			
		});
		HBox btnBox = new HBox();
		btnBox.setSpacing(15);
		btnBox.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		btnBox.getChildren().addAll(hostBtn, joinBtn, backBtn);
		btnBox.setAlignment(Pos.CENTER);
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(subnetIPLabel, publicIPLabel, ipHBox, state, btnBox);
		vbox.setSpacing(25);
		vbox.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(vbox);
		pane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		Scene newScene = new Scene(pane);
		newScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Abril+Fatface");
		return newScene;
	}
	
	public Scene getScene(){
		return scene;
	}
	
	public String getTextField(){
		return targetIP.getText();
	}
	
	public void changeState(String str){
		state.setText(str);
	}
}
