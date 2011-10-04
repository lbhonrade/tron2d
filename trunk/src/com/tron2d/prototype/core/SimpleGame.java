package com.tron2d.prototype.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

public class SimpleGame extends BasicGame {
	Image lightcycle = null,vWall = null,hWall = null,elbow[] = null;
	public float x = 400;
	public float y = 300;
	public float width, height;
	float scale = 0.1f;
	public List<FieldLocation> path = new LinkedList<FieldLocation>();
	public List<FieldLocation> updateQueue = new LinkedList<FieldLocation>();
	public boolean isPlaying = false;
	private float screenRotation=0;
	private float  elbow_scale=0.555f;//elbow_scale=0.2f;
	private Color playerColor=Color.green;
	
	public SimpleGame(String title)throws SlickException{
		super(title);
		// TODO Auto-generated constructor stub
	}

	private void drawWall(Graphics g,FieldLocation a,FieldLocation b){
		Line l=new Line(a.x,a.y,b.x,b.y);
		Polygon wall = null;
		if(l.getDX()==0){
			wall=new Polygon(new float[]{a.x-5f,a.y,a.x+5f,a.y,b.x+5f,b.y,b.x-5f,b.y});
			g.texture(wall, vWall, true);
		}else{
			wall=new Polygon(new float[]{a.x,a.y-5f,a.x,a.y+5f,b.x,b.y+5f,b.x,b.y-5f});
			g.texture(wall, hWall, true);
		}
	}
	
	private void drawElbow(Graphics g,FieldLocation a,FieldLocation b,FieldLocation c){
		int dX1=c.x-a.x<0?1:0;
		int dY1=c.y-a.y<0?1:0;
		Line ab=new Line(a.x,a.y,b.x,b.y);
		Line bc=new Line(b.x,b.y,c.x,c.y);
		int Dab=ab.getDX()==0?0:1;
		int Dbc=bc.getDX()==0?0:1;
		int classifier=dX1*1000+dY1*100+Dab*10+Dbc;
		int type=0;
		switch(classifier){
			case 1110:case 1://14 1
				type=0;
				break;
			case 1101:case 10://13 2
				type=2;
				break;
			case 101:case 1010://5 10
				type=1;
				break;
			case 110:case 1001://6 9
				type=3;
				break;
		}
		g.drawImage(elbow[type],b.x-elbow[type].getWidth()/2.0f,b.y-elbow[type].getHeight()/2.0f,playerColor);
		/*
		1110
		1101
		 101
		 110
		1010
		1001
		   1
		  10
		  */
	}
	@Override
	public void render(GameContainer arg0, Graphics graphics)
			throws SlickException {
		// TODO Auto-generated method stub
		graphics.setBackground(Color.black);
		graphics.setColor(Color.white);
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				graphics.fillRect((x * 100), (y * 100), 80, 80);
			}
		}
		graphics.setColor(playerColor);
		for (int i = 1; i < path.size(); i++) {
			drawWall(graphics,path.get(i - 1),path.get(i));
			if(i>1&&i<path.size())
				drawElbow(graphics,path.get(i-2),path.get(i-1),path.get(i));
		}
		drawWall(graphics,path.get(path.size()-1),new FieldLocation(x+lightcycle.getCenterOfRotationX(),y+lightcycle.getCenterOfRotationY()));
		if(path.size()>1){
			drawElbow(graphics,path.get(path.size()-2),path.get(path.size()-1),new FieldLocation(x+lightcycle.getCenterOfRotationX(),y+lightcycle.getCenterOfRotationY()));
		}
		Image scaledLightCycle=lightcycle.getScaledCopy(scale);
		scaledLightCycle.rotate(lightcycle.getRotation());
		graphics.drawImage(scaledLightCycle, x, y, playerColor);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		lightcycle = new Image("graphics/lightcycle-topview.png");
		lightcycle.setCenterOfRotation(lightcycle.getWidth() / 2.0f * scale,
				lightcycle.getHeight() / 2.0f * scale);
		width = lightcycle.getWidth() * scale;
		height = lightcycle.getHeight() * scale;
		path.add(new FieldLocation(x + lightcycle.getCenterOfRotationX(), y + lightcycle.getCenterOfRotationY()));
		hWall = new Image("graphics/hWall.png");
		vWall = new Image("graphics/vWall.png");
		elbow=new Image[4];
		for(int i=0;i<4;i++){
			elbow[i]=new Image("graphics/elbow_"+i+".png");
			elbow[i]=elbow[i].getScaledCopy(elbow_scale);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		if (isPlaying) {
			float hip = 0.4f * delta;

			float rotation = lightcycle.getRotation();

			/*
			 * For arc turning 
			   double prevX=x+lightcycle.getCenterOfRotationX()-Math.sin(Math.toRadians(rotation))*(height/2);
			   double prevY=y+lightcycle.getCenterOfRotationY()+Math.cos(Math.toRadians(rotation))*(height/2);
			 
			/*
			 * For sharp turning double
			 * prevX=x+lightcycle.getCenterOfRotationX(); double
			 * prevY=y+lightcycle.getCenterOfRotationY(); //
			 */
			 //path.add(new FieldLocation((float)prevX,(float)prevY));
			x += hip * Math.sin(Math.toRadians(rotation));
			y -= hip * Math.cos(Math.toRadians(rotation));
		}

		if (input.isKeyPressed(Input.KEY_W)) {
			isPlaying = !isPlaying;
		}

		if (input.isKeyPressed(Input.KEY_A)) {
			FieldLocation fl=new FieldLocation(x + lightcycle.getCenterOfRotationX(), y + lightcycle.getCenterOfRotationY(),path.size());
			path.add(fl);
			updateQueue.add(fl);
			lightcycle.rotate(-90.0f);
		}

		if (input.isKeyPressed(Input.KEY_D)) {
			FieldLocation fl=new FieldLocation(x + lightcycle.getCenterOfRotationX(), y + lightcycle.getCenterOfRotationY(),path.size());
			path.add(fl);
			updateQueue.add(fl);
			lightcycle.rotate(90.0f);
		}

		if (input.isKeyDown(Input.KEY_2)) {
			scale += (scale >= 0.4f) ? 0 : 0.05f;
			lightcycle.setCenterOfRotation(
					lightcycle.getWidth() / 2.0f * scale,
					lightcycle.getHeight() / 2.0f * scale);
		}

		if (input.isKeyDown(Input.KEY_1)) {
			scale -= (scale < 0.25f) ? 0 : 0.05f;
			lightcycle.setCenterOfRotation(
					lightcycle.getWidth() / 2.0f * scale,
					lightcycle.getHeight() / 2.0f * scale);
		}
	}

}
