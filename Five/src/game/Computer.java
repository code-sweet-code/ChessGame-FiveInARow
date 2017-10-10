package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computer implements GameObserver {
	HostGame hgame = null;
	GameInterface game = null;
	String name = "Computer";
	Board board = null;
	String myColor = null;
	String yourColor = null;
	@Override
	public void setGameInterface(GameInterface game) {
		this.game = game;
		this.hgame = (HostGame) game;
		this.board = hgame.getBoard();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void waitForPickingColor(String playername) {
		if(name.equals(playername)){
			game.pickColor(name, "black");
		}
	}

	@Override
	public void waitForMove(String playername) {
		if(name.equals(playername)){
			move();
		}
	}

	@Override
	public void winnerIs(String playername) {
		// TODO Auto-generated method stub

	}

	@Override
	public void waitForRolling() {
		game.makeRoll(name);
	}

	@Override
	public void moved(String name, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void UnvalidMove() {
		move();
	}

	private void move(){
		
		List<ComputerMove> cpMove = new ArrayList<ComputerMove>();
		blockOpponent(cpMove);
		improveSelf(cpMove);
		cpMove.sort(null);
		for(int i=0; i<cpMove.size(); i++){
			ComputerMove cp = cpMove.get(i);
			int x = cp.getX();
			int y = cp.getY();
			if(game.isMoveValid(x, y)){
				game.makeMove(name, x, y);
				return;
			}
		}
		Random r = new Random();
		game.makeMove(name, r.nextInt(15), r.nextInt(15));
	}

	private void improveSelf(List<ComputerMove> cpMove) {
	//	List<Piece> myPieces = board.getPiecesbyColor(new Color(myColor));
		
	}

	private void blockOpponent(List<ComputerMove> cpMove) {
		List<Piece> yourPieces = board.getPiecesbyColor(new Color(yourColor));
		for(int i=0; i<15; i++){
			List<Piece> vline = getVLine(yourPieces, i);
			List<Piece> hline = getHLine(yourPieces, i);
			checkVLine(vline, cpMove);
			checkHLine(hline, cpMove);
		}
		for(int i=0; i<21; i++){
			List<Piece> ldline = getLDLine(yourPieces, i);
			List<Piece> rdline = getRDine(yourPieces, i);
			checkLDLine(ldline, cpMove);
			checkRDLine(rdline, cpMove);
		}
	}

	private void checkRDLine(List<Piece> rdine, List<ComputerMove> cpMove) {
		rdine.sort(null);
		int count = 1;
		for(int i=0; i<rdine.size()-1; i++){
			Piece currentpiece = rdine.get(i);
			Piece nextpiece =  rdine.get(i+1);
			if(currentpiece.getX()+1 == nextpiece.getX() && currentpiece.getY()+1 == nextpiece.getY()){
				count++;
				if(count >= 4){
					cpMove.add(new ComputerMove(nextpiece.getX()+1, nextpiece.getY()+1, 10));
					cpMove.add(new ComputerMove(nextpiece.getX()-count, nextpiece.getY()-count, 10));
				}else if(count == 3){
					if(!hasMyPiece(nextpiece.getX()+1, nextpiece.getY()+1) && !hasMyPiece(nextpiece.getX()-count, nextpiece.getY()-count)){
						cpMove.add(new ComputerMove(nextpiece.getX()+1, nextpiece.getY()+1, 9));
						cpMove.add(new ComputerMove(nextpiece.getX()-count, nextpiece.getY()-count, 9));
					}else{
						cpMove.add(new ComputerMove(nextpiece.getX()+1, nextpiece.getY()+1, 8));
						cpMove.add(new ComputerMove(nextpiece.getX()-count, nextpiece.getY()-count, 8));
					}
				}else if(count == 2){
					cpMove.add(new ComputerMove(nextpiece.getX()+1, nextpiece.getY()+1, 7));
					cpMove.add(new ComputerMove(nextpiece.getX()-count, nextpiece.getY()-count, 7));
				}
			}
			else{
				count = 1;
			}
		}
	}

	private void checkLDLine(List<Piece> ldline, List<ComputerMove> cpMove) {
		ldline.sort(null);
		int count = 1;
		for(int i=0; i<ldline.size()-1; i++){
			Piece currentpiece = ldline.get(i);
			Piece nextpiece =  ldline.get(i+1);
			if(currentpiece.getX()-1 == nextpiece.getX() && currentpiece.getY()+1 == nextpiece.getY()){
				count++;
				if(count >= 4){
					cpMove.add(new ComputerMove(nextpiece.getX()-1, nextpiece.getY()+1, 10));
					cpMove.add(new ComputerMove(nextpiece.getX()+count, nextpiece.getY()-count, 10));
				}else if(count == 3){
					if(!hasMyPiece(nextpiece.getX()-1, nextpiece.getY()+1) && !hasMyPiece(nextpiece.getX()+count, nextpiece.getY()-count)){
						cpMove.add(new ComputerMove(nextpiece.getX()-1, nextpiece.getY()+1, 9));
						cpMove.add(new ComputerMove(nextpiece.getX()+count, nextpiece.getY()-count, 9));
					}else{
						cpMove.add(new ComputerMove(nextpiece.getX()-1, nextpiece.getY()+1, 8));
						cpMove.add(new ComputerMove(nextpiece.getX()+count, nextpiece.getY()-count, 8));
					}
				}else if(count == 2){
					cpMove.add(new ComputerMove(nextpiece.getX()-1, nextpiece.getY()+1, 7));
					cpMove.add(new ComputerMove(nextpiece.getX()+count, nextpiece.getY()-count, 7));
				}
			}
			else{
				count = 1;
			}
		}
	}

	private List<Piece> getRDine(List<Piece> yourPieces, int line) {
		int base = 10 - line;
		List<Piece> plist = new ArrayList<Piece>();
		for(int i=0; i<yourPieces.size(); i++){
			Piece p = yourPieces.get(i);
			if(p.getX()-p.getY() == base){
				plist.add(p);
			}
		}
		return plist;
	}

	private List<Piece> getLDLine(List<Piece> yourPieces, int line) {
		int base = line + 4;
		List<Piece> plist = new ArrayList<Piece>();
		for(int i=0; i<yourPieces.size(); i++){
			Piece p = yourPieces.get(i);
			if(p.getX()+p.getY() == base){
				plist.add(p);
			}
		}
		return plist;
	}

	private List<Piece> getHLine(List<Piece> yourPieces, int line) {
		List<Piece> plist = new ArrayList<Piece>();
		for(int i=0; i<yourPieces.size(); i++){
			Piece p = yourPieces.get(i);
			if(p.getY() == line){
				plist.add(p);
			}
		}
		return plist;
	}

	private List<Piece> getVLine(List<Piece> yourPieces, int line) {
		List<Piece> plist = new ArrayList<Piece>();
		for(int i=0; i<yourPieces.size(); i++){
			Piece p = yourPieces.get(i);
			if(p.getX() == line){
				plist.add(p);
			}
		}
		return plist;
	}

	private void checkHLine(List<Piece> hline, List<ComputerMove> cpMove) {
		hline.sort(null);
		int count = 1;
		for(int i=0; i<hline.size()-1; i++){
			Piece currentpiece = hline.get(i);
			Piece nextpiece =  hline.get(i+1);
			if(currentpiece.getX()+1 == nextpiece.getX()){
				count++;
				if(count >= 4){
					cpMove.add(new ComputerMove(nextpiece.getX()+1, nextpiece.getY(), 10));
					cpMove.add(new ComputerMove(nextpiece.getX()-count, nextpiece.getY(), 10));
				}else if(count == 3){
					if(!hasMyPiece(nextpiece.getX()+1, nextpiece.getY()) && !hasMyPiece(nextpiece.getX()-count, nextpiece.getY())){
						cpMove.add(new ComputerMove(nextpiece.getX()+1, nextpiece.getY(), 9));
						cpMove.add(new ComputerMove(nextpiece.getX()-count, nextpiece.getY(), 9));
					}else{
						cpMove.add(new ComputerMove(nextpiece.getX()+1, nextpiece.getY(), 8));
						cpMove.add(new ComputerMove(nextpiece.getX()-count, nextpiece.getY(), 8));
					}
				}else if(count == 2){
					cpMove.add(new ComputerMove(nextpiece.getX()+1, nextpiece.getY(), 7));
					cpMove.add(new ComputerMove(nextpiece.getX()-count, nextpiece.getY(), 7));
				}
			}
			else{
				count = 1;
			}
		}
	}

	private void checkVLine(List<Piece> vline, List<ComputerMove> cpMove) {
		vline.sort(null);
		int count = 1;
		for(int i=0; i<vline.size()-1; i++){
			Piece currentpiece = vline.get(i);
			Piece nextpiece =  vline.get(i+1);
			if(currentpiece.getY()+1 == nextpiece.getY()){
				count++;
				if(count >= 4){
					cpMove.add(new ComputerMove(nextpiece.getX(), nextpiece.getY()+1, 10));
					cpMove.add(new ComputerMove(nextpiece.getX(), nextpiece.getY()-count, 10));
				}else if(count == 3){
					if(!hasMyPiece(nextpiece.getX(), nextpiece.getY()+1) && !hasMyPiece(nextpiece.getX(), nextpiece.getY()-count)){
						cpMove.add(new ComputerMove(nextpiece.getX(), nextpiece.getY()+1, 9));
						cpMove.add(new ComputerMove(nextpiece.getX(), nextpiece.getY()-count, 9));
					}else{
						cpMove.add(new ComputerMove(nextpiece.getX(), nextpiece.getY()+1, 8));
						cpMove.add(new ComputerMove(nextpiece.getX(), nextpiece.getY()-count, 8));
					}
				}else if(count == 2){
					cpMove.add(new ComputerMove(nextpiece.getX(), nextpiece.getY()+1, 7));
					cpMove.add(new ComputerMove(nextpiece.getX(), nextpiece.getY()-count, 7));
				}
			}
			else{
				count = 1;
			}
		}
		
	}
	
	private boolean hasMyPiece(int x, int y) {
		boolean hasPiece = false;
		List<Piece> myPieces = board.getPiecesbyColor(new Color(myColor));
		for(int i=0; i<myPieces.size(); i++){
			Piece p = myPieces.get(i);
			if(p.getX() == x && p.getY() == y){
				hasPiece = true;
				break;
			}
		}
		return hasPiece;
	}

	@Override
	public void pickedColor(String color) {
		myColor = color;
		if(myColor.equals(Color.BLACK)){
			yourColor = Color.WHITE;
		}else{
			yourColor = Color.BLACK;
		}
	}

	@Override
	public void gameRestarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameEnds() {
		// TODO Auto-generated method stub
		
	}
}
