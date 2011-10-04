package ph.edu.uplb.ics.cmsc137.tron2d.utils;

public class TUtils {
	public static float getMin(float[] arr){
		int i=0;
		float min=arr[0];
		while( ++i < arr.length){
			if(arr[i]<min) min=arr[i];
		}
		return min;
	}
}
