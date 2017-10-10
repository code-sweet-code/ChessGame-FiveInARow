package game;

import java.util.Random;

import exceptions.PositionUnavailableException;

public class HostGame implements GameInterface, GameDataInterface{
	
	private Player player1;
	private Player player2;
	private Player rollWinner;
	private Player activatePlayer;
	private Player winner;
	private Board board;
	private Rule rule;
	private GameState currentState;
	private GameState rollState;
	private GameState colorState;
	private GameState moveState;
	private GameState winState;
	private GameState restartState;
	private GameState endState;
	
	public HostGame(GameObserver p1, GameObserver p2){
		this.board = new Board();
		this.player1 = new Player(p1);
		this.player2 = new Player(p2);
		initStates();
		player1.addOpponent(player2);
		player2.addOpponent(player1);
		rollWinner = null;
		activatePlayer = null;
		rule = new Rule(board);
		p1.setGameInterface(this);
		p2.setGameInterface(this);
	}
	
	@Override
	public void restart(){
		rollWinner = null;
		activatePlayer = null;
		winner = null;
		board = new Board();
		player1 = new Player(player1.getObserver());
		player2 = new Player(player2.getObserver());
		player1.addOpponent(player2);
		player2.addOpponent(player1);
		rule = new Rule(board);
		currentState.goNextState();
		start();
	}
	
	public void initStates(){
		rollState = new RollState(this);
		colorState = new PickColorState(this);
		moveState = new MoveState(this);
		winState = new WinState(this);
		currentState = new NoState(this);
		restartState = new RestartState(this);
		endState = new EndState(this);
	}
	
	public void start(){
		setState(getRollState());
	}
	
	public boolean isRollCompared(){
		boolean isCompared = false;
		if(player1.isRolled() && player2.isRolled()){
			if(player1.getRoll() >= player2.getRoll()){
				rollWinner = player1;
			}else{
				rollWinner = player2;
			}
			isCompared = true;
		}
		return isCompared;
	}

	public Board getBoard(){
		return board;
	}
	
	public void makeRoll(String name){
		currentState.roll(name);
	}
	
	@Override
	public void assignColor(String name, String color){
		if(isPlayerName(rollWinner, name)){
			if(color.equals(Color.WHITE)){
				rollWinner.setColor(new Color(Color.WHITE));
				rollWinner.getOpponent().setColor(new Color(Color.BLACK));
				activatePlayer = rollWinner.getOpponent();
			}else if(color.equals(Color.BLACK)){
				rollWinner.setColor(new Color(Color.BLACK));
				rollWinner.getOpponent().setColor(new Color(Color.WHITE));
				activatePlayer = rollWinner;
			}
			currentState.assignedColorNotification();
			currentState.goNextState();
		}
	}

	public void pickColor(String name, String color){
		currentState.pickColor(name, color);
	}

	public void makeMove(String name, int x, int y){
		currentState.move(name, x, y);
	}
	
	private Player getPlayerbyName(String name){
		Player player = null;
		if(isPlayerName(player1, name)){
			return player1;
		}else if(isPlayerName(player2, name)){
			return player2;
		}
		return player;
	}

	@Override
	public GameState getPickColorState() {
		return colorState;
	}

	@Override
	public Player[] getPlayers() {
		Player[] players = {player1, player2};
		return players;
	}

	@Override
	public void setState(GameState state) {
		this.currentState = state;
		currentState.stateChangeNotification();
	}

	@Override
	public GameState getRollState() {
		return rollState;
	}

	@Override
	public GameState getMoveState() {
		return moveState;
	}

	@Override
	public void roll(String name) {
		Player requestedPlayer = getPlayerbyName(name);
		if(requestedPlayer != null && !requestedPlayer.isRolled()){
			Random rand = new Random();
			int rollpoint = rand.nextInt(100) + 1;
			requestedPlayer.setRoll(rollpoint);
		}
		if(isRollCompared()){
			currentState.goNextState();
		}
	}

	@Override
	public Player getRollWinner() {
		return rollWinner;
	}

	@Override
	public void move(String name, int x, int y) {
		if(isPlayerName(activatePlayer, name)){
			MoveAction newAction = new MoveAction(x, y, activatePlayer.getColor()); 
			activatePlayer.addAction(newAction);
			try {
				newAction.expire();
				board.move(newAction);
				currentState.movedNotification(newAction.getX(), newAction.getY());
				if(rule.isWinner(newAction)){
					winner = activatePlayer;
					currentState.goNextState();
				}else{
					activatePlayer = activatePlayer.getOpponent();
					currentState.stateChangeNotification();
				}
			} catch (PositionUnavailableException e) {
				currentState.unvalidMoveNotification();
			}
			
		}
	}
	
	@Override
	public boolean isMoveValid(int x, int y){
		boolean isValid = false;
		if(board.isAvailable(x, y)){
			isValid = true;
		}
		return isValid;
	}
	
	private boolean isPlayerName(Player player, String name){
		boolean isEqual = false;
		if(player != null && player.getName().equals(name)){
			isEqual = true;
		}
		return isEqual;
	}

	@Override
	public Player getActivatePlayer() {
		return activatePlayer;
	}

	@Override
	public GameState getWinState() {
		return winState;
	}

	@Override
	public Player getWinner() {
		return winner;
	}

	@Override
	public GameState getRestartState() {
		return restartState;
	}

	@Override
	public void restartGame() {
		currentState.restartGame();
	}

	@Override
	public void giveUpGame(String name) {
		Player requestedPlayer = getPlayerbyName(name);
		if(requestedPlayer != null){
			winner = requestedPlayer.getOpponent();
			setState(getWinState());
		}
	}

	@Override
	public void quitGame() {
		currentState.quit();
	}

	@Override
	public GameState getEndState() {
		return endState;
	}

}
