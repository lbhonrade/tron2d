package ph.edu.uplb.ics.cmsc137.tron2d.core;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.state.TGame;

/*
 * Commandline args: -Djava.library.path=natives/natives-win32 -Djgroups.bind_addr=169.254.29.64
 * 
 * */
public class Tron2D extends StateBasedGame {

	public Tron2D(String name,String[] playerSettings) throws Exception {
		super(name);
		// TODO Auto-generated constructor stub
		this.addState(new TGame(playerSettings));
		this.enterState(TConstants.GAMEPLAY_STATE);
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
		AppGameContainer app = new AppGameContainer(new Tron2D("---Tron2D---",args));
		
		app.setDisplayMode(800, 600, false);
		app.setShowFPS(false);
		app.setIcon("graphics/GameIcon - 32x32.png");
		app.start();
	}

}
