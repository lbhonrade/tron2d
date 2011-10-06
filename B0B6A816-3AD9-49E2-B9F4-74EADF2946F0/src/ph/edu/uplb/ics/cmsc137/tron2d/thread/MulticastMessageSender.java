package ph.edu.uplb.ics.cmsc137.tron2d.thread;

import org.jgroups.JChannel;
import org.jgroups.Message;

public class MulticastMessageSender extends Thread {
	JChannel channel;
	Message msg;
	public MulticastMessageSender(JChannel c,Message m){
		super();
		channel=c;
		msg=m;
	}
	
	@Override
	public void run(){
		try {
			channel.send(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(){
		this.start();
	}
}
