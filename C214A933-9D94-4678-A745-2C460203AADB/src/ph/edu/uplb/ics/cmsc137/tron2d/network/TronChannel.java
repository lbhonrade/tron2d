package ph.edu.uplb.ics.cmsc137.tron2d.network;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import ph.edu.uplb.ics.cmsc137.tron2d.core.GridGame;
import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;

public class TronChannel {
	public MulticastSocket socket;
    public InetAddress mCastAddress;
    public boolean isListening = false;
    private PacketReceiver globalHandler;
	public WeakReference<GridGame> currentGame;
	
	public TronChannel(String clusterName,WeakReference<GridGame> cg) throws IOException,UnknownHostException {
		socket = new MulticastSocket(TConstants.connectionPort);
		mCastAddress = InetAddress.getByName(TConstants.mCastAddress);
		socket.joinGroup(mCastAddress);
		socket.setSoTimeout(1000);
		globalHandler = new PacketReceiver(this);
		globalHandler.start();
		currentGame = cg;
	}
	
	public void send(String data){
		new MulticastMessageSender(this,data).send();
	}
}
