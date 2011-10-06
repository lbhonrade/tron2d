package ph.edu.uplb.ics.cmsc137.tron2d.data;

public class TurnLocation extends GridLocation {
	public int turnType;
	public TurnLocation(float _x, float _y) {
		super(_x, _y);
		turnType=0;
	}
	
	public TurnLocation(float _x, float _y, float _currentRotation, float _nextRotation) {
		super(_x, _y);
		int a=(int) ((360+_currentRotation)/90)%4;
		int b=(int) ((360+_nextRotation)/90)%4;
		//01 12 23 30
		//03 32 21 10
		switch(a*10+b){
			case 1:case 32:
				turnType=1;
				break;
			case 12:case 3:
				turnType=2;
				break;
			case 23:case 10:
				turnType=3;
				break;
			case 30:case 21:
				turnType=0;
				break;
			default:
				System.out.println("Unknown type:"+a*10+b);
				break;
		}
		// TODO Auto-generated constructor stub
	}
	
}
