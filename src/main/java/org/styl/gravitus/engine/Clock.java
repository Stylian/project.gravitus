package org.styl.gravitus.engine;

import java.util.ArrayDeque;
import java.util.Deque;

import org.styl.gravitus.Specs;

public class Clock {

	private static Clock instance = new Clock();
	
	private long prevTime = 0L;
	private Deque<Long> fps = new ArrayDeque<>(100);
	
	public static int t() {
		return Clock.getInstance().time();
	}
	
	private Clock() { }
	
	public static Clock getInstance() {
		return instance;
	}
	
	public int time() {
		return Specs.instance.clockInterval;
	}
	
	public int fps() {
		
		if(fps.size() == 100)
			fps.removeLast();
		
		long time = System.currentTimeMillis();
		if (time != prevTime) {
			fps.addFirst(1000/(time - prevTime));
		}
		prevTime = time;
		
		return (int) (fps.stream()
						.mapToDouble(a -> a)
						.average()
						.getAsDouble());
	}
	
}
