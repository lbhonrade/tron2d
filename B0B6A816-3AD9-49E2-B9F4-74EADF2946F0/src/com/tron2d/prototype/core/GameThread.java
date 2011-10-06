package com.tron2d.prototype.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

class GameThread extends Thread {
	private AppGameContainer app;
	public GameThread(AppGameContainer p){
		app=p;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
