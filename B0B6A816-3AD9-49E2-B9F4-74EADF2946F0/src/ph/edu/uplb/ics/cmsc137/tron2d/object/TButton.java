package ph.edu.uplb.ics.cmsc137.tron2d.object;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXImagePanel.Style;
import org.jdesktop.swingx.JXLabel;
import org.pushingpixels.trident.Timeline;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.Animation;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.TUtils;

public class TButton extends JLabel implements MouseListener{
	protected int xo=0;
	protected int yo=0;
	private JLabel text;
	private JXImagePanel hoveredBG,commonBG,pressedBG;
	
	public TButton(String label,int xi,int yi){
		super();
		xo=xi;
		yo=yi;
		if(label==null) label="";
		text=new JLabel(label);
		text.setBounds(0,0,TConstants.UNSELECTED_BTN_BG.getIconWidth(), TConstants.UNSELECTED_BTN_BG.getIconHeight());
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setFont(TUtils.getFont(TConstants.FONT_BATTLEFIELD3).deriveFont(21f));
		text.setForeground(new Color(18,149,241));
		this.add(text);
		
		pressedBG=new JXImagePanel();
		pressedBG.setBounds(0,0,TConstants.PRESSED_BTN_BG.getIconWidth(), TConstants.PRESSED_BTN_BG.getIconHeight());
		pressedBG.setImage(TConstants.PRESSED_BTN_BG.getImage());
		pressedBG.setStyle(Style.SCALED);
		pressedBG.setOpaque(false);
		pressedBG.setAlpha(0f);
		this.add(pressedBG);
		
		hoveredBG=new JXImagePanel();
		hoveredBG.setBounds(0,0,TConstants.HOVERED_BTN_BG.getIconWidth(), TConstants.HOVERED_BTN_BG.getIconHeight());
		hoveredBG.setImage(TConstants.HOVERED_BTN_BG.getImage());
		hoveredBG.setStyle(Style.SCALED);
		hoveredBG.setOpaque(false);
		hoveredBG.setAlpha(0f);
		this.add(hoveredBG);
		
		commonBG=new JXImagePanel();
		commonBG.setBounds(0,0,TConstants.UNSELECTED_BTN_BG.getIconWidth(), TConstants.UNSELECTED_BTN_BG.getIconHeight());
		commonBG.setImage(TConstants.UNSELECTED_BTN_BG.getImage());
		commonBG.setStyle(Style.SCALED);
		commonBG.setOpaque(false);
		this.add(commonBG);
		
		this.setBounds(xo, yo, TConstants.UNSELECTED_BTN_BG.getIconWidth(), TConstants.UNSELECTED_BTN_BG.getIconHeight());
		this.addMouseListener(this);
	}
	
	public void setLocationX(int newX){
		xo=newX;
		setLocation(xo, yo);
	}
	
	public void setLocationY(int newY){
		yo=newY;
		setLocation(xo, yo);
	}

	public Font getTextFont() {
		return text.getFont();
	}
	
	public void setTextFont(Font font) {
		text.setFont(font);
	}
	
	@Override
	public void setSize(int w,int h){
		super.setSize(w,h);
		hoveredBG.setSize(w,h);
		commonBG.setSize(w,h);
		pressedBG.setSize(w,h);
		text.setSize(w,h);
	}
	
	@Override
	public void setFont(Font font) {
		super.setFont(font);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Thread thread1 = Animation.FadeIn(pressedBG, 100);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Animation.FadeOut(pressedBG, 100);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		text.setForeground(Color.WHITE);
		Thread thread1 = Animation.FadeIn(hoveredBG, 500);
		//Animation.FadeOut(unSelectedIcon, 500);
		//unSelectedIcon.setVisible(false);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		text.setForeground(new Color(18,149,241));
		//unSelectedIcon.setVisible(true);
		//Thread thread1 = Animation.FadeIn(unSelectedIcon, 500);
		Animation.FadeOut(hoveredBG, 1000);
	}
}
