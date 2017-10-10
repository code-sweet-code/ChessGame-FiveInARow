package game;

public class MoveAction {
	private boolean isNew;
	private int x;
	private int y;
	private Color color;

	public MoveAction(int x, int y, Color color) {
		this.isNew = true;
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Color getColor(){
		return color;
	}

	public boolean isNew(){
		return isNew;
	}
	
	public void expire(){
		isNew = false;
	}
}
