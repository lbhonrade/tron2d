package ph.edu.uplb.ics.cmsc137.tron2d.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import ph.edu.uplb.ics.cmsc137.tron2d.data.PlayerSettings;
import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.data.TurnLocation;
import ph.edu.uplb.ics.cmsc137.tron2d.network.GameplayConnector;
import ph.edu.uplb.ics.cmsc137.tron2d.object.Player;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.TUtils;

public class GridGame extends BasicGame {
	private Image elbow[],hWall,vWall,lightCycle,bg;
	private Map<String,Player> players;
	private ArrayList<String> playerNames;
	private Player controller;
	private float scale = TConstants.CYCLE_SCALE,elbow_scale=0.2775f/*0.555f/*=0.2f*/,hip=0;
	private GameplayConnector connection;
	private boolean isPaused = true;
	private ArrayList<PlayerSettings> playerSettings;
	private Area collisionWalls;
	private float[][] collisionCycle;
	private Animation explosion;
	private String newCollided = null;
	private boolean isPlayersInitialized = false;
	
	private static java.lang.Float[][] wallCorners={
		{0f,0f,(float)TConstants.GAME_SCREEN_WIDTH,5f},
		{TConstants.GAME_SCREEN_WIDTH-5f,0f,5f,(float)TConstants.GAME_SCREEN_HEIGHT},
		{0f,TConstants.GAME_SCREEN_HEIGHT-5f,(float)TConstants.GAME_SCREEN_WIDTH,5f},
		{0f,0f,5f,(float)TConstants.GAME_SCREEN_HEIGHT}
	};
	
	public GridGame(ArrayList<PlayerSettings> _playerSettings){
		super("Tron2D");
		playerSettings = _playerSettings;
		players=new HashMap<String,Player>();
		playerNames = new ArrayList<String>();
		try {
			connection=new GameplayConnector(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		collisionWalls = new Area();
	}

	@Override
	public void keyPressed(int keyCode, char c) {
		// TODO Auto-generated method stub
		switch(keyCode){
			case Input.KEY_W:
			case Input.KEY_A:
			case Input.KEY_D:
				try {
					connection.sendPlayerInput(controller, controller.x, controller.y, keyCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case Input.KEY_F:
				newCollided=controller.name;
				break;
		}
	}

	@Override
	public void init(GameContainer arg0)
			throws SlickException {
		// TODO Auto-generated method stub
		
		lightCycle = new Image("graphics/lightcycle-topview.png");
		hWall = new Image("graphics/hWall.png");
		vWall = new Image("graphics/vWall.png");
		bg = new Image("graphics/filledhexsbg_2.png");
		elbow=new Image[4];
		for(int i=0;i<4;i++){
			elbow[i]=new Image("graphics/elbow_"+i+".png");
			elbow[i]=elbow[i].getScaledCopy(elbow_scale);
		}
		explosion = new Animation(new SpriteSheet("graphics/explosion.png",64,64),100);
		explosion.setLooping(false);
		
		collisionCycle=new float[][]{{lightCycle.getWidth()*scale/3f,0f,lightCycle.getWidth()*scale/3f,lightCycle.getHeight()*scale/4f},
									 {3f*lightCycle.getHeight()*scale/4f,lightCycle.getWidth()*scale/3f,lightCycle.getHeight()*scale/4f,lightCycle.getWidth()*scale/3f},
									 {lightCycle.getWidth()*scale/3f,3f*lightCycle.getHeight()*scale/4f,lightCycle.getWidth()*scale/3f,lightCycle.getHeight()*scale/4f},
									 {0f,lightCycle.getWidth()*scale/3f,lightCycle.getHeight()*scale/4f,lightCycle.getWidth()*scale/3f}};
		if(!isPlayersInitialized){
			for(PlayerSettings p:playerSettings)
				newPlayer(p);
			controller = players.get("player_0");
			isPlayersInitialized = true;
		}
		//connection.acknowledgeExistenceToOldPlayers();
		for(int i=0;i<4;i++)
			collisionWalls.add(new Area(new Rectangle2D.Float(wallCorners[i][0],wallCorners[i][1],wallCorners[i][2],wallCorners[i][3])));
	}

	@Override
	public void render(GameContainer arg0, Graphics graphics)
			throws SlickException {
		// TODO Auto-generated method stub
		for(int i=0;i<=TConstants.GAME_SCREEN_WIDTH/bg.getWidth();i++)
			for(int j=0;j<=TConstants.GAME_SCREEN_HEIGHT/bg.getHeight();j++)
				bg.draw(i*bg.getWidth(), j*bg.getHeight());
		graphics.setColor(Color.white);
		ArrayList<TurnLocation> boundaries=new ArrayList<TurnLocation>();
		
		boundaries.add(new TurnLocation(2.5f,2.5f,0f,90f));
		boundaries.add(new TurnLocation(TConstants.GAME_SCREEN_WIDTH-2.5f,2.5f,90f,180f));
		boundaries.add(new TurnLocation(TConstants.GAME_SCREEN_WIDTH-2.5f,TConstants.GAME_SCREEN_HEIGHT-2.5f,180f,270f));
		boundaries.add(new TurnLocation(2.5f,TConstants.GAME_SCREEN_HEIGHT-2.5f,270f,360f));
		boundaries.add(new TurnLocation(2.5f,2.5f,0f,90f));
	
		for(int i=1;i<5;i++){
			drawWall(graphics,boundaries.get(i-1),boundaries.get(i));
			//drawElbow(graphics,dummy,dummy.turns.get(i-1));
		}
		Player playerInstance;
		for(Entry<String,Player> playerEntry:players.entrySet()){
			playerInstance = playerEntry.getValue();
			graphics.setColor(playerInstance.playerColor);
			for (int i = 1; i < playerInstance.turns.size(); i++) {
				drawWall(graphics,playerInstance.turns.get(i - 1),playerInstance.turns.get(i));
				if(i>1&&i<playerInstance.turns.size())
					drawElbow(graphics,playerInstance.playerColor,playerInstance.turns.get(i-1));
			}
			TurnLocation currentLocation=new TurnLocation(playerInstance.x+playerInstance.icon.getCenterOfRotationX(),playerInstance.y+playerInstance.icon.getCenterOfRotationY());
			drawWall(graphics,playerInstance.turns.get(playerInstance.turns.size()-1),currentLocation);
			if(playerInstance.turns.size()>2){
				drawElbow(graphics,playerInstance.playerColor,playerInstance.turns.get(playerInstance.turns.size()-1));
			}
			
			if(!playerInstance.isAlive){
				if(playerInstance.name.equals(newCollided)){
					explosion.draw(playerInstance.x+playerInstance.icon.getCenterOfRotationX()-32, playerInstance.y+playerInstance.icon.getCenterOfRotationY()-32);
					if(explosion.isStopped()){
						explosion.restart();
						newCollided=null;
					}
				}
				continue;
			}
			graphics.drawImage(playerInstance.icon, playerInstance.x, playerInstance.y, playerInstance.playerColor);
		}
		
		int orientation=(int)((360+controller.icon.getRotation())/90f)%4;
		float pts[]=drawWall(null,new TurnLocation(controller.x+controller.icon.getCenterOfRotationX(),controller.y+controller.icon.getCenterOfRotationY()),controller.turns.get(controller.turns.size()-1));
		float X[]=new float[pts.length/2],Y[]=new float[pts.length/2];
		for(int i=0;i<pts.length;i++){
			if(i%2==0) X[i/2]=pts[i];
			else Y[i/2]=pts[i];
		}
		graphics.setColor(Color.green);
		graphics.draw(new Rectangle((float)(controller.x+controller.icon.getCenterOfRotationX()-(controller.icon.getHeight()/2)*(orientation%2)-(controller.icon.getWidth()/2)*(1-orientation%2)+collisionCycle[orientation][0]),(float)(controller.y+controller.icon.getCenterOfRotationY()-(controller.icon.getWidth()/2)*(orientation%2)-(controller.icon.getHeight()/2)*(1-orientation%2)+collisionCycle[orientation][1]),collisionCycle[orientation][2],collisionCycle[orientation][3]));
		
	}

	@Override
	public void update(GameContainer gc, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(isPaused) return;
		
		//hip += 1.0f;
		//if(hip<1) return;
		hip=delta*0.2f;
		boolean intersectedAlready=false;
		for(Entry<String,Player> player:players.entrySet()){
			if(!player.getValue().isAlive) continue;
			float rotation = player.getValue().icon.getRotation();
			float oldX=player.getValue().x;
			float oldY=player.getValue().y;
			player.getValue().x += (java.lang.Float)(hip * (float)Math.sin(Math.toRadians(rotation)));
			player.getValue().y -= (java.lang.Float)(hip * (float)Math.cos(Math.toRadians(rotation)));
			
			float pts[]=drawWall(null,new TurnLocation(player.getValue().x+player.getValue().icon.getCenterOfRotationX(),player.getValue().y+player.getValue().icon.getCenterOfRotationY()),
									  new TurnLocation(oldX+player.getValue().icon.getCenterOfRotationX(),oldY+player.getValue().icon.getCenterOfRotationY()));
			float X[]=new float[pts.length/2],Y[]=new float[pts.length/2];
			for(int i=0;i<pts.length;i++){
				if(i%2==0) X[i/2]=pts[i];
				else Y[i/2]=pts[i];
			}
			Polygon dtr = new Polygon(pts);	
			Area newWall=new Area(new Rectangle2D.Float(TUtils.getMin(X),TUtils.getMin(Y),dtr.getWidth(),dtr.getHeight()));
			if(player==controller){
				Area newWallCopy=(Area) newWall.clone();
				newWallCopy.exclusiveOr(collisionWalls);
				Area intersection=(Area)collisionWalls.clone();
				intersection.subtract(newWallCopy);
				intersectedAlready=!intersection.isSingular();
			}
			collisionWalls.add(newWall);
		}
		int orientation=(int)((360+controller.icon.getRotation())/90f)%4;
		
		if(controller.isAlive && (collisionWalls.intersects(new Rectangle2D.Float((float)(controller.x+controller.icon.getCenterOfRotationX()-(controller.icon.getHeight()/2)*(orientation%2)-(controller.icon.getWidth()/2)*(1-orientation%2)+collisionCycle[orientation][0]),(float)(controller.y+controller.icon.getCenterOfRotationY()-(controller.icon.getWidth()/2)*(orientation%2)-(controller.icon.getHeight()/2)*(1-orientation%2)+collisionCycle[orientation][1]),collisionCycle[orientation][2],collisionCycle[orientation][3])))){
			System.out.println("Collision detected.");
			connection.notifyCollision(controller.x, controller.y, controller.name);
		}else if(intersectedAlready){
			connection.notifyCollision(controller.x, controller.y, controller.name);
		}
	}
	
	private float[] drawWall(Graphics g,TurnLocation a,TurnLocation b){
		Line l=new Line(a.x,a.y,b.x,b.y);
		Polygon wall = null;
		float[] retval;
		if(l.getDX()==0){
			wall=new Polygon(retval=new float[]{a.x-2.5f,a.y,
										 a.x+2.5f,a.y,
										 b.x+2.5f,b.y,
										 b.x-2.5f,b.y});
			if(g!=null) g.texture(wall, vWall, true);
		}else{
			wall=new Polygon(retval=new float[]{a.x,a.y-2.5f,
										 a.x,a.y+2.5f,
										 b.x,b.y+2.5f,
										 b.x,b.y-2.5f});
			if(g!=null) g.texture(wall, hWall, true);
		}
		return retval;
	}
	
	private void drawElbow(Graphics g,Color color,TurnLocation t){
		g.drawImage(elbow[t.turnType],t.x-elbow[t.turnType].getWidth()/2.0f,t.y-elbow[t.turnType].getHeight()/2.0f,color);
	}

	private void subtractWall(TurnLocation a,TurnLocation b){
		float coords[]=drawWall(null,a,b);
		float X[]=new float[coords.length/2];
		float Y[]=new float[coords.length/2];
		for(int i=0;i<coords.length;i++){
			if(i%2==0) X[i/2]=coords[i];
			else Y[i/2]=coords[i];
		}
		Polygon rect = new Polygon(coords);
		collisionWalls.subtract(new Area(new Rectangle2D.Float(TUtils.getMin(X),TUtils.getMin(Y),rect.getWidth(),rect.getHeight())));
	}
	
	public Player getControlledPlayer(){
		return controller;
	}
	
	public int[] updatePlayerInput(String[] dataSet) {
		String playerName = dataSet[1];
		Float dataX = java.lang.Float.valueOf(dataSet[2]);
		Float dataY = java.lang.Float.valueOf(dataSet[3]);
		Integer key = Integer.valueOf(dataSet[4]);
		Player player = players.get(playerName);
		float currentRotation;
		TurnLocation turn;
		switch(key){
			case Input.KEY_W:
				isPaused=!isPaused;
				break;
			case Input.KEY_A:
				currentRotation = player.icon.getRotation();
				player.icon.rotate(-90.0f);
				turn = new TurnLocation(dataX + player.icon.getCenterOfRotationX(), dataY + player.icon.getCenterOfRotationY(),currentRotation,player.icon.getRotation());
				player.turns.add(turn);
				
				subtractWall(new TurnLocation(player.x+player.icon.getCenterOfRotationX(),player.y+player.icon.getCenterOfRotationY()),
							 new TurnLocation(dataX+player.icon.getCenterOfRotationX(),dataY+player.icon.getCenterOfRotationY()));
				break;
			case Input.KEY_D:
				currentRotation = player.icon.getRotation();
				player.icon.rotate(90.0f);
				turn = new TurnLocation(dataX + player.icon.getCenterOfRotationX(), dataY + player.icon.getCenterOfRotationY(),currentRotation,player.icon.getRotation());
				player.turns.add(turn);
				
				subtractWall(new TurnLocation(player.x+player.icon.getCenterOfRotationX(),player.y+player.icon.getCenterOfRotationY()),
						     new TurnLocation(dataX+player.icon.getCenterOfRotationX(),dataY+player.icon.getCenterOfRotationY()));
				break;
		}
		return new int[]{TConstants.PLAYER_STATUS_SYNCHRONIZED,-1};
	}
	
	public int[] updatePlayerLocation(String[] dataSet){
		String playerName = dataSet[1];
		Float dataX = java.lang.Float.valueOf(dataSet[2]);
		Float dataY = java.lang.Float.valueOf(dataSet[3]);
		Player player = players.get(playerName);
		player.x = dataX;
		player.y = dataY;
		return new int[]{TConstants.PLAYER_STATUS_SYNCHRONIZED,-1};
	}
	
	public void newPlayer(PlayerSettings playerSettings) throws SlickException{
		if(playerNames.contains(playerSettings.name)) return;
		String playerName = playerSettings.name;
		Integer colorIndex = playerSettings.colorIndex;
		Integer side = playerSettings.side;
		Integer sideIndex = playerSettings.sideIndex;
		Player newInstance = new Player(TConstants.CYCLE_START_LOCATION[side][sideIndex][0], TConstants.CYCLE_START_LOCATION[side][sideIndex][1], playerName, TConstants.CYCLE_COLOR[colorIndex]);
		newInstance.icon = lightCycle.getScaledCopy(scale);
		newInstance.icon.setCenterOfRotation(newInstance.icon.getWidth() / 2.0f,
				newInstance.icon.getHeight() / 2.0f);
		newInstance.icon.setRotation(TConstants.CYCLE_START_LOCATION[side][sideIndex][2]);
		newInstance.turns.add(new TurnLocation(newInstance.x+newInstance.icon.getCenterOfRotationX(),newInstance.y+newInstance.icon.getCenterOfRotationY()));
		players.put(playerName, newInstance);
		playerNames.add(playerName);
	}
	
	public Player getPlayer(String playerName){
		return players.get(playerName);
	}
	
	public void killPlayer(String[] dataSet){
		String playerName = dataSet[1];
		Float dataX = java.lang.Float.valueOf(dataSet[2]);
		Float dataY = java.lang.Float.valueOf(dataSet[3]);
		Player player = players.get(playerName);
		subtractWall(new TurnLocation(player.x+player.icon.getCenterOfRotationX(),player.y+player.icon.getCenterOfRotationY()),
				 	 new TurnLocation(dataX+player.icon.getCenterOfRotationX(),dataY+player.icon.getCenterOfRotationY()));
		player.x=dataX;
		player.y=dataY;
		player.isAlive=false;
		newCollided=player.name;
		try {
			(new Sound("audio/explosion.wav")).play();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
