package org.styl.gravitus.engine;

import java.util.ArrayDeque;
import java.util.Deque;

import org.styl.gravitus.Specs;

public class Clock {

	public static final Clock INSTANCE = new Clock();
	
	private long prevTime = 0L;
	private Deque<Long> fpsBuffer = new ArrayDeque<>(100);
	
		
	private Clock() { }
	
	public int t() {
		return Specs.instance.clockInterval;
	}
	
	public int fps() {
		
		if(fpsBuffer.size() == 100)
			fpsBuffer.removeLast();
		
		long time = System.currentTimeMillis();
		if (time != prevTime) {
			fpsBuffer.addFirst(1000/(time - prevTime));
		}
		prevTime = time;
		
		return (int) (fpsBuffer.stream()
						.mapToDouble(a -> a)
						.average()
						.getAsDouble());
	}

	public void delay() {
		try {
			Thread.sleep( t() );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
