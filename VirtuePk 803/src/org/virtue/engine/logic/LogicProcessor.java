package org.virtue.engine.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.virtue.Launcher;
import org.virtue.engine.gametick.Tick;
import org.virtue.engine.gametick.TickState;

/**
 * @author Taylor Moon
 * @since Jan 23, 2014
 */
public class LogicProcessor extends Tick {
	
	/**
	 * Represents a list of pending event.
	 */
	private List<LogicEvent> events = Collections.synchronizedList(new ArrayList<LogicEvent>());

	@Override
	public TickState onTick() {
		try {
			synchronized (events) {
				if (events.isEmpty()) {
					events.wait();
					return TickState.ALIVE;
				}
				for (LogicEvent event : events) {
					if (event.singleShotEvent()) {
						Launcher.getEngine().registerLogicEvent(event);
					} else {
						Launcher.getEngine().registerLogicEvent(event, event.getInitialDelay(), event.getIntervalDelay());
					}
				}
				events.clear();
			}
		} catch (Exception e) {
			Launcher.getEngine().handleException(e);
		}
		return TickState.ALIVE;
	}
	
	/**
	 * Registers an event in the logic list.
	 * @param event The event to register.
	 */
	public void registerEvent(LogicEvent event) {
		synchronized (events) {
			events.add(event);
			events.notifyAll();
		}
	}
}
