package ph.edu.uplb.ics.cmsc137.tron2d.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;

public class TUtils {
	private static HashMap<Integer,Font> gameFonts;

	static{
		gameFonts = new HashMap<Integer,Font>();
		try {
			gameFonts.put(TConstants.FONT_BATTLEFIELD3, Font.createFont(0,new File("fonts/battlefield3.ttf")));
			gameFonts.put(TConstants.FONT_ANCIENTTRON, Font.createFont(0,new File("fonts/ancienttron.ttf")));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static float getMin(float[] arr){
		int i=0;
		float min=arr[0];
		while( ++i < arr.length){
			if(arr[i]<min) min=arr[i];
		}
		return min;
	}
	public static Font getFont(int key){
		Font f = gameFonts.get(key);
		return f!=null?f:new Font(Font.MONOSPACED,Font.PLAIN,21);
	}
}
