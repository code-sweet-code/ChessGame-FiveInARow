package game;

public class RestartState implements GameState {
	GameDataInterface game;
	
	public RestartState(GameDataInterface game){
		this.game = game;
	}
	
	@Override
	public void move(String name, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pickColor(String name, String color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void roll(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void goNextState() {
	}

	@Override
	public void stateChangeNotification() {
		Player[] players = game.getPlayers();
		for(int i=0; i<players.length; i++){
			GameObserver ob = players[i].getObserver();
			ob.gameRestarted();
		}
	}

	@Override
	public void unvalidMoveNotification() {
		// TODO Auto-generated method stub

	}

	@Override
	public void movedNotification(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void assignedColorNotification() {
		// TODO Auto-generated method stub

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
