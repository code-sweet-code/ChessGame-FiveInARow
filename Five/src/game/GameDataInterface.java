package game;

public interface GameDataInterface {
	public void roll(String name);
	
	public void move(String name, int x, int y);

	public GameState getPickColorState();
	
	public GameState getRollState();
	
	public GameState getMoveState();
	
	public GameState getRestartState();

	public Player[] getPlayers();

	public void setState(GameState state);

	public Player getRollWinner();
	
	public void assignColor(String name, String color);

	public Player getActivatePlayer();

	public GameState getWinState();

	public Player getWinner();

	public void restart();

	public GameState getEndState();


}
