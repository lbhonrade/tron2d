package ph.edu.uplb.ics.cmsc137.tron2d.core;


import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXLabel.TextAlignment;
import org.newdawn.slick.SlickException;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;
import ph.edu.uplb.ics.cmsc137.tron2d.object.DialogBackground;
import ph.edu.uplb.ics.cmsc137.tron2d.object.DialogContent;
import ph.edu.uplb.ics.cmsc137.tron2d.object.TMenuButton;

public class MainMenu extends JFrame implements MouseListener{
	private static final long serialVersionUID = -2584582823913793380L;
	public DialogContent dialogContent;
	/**
	 * Launch the application.
	 * @throws Exception
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException, Exception {
		//System.setProperty("jgroups.bind_addr", "10.0.0.7");
		System.setProperty("java.library.path", "natives/windows");
		
		Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
		fieldSysPath.setAccessible( true );
		fieldSysPath.set( null, null );
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
	
	/**
	 * Create the frame.
	 * @throws Exception 
	 * @throws SlickException 
	 */
	public MainMenu() throws SlickException, Exception {
		setResizable(false);
		setBounds(0, 0, TConstants.MENU_SCREEN_WIDTH, TConstants.MENU_SCREEN_HEIGHT);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
		dialogContent = new DialogContent("");
		this.getContentPane().add(dialogContent);
		
		this.getContentPane().add(DialogBackground.tr);
		this.getContentPane().add(DialogBackground.tl);
		this.getContentPane().add(DialogBackground.bl);
		this.getContentPane().add(DialogBackground.br);
		
		TMenuButton playBtn = new TMenuButton("PLAY NOW!", TConstants.MENU_SCREEN_WIDTH/2-TConstants.UNSELECTED_BTN_BG.getIconWidth(), TConstants.MENU_SCREEN_HEIGHT/2-TConstants.UNSELECTED_BTN_BG.getIconHeight(),TConstants.MENU_SCREEN_WIDTH,-1*TConstants.UNSELECTED_BTN_BG.getIconHeight()){
			private static final long serialVersionUID = -1344437815898787850L;

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				MainMenu main = (MainMenu)this.getRootPane().getParent();
				main.dialogContent.setTitle("Gaming Room");
			}
		};
		TMenuButton settingsBtn = new TMenuButton("SETTINGS", TConstants.MENU_SCREEN_WIDTH/2, TConstants.MENU_SCREEN_HEIGHT/2-TConstants.UNSELECTED_BTN_BG.getIconHeight()-14,-1*TConstants.UNSELECTED_BTN_BG.getIconWidth(),-1*TConstants.UNSELECTED_BTN_BG.getIconHeight()){
			private static final long serialVersionUID = 7424850087775595855L;

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				MainMenu main = (MainMenu)this.getRootPane().getParent();
				main.dialogContent.setTitle("Configuration");
			}
		};
		TMenuButton aboutBtn = new TMenuButton("ABOUT", TConstants.MENU_SCREEN_WIDTH/2-18, TConstants.MENU_SCREEN_HEIGHT/2-14,-1*TConstants.UNSELECTED_BTN_BG.getIconWidth(),TConstants.MENU_SCREEN_HEIGHT){
			private static final long serialVersionUID = 6031153029268646340L;

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				MainMenu main = (MainMenu)this.getRootPane().getParent();
				main.dialogContent.setTitle("About");
			}
		};
		TMenuButton instructionsBtn = new TMenuButton("INSTRUCTIONS", TConstants.MENU_SCREEN_WIDTH/2-TConstants.UNSELECTED_BTN_BG.getIconWidth()-18, TConstants.MENU_SCREEN_HEIGHT/2,TConstants.MENU_SCREEN_WIDTH,TConstants.MENU_SCREEN_HEIGHT){
			private static final long serialVersionUID = 5671309400034254341L;

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				MainMenu main = (MainMenu)this.getRootPane().getParent();
				main.dialogContent.setTitle("Instructions");

				JXLabel instructionContent = new JXLabel();
				instructionContent.setTextAlignment(TextAlignment.JUSTIFY);
				instructionContent.setLineWrap(true);
				instructionContent.setText("Basic networking\r\n\r\nThe server simulates the game in discrete time steps called ticks. By default, the timestep is 15ms, so 66.666... ticks per second are simulated, but mods can specify their own tickrate. During each tick, the server processes incoming user commands, runs a physical simulation step, checks the game rules, and updates all object states. After simulating a tick, the server decides if any client needs a world update and takes a snapshot of the current world state if necessary. A higher tickrate increases the simulation precision, but also requires more CPU power and available bandwidth on both server and client. The server admin may override the default tickrate with the -tickrate command line parameter, though tickrate changes done this way are not recommended because the mod may not work as designed if its tickrate is changed.\r\n Note:\tThe -tickrate command line parameter is not available on CSS, TF2, L4D and L4D2 because changing tickrate causes server timing issues. The tickrate is set to 66 in CSS and TF2, and 30 in L4D and L4D2.\r\nClients usually have only a limited amount of available bandwidth. In the worst case, players with a modem connection can't receive more than 5 to 7 KB/sec. If the server tried to send them updates with a higher data rate, packet loss would be unavoidable. Therefore, the client has to tell the server its incoming bandwidth capacity by setting the console variable rate (in bytes/second). This is the most important network variable for clients and it has to be set correctly for an optimal gameplay experience. The client can request a certain snapshot rate by changing cl_updaterate (default 20), but the server will never send more updates than simulated ticks or exceed the requested client rate limit. Server admins can limit data rate values requested by clients with sv_minrate and sv_maxrate (both in bytes/second). Also the snapshot rate can be restricted with sv_minupdaterate and sv_maxupdaterate (both in snapshots/second).\r\nThe client creates user commands from sampling input devices with the same tick rate that the server is running with. A user command is basically a snapshot of the current keyboard and mouse state. But instead of sending a new packet to the server for each user command, the client sends command packets at a certain rate of packets per second (usually 30). This means two or more user commands are transmitted within the same packet. Clients can increase the command rate with cl_cmdrate. This will increase responsiveness but requires more outgoing bandwidth, too.\r\nGame data is compressed using delta compression to reduce network load. That means the server doesn't send a full world snapshot each time, but rather only changes (a delta snapshot) that happened since the last acknowledged update. With each packet sent between the client and server, acknowledge numbers are attached to keep track of their data flow. Usually full (non-delta) snapshots are only sent when a game starts or a client suffers from heavy packet loss for a couple of seconds. Clients can request a full snapshot manually with the cl_fullupdate command.\r\nResponsiveness, or the time between user input and its visible feedback in the game world, are determined by lots of factors, including the server/client CPU load, simulation tickrate, data rate and snapshot update settings, but mostly by the network packet traveling time. The time between the client sending a user command, the server responding to it, and the client receiving the server's response is called the latency or ping (or round trip time). Low latency is a significant advantage when playing a multiplayer online game. Techniques like prediction and lag compensation try to minimize that advantage and allow a fair game for players with slower connections. Tweaking networking setting can help to gain a better experience if the necessary bandwidth and CPU power is available. We recommend keeping the default settings, since improper changes may cause more negative side effects than actual benefits.\r\nEntity interpolation\r\n\r\nBy default, the client receives about 20 snapshot per second. If the objects (entities) in the world were only rendered at the positions received by the server, moving objects and animation would look choppy and jittery. Dropped packets would also cause noticeable glitches. The trick to solve this problem is to go back in time for rendering, so positions and animations can be continuously interpolated between two recently received snapshot. With 20 snapshots per second, a new update arrives about every 50 milliseconds. If the client render time is shifted back by 50 milliseconds, entities can be always interpolated between the last received snapshot and the snapshot before that.\r\nSource defaults to an interpolation period ('lerp') of 100-milliseconds (cl_interp 0.1); this way, even if one snapshot is lost, there are always two valid snapshots to interpolate between. Take a look at the following figure showing the arrival times of incoming world snapshots:\r\n\r\nThe last snapshot received on the client was at tick 344 or 10.30 seconds. The client time continues to increase based on this snapshot and the client frame rate. If a new video frame is rendered, the rendering time is the current client time 10.32 minus the view interpolation delay of 0.1 seconds. This would be 10.22 in our example and all entities and their animations are interpolated using the correct fraction between snapshot 340 and 342.\r\nSince we have an interpolation delay of 100 milliseconds, the interpolation would even work if snapshot 342 were missing due to packet loss. Then the interpolation could use snapshots 340 and 344. If more than one snapshot in a row is dropped, interpolation can't work perfectly because it runs out of snapshots in the history buffer. In that case the renderer uses extrapolation (cl_extrapolate 1) and tries a simple linear extrapolation of entities based on their known history so far. The extrapolation is done only for 0.25 seconds of packet loss (cl_extrapolate_amount), since the prediction errors would become too big after that.\r\nEntity interpolation causes a constant view \"lag\" of 100 milliseconds by default (cl_interp 0.1), even if you're playing on a listenserver (server and client on the same machine). This doesn't mean you have to lead your aiming when shooting at other players since the server-side lag compensation knows about client entity interpolation and corrects this error.\r\n Tip:\tMore recent Source games have the cl_interp_ratio cvar. With this you can easily and safely decrease the interpolation period by setting cl_interp to 0, then increasing the value of cl_updaterate (the useful limit of which depends on server tickrate). You can check your final lerp with net_graph 1.\r\n Note:\tIf you turn on sv_showhitboxes (not available in Source 2009) you will see player hitboxes drawn in server time, meaning they are ahead of the rendered player model by the lerp period. This is perfectly normal!\r\nInput prediction\r\n\r\nLets assume a player has a network latency of 150 milliseconds and starts to move forward. The information that the +FORWARD key is pressed is stored in a user command and send to the server. There the user command is processed by the movement code and the player's character is moved forward in the game world. This world state change is transmitted to all clients with the next snapshot update. So the player would see his own change of movement with a 150 milliseconds delay after he started walking. This delay applies to all players actions like movement, shooting weapons, etc. and becomes worse with higher latencies.\r\nA delay between player input and corresponding visual feedback creates a strange, unnatural feeling and makes it hard to move or aim precisely. Client-side input prediction (cl_predict 1) is a way to remove this delay and let the player's actions feel more instant. Instead of waiting for the server to update your own position, the local client just predicts the results of its own user commands. Therefore, the client runs exactly the same code and rules the server will use to process the user commands. After the prediction is finished, the local player will move instantly to the new location while the server still sees him at the old place.\r\nAfter 150 milliseconds, the client will receive the server snapshot that contains the changes based on the user command he predicted earlier. Then the client compares the server position with his predicted position. If they are different, a prediction error has occurred. This indicates that the client didn't have the correct information about other entities and the environment when it processed the user command. Then the client has to correct its own position, since the server has final authority over client-side prediction. If cl_showerror 1 is turned on, clients can see when prediction errors happen. Prediction error correction can be quite noticeable and may cause the client's view to jump erratically. By gradually correcting this error over a short amount of time (cl_smoothtime), errors can be smoothly corrected. Prediction error smoothing can be turned off with cl_smooth 0.\r\nPredicting an object's behavior only works if the clients knows the same rules and state of the object as the server. That's usually not the case since the server knows more internal information about objects than the clients do. Clients see only a small part of the world and just get enough information to render objects. Therefore, prediction works only for your own player, and the weapons controlled by you. Proper prediction of other players or interactive objects is not possible on the client at this point.\r\nLag compensation\r\n\r\nAll source code for lag compensation and view interpolation is available in the Source SDK. See Lag compensation for implementation details.\r\nLet's say a player shoots at a target at client time 10.5. The firing information is packed into a user command and sent to the server. While the packet is on its way through the network, the server continues to simulate the world, and the target might have moved to a different position. The user command arrives at server time 10.6 and the server wouldn't detect the hit, even though the player has aimed exactly at the target. This error is corrected by the server-side lag compensation.\r\nThe lag compensation system keeps a history of all recent player positions for one second. If a user command is executed, the server estimates at what time the command was created as follows:\r\nCommand Execution Time = Current Server Time - Packet Latency - Client View Interpolation\r\nThen the server moves all other players - only players - back to where they were at the command execution time. The user command is executed and the hit is detected correctly. After the user command has been processed, the players revert to their original positions.\r\n Note:\tSince entity interpolation is included in the equation, failing to have it on can cause undesired results.\r\nOn a listen server you can enable sv_showimpacts 1 to see the different server and client hitboxes:\r\n\r\nThis screenshot was taken on a listen server with 200 milliseconds of lag (using net_fakelag), right after the server confirmed the hit. The red hitbox shows the target position on the client where it was 100ms + interp period ago. Since then, the target continued to move to the left while the user command was travelling to the server. After the user command arrived, the server restored the target position (blue hitbox) based on the estimated command execution time. The server traces the shot and confirms the hit (the client sees blood effects).\r\nClient and server hitboxes don't exactly match because of small precision errors in time measurement. Even a small difference of a few milliseconds can cause an error of several inches for fast-moving objects. Multiplayer hit detection is not pixel perfect and has known precision limitations based on the tickrate and the speed of moving objects. Increasing the tickrate does improve the precision of hit detection, but also requires more CPU, memory, and bandwidth capacity for server and clients.\r\nThe question arises, why is hit detection so complicated on the server? Doing the back tracking of player positions and dealing with precision errors while hit detection could be done client-side way easier and with pixel precision. The client would just tell the server with a \"hit\" message what player has been hit and where. We can't allow that simply because a game server can't trust the clients on such important decisions. Even if the client is \"clean\" and protected by Valve Anti-Cheat, the packets could be still modified on a 3rd machine while routed to the game server. These \"cheat proxies\" could inject \"hit\" messages into the network packet without being detected by VAC (a \"man-in-the-middle\" attack).\r\nNetwork latencies and lag compensation can create paradoxes that seem illogical compared to the real world. For example, you can be hit by an attacker you can't even see anymore because you already took cover. What happened is that the server moved your player hitboxes back in time, where you were still exposed to your attacker. This inconsistency problem can't be solved in general because of the relatively slow packet speeds. In the real world, you don't notice this problem because light (the packets) travels so fast and you and everybody around you sees the same world as it is right now.");
				main.dialogContent.setContent(instructionContent);
			}
		};
		
		this.getContentPane().add(playBtn);
		this.getContentPane().add(settingsBtn);
		this.getContentPane().add(instructionsBtn);
		this.getContentPane().add(aboutBtn);
		
		JLabel bgLabel = new JLabel("");
		bgLabel.setIcon(new ImageIcon("graphics/menubg.png"));
		bgLabel.setBounds(0, 0, TConstants.MENU_SCREEN_WIDTH, TConstants.MENU_SCREEN_HEIGHT);
		getContentPane().add(bgLabel);
		
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
