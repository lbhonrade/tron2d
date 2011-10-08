package ph.edu.uplb.ics.cmsc137.tron2d.data;

import javax.swing.ImageIcon;

import org.newdawn.slick.Color;

public class TConstants {
	//States
	public static final int GAMEPLAY_STATE=1;
	
	//Dataset Types
	public static final int PLAYER_INPUT_UPDATE=0;
	public static final int PLAYER_LOCATION_UPDATE=1;
	public static final int NEW_PLAYER_JOINED=2;
	public static final int NEW_PLAYER_JOINED_RESPONSE=3;
	public static final int COLLISION_NOTIFICATION=4;
	
	//Player Status
	public static final Integer PLAYER_STATUS_SYNCHRONIZED=0;
	public static final Integer PLAYER_STATUS_PATH_NODE_MISSING=1;
	public static final Integer PLAYER_STATUS_KILLED=2;
	
	//Global Constants
	public static final int FONT_BATTLEFIELD3 = 0;
	public static final int FONT_ANCIENTTRON = 1;
	
	//Menu Constants
	public static final ImageIcon UNSELECTED_BTN_BG=new ImageIcon("graphics/unselected_btn.png");
	public static final ImageIcon HOVERED_BTN_BG=new ImageIcon("graphics/selected_btn.png");
	public static final ImageIcon PRESSED_BTN_BG=new ImageIcon("graphics/pressed_btn.png");
	
	//Game Constants
	public static final Integer GAME_SCREEN_WIDTH=600;
	public static final Integer GAME_SCREEN_HEIGHT=600;
	
	public static final Integer MENU_SCREEN_WIDTH=800;
	public static final Integer MENU_SCREEN_HEIGHT=600;
	
	public static final Float CYCLE_SCALE=0.05f;
	
	public static final Color[] CYCLE_COLOR={
		 Color.red
		,Color.green
		,Color.blue
		,Color.cyan
		,Color.darkGray
		,Color.lightGray
		,Color.magenta
		,new Color(206,103,0)//Orange
		,Color.pink
		,new Color(128,0,0)//Maroon
		,Color.yellow
		,new Color(64,0,255)//Violet
	};
	
	private static final Float CYCLE_SCALED_WIDTH=271f*CYCLE_SCALE;
	private static final Float CYCLE_SCALED_HEIGHT=698f*CYCLE_SCALE;
	private static final Float SIDE_DISTANCE=40f;
	private static final Float SIDE_LENGTH=CYCLE_SCALED_WIDTH*3+2*SIDE_DISTANCE;
	public static final Float CYCLE_START_LOCATION[][][]={
		{
			 {GAME_SCREEN_WIDTH/2f-SIDE_LENGTH/2,20f,180f},
			 {GAME_SCREEN_WIDTH/2f-SIDE_LENGTH/2+SIDE_DISTANCE+CYCLE_SCALED_WIDTH,20f,180f},
			 {GAME_SCREEN_WIDTH/2f-SIDE_LENGTH/2+2*SIDE_DISTANCE+2*CYCLE_SCALED_WIDTH,20f,180f}
		},
		{
			 {GAME_SCREEN_WIDTH-20f-CYCLE_SCALED_HEIGHT,GAME_SCREEN_HEIGHT/2f-SIDE_LENGTH/2,270f},
			 {GAME_SCREEN_WIDTH-20f-CYCLE_SCALED_HEIGHT,GAME_SCREEN_HEIGHT/2f-SIDE_LENGTH/2+SIDE_DISTANCE+CYCLE_SCALED_WIDTH,270f},
			 {GAME_SCREEN_WIDTH-20f-CYCLE_SCALED_HEIGHT,GAME_SCREEN_HEIGHT/2f-SIDE_LENGTH/2+2*SIDE_DISTANCE+2*CYCLE_SCALED_WIDTH,270f}
		},
		{
			 {GAME_SCREEN_WIDTH/2f-SIDE_LENGTH/2,GAME_SCREEN_HEIGHT-20f-CYCLE_SCALED_HEIGHT,0f},
			 {GAME_SCREEN_WIDTH/2f-SIDE_LENGTH/2+SIDE_DISTANCE+CYCLE_SCALED_WIDTH,GAME_SCREEN_HEIGHT-20f-CYCLE_SCALED_HEIGHT,0f},
			 {GAME_SCREEN_WIDTH/2f-SIDE_LENGTH/2+2*SIDE_DISTANCE+2*CYCLE_SCALED_WIDTH,GAME_SCREEN_HEIGHT-20f-CYCLE_SCALED_HEIGHT,0f}
		},
		{
			{20f,GAME_SCREEN_HEIGHT/2f-SIDE_LENGTH/2,90f},
			{20f,GAME_SCREEN_HEIGHT/2f-SIDE_LENGTH/2+SIDE_DISTANCE+CYCLE_SCALED_WIDTH,90f},
			{20f,GAME_SCREEN_HEIGHT/2f-SIDE_LENGTH/2+2*SIDE_DISTANCE+2*CYCLE_SCALED_WIDTH,90f}
		}
	};
}
