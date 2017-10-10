package game;

public class RollState implements GameState {
	GameDataInterface game;
	
	public RollState(GameDataInterface game){
		this.game = game;
	}
	
	@Override
	public void stateChangeNotification(){
		Player[] players = game.getPlayers();
		for(int i=0; i<players.length; i++){
			GameObserver ob = players[i].getObserver();
			ob.waitForRolling();
		}
	}

	@Override
	public void roll(String name) {
		game.roll(name);
	}

	@Override
	public  void goNextState() {
		game.setState(game.getPickColorState());
	}

	@Override
	public void move(String name, int x, int y) {}

	@Override
	public void pickColor(String name, String color) {}

	@Override
	public void unvalidMoveNotification() {}

	@Override
	public void movedNotification(int x, int y) {}

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
