package graphic;

import game.GameLauncher;
import game.GameObserver;
import javafx.application.Platform;
import javafx.stage.Stage;
import network.NetWork;
import network.NetworkObserver;

public class MultiplayerSceneController implements Controller, NetworkObserver {
	private MultiplayerScene scene = null;
	private Stage stage = null;
	private ControllerCollector controllers = null;
	private boolean isHost = false;
	private boolean isProcessing = false;
	private NetWork network = null;
	private String ip = "";
	
	public MultiplayerSceneController(Stage stage, ControllerCollector controllers) {
		this.scene = new MultiplayerScene(this);
		this.stage = stage;
		this.controllers = controllers;
	}

	@Override
	public void show() {
		stage.setScene(scene.getScene());
		stage.show();
	}

	public void hostGame() {
		if(!isProcessing){
			isProcessing = true;
			isHost = true;
			ip = scene.getTextField();
			network = new NetWork(ip);
			network.addObserver(this);
			scene.changeState("Connecting to joiner");
			new Thread(new Runnable(){

				@Override
				public void run() {
					network.connect();
				}
				
			}).start();
			
		}
	}

	public void goBack() {
		isProcessing = false;
		if(network != null){
			network.close();
		}
		controllers.getController("WELCOME").show();
	}

	public void joinGame() {
		if(!isProcessing){
			isProcessing = true;
			isHost = false;
			String ip = scene.getTextField();
			network = new NetWork(ip);
			network.addObserver(this);
			scene.changeState("Connecting to host");
			new Thread(new Runnable(){

				@Override
				public void run() {
					network.connect();
				}
				
			}).start();
		}
	}

	@Override
	public void networkMessage(String message) {
		isProcessing = false;
	}

	@Override
	public void networkError(final String error) {
		isProcessing = false;
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				scene.changeState(error);
			}
			
		});
		
		
	}

	@Override
	public void networkNotification(final String notification) {
		isProcessing = false;
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				scene.changeState(notification);
			}
			
		});
	}

	@Override
	public void connectionEstablished() {
		isProcessing = false;
		final Controller gameController = controllers.getController("GAME");
		
		GameLauncher launcher = new GameLauncher();
		GameObserver observer = (GameObserver) gameController;
		if(isHost){
			launcher.multiGameHost(network, observer);
		}else{
			launcher.multiGameGuest(network, observer);
		}
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				scene.changeState("");
				gameController.show();
				
				
			}
			
		});
		
	}

}
