package graphic;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameScene {
	private GameSceneController controller = null;
	private Scene scene = null;
	private Label state = null;
	private Canvas upLayer = null;
	private HBox colorOptions = null;
	private HBox gameEndsOptions = null;
	private Button rollBtn = null;
	private Button giveupButton = null;
	private final double GAP = 30;
	private final double PIECEWIDTH = 20;
	
	public GameScene(GameSceneController controller){
		this.controller = controller;
		this.scene = generateScene();
	}

	private Scene generateScene() {
		BorderPane border = new BorderPane();
		border.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
		
		state = new Label();
		state.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		state.setAlignment(Pos.CENTER);
		state.setMaxWidth(Double.MAX_VALUE);
		state.setPadding(new Insets(10,10,10,10));
		
		Canvas board = new Canvas(420, 420);
		GraphicsContext gc = board.getGraphicsContext2D();
		drawBoard(gc);
		upLayer = new Canvas(440, 440);
		upLayer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				double x = event.getX();
				double y = event.getY();
				int boardX = mouseToBoard(x);
				int boardY = mouseToBoard(y);
				controller.move(boardX, boardY);
			}
			
		});
		
		initPickColorOptions();
		initRollButton();
		initGameEndsOptions();
		StackPane pane = new StackPane();
		pane.getChildren().addAll(board, upLayer, colorOptions, rollBtn, gameEndsOptions);
		pane.setAlignment(Pos.CENTER);
		
		giveupButton = new Button("Give Up");
		giveupButton.setPadding(new Insets(5,10,5,10));
		giveupButton.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		giveupButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				controller.giveUp();
			}
			
		});
		HBox bottomButton = new HBox();
		bottomButton.setAlignment(Pos.CENTER_RIGHT);
		bottomButton.getChildren().add(giveupButton);
		HBox.setMargin(giveupButton, new Insets(0,50,10,0));
		
		border.setTop(state);
		border.setCenter(pane);
		border.setBottom(bottomButton);
		
		Scene newScene = new Scene(border);
		newScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Abril+Fatface");
		return newScene;
	}
	
	private void initGameEndsOptions() {
		gameEndsOptions = new HBox();
		gameEndsOptions.setSpacing(50);
		gameEndsOptions.setAlignment(Pos.CENTER);
		gameEndsOptions.setVisible(false);
		Button restartBtn = new Button("Play Again");
		restartBtn.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		restartBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				controller.restart();
				e.consume();
			}
			
		});
		Button goBackBtn = new Button("Go Back");
		goBackBtn.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		goBackBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				controller.goBack();
				e.consume();
			}
			
		});
		gameEndsOptions.getChildren().addAll(restartBtn, goBackBtn);
	}

	private void initRollButton() {
		rollBtn = new Button("Roll");
		rollBtn.setStyle("-fx-font-family: 'Abril Fatface', cursive; -fx-font-size: 20;");
		rollBtn.setVisible(false);
		rollBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				controller.rollButtonOnClick();
				event.consume();
			}
			
		});
	}

	private void initPickColorOptions() {
		colorOptions = new HBox();
		colorOptions.setSpacing(50);
		colorOptions.setAlignment(Pos.CENTER);
		colorOptions.setVisible(false);
		Button whiteBtn = new Button();
		double r = 25;
		whiteBtn.setShape(new Circle(r));
		whiteBtn.setMaxSize(2*r, 2*r);
		whiteBtn.setMinSize(2*r, 2*r);
		whiteBtn.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		whiteBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				controller.selectWhite();
				e.consume();
			}
			
		});
		Button blackBtn = new Button();
		blackBtn.setShape(new Circle(r));
		blackBtn.setMaxSize(2*r, 2*r);
		blackBtn.setMinSize(2*r, 2*r);
		blackBtn.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		blackBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				controller.selectBlack();
				e.consume();
			}
			
		});
		colorOptions.getChildren().addAll(blackBtn, whiteBtn);
	}

	private void drawBoard(GraphicsContext gc) {
		drawEdge(gc);
		drawInside(gc);
	}

	private void drawEdge(GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(5);
		gc.strokeLine(0, 0, 420, 0);
		gc.strokeLine(0, 420, 420, 420);
		gc.strokeLine(0, 0, 0, 420);
		gc.strokeLine(420, 0, 420, 420);
	}

	private void drawInside(GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		for(int i=1; i<=13; i++){
			gc.strokeLine(0, 0+30*i, 420, 0+30*i);
			gc.strokeLine(0+30*i, 0, 0+30*i, 420);
		}
	}

	public Scene getScene(){
		return scene;
	}
	
	public void changeState(String content){
		state.setText(content);
	}
	
	public void setStateVisible(boolean b){
		state.setVisible(b);
	}
	
	public void setColorOptionVisible(boolean b){
		colorOptions.setVisible(b);
	}
	
	public void setRollBtnVisible(boolean b){
		rollBtn.setVisible(b);
	}
	
	public void setGiveupBtnVisible(boolean b){
		giveupButton.setVisible(b);
	}
	
	public void setGameEndsOptionsVisible(boolean b){
		gameEndsOptions.setVisible(b);
	}
	
	public void drawPiece(String color, int x, int y){
		GraphicsContext gc = upLayer.getGraphicsContext2D();
		if(color.equals("white")){
			gc.setFill(Color.WHITE);
		}else if(color.equals("black")){
			gc.setFill(Color.BLACK);
		}
		int graphicX = boardToMouse(x);
		int graphicY = boardToMouse(y);
		gc.fillOval(graphicX-PIECEWIDTH/2, graphicY-PIECEWIDTH/2, PIECEWIDTH, PIECEWIDTH);
	}
	
	private int mouseToBoard(double mn){
		mn = mn-10;
		double remaind = mn%GAP;
		int bn = (int) ((remaind > GAP/2)?mn/GAP+1: mn/GAP);
		return bn;
	}
	
	private int boardToMouse(int bn){
		int mn = bn*30+10;
		return mn;
	}
	
}
