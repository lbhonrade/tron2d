package ph.edu.uplb.ics.cmsc137.tron2d.network;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.DatagramPacket;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;

public class MulticastMessageSender extends Thread {
	private WeakReference<TronChannel> channel;
	private ReferenceQueue<TronChannel> rq = new ReferenceQueue<TronChannel>();
	private String data;

	public MulticastMessageSender(TronChannel tChannel, String _data) {
		super();
		channel = new WeakReference<TronChannel>(tChannel, rq);
		data = _data;
	}

	@Override
	public void run() {
		try {
			DatagramPacket packet = new DatagramPacket(data.getBytes(),
					data.length(), channel.get().mCastAddress,
					TConstants.connectionPort);
			channel.get().socket.send(packet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send() {
		this.start();
	}
}
