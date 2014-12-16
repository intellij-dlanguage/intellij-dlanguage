/*******************************************************************************
 * Copyright (c) 2012, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.util;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.text.MessageFormat;
import java.util.HashMap;

public class TimePerfUtil {
	
	public static final HashMap<String, TimePerfUtil> trackers = new HashMap<String, TimePerfUtil>();
	
	public static TimePerfUtil get(String id) {
		if(trackers.containsKey(id) == false) {
			trackers.put(id, new TimePerfUtil(id));
		}
		return trackers.get(id);
	}
	
	public static void resetAll() {
		for (TimePerfUtil tpu : trackers.values()) {
			tpu.reset();
		}
	}
	
	public final String id;
	public long startTime = -1;
	public long totalTime = 0;
	
	public TimePerfUtil(String id) {
		this.id = id;
	}
	
	public void start() {
		assertTrue(startTime == -1);
		startTime = System.nanoTime();
	}
	
	public void reset() {
		totalTime = 0;
		startTime = -1;
	}
	
	public void stop() {
		assertTrue(startTime != -1);
		long delta = System.nanoTime() - startTime;
		assertTrue(delta > 0);
		totalTime += delta;
		startTime = -1;
	}
	
	public TimePerfUtil printTotalTime() {
		System.out.println(MessageFormat.format("Timetotal ({1}) : {0,number,00000,000 µs}", totalTime / 1000, id));
		return this;
	}
	
}