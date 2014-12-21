/*******************************************************************************
 * Copyright (c) 2014, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.engine;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.HashMap;

public class LoopDetector {
	
	// This map forms a tree, each thread id is a node.
	private HashMap<Long, Long> waitingThreads = new HashMap<>();
	
	public synchronized void registerWaitingThread(long currentThreadId, long otherThreadId) 
			throws ResolutionLoopException {
		
		assertTrue(waitingThreads.containsKey(currentThreadId) == false);
		
		checkForLoop(currentThreadId, otherThreadId);
		
		waitingThreads.put(currentThreadId, otherThreadId);
	}

	private void checkForLoop(long currentThreadId, long otherThreadId) throws ResolutionLoopException {
		if(currentThreadId == otherThreadId) {
			throw new ResolutionLoopException();
		}
		
		Long nextId = waitingThreads.get(otherThreadId);
		if(nextId != null) {
			checkForLoop(currentThreadId, nextId);
		}
	}
	
	public synchronized void unregisterWaitingThread(long currentThreadId) {
		assertTrue(waitingThreads.containsKey(currentThreadId));
		waitingThreads.remove(currentThreadId);
	}
	
	@SuppressWarnings("serial")
	public static class ResolutionLoopException extends Throwable {
		
	}
	
}