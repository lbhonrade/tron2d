package ph.edu.uplb.ics.cmsc137.tron2d.network;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.DatagramPacket;

public class PacketReceiver extends Thread {
	private WeakReference<TronChannel> channel;
	private ReferenceQueue<TronChannel> rq = new ReferenceQueue<TronChannel>();
	public boolean isListening = true;

	public PacketReceiver(TronChannel ch) {
		super();
		channel = new WeakReference<TronChannel>(ch, rq);
	}

	public void run() {
		while (isListening) {
			DatagramPacket packet = new DatagramPacket(new byte[256], 256);
			try {
				channel.get().socket.receive(packet);
				new PacketHandler(channel, packet).start();
			} catch (Exception e) {
				if (e.getClass() == java.net.SocketTimeoutException.class
						&& isListening)
					continue;
				e.printStackTrace();
			}
		}
	}
}
