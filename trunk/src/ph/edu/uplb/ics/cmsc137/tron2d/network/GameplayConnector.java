package ph.edu.uplb.ics.cmsc137.tron2d.network;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.object.Player;
import ph.edu.uplb.ics.cmsc137.tron2d.state.TGame;
import ph.edu.uplb.ics.cmsc137.tron2d.thread.MulticastMessageSender;

public class GameplayConnector extends ReceiverAdapter {
	private JChannel channel;
	private String user_name = System.getProperty("user.name", "n/a"), appAddress;
	private WeakReference<TGame> currentGame;
	private ReferenceQueue rq = new ReferenceQueue();
	private ArrayList<HashMap<String,Object>> dataSetArray = new ArrayList<HashMap<String,Object>>();
	
	public GameplayConnector(TGame tg) throws Exception{
		super();
		// Establish Connection
		channel = new JChannel(new File("udpConfig.xml"));
		channel.setReceiver(this);
		channel.connect("GameCluster");
		channel.getState(null, 10000);
		appAddress = channel.getAddressAsString();
		
		currentGame = new WeakReference<TGame>(tg,rq);
	}
	
	public void viewAccepted(View new_view) {
		System.out.println("** view: " + new_view);
	}

	public void receive(Message msg) {
		//System.out.println("Received data from "+msg.getSrc());
		HashMap<String,Object> dataSet=(HashMap<String,Object>)msg.getObject();
		switch((Integer)dataSet.get("type")){
			case TConstants.PLAYER_INPUT_UPDATE:
				if(!currentGame.get().getPlayer((String)dataSet.get("name")).isAlive) break;
				currentGame.get().updatePlayerInput(dataSet);
			case TConstants.PLAYER_LOCATION_UPDATE:
				currentGame.get().updatePlayerLocation(dataSet);
				break;
			case TConstants.NEW_PLAYER_JOINED:
				for(int i=0;i<50;i++)
					selfIntroductionToNewPlayer();
			case TConstants.NEW_PLAYER_JOINED_RESPONSE:
				try {
					currentGame.get().newPlayer((String)dataSet.get("name"), new Color(0,255,0), (Float)dataSet.get("x"), (Float)dataSet.get("y"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case TConstants.COLLISION_NOTIFICATION:
				currentGame.get().killPlayer(dataSet);
				break;
		}
	}
	
	public void sendPlayerInput(Player player, Float x, Float y, int key) throws Exception{
		HashMap<String,Object> dataSet=new HashMap<String,Object>();
		dataSet.put("type", (Integer)TConstants.PLAYER_INPUT_UPDATE);
		dataSet.put("name", (String)player.name);
		dataSet.put("nodenumber", (Integer)player.turns.size());
		dataSet.put("x", x);
		dataSet.put("y", y);
		dataSet.put("keyStroke", (Integer)key);
		Message msg = new Message(null, null, dataSet);
		new MulticastMessageSender(channel,msg).send();
	}
	
	public void sendPlayerLocation(Player player, Float x, Float y){
		HashMap<String,Object> dataSet=new HashMap<String,Object>();
		dataSet.put("type", (Integer)TConstants.PLAYER_LOCATION_UPDATE);
		dataSet.put("name", (String)player.name);
		dataSet.put("x", x);
		dataSet.put("y", y);
		Message msg = new Message(null, null, dataSet);
		new MulticastMessageSender(channel,msg).send();
	}
	
	public void disonnect(){
		channel.close();
	}
	
	public void acknowledgeExistenceToOldPlayers(){
		HashMap<String,Object> dataSet=new HashMap<String,Object>();
		Player thisPlayer = currentGame.get().getControlledPlayer();
		dataSet.put("type", TConstants.NEW_PLAYER_JOINED);
		dataSet.put("name", thisPlayer.name);
		dataSet.put("x", (Float)thisPlayer.x);
		dataSet.put("y", (Float)thisPlayer.y);
		dataSet.put("playerColor", thisPlayer.playerColor);
		Message msg = new Message(null, null, dataSet);
		new MulticastMessageSender(channel,msg).send();
	}
	
	public void selfIntroductionToNewPlayer(){
		HashMap<String,Object> dataSet=new HashMap<String,Object>();
		Player thisPlayer = currentGame.get().getControlledPlayer();
		dataSet.put("type", TConstants.NEW_PLAYER_JOINED_RESPONSE);
		dataSet.put("name", thisPlayer.name);
		dataSet.put("x", (Float)thisPlayer.x);
		dataSet.put("y", (Float)thisPlayer.y);
		dataSet.put("playerColor", thisPlayer.playerColor);
		Message msg = new Message(null, null, dataSet);
		new MulticastMessageSender(channel,msg).send();
	}
	
	public void notifyCollision(Float x,Float y,String playerName){
		HashMap<String,Object> dataSet=new HashMap<String,Object>();
		dataSet.put("type", TConstants.COLLISION_NOTIFICATION);
		dataSet.put("name", playerName);
		dataSet.put("x", x);
		dataSet.put("y", y);
		Message msg = new Message(null, null, dataSet);
		new MulticastMessageSender(channel,msg).send();
	}
}
