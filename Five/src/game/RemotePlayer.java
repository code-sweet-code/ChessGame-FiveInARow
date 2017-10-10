package game;

import java.util.ArrayList;
import java.util.List;

import exceptions.NetworkReceiveException;
import network.NetWork;
import network.NetworkMessage;
import network.NetworkObserver;

public class RemotePlayer implements GameObserver, NetworkObserver {
	private GameInterface game = null;
	private NetWork network = null;
	private String name = null;
	private List<NetworkMessage> msgHistory = null;
	
	public RemotePlayer(NetWork network){
		this.network = network;
		this.network.addObserver(this);
		msgHistory = new ArrayList<NetworkMessage>();
		name = "remote";
		startReceiving();
	}

	private void startReceiving() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					while(true){
						network.receive();
					}
				} catch (NetworkReceiveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}).start();
	}

	@Override
	public void networkMessage(String message) {
		NetworkMessage msg = new NetworkMessage(message);
		String msgType = msg.getMsgType();
		String msgName = msg.getMsgName();
		if(msgType.equals("action")){
			switch(msgName){
			case "roll":{
				game.makeRoll(name);
				break;
			}
			case "color":{
				List<String> args = msg.getArguements();
				if(args.size() > 0){
					game.pickColor(name, args.get(0));
				}else{
					sendResendMessage(msg.getId());
				}
				break;
			}
			case "move":{
				List<String> args = msg.getArguements();
				if(args.size() > 1){
					game.makeMove(name, Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)));
				}else{
					sendResendMessage(msg.getId());
				}
				break;
			}
			case "movevalidate":{
				List<String> args = msg.getArguements();
				if(args.size() > 1){
					game.isMoveValid(Integer.valueOf(args.get(0)), Integer.valueOf(args.get(1)));
				}else{
					sendResendMessage(msg.getId());
				}
				break;
			}
			case "restart":{
				game.restartGame();
				break;
			}
			case "end":{
				game.quitGame();
				break;
			}
			case "giveup":{
				game.giveUpGame(name);
				break;
			}
			default:break;
			}
		}
	}

	@Override
	public void networkError(String error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void networkNotification(String notification) {
		
	}

	@Override
	public void connectionEstablished() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGameInterface(GameInterface game) {
		this.game = game;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void waitForPickingColor(String playername) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("color");
		msg.addArguement(playername);
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void waitForMove(String playername) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("move");
		msg.addArguement(playername);
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void winnerIs(String playername) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("win");
		msg.addArguement(playername);
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void waitForRolling() {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("roll");
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void moved(String name, int x, int y) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("moved");
		msg.addArguement(name);
		msg.addArguement(String.valueOf(x));
		msg.addArguement(String.valueOf(y));
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void UnvalidMove() {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("unvalidmove");
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void pickedColor(String color) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("colored");
		msg.addArguement(color);
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void gameRestarted() {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("restarted");
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	private void sendResendMessage(int i){
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("resend");
		msg.setId(i);
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void gameEnds() {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("gamestate");
		msg.setMsgName("end");
		network.send(msg.getMessage());
		msgHistory.add(msg);
		network.close();
	}

}
