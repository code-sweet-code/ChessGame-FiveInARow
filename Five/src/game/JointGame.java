package game;

import java.util.ArrayList;
import java.util.List;

import exceptions.NetworkReceiveException;
import network.NetWork;
import network.NetworkMessage;
import network.NetworkObserver;

public class JointGame implements GameInterface, NetworkObserver {
	private GameObserver player;
	private NetWork network = null;
	private List<NetworkMessage> msgHistory = null;
	
	public JointGame(GameObserver p, NetWork network){
		player = p;
		player.setGameInterface(this);
		this.network = network;
		this.network.addObserver(this);
		msgHistory = new ArrayList<NetworkMessage>();
	}
	
	public void start() {
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
		if(msgType.equals("gamestate")){
			switch(msgName){
			case "color":{
				List<String> args = msg.getArguements();
				if(args.size() > 0){
					player.waitForPickingColor(args.get(0));
				}else{
					sendResendMessage(msg.getId());
				}
				break;
			}
			case "move":{
				List<String> args = msg.getArguements();
				if(args.size() > 0){
					player.waitForMove(args.get(0));
				}else{
					sendResendMessage(msg.getId());
				}
				break;
			}
			case "win":{
				List<String> args = msg.getArguements();
				if(args.size() > 0){
					player.winnerIs(args.get(0));
				}else{
					sendResendMessage(msg.getId());
				}
				break;
			}
			case "roll":{
				player.waitForRolling();
				break;
			}
			case "end":{
				player.gameEnds();
				network.close();
				break;
			}
			case "moved":{
				List<String> args = msg.getArguements();
				if(args.size() > 2){
					player.moved(args.get(0), Integer.valueOf(args.get(1)), Integer.valueOf(args.get(2)));
				}else{
					sendResendMessage(msg.getId());
				}
				break;
			}
			case "unvalidmove":{
				player.UnvalidMove();
				break;
			}
			case "colored":{
				List<String> args = msg.getArguements();
				if(args.size() > 0){
					player.pickedColor(args.get(0));
				}else{
					sendResendMessage(msg.getId());
				}
				
				break;
			}
			case "restarted":{
				player.gameRestarted();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionEstablished() {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeRoll(String name) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("action");
		msg.setMsgName("roll");
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void pickColor(String name, String color) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("action");
		msg.setMsgName("color");
		msg.addArguement(color);
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void makeMove(String name, int x, int y) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("action");
		msg.setMsgName("move");
		msg.addArguement(String.valueOf(x));
		msg.addArguement(String.valueOf(y));
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public boolean isMoveValid(int x, int y) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("action");
		msg.setMsgName("movevalidate");
		msg.addArguement(String.valueOf(x));
		msg.addArguement(String.valueOf(y));
		network.send(msg.getMessage());
		msgHistory.add(msg);
		return false;
	}

	@Override
	public void restartGame() {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("action");
		msg.setMsgName("restart");
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void giveUpGame(String name) {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("action");
		msg.setMsgName("giveup");
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	private void sendResendMessage(int i){
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("resend");
		msg.setId(i);
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}

	@Override
	public void quitGame() {
		NetworkMessage msg = new NetworkMessage();
		msg.setMsgType("action");
		msg.setMsgName("end");
		network.send(msg.getMessage());
		msgHistory.add(msg);
	}
}
