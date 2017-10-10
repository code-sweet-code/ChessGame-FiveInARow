package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Rule{
	private Board board;
	
	public Rule(Board gameBoard){
		this.board = gameBoard;
	}
	
	public boolean isWinner(MoveAction move){
		boolean isWin = false;
		List<Piece> pieces = board.getPiecesbyColor(move.getColor());
		Piece newPiece = new Piece(move.getX(), move.getY());
		if(hasFiveInOneRow(pieces, newPiece)){
			isWin = true;
		}
		return isWin;
	}
	
	private boolean hasFiveInOneRow(List<Piece> pieces, Piece newPiece){
		boolean gotFive = false;
		List<Piece> vertical = new ArrayList<Piece>();
		List<Piece> horizontal = new ArrayList<Piece>();
		List<Piece> leftDiagonal = new ArrayList<Piece>();
		List<Piece> rightDiagonal = new ArrayList<Piece>();
		Iterator<Piece> iter = pieces.iterator();
		while(iter.hasNext()){
			Piece neighborPiece = iter.next();
			if(isVertical(neighborPiece, newPiece)){
				vertical.add(neighborPiece);
			}
			if(isHorizontal(neighborPiece, newPiece)){
				horizontal.add(neighborPiece);
			}
			if(isLeftDiagonal(neighborPiece, newPiece)){
				leftDiagonal.add(neighborPiece);
			}
			if(isRightDiagonal(neighborPiece, newPiece)){
				rightDiagonal.add(neighborPiece);
			}
		}
		int vCount = countVertical(vertical);
		int hCount = countHorizontal(horizontal);
		int lCount = countLeftDiagonal(leftDiagonal);
		int rCount = countRightDiagonal(rightDiagonal);
		if(vCount >= 5 || hCount >= 5 || lCount >= 5 || rCount >= 5){
			gotFive = true;
		}
		
		return gotFive;
	}

	private boolean isVertical(Piece p1, Piece p2){
		boolean result = false;
		if(p1.getX() == p2.getX()){
			result = true;
		}
		return result;
	}
	
	private boolean isHorizontal(Piece p1, Piece p2){
		boolean result = false;
		if(p1.getY() == p2.getY()){
			result = true;
		}
		return result;
	}
	
	private boolean isRightDiagonal(Piece p1, Piece p2){
		boolean result = false;
		if((p1.getX() - p2.getX()) == (p1.getY() - p2.getY())){
			result = true;
		}
		return result;
	}
	
	private boolean isLeftDiagonal(Piece p1, Piece p2){
		boolean result = false;
		if((p1.getX() - p2.getX()) == (p2.getY() - p1.getY())){
			result = true;
		}
		return result;
	}
	
	private int countVertical(List<Piece> pieces){
		pieces.sort(null);
		int count = 1;
		for(int i=0; i<pieces.size()-1; i++){
			if(pieces.get(i).getY()+1 == pieces.get(i+1).getY()){
				count++;
				if(count >= 5){
					break;
				}
			}
			else{
				count = 1;
			}
		}
		return count;
	}
	
	private int countHorizontal(List<Piece> pieces){
		pieces.sort(null);
		int count = 1;
		for(int i=0; i<pieces.size()-1; i++){
			if(pieces.get(i).getX()+1 == pieces.get(i+1).getX()){
				count++;
				if(count >= 5){
					break;
				}
			}
			else{
				count = 1;
			}
		}
		return count;
	}
	
	private int countLeftDiagonal(List<Piece> pieces){
		pieces.sort(null);
		int count = 1;
		for(int i=0; i<pieces.size()-1; i++){
			if(pieces.get(i).getX()-1 == pieces.get(i+1).getX() &&
					pieces.get(i).getY()+1 == pieces.get(i+1).getY()){
				count++;
				if(count >= 5){
					break;
				}
			}
			else{
				count = 1;
			}
		}
		return count;
	}
	
	private int countRightDiagonal(List<Piece> pieces){
		pieces.sort(null);
		int count = 1;
		for(int i=0; i<pieces.size()-1; i++){
			if(pieces.get(i).getX()+1 == pieces.get(i+1).getX() && 
					pieces.get(i).getY()+1 == pieces.get(i+1).getY()){
				count++;
				if(count >= 5){
					break;
				}
			}
			else{
				count = 1;
			}
		}
		return count;
	}
}
