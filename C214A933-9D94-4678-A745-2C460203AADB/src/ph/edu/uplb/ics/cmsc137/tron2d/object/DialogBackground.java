package ph.edu.uplb.ics.cmsc137.tron2d.object;

import javax.swing.ImageIcon;

import org.jdesktop.swingx.JXImagePanel;
import org.pushingpixels.trident.Timeline;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.Animation;

public class DialogBackground extends JXImagePanel {
	private static final long serialVersionUID = 4166722843833699192L;
	private int  xo=0
				,yo=0
				,extX=0
				,extY=0;
	public static DialogBackground tl,tr,bl,br;
	private Timeline showHideAnimation;
	static{
		tl = new DialogBackground(TConstants.DIALOG_BG_TL,TConstants.MENU_SCREEN_WIDTH/2-TConstants.DIALOG_BG_TL.getIconWidth(), TConstants.MENU_SCREEN_HEIGHT/2-TConstants.DIALOG_BG_TL.getIconHeight(),TConstants.MENU_SCREEN_WIDTH,-1*TConstants.DIALOG_BG_TR.getIconHeight(),0);
		tr = new DialogBackground(TConstants.DIALOG_BG_TR,TConstants.MENU_SCREEN_WIDTH/2, TConstants.MENU_SCREEN_HEIGHT/2-TConstants.DIALOG_BG_TR.getIconHeight(),-1*TConstants.DIALOG_BG_TL.getIconWidth(),-1*TConstants.DIALOG_BG_TL.getIconHeight(),1);
		bl = new DialogBackground(TConstants.DIALOG_BG_BL,TConstants.MENU_SCREEN_WIDTH/2-TConstants.DIALOG_BG_BL.getIconWidth(), TConstants.MENU_SCREEN_HEIGHT/2,TConstants.MENU_SCREEN_WIDTH,TConstants.MENU_SCREEN_HEIGHT,3);
		br = new DialogBackground(TConstants.DIALOG_BG_BR,TConstants.MENU_SCREEN_WIDTH/2, TConstants.MENU_SCREEN_HEIGHT/2,-1*TConstants.DIALOG_BG_BL.getIconWidth(),TConstants.MENU_SCREEN_HEIGHT,2);
	}
	
	public DialogBackground(ImageIcon img,int nX,int nY,int xX,int xY,int c){
		setBounds(xX,xY,img.getIconWidth(),img.getIconHeight());
		xo = nX;
		yo = nY + 50;
		extX = xX;
		extY = xY;
		this.setImage(img.getImage());
		this.setOpaque(false);
		showHideAnimation = new Timeline(this);
		showHideAnimation.addPropertyToInterpolate("locationX", extX,xo);
		showHideAnimation.addPropertyToInterpolate("locationY", extY,yo);
		showHideAnimation.setDuration(300);
		showHideAnimation.setInitialDelay(150*c);
	}
	
	public void setLocationX(int newX){
		xo=newX;
		setLocation(xo, yo);
	}
	
	public void setLocationY(int newY){
		yo=newY;
		setLocation(xo, yo);
	}
	
	public static void hideDialogBackground(){
		tl.showHideAnimation.playReverse();
		tr.showHideAnimation.playReverse();
		bl.showHideAnimation.playReverse();
		br.showHideAnimation.playReverse();
	}
	
	public static void showDialogBackground(){
		tl.showHideAnimation.play();
		tr.showHideAnimation.play();
		bl.showHideAnimation.play();
		br.showHideAnimation.play();
	}
}
