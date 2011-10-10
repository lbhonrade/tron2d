package ph.edu.uplb.ics.cmsc137.tron2d.network;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;

public class PacketHandler extends Thread {
	private WeakReference<TronChannel> channel;
	private String data;
	public PacketHandler(WeakReference<TronChannel> ch,String _data){
		super();
		channel = ch;
		data = _data;
	}
	public void run(){
		String[] dataSet=data.split("\0");
		switch(Integer.valueOf(dataSet[0])){
			case TConstants.PLAYER_INPUT_UPDATE:
				if(!channel.get().currentGame.get().getPlayer(dataSet[1]).isAlive) break;
				channel.get().currentGame.get().updatePlayerInput(dataSet);
			case TConstants.PLAYER_LOCATION_UPDATE:
				channel.get().currentGame.get().updatePlayerLocation(dataSet);
				break;
			case TConstants.NEW_PLAYER_JOINED:
				//for(int i=0;i<50;i++)
					//selfIntroductionToNewPlayer();
			case TConstants.NEW_PLAYER_JOINED_RESPONSE:
				try {
					//currentGame.get().newPlayer((String)dataSet.get("name"), (Integer)dataSet.get("colorIndex"), (Integer)dataSet.get("side"), (Integer)dataSet.get("sideIndex"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case TConstants.COLLISION_NOTIFICATION:
				channel.get().currentGame.get().killPlayer(dataSet);
				break;
		}
	}
}
