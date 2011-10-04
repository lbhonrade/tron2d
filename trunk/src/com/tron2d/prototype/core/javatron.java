package com.tron2d.prototype.core;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class TronTimer extends TimerTask
{
  TronBoard tb;
  public void run() {
    for(int i=0;i<tb.troncount;i++)
      tb.tron[i].step(tb.getGraphics());
  }
  TronTimer(TronBoard tb1)
  {
    tb=tb1;
  }
}
class TronBoard extends Canvas
{
  int board [][];
  int fwidth;
  int fheight;
  int troncount;
  Tron tron [];
  int state;
  public static final int GAME_RUNNING=0;
  public static final int GAME_OVER=1;
  TronBoard(int nwidth, int nheight)
  {
    super();
    board=new int[nheight][];
    for(int i=0;i<nheight;i++)
    {
      board[i]=new int[nwidth];
    }
    for(int i=0;i<nheight;i++)
      for(int j=0;j<nwidth;j++)
        board[i][j]=0;
    setBounds(10,50,nwidth*10+1,nheight*10+1);
    setBackground(Color.black);
    fwidth=nwidth; 
    fheight=nheight;
    troncount=1;
    tron=new Tron[troncount];
    tron[0]=new Tron(5,5,Tron.DOWN,this); 
    addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e)
      {
        if (state==TronBoard.GAME_RUNNING)
        {
          switch (e.getKeyCode()) 
          {
            case KeyEvent.VK_DOWN:tron[0].dir=Tron.DOWN;break;
            case KeyEvent.VK_UP:tron[0].dir=Tron.UP;break;
            case KeyEvent.VK_LEFT:tron[0].dir=Tron.LEFT;break;
            case KeyEvent.VK_RIGHT:tron[0].dir=Tron.RIGHT;break;
          }
        }  
        //System.out.print("key pressed:");
        //System.out.println(Integer.toString(tron[0].dir));
      }
    });
    
    state=GAME_RUNNING;
    Timer timer=new Timer();
    timer.schedule(new TronTimer(this),250,250);
    
  }
  public void drawCell(Graphics g,int i,int j)
  {
    switch (board[i][j])
    {
      case 0:break;
      case 1:g.setColor(Color.red); g.fillRect(j*10,i*10,10,10);  
    }
    
  }
  public void paint(Graphics g)
  {
    for(int i=0;i<fheight;i++)
    {
      for(int j=0;j<fwidth;j++)
      {
        drawCell(g,i,j);
      }
    }
  }
}

class Tron
{
  int headx;
  int heady;
  public int dir;
  public static int DOWN=0;
  public static int LEFT=1;
  public static int UP=2;
  public static int RIGHT=3;
  TronBoard tb;
  Tron(int startx,int starty,int startdir,TronBoard starttb)
  {
    headx=startx;
    heady=starty;
    dir=startdir;
    tb=starttb;
  }
  public void step(Graphics g)
  {
    int tx=headx,ty=heady;
    switch (dir)
    {
      case 0:heady++;break;//down
      case 1:headx--;break;//left
      case 2:heady--;break;//up
      case 3:headx++;break;//right
    }
    
    if ( (headx<0)||(heady<0)||(headx==tb.fwidth)||(heady==tb.fheight)||
          (tb.board[heady][headx]==1) )
    {
      headx=tx;
      heady=ty;
      tb.state=TronBoard.GAME_OVER;
    }
    else
    {
      tb.board[heady][headx]=1;
      tb.drawCell(g,heady,headx);
    }
  }
}

class JavaTron extends Frame
{
  TronBoard tb;
  JavaTron(String s)
  {
    super(s);
    setSize(640,480);
    setVisible(true);
    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent ev){
        System.exit(0);
      }
    });
    tb=new TronBoard(60,40);
    add(tb);
    tb.setVisible(true);
    tb.repaint();
  }
  
  /*public static void main(String[] args)
  {
    Frame f=new JavaTron("JavaTron Game");
  }*/

    
}