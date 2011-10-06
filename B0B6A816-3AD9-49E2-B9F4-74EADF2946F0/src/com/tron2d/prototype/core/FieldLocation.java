package com.tron2d.prototype.core;

import java.io.Serializable;

class FieldLocation implements Serializable{
	public float x,y,rotation;
	public int index;
	public FieldLocation(float a,float b){
		x=a;
		y=b;
		rotation=0;
	}
	public FieldLocation(float a,float b,float c){
		x=a;
		y=b;
		rotation=c;
	}
	public FieldLocation(float a,float b,int d){
		x=a;
		y=b;
		index=d;
	}
	public FieldLocation(float a,float b,float c,int d){
		x=a;
		y=b;
		rotation=c;
		index=d;
	}
}
