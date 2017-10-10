package game;

public interface GameInterface {
	public void makeRoll(String name);
	public void pickColor(String name, String color);
	public void makeMove(String name, int x, int y);
	public boolean isMoveValid(int x, int y);
	public void restartGame();
	public void giveUpGame(String name);
	public void quitGame();
}
