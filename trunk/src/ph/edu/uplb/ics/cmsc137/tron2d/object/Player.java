package ph.edu.uplb.ics.cmsc137.tron2d.object;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TurnLocation;

public class Player {
	public float x,y,scaledWidth,scaledHeight;
	public Image icon;
	public Color playerColor=Color.green;
	public String name="n/a";
	public List<TurnLocation> turns=new ArrayList<TurnLocation>();
	public boolean isTurnsListLocked=false,isAlive=true;
	
	public Player(float _x, float _y, String _playerAddress, Color _playerColor) throws SlickException{
		x = _x;
		y = _y;
		name = _playerAddress;
		playerColor = _playerColor;
	}
	
}
