package ph.edu.uplb.ics.cmsc137.tron2d.object;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.pushingpixels.trident.Timeline;

import ph.edu.uplb.ics.cmsc137.tron2d.core.MainMenu;
import ph.edu.uplb.ics.cmsc137.tron2d.utils.Animation;

public class TMenuButton extends TButton {
	private static final long serialVersionUID = 6659834109006929125L;
	private final int extX, extY;
	private static ArrayList<TMenuButton> instances = new ArrayList<TMenuButton>();
	private Timeline showHideAnimation;

	public TMenuButton(String label, int nX, int nY, int xX, int xY) {
		super(label, nX, nY + 50);
		extX = xX;
		extY = xY;
		instances.add(this);
		this.setLocation(extX, extY);

		showHideAnimation = new Timeline(this);
		showHideAnimation.addPropertyToInterpolate("locationX", extX, xo);
		showHideAnimation.addPropertyToInterpolate("locationY", extY, yo);
		showHideAnimation.setDuration(300);
		showHideAnimation.setInitialDelay(150 * instances.indexOf(this));

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);

		TMenuButton.hideMenu();
		DialogBackground.showDialogBackground();
		MainMenu rootParent = (MainMenu) this.getRootPane().getParent();
		rootParent.dialogContent.setVisible(true);
		Animation.FadeIn(rootParent.dialogContent, 1200);

	}

	public static void hideMenu() {
		new Sound("audio/menu-dialog-transition.wav").start();
		for (TMenuButton menuBtn : instances) {
			menuBtn.showHideAnimation.playReverse();
		}
	}

	public static void showMenu() {
		new Sound("audio/menu-dialog-transition.wav").start();
		for (TMenuButton menuBtn : instances) {
			menuBtn.showHideAnimation.play();
		}
	}
}
