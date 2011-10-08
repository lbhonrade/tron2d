package ph.edu.uplb.ics.cmsc137.tron2d.design;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jdesktop.swingx.JXPanel;
import org.pushingpixels.trident.Timeline;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.object.TButton;
import ph.edu.uplb.ics.cmsc137.tron2d.object.TMenuButton;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.TUtils;

public class MainMenu extends JFrame implements MouseListener{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MainMenu frame = new MainMenu();
	}
	
	private Timeline exitTween;
	/**
	 * Create the frame.
	 */
	public MainMenu() {
		setResizable(false);
		setBounds(0, 0, TConstants.MENU_SCREEN_WIDTH, TConstants.MENU_SCREEN_HEIGHT);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		JLabel bgLabel = new JLabel("");
		bgLabel.setIcon(new ImageIcon("graphics/menubg.png"));
		bgLabel.setBounds(0, 0, TConstants.MENU_SCREEN_WIDTH, TConstants.MENU_SCREEN_HEIGHT);
		getContentPane().add(bgLabel);
		
		TMenuButton createBtn = new TMenuButton("CREATE GAME", TConstants.MENU_SCREEN_WIDTH/2-TConstants.UNSELECTED_BTN_BG.getIconWidth(), TConstants.MENU_SCREEN_HEIGHT/2-TConstants.UNSELECTED_BTN_BG.getIconHeight(),-1*TConstants.UNSELECTED_BTN_BG.getIconWidth(),-1*TConstants.UNSELECTED_BTN_BG.getIconHeight());
		TMenuButton joinBtn = new TMenuButton("JOIN GAME", TConstants.MENU_SCREEN_WIDTH/2, TConstants.MENU_SCREEN_HEIGHT/2-TConstants.UNSELECTED_BTN_BG.getIconHeight()-14,TConstants.MENU_SCREEN_WIDTH,-1*TConstants.UNSELECTED_BTN_BG.getIconHeight());
		TMenuButton settingsBtn = new TMenuButton("SETTINGS", TConstants.MENU_SCREEN_WIDTH/2-18, TConstants.MENU_SCREEN_HEIGHT/2-14,TConstants.MENU_SCREEN_WIDTH,TConstants.MENU_SCREEN_HEIGHT);
		TMenuButton observeBtn = new TMenuButton("OBSERVE GAME", TConstants.MENU_SCREEN_WIDTH/2-TConstants.UNSELECTED_BTN_BG.getIconWidth()-18, TConstants.MENU_SCREEN_HEIGHT/2,-1*TConstants.UNSELECTED_BTN_BG.getIconWidth(),TConstants.MENU_SCREEN_HEIGHT);
		
		this.getContentPane().add(createBtn,0);
		this.getContentPane().add(joinBtn,0);
		this.getContentPane().add(observeBtn,0);
		this.getContentPane().add(settingsBtn,0);
		
		this.update(this.getGraphics());
		
		TMenuButton.showMenu();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
