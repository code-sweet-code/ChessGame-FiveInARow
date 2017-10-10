package game;

public class ComputerMove implements Comparable<ComputerMove>{
	private int x;
	private int y;
	private int prior;
	public ComputerMove(int x, int y, int prior){
		this.x = x;
		this.y = y;
		this.prior = prior;
	}
	@Override
	public int compareTo(ComputerMove o) {
		if(prior < o.prior){
			return 1;
		}else if(prior > o.prior){
			return -1;
		}
		return 0;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
