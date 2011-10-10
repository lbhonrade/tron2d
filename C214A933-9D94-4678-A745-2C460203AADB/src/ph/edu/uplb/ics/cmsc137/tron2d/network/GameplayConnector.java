package ph.edu.uplb.ics.cmsc137.tron2d.network;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import ph.edu.uplb.ics.cmsc137.tron2d.core.GridGame;
import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.object.Player;

public class GameplayConnector {
	private TronChannel channel;
	private WeakReference<GridGame> currentGame;
	private ReferenceQueue<GridGame> rq = new ReferenceQueue<GridGame>();
	
	public GameplayConnector(GridGame tg) throws Exception{
		super();
		currentGame = new WeakReference<GridGame>(tg,rq);
		// Establish Connection
		channel = new TronChannel("GameCluster",currentGame);
	}
	
	public void sendPlayerInput(Player player, Float x, Float y, int key) throws Exception{
		channel.send(TConstants.PLAYER_INPUT_UPDATE+"\0"+
				player.name+"\0"+
				x+"\0"+
				y+"\0"+
				key
		);
	}
	
	public void sendPlayerLocation(Player player, Float x, Float y){
		HashMap<String,Object> dataSet=new HashMap<String,Object>();
		dataSet.put("type", (Integer)TConstants.PLAYER_LOCATION_UPDATE);
		dataSet.put("name", (String)player.name);
		dataSet.put("x", x);
		dataSet.put("y", y);
		//new MulticastMessageSender(channel,msg).send();
	}
	
	public void disonnect(){
		//channel.close();
	}
	
	public void acknowledgeExistenceToOldPlayers(){
		HashMap<String,Object> dataSet=new HashMap<String,Object>();
		Player thisPlayer = currentGame.get().getControlledPlayer();
		dataSet.put("type", TConstants.NEW_PLAYER_JOINED);
		dataSet.put("name", thisPlayer.name);
		dataSet.put("x", (Float)thisPlayer.x);
		dataSet.put("y", (Float)thisPlayer.y);
		dataSet.put("playerColor", thisPlayer.playerColor);
	}
	
	public void selfIntroductionToNewPlayer(){
		HashMap<String,Object> dataSet=new HashMap<String,Object>();
		Player thisPlayer = currentGame.get().getControlledPlayer();
		dataSet.put("type", TConstants.NEW_PLAYER_JOINED_RESPONSE);
		dataSet.put("name", thisPlayer.name);
		dataSet.put("x", (Float)thisPlayer.x);
		dataSet.put("y", (Float)thisPlayer.y);
		dataSet.put("playerColor", thisPlayer.playerColor);
	}
	
	public void notifyCollision(Float x,Float y,String playerName){
		channel.send(TConstants.COLLISION_NOTIFICATION+"\0"+
				playerName+"\0"+
				x+"\0"+
				y
		);
	}
}
