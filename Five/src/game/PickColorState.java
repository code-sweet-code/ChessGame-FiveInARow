package game;

public class PickColorState implements GameState {
	GameDataInterface game;
	
	public PickColorState(GameDataInterface game){
		this.game = game;
	}

	@Override
	public void pickColor(String name, String color) {
		game.assignColor(name, color);
	}

	@Override
	public void roll(String name) {}

	@Override
	public void goNextState() {
		game.setState(game.getMoveState());
	}

	@Override
	public void stateChangeNotification() {
		Player[] players = game.getPlayers();
		for(int i=0; i<players.length; i++){
			GameObserver ob = players[i].getObserver();
			Player winner = game.getRollWinner();
			ob.waitForPickingColor(winner.getName());
		}
	}

	@Override
	public void move(String name, int x, int y) {}

	@Override
	public void unvalidMoveNotification() {}

	@Override
	public void movedNotification(int x, int y) {}

	@Override
	public void assignedColorNotification() {
		Player[] players = game.getPlayers();
		for(int i=0; i<players.length; i++){
			GameObserver ob = players[i].getObserver();
			ob.pickedColor(players[i].getColor().color());
		}
	}

	@Override
	public void restartGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		
	}

}
