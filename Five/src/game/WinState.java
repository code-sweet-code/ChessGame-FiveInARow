package game;

public class WinState implements GameState {
	GameDataInterface game;
	
	public WinState(GameDataInterface game){
		this.game = game;
	}
	@Override
	public void move(String name, int x, int y) {}

	@Override
	public void pickColor(String name, String color) {}

	@Override
	public void roll(String name) {}

	@Override
	public void goNextState() {
		game.setState(game.getRestartState());
	}

	@Override
	public void stateChangeNotification() {
		Player[] players = game.getPlayers();
		for(int i=0; i<players.length; i++){
			GameObserver ob = players[i].getObserver();
			Player winner = game.getWinner();
			ob.winnerIs(winner.getName());
		}
	}

	@Override
	public void unvalidMoveNotification() {}

	@Override
	public void movedNotification(int x, int y) {}
	@Override
	public void assignedColorNotification() {}
	@Override
	public void restartGame() {
		game.restart();
	}
	@Override
	public void quit() {
		game.setState(game.getEndState());
	}

}
