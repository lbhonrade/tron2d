package ph.edu.uplb.ics.cmsc137.tron2d.data;

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
	public static final int PLAYER_STATUS_SYNCHRONIZED=0;
	public static final int PLAYER_STATUS_PATH_NODE_MISSING=1;
	public static final int PLAYER_STATUS_KILLED=2;
}
