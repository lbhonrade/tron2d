package ph.edu.uplb.ics.cmsc137.tron2d.object;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXImagePanel;

import ph.edu.uplb.ics.cmsc137.tron2d.core.MainMenu;
import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.Animation;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.TUtils;



public class DialogContent extends JXImagePanel {

	private static final long serialVersionUID = -6705415591789271983L;
	private JLabel dialogLabel;
	private JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	public DialogContent(String title) {
		setLayout(null);
		setBounds(TConstants.MENU_SCREEN_WIDTH/2-TConstants.DIALOG_BG_TL.getIconWidth(), TConstants.MENU_SCREEN_HEIGHT/2-TConstants.DIALOG_BG_TL.getIconHeight()+50, TConstants.DIALOG_BG_TL.getIconWidth()*2, TConstants.DIALOG_BG_TL.getIconHeight()*2);
		setOpaque(false);
		setAlpha(0f);
		setVisible(false);
		
		TButton backToMenu = new TButton("Go Back to Menu",15,15){
			private static final long serialVersionUID = 6336367965018809553L;

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				DialogBackground.hideDialogBackground();
				TMenuButton.showMenu();
				MainMenu rootParent = (MainMenu)this.getRootPane().getParent();
				Animation.FadeOut(rootParent.dialogContent, 1200);
				rootParent.dialogContent.setVisible(false);
				rootParent.dialogContent.scrollPane.setViewportView(null);
			}
		};
		backToMenu.setTextFont(backToMenu.getTextFont().deriveFont(14f));
		backToMenu.setSize(backToMenu.getWidth(), backToMenu.getHeight()/2);
		add(backToMenu);
		
		dialogLabel = new JLabel(title);
		dialogLabel.setBounds(400, 380, 325, 50);
		dialogLabel.setFont(TUtils.getFont(TConstants.FONT_BATTLEFIELD3).deriveFont(21f));
		dialogLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(dialogLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(25,50,this.getWidth()-50,this.getHeight()-100);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		add(scrollPane);
	}
	
	public void setTitle(String t){
		dialogLabel.setText(t);
	}
	
	public void setContent(JComponent c){
		scrollPane.setViewportView(c);
		c.setOpaque(false);
	}
}
