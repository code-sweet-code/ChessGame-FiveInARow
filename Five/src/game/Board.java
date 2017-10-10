package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import exceptions.PositionUnavailableException;

public class Board {
	private List<Piece> whitePieces;
	private List<Piece> blackPieces;
	private static final int MAXMAP = 15;
	
	public Board(){
		whitePieces = new ArrayList<Piece>();
		blackPieces = new ArrayList<Piece>();
	}
	
	public void move(MoveAction move) throws PositionUnavailableException{
		int x = move.getX();
		int y = move.getY();
		if(isAvailable(x, y)){
			Piece newPiece = new Piece(x, y);
			if(move.getColor().color() == Color.BLACK){
				blackPieces.add(newPiece);
			}else if(move.getColor().color() == Color.WHITE){
				whitePieces.add(newPiece);
			}
		}
		else{
			throw new PositionUnavailableException();
		}
	}
	
	//optimizable
	public boolean isAvailable(int x, int y){
		boolean isAvailable = true;
		if(x > MAXMAP || y > MAXMAP){
			isAvailable = false;
		}else{
			Iterator<Piece> iterator = whitePieces.iterator();
			while(iterator.hasNext()){
				Piece piece = iterator.next();
				if(piece.getX() == x && piece.getY() == y){
					isAvailable = false;
					break;
				}
			}
			iterator = blackPieces.iterator();
			while(iterator.hasNext()){
				Piece piece = iterator.next();
				if(piece.getX() == x && piece.getY() == y){
					isAvailable = false;
					break;
				}
			}
		}
		
		return isAvailable;
	}

	public List<Piece> getPiecesbyColor(Color color) {
		List<Piece> pieces = new ArrayList<Piece>();
		if(color.color() == Color.BLACK){
			pieces = blackPieces; 
		}else if(color.color() == Color.WHITE){
			pieces = whitePieces;
		}
		
		return pieces;
	}


}
