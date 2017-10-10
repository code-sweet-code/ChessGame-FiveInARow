package game;

public interface GameState {
	public void move(String name, int x, int y);
	public void pickColor(String name, String color);
	public void roll(String name);
	public void goNextState();
	public void stateChangeNotification();
	public void unvalidMoveNotification();
	public void movedNotification(int x, int y);
	public void assignedColorNotification();
	public void restartGame();
	public void quit();
}
