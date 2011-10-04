package com.tron2d.prototype.core;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;
import org.newdawn.slick.AppGameContainer;

import com.google.gson.Gson;
//import com.google.gson.*;

import java.io.*;
import java.util.List;
import java.util.LinkedList;

public class Tron2D extends ReceiverAdapter {
	JChannel channel;
	String user_name = System.getProperty("user.name", "n/a"), appAddress;
	private AppGameContainer app;
	private SimpleGame game;
	private Gson parser = new Gson();
	private float prevX = -1, prevY = -1;
	final List<FieldLocation> state = new LinkedList<FieldLocation>();

	public Tron2D() {
		super();
	}

	public void viewAccepted(View new_view) {
		System.out.println("** view: " + new_view);
	}

	public void receive(Message msg) {
		if (msg.getSrc().toString()==appAddress)
			return;
		String jsonString = (String) msg.getObject();
		System.out.println(msg.getSrc()+":"+jsonString);
		TronPacket obj = parser.fromJson(jsonString, TronPacket.class);
		//TronPacket obj = (TronPacket)msg.getObject();
		game.x = obj.lightCycleData.x;
		game.y = obj.lightCycleData.y;
		game.lightcycle.setRotation(obj.lightCycleData.rotation);
		for(FieldLocation fl:obj.lightCyclePath){
			game.path.add(fl.index, fl);
		}
	}

	private void start() throws Exception {
		// Establish Connection
		channel = new JChannel(new File("udpConfig.xml"));
		channel.setReceiver(this);
		channel.connect("GameCluster");
		channel.getState(null, 10000);
		appAddress = channel.getAddressAsString();
		System.out.println(appAddress);
		// Initialize Graphics

		game = new SimpleGame("Tron");
		app = new AppGameContainer(game);
		app.setDisplayMode(1024, 748, false);
		GameThread ui = new GameThread(app);
		ui.start();
		System.out.println("Graphics Initialized.");
		eventLoop();
		channel.close();
	}

	private void eventLoop() {
		String data;
		while (true) {
			try {
				if ((!game.isPlaying)||game.x == prevX && game.y == prevY)
					continue;
				TronPacket tpacket=new TronPacket(new FieldLocation(game.x, game.y,game.lightcycle.getRotation()),game.updateQueue.toArray(new FieldLocation[game.updateQueue.size()]));
				//data = parser.toJson(tpacket);
				game.updateQueue.clear();
				Message msg = new Message(null, null, tpacket);
				channel.send(msg);
				prevX = game.x;
				prevY = game.y;
				// System.out.println("Sent:"+data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new Tron2D().start();
	}
}