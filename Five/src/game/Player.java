package game;

import java.util.ArrayList;

public class Player {
	private String name;
	private GameObserver observer;
	private ArrayList<MoveAction> moveList;
	private Color color;
	private Player opponent;
	private int roll;
	
	public Player(GameObserver observer){
		this.name = observer.getName();
		this.moveList = new ArrayList<MoveAction>();
		this.observer = observer;
		this.color = new Color(Color.UNDEFINED);
		this.roll = 0;
	}
	
	public void addOpponent(Player player){
		opponent = player;
	}
	
	public Player getOpponent(){
		return opponent;
	}
	
	public void addAction(MoveAction newAction){
		moveList.add(newAction);
	}
	
	public GameObserver getObserver(){
		return observer;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public String getName(){
		return name;
	}
	
	public MoveAction getLastAction(){
		int size = moveList.size();
		MoveAction action;
		if(size > 0){
			action = moveList.get(size-1);
		}else{
			action = null;
		}
		return action;
	}
	
	public boolean isRolled(){
		if(roll == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public void setRoll(int roll){
		this.roll = roll;
	}
	
	public int getRoll(){
		return roll;
	}
}
