package com.tron2d.prototype.core;

import java.io.Serializable;

class TronPacket implements Serializable{
	FieldLocation lightCycleData;
	FieldLocation lightCyclePath[];
	public TronPacket(FieldLocation d,FieldLocation[] p){
		lightCycleData=d;
		lightCyclePath=p;
	}
}
