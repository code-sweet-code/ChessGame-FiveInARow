package game;

public class Color {
	public static final String WHITE = "white";
	public static final String BLACK = "black";
	public static final String UNDEFINED = "undefined";
	
	private String myColor;
	
	public Color(String color){
		setColor(color);
	}
	
	public String color(){
		return myColor;
	}
	
	public void setColor(String color){
		switch(color){
		case WHITE:	{
			myColor = WHITE;
			break;
		}
		case BLACK:{
			myColor = BLACK;
			break;
		}
		default:{
			myColor = UNDEFINED;
			break;
		}
		}
	}
}
