package game;

public class MoveState implements GameState {
	GameDataInterface game;
	
	public MoveState(GameDataInterface game){
		this.game = game;
	}
	
	@Override
	public void move(String name, int x, int y) {
		game.move(name, x, y);
	}

	@Override
	public void pickColor(String name, String color) {}

	@Override
	public void roll(String name) {}

	@Override
	public void goNextState() {
		game.setState(game.getWinState());
	}

	@Override
	public void stateChangeNotification() {
		Player[] players = game.getPlayers();
		for(int i=0; i<players.length; i++){
			GameObserver ob = players[i].getObserver();
			Player activatePlayer = game.getActivatePlayer();
			ob.waitForMove(activatePlayer.getName());
		}
	}

	@Override
	public void unvalidMoveNotification() {
		Player player = game.getActivatePlayer();
		GameObserver ob = player.getObserver();
		ob.UnvalidMove();
	}

	@Override
	public void movedNotification(int x, int y) {
		Player player1 = game.getActivatePlayer();
		Player player2 = player1.getOpponent();
		GameObserver observer1 = player1.getObserver();
		GameObserver observer2 = player2.getObserver();
		observer1.moved(player1.getName(), x, y);
		observer2.moved(player1.getName(), x, y);
	}

	@Override
	public void assignedColorNotification() {}

	@Override
	public void restartGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		
	}

}
