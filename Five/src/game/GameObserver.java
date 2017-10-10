package game;

public interface GameObserver {
	public void setGameInterface(GameInterface game);
	public String getName();
	public void setName(String name);
	public void waitForPickingColor(String playername);
	public void waitForMove(String playername);
	public void winnerIs(String playername);
	public void waitForRolling();
	public void moved(String playername, int x, int y);
	public void UnvalidMove();
	public void pickedColor(String color);
	public void gameRestarted();
	public void gameEnds();
}
