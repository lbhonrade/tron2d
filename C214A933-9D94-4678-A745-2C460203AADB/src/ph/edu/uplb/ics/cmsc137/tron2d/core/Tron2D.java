package ph.edu.uplb.ics.cmsc137.tron2d.core;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import ph.edu.uplb.ics.cmsc137.tron2d.data.PlayerSettings;
import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;

/*
 * Commandline args: -Djava.library.path=natives/windows -Djgroups.bind_addr=10.0.0.7
 * 
 * */
public class Tron2D extends StateBasedGame {

	public Tron2D(String name,ArrayList<PlayerSettings> playersSettings) throws Exception {
		super(name);
		// TODO Auto-generated constructor stub
		//this.addState(new TGame(playersSettings));
		//this.enterState(TConstants.GAMEPLAY_STATE);
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		// TODO Auto-generated method stub
		this.getState(TConstants.GAMEPLAY_STATE).init(gameContainer, this);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//String playerName=JOptionPane.showInputDialog(null, "Please enter your name:");
		if(args.length!=3) return;
		System.setProperty("jgroups.bind_addr", "10.0.0.18");
		System.setProperty("java.library.path", "natives/windows");
		
		Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
		fieldSysPath.setAccessible( true );
		fieldSysPath.set( null, null );
		
		ArrayList<PlayerSettings> players = new ArrayList<PlayerSettings>();
		for(int i=0;i<1;i++)
			for(int j=0;j<1;j++){
				PlayerSettings controller = new PlayerSettings();
				controller.name="player_"+(i*3+j);
				controller.side=i;
				controller.sideIndex=j;
				controller.colorIndex=(i*3+j);
				players.add(controller);
			}
		
		AppGameContainer app = new AppGameContainer(new GridGame(players));
		
		app.setDisplayMode(TConstants.GAME_SCREEN_WIDTH, TConstants.GAME_SCREEN_HEIGHT, false);
		app.setShowFPS(false);
		app.setIcon("graphics/GameIcon - 32x32.png");
		app.start();
	}

}
