package network;

import java.util.ArrayList;
import java.util.List;

public class NetworkMessage {
	static int id = 0;
	String messageType = "";
	String messageName = "";
	List<String> args = null;
	
	public NetworkMessage(){
		id++;
		args = new ArrayList<String>();
	}
	
	public NetworkMessage(String message) {
		args = new ArrayList<String>();
		String[] s = message.split(",");
		if(s.length >= 3){
			id = Integer.valueOf(s[0]);
			messageType = s[1];
			messageName = s[2];
			for(int i=3; i< s.length; i++){
				args.add(s[i]);
			}
		}
	}

	public void setMsgType(String type){
		messageType = type;
	}
	
	public String getMsgType(){
		return messageType;
	}
	
	public void setMsgName(String name){
		messageName = name;
	}
	
	public String getMsgName(){
		return messageName;
	}
	
	public boolean hasArguements(){
		if(args.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public List<String> getArguements(){
		return args;
	}
	
	public void addArguement(String arg){
		args.add(arg);
	}
	
	public String getMessage(){
		StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append(String.valueOf(id));
		msgBuilder.append(",");
		msgBuilder.append(messageType);
		msgBuilder.append(",");
		msgBuilder.append(messageName);
		for(int i=0; i<args.size(); i++){
			msgBuilder.append(",");
			msgBuilder.append(args.get(i));
		}
		return msgBuilder.toString();
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int i){
		id = i;
	}
}
