package network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import exceptions.NetworkReceiveException;

public class NetWork {
	private final int PORT = 25252;
	private DatagramSocket socket;
	private SocketAddress targetAddr;
	private SocketAddress listenAddr;
	private final int TIMEOUT = 1000;
	private final int BUFFERSIZE = 500;
	private List<NetworkObserver> observers;
	
	public NetWork(String ip){
		targetAddr = new InetSocketAddress(ip, PORT); 
		listenAddr = new InetSocketAddress(PORT);
		observers = new ArrayList<NetworkObserver>();
		try {
			socket = new DatagramSocket(null);
			socket.setReuseAddress(true);
			socket.bind(listenAddr);
		} catch (SocketException e) {
			notifyObservers(NetworkState.ERROR, "Can't open socket");
			socket = null;
			e.printStackTrace();
		}
	}
	
	public void addObserver(NetworkObserver observer){
		observers.add(observer);
	}
	
	public static String getMySubnetIP(){
		String ip = "";
		try {
			Enumeration<NetworkInterface> a = NetworkInterface.getNetworkInterfaces();
			while(a.hasMoreElements()){
				NetworkInterface n = a.nextElement();
				Enumeration<InetAddress> e = n.getInetAddresses();
				while(e.hasMoreElements()){
					InetAddress i = e.nextElement();
					if(i.isSiteLocalAddress()){
						ip = i.getHostAddress();
						break;
					}
				}
				if(!ip.equals("")) break;
			}
		} catch (SocketException e1) {
			System.out.println("<ERROR> Can't get network interface");
			e1.printStackTrace();
		}
		return ip;
	}
	
	public static String getMyPublicIP(){
		return getMySubnetIP();
	}
	
	public void connect(){
		notifyObservers(NetworkState.INFO, "Connecting");
		send("connect1");
		try {
			while(true){
				String rsp;
				rsp = receive();
				if(rsp.equals("connect1")){
					send("connect2");
				}if(rsp.equals("connect2")){
					send("connect3");
				}if(rsp.equals("connect3")){
					send("connect3");
					notifyObservers(NetworkState.ESTABLISHED, "Connection established");
					break;
				}
			}
		} catch (NetworkReceiveException e) {
			notifyObservers(NetworkState.ERROR, "Socket Exception");
			e.printStackTrace();
		}
		
		
	}
	
	public void send(String s){
		try {
			DatagramPacket packet = new DatagramPacket(s.getBytes("UTF-8"), s.getBytes("UTF-8").length, targetAddr);
			socket.send(packet);
		} catch (UnsupportedEncodingException e) {
			notifyObservers(NetworkState.ERROR, "Doesn't support UTF-8");
			e.printStackTrace();
		} catch (IOException e) {
			notifyObservers(NetworkState.ERROR, "Socket send function fail");
			e.printStackTrace();
		}
	}
	
	public String receive() throws NetworkReceiveException{
		byte[] buff = new byte[BUFFERSIZE];
		String receivedStr = "";
		DatagramPacket packet = new DatagramPacket(buff, BUFFERSIZE);
		try {
			socket.receive(packet);
			receivedStr = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
			notifyObservers(NetworkState.MESSAGE, receivedStr);
		}catch(SocketTimeoutException e){
			notifyObservers(NetworkState.INFO, "timeout");
			throw new NetworkReceiveException();
		}catch (PortUnreachableException e){
			notifyObservers(NetworkState.ERROR, "Port unreachable");
			e.printStackTrace();
			throw new NetworkReceiveException();
		}catch (IOException e) {
			notifyObservers(NetworkState.ERROR, "Socket receive function fail");
			e.printStackTrace();
			throw new NetworkReceiveException();
		}
		return receivedStr;
	}
	
	private void notifyObservers(NetworkState type, String content){
		for(int i=0; i<observers.size(); i++){
			NetworkObserver obser = observers.get(i);
			switch(type){
			case ERROR:{
				obser.networkError(content);
				break;
			}
			case INFO:{
				obser.networkNotification(content);
				break;
			}
			case MESSAGE:{
				obser.networkMessage(content);
				break;
			}
			case ESTABLISHED:{
				obser.networkNotification(content);
				obser.connectionEstablished();
				break;
			}
			default:break;
			}
		}
		
	}
	
	public void setTimeOut(){
		try {
			socket.setSoTimeout(TIMEOUT);
		} catch (SocketException e) {
			notifyObservers(NetworkState.ERROR, "Socket timeout function fail");
			e.printStackTrace();
		}
	}
	
	public void close(){
		socket.close();
	}
}
