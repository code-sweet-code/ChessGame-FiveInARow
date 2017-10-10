package network;

public interface NetworkObserver {
	public void networkMessage(String message);
	public void networkError(String error);
	public void networkNotification(String notification);
	public void connectionEstablished();
}
