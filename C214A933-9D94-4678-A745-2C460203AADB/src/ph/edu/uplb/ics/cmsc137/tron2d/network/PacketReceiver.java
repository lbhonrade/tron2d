package ph.edu.uplb.ics.cmsc137.tron2d.network;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.DatagramPacket;
import java.net.MulticastSocket;


import ph.edu.uplb.ics.cmsc137.tron2d.core.GridGame;

public class PacketReceiver extends Thread {
	private WeakReference<TronChannel> channel;
	private ReferenceQueue<TronChannel> rq = new ReferenceQueue<TronChannel>();
	public boolean isListening = true;
	
	public PacketReceiver(TronChannel ch){
		super();
		channel = new WeakReference<TronChannel>(ch);
	}
	
	public void run(){
		while(isListening){
			DatagramPacket packet = new DatagramPacket(new byte[256],256);
			try {
				channel.get().socket.receive(packet);
				String data = new String(packet.getData()).trim();
				new PacketHandler(channel,data).start();
			} catch (Exception e) {
				if(e.getClass()==java.net.SocketTimeoutException.class && isListening) continue;
				e.printStackTrace();
			}
		}
	}
}
