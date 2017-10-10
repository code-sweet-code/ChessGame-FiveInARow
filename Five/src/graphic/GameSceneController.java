package graphic;

import game.GameInterface;
import game.GameObserver;
import javafx.application.Platform;
import javafx.stage.Stage;

public class GameSceneController implements GameObserver, Controller{
	private GameScene scene = null;
	private Stage stage = null;
	private ControllerCollector controllers = null;
	private String playerName = null;
	private String color = null;
	private GameInterface game = null;
	private boolean movable = false;
	private GameSceneController self = this;
	
	public GameSceneController(Stage stage, ControllerCollector controllers) {
		scene = new GameScene(this);
		this.controllers = controllers;
		this.stage = stage;
		this.playerName = "Human";
	}

	@Override
	public String getName() {
		return playerName;
	}

	@Override
	public void waitForPickingColor(final String name) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				if(name.equals(playerName)){
					scene.changeState("Pick a color");
					scene.setColorOptionVisible(true);
				}else{
					scene.changeState("Opponent is picking a color");
					scene.setColorOptionVisible(false);
				}
				scene.setGameEndsOptionsVisible(false);
				scene.setRollBtnVisible(false);
			}
			
		});
		
		
	}

	@Override
	public void waitForMove(final String name) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				if(name.equals(playerName)){
					scene.changeState("Your turn");
					scene.setStateVisible(true);
					movable = true;
				}
				else{
					scene.changeState("Opponent's turn");
					scene.setStateVisible(true);
				}
			}
			
		});
		
	}

	@Override
	public void winnerIs(final String name) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				if(name.equals(playerName)){
					scene.changeState("You Won");
					scene.setStateVisible(true);
				}else{
					scene.changeState("You Lost");
					scene.setStateVisible(true);
				}
				scene.setGameEndsOptionsVisible(true);
				scene.setColorOptionVisible(false);
				scene.setRollBtnVisible(false);
				scene.setGiveupBtnVisible(false);
			}
			
		});
		
	}

	@Override
	public void waitForRolling() {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				scene.changeState("Roll a number");
				scene.setRollBtnVisible(true);
				scene.setColorOptionVisible(false);
				scene.setGameEndsOptionsVisible(false);
			}
			
		});
		
	}

	@Override
	public void moved(final String name, final int x, final int y) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				String drawColor;
				if(playerName.equals(name)){
					drawColor = color;
				}else{
					drawColor = (color.equals("white"))?"black":"white";
				}
				
				scene.drawPiece(drawColor, x, y);
			}
			
		});
		
	}

	@Override
	public void UnvalidMove() {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				scene.changeState("Unvalid position");
				scene.setStateVisible(true);
				movable = true;
			}
			
		});
		
	}
	
	@Override
	public void setGameInterface(GameInterface game) {
		this.game = game;
	}

	@Override
	public void pickedColor(final String mycolor) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				color = mycolor;
				scene.changeState("Your color is "+color);
				scene.setStateVisible(true);
			}
			
		});
		
		
	}

	@Override
	public void gameRestarted() {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				scene = new GameScene(self);
				show();
			}
			
		});
		
	}

	@Override
	public void show(){
		stage.setScene(scene.getScene());
		stage.show();
	}

	
	public void giveUp() {
		game.giveUpGame(playerName);
	}

	public void move(int boardX, int boardY) {
		if(movable){
			movable = false;
			scene.changeState("Waiting for response");
			scene.setStateVisible(true);
			if(game != null){
			/*	if(game.isMoveValid(boardX, boardY)){
					scene.drawPiece(color, boardX, boardY);
					game.makeMove(playerName, boardX, boardY);
				}else{
					UnvalidMove();
				}*/
				game.makeMove(playerName, boardX, boardY);
			}
		}
	}

	public void selectWhite() {
		color = "white";
		scene.setColorOptionVisible(false);
		scene.changeState("Waiting for response");
		scene.setStateVisible(true);
		if(game != null){
			game.pickColor(playerName, color);
		}
	}

	public void selectBlack() {
		color = "black";
		scene.setColorOptionVisible(false);
		scene.changeState("Waiting for response");
		scene.setStateVisible(true);
		if(game != null){
			game.pickColor(playerName, color);
		}
	}

	public void rollButtonOnClick() {
		scene.changeState("Waiting for opponent roll");
		scene.setStateVisible(true);
		scene.setRollBtnVisible(false);
		if(game != null){
			game.makeRoll(playerName);
		}
	}

	
	public void restart(){
		game.restartGame();
	}

	public void goBack() {
		game.quitGame();
	//	scene = new GameScene(this);
	//	controllers.getController("WELCOME").show();
	}

	@Override
	public void setName(String name) {
		playerName = name;
	}

	@Override
	public void gameEnds() {
		scene = new GameScene(this);
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				controllers.getController("WELCOME").show();
			}
			
		});
		
	}
}
