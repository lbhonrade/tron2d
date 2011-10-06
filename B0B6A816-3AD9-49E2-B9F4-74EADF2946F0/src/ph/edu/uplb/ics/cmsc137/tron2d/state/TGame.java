package ph.edu.uplb.ics.cmsc137.tron2d.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import org.newdawn.slick.Animation;
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
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.data.TurnLocation;
import ph.edu.uplb.ics.cmsc137.tron2d.network.GameplayConnector;
import ph.edu.uplb.ics.cmsc137.tron2d.object.Player;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.TUtils;

public class TGame implements GameState {
	private Image elbow[],hWall,vWall,lightCycle;
	private Map<String,Player> players;
	private ArrayList<String> playerNames;
	private Player controller;
	private float scale = 0.05f,elbow_scale=0.2775f/*0.555f/*=0.2f*/,hip=0;
	private GameplayConnector connection;
	private boolean isPaused = true;
	private String[] thisPlayerSettings;
	private Area collisionWalls;
	private float[][] collisionCycle;
	private Animation explosion;
	private String newCollided = null;
	private Sound explosionSound;
	
	public TGame(String[] _playerSettings){
		super();
		thisPlayerSettings = _playerSettings;
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
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub

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
				explosionSound.play();
				break;
		}
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerButtonPressed(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerButtonReleased(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerDownPressed(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerDownReleased(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerLeftPressed(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerLeftReleased(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerRightPressed(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerRightReleased(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerUpPressed(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerUpReleased(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return TConstants.GAMEPLAY_STATE;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
		lightCycle = new Image("graphics/lightcycle-topview.png");
		hWall = new Image("graphics/hWall.png");
		vWall = new Image("graphics/vWall.png");
		elbow=new Image[4];
		for(int i=0;i<4;i++){
			elbow[i]=new Image("graphics/elbow_"+i+".png");
			elbow[i]=elbow[i].getScaledCopy(elbow_scale);
		}
		explosion = new Animation(new SpriteSheet("graphics/explosion.png",64,64),100);
		explosion.setLooping(false);
		
		explosionSound = new Sound("audio/explosion.wav");
		
		collisionCycle=new float[][]{{lightCycle.getWidth()*scale/3f,0f,lightCycle.getWidth()*scale/3f,lightCycle.getHeight()*scale/4f},
									 {3f*lightCycle.getHeight()*scale/4f,lightCycle.getWidth()*scale/3f,lightCycle.getHeight()*scale/4f,lightCycle.getWidth()*scale/3f},
									 {lightCycle.getWidth()*scale/3f,3f*lightCycle.getHeight()*scale/4f,lightCycle.getWidth()*scale/3f,lightCycle.getHeight()*scale/4f},
									 {0f,lightCycle.getWidth()*scale/3f,lightCycle.getHeight()*scale/4f,lightCycle.getWidth()*scale/3f}};
		
		newPlayer(thisPlayerSettings[0], new Color(255,0,0), java.lang.Float.valueOf(thisPlayerSettings[1]), java.lang.Float.valueOf(thisPlayerSettings[2]));
		controller = players.get(thisPlayerSettings[0]);
		connection.acknowledgeExistenceToOldPlayers();
		
		collisionWalls.add(new Area(new Rectangle2D.Float(0f,0f,800f,5f)));
		collisionWalls.add(new Area(new Rectangle2D.Float(0f,0f,5f,600f)));
		collisionWalls.add(new Area(new Rectangle2D.Float(795f,0f,5f,600f)));
		collisionWalls.add(new Area(new Rectangle2D.Float(0f,595f,800f,5f)));
		
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		//connection.disonnect();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		// TODO Auto-generated method stub
		graphics.setBackground(Color.black);
		graphics.setColor(Color.white);
		ArrayList<TurnLocation> boundaries=new ArrayList<TurnLocation>();
		boundaries.add(new TurnLocation(2.5f,2.5f,0f,90f));
		boundaries.add(new TurnLocation(797.5f,2.5f,90f,180f));
		boundaries.add(new TurnLocation(797.5f,597.5f,180f,270f));
		boundaries.add(new TurnLocation(2.5f,597.5f,270f,360f));
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
			Image scaledLightCycle=playerInstance.icon.getScaledCopy(scale);
			scaledLightCycle.rotate(playerInstance.icon.getRotation());
			graphics.drawImage(scaledLightCycle, playerInstance.x, playerInstance.y, playerInstance.playerColor);
		}
		
		int orientation=(int)((360+controller.icon.getRotation())/90f)%4;
		float pts[]=drawWall(null,new TurnLocation(controller.x+controller.icon.getCenterOfRotationX(),controller.y+controller.icon.getCenterOfRotationY()),controller.turns.get(controller.turns.size()-1));
		float X[]=new float[pts.length/2],Y[]=new float[pts.length/2];
		for(int i=0;i<pts.length;i++){
			if(i%2==0) X[i/2]=pts[i];
			else Y[i/2]=pts[i];
		}
		Polygon dtr = new Polygon(pts);
		graphics.setColor(Color.green);
		graphics.draw(new Rectangle((float)(controller.x+controller.icon.getCenterOfRotationX()-(controller.icon.getHeight()*scale/2)*(orientation%2)-(controller.icon.getWidth()*scale/2)*(1-orientation%2)+collisionCycle[orientation][0]),(float)(controller.y+controller.icon.getCenterOfRotationY()-(controller.icon.getWidth()*scale/2)*(orientation%2)-(controller.icon.getHeight()*scale/2)*(1-orientation%2)+collisionCycle[orientation][1]),collisionCycle[orientation][2],collisionCycle[orientation][3]));
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(isPaused) return;
		
		//hip += 1.0f;
		//if(hip<1) return;
		hip=delta*0.5f;
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
			collisionWalls.add(new Area(new Rectangle2D.Float(TUtils.getMin(X),TUtils.getMin(Y),dtr.getWidth(),dtr.getHeight())));
		}
		int orientation=(int)((360+controller.icon.getRotation())/90f)%4;
		
		if(controller.isAlive && collisionWalls.intersects(new Rectangle2D.Float((float)(controller.x+controller.icon.getCenterOfRotationX()-(controller.icon.getHeight()*scale/2)*(orientation%2)-(controller.icon.getWidth()*scale/2)*(1-orientation%2)+collisionCycle[orientation][0]),(float)(controller.y+controller.icon.getCenterOfRotationY()-(controller.icon.getWidth()*scale/2)*(orientation%2)-(controller.icon.getHeight()*scale/2)*(1-orientation%2)+collisionCycle[orientation][1]),collisionCycle[orientation][2],collisionCycle[orientation][3]))){
			System.out.println("Collision detected.");
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
	
	public int[] updatePlayerInput(HashMap<String,Object> dataSet) {
		String playerName = (String)dataSet.get("name");
		Player player = players.get(playerName);
		float currentRotation;
		TurnLocation turn;
		float pts[],X[],Y[];
		switch((Integer)dataSet.get("keyStroke")){
			case Input.KEY_W:
				isPaused=!isPaused;
				break;
			case Input.KEY_A:
				currentRotation = player.icon.getRotation();
				player.icon.rotate(-90.0f);
				turn = new TurnLocation((java.lang.Float)dataSet.get("x") + player.icon.getCenterOfRotationX(), (java.lang.Float)dataSet.get("y") + player.icon.getCenterOfRotationY(),currentRotation,player.icon.getRotation());
				player.turns.add(turn);
				
				subtractWall(new TurnLocation(player.x+player.icon.getCenterOfRotationX(),player.y+player.icon.getCenterOfRotationY()),
							 new TurnLocation((java.lang.Float)dataSet.get("x")+player.icon.getCenterOfRotationX(),(java.lang.Float)dataSet.get("y")+player.icon.getCenterOfRotationY()));
				break;
			case Input.KEY_D:
				currentRotation = player.icon.getRotation();
				player.icon.rotate(90.0f);
				turn = new TurnLocation((java.lang.Float)dataSet.get("x") + player.icon.getCenterOfRotationX(), (java.lang.Float)dataSet.get("y") + player.icon.getCenterOfRotationY(),currentRotation,player.icon.getRotation());
				player.turns.add(turn);
				
				subtractWall(new TurnLocation(player.x+player.icon.getCenterOfRotationX(),player.y+player.icon.getCenterOfRotationY()),
						     new TurnLocation((java.lang.Float)dataSet.get("x")+player.icon.getCenterOfRotationX(),(java.lang.Float)dataSet.get("y")+player.icon.getCenterOfRotationY()));
				break;
		}
		return new int[]{TConstants.PLAYER_STATUS_SYNCHRONIZED,-1};
	}
	
	public int[] updatePlayerLocation(HashMap<String,Object> dataSet){
		String playerName = (String)dataSet.get("name");
		Player player = players.get(playerName);
		player.x = (java.lang.Float)dataSet.get("x");
		player.y = (java.lang.Float)dataSet.get("y");
		return new int[]{TConstants.PLAYER_STATUS_SYNCHRONIZED,-1};
	}
	
	public void newPlayer(String playerName, Color playerColor, java.lang.Float startX, java.lang.Float startY) throws SlickException{
		if(playerNames.contains(playerName)) return;
		Player newInstance = new Player(startX, startY, playerName, playerColor);
		newInstance.icon = lightCycle.copy();
		newInstance.icon.setCenterOfRotation(newInstance.icon.getWidth() / 2.0f * scale,
		newInstance.icon.getHeight() / 2.0f * scale);
		newInstance.scaledWidth = newInstance.icon.getWidth() * scale;
		newInstance.scaledHeight = newInstance.icon.getHeight() * scale;
		newInstance.turns.add(new TurnLocation(newInstance.x+newInstance.icon.getCenterOfRotationX(),newInstance.y+newInstance.icon.getCenterOfRotationY()));
		players.put(playerName, newInstance);
		playerNames.add(playerName);
	}
	
	public Player getPlayer(String playerName){
		return players.get(playerName);
	}
	
	public void killPlayer(HashMap<String,Object> dataSet){
		String playerName = (String)dataSet.get("name");
		Player player = players.get(playerName);
		subtractWall(new TurnLocation(player.x+player.icon.getCenterOfRotationX(),player.y+player.icon.getCenterOfRotationY()),
				 new TurnLocation((java.lang.Float)dataSet.get("x")+player.icon.getCenterOfRotationX(),(java.lang.Float)dataSet.get("y")+player.icon.getCenterOfRotationY()));
		
		int orientation=(int)((360+player.icon.getRotation())/90f)%4;
		if(collisionWalls.intersects(new Rectangle2D.Float((float)(player.x+player.icon.getCenterOfRotationX()-(player.icon.getHeight()*scale/2)*(orientation%2)-(player.icon.getWidth()*scale/2)*(1-orientation%2)+collisionCycle[orientation][0]),(float)(player.y+player.icon.getCenterOfRotationY()-(player.icon.getWidth()*scale/2)*(orientation%2)-(player.icon.getHeight()*scale/2)*(1-orientation%2)+collisionCycle[orientation][1]),collisionCycle[orientation][2],collisionCycle[orientation][3]))){
			player.isAlive=false;
			newCollided=player.name;
			explosionSound.play();
		}
	}
}
