package ph.edu.uplb.ics.cmsc137.tron2d.utils;

import org.pushingpixels.trident.Timeline;

import ph.edu.uplb.ics.cmsc137.tron2d.data.TConstants;

public class Animation {
	public static Thread FadeIn(final Object obj,final int duration){
		Thread thread = new Thread(){
			Timeline timeline;
			public void run(){
				timeline = new Timeline(obj);
				timeline.addPropertyToInterpolate("alpha", 0f, 1f);
				timeline.setDuration(duration);
				timeline.play();
			}
		};
		thread.start();
		return thread;
	}
	public static Thread FadeOut(final Object obj,final int duration){
		Thread thread = new Thread(){
			Timeline timeline;
			public void run(){
				timeline = new Timeline(obj);
				timeline.addPropertyToInterpolate("alpha", 1f, 0f);
				timeline.setDuration(duration);
				timeline.play();
			}
		};
		thread.start();
		return thread;
	}
}
