package ph.edu.uplb.ics.cmsc137.tron2d.object;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.pushingpixels.trident.Timeline;

public class TMenuButton extends TButton {
	private final int extX,extY;
	private static ArrayList<TMenuButton> instances = new ArrayList<TMenuButton>();
	private Timeline showHideAnimation;
	private boolean isHidden = false;
	
	public TMenuButton(String label,int nX,int nY,int xX,int xY){
		super(label,nX,nY);
		extX = xX;
		extY = xY;
		instances.add(this);
		this.setLocation(extX,extY);
		
		
		showHideAnimation = new Timeline(this);
		showHideAnimation.addPropertyToInterpolate("locationX", extX, xo);
		showHideAnimation.addPropertyToInterpolate("locationY", extY, yo);
		showHideAnimation.setDuration(300);
		showHideAnimation.setInitialDelay(150*instances.indexOf(this));
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		TMenuButton.hideMenu();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		//TMenuButton.hideMenu();
		TMenuButton.showMenu();
	}
	
	public static void hideMenu(){
		for(TMenuButton menuBtn:instances){
			menuBtn.showHideAnimation.playReverse();
		}
	}
	
	public static void showMenu(){
		for(TMenuButton menuBtn:instances){
			menuBtn.showHideAnimation.play();
		}
	}
}
