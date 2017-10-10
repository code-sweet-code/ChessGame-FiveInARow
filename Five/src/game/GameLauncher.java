package game;

import network.NetWork;

public class GameLauncher {
	public void singleGame(GameObserver humanPlayer){
		GameObserver computer = new Computer();
		humanPlayer.setName("human");
		HostGame game = new HostGame(humanPlayer, computer);
		game.start();
	}
	
	public void multiGameHost(NetWork network, GameObserver hostPlayer){
		GameObserver guestPlayer = new RemotePlayer(network);
		hostPlayer.setName("host");
		guestPlayer.setName("guest");
		HostGame game = new HostGame(hostPlayer, guestPlayer);
		game.start();
	}
	
	public void multiGameGuest(NetWork network, GameObserver jointPlayer){
		jointPlayer.setName("guest");
		JointGame game = new JointGame(jointPlayer, network);
		game.start();
	}
}
