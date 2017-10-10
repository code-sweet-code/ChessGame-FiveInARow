package game;

public class Piece implements Comparable<Piece>{
	private int x;
	private int y;
	
	public Piece(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	@Override
	public int compareTo(Piece p) {
		if(this.getY() < p.getY()) {
			return -1;
		}else if(this.getY() > p.getY()){
			return 1;
		}else if(this.getX() < p.getX()){
			return -1;
		}else if(this.getX() > p.getX()){
			return 1;
		}else{
			return 0;
		}
	}

}
