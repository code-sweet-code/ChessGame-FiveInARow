package game;

public class NoState implements GameState {
	GameDataInterface game;
	
	public NoState(GameDataInterface game){
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
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChangeNotification() {
		// TODO Auto-generated method stub

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
