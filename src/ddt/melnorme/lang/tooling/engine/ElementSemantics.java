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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import ddt.melnorme.lang.tooling.ast.ILanguageElement;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.LoopDetector.ResolutionLoopException;

/**
 * A class responsible for doing semantic analysis.
 * Each instance is bound to a specific {@link ILanguageElement}.
 * 
 * This class uses the {@link #hashCode()} and {@link #equals()} of Object, such that each instance of 
 * this class can be seperately inserted in a {@link Map}. 
 */
public abstract class ElementSemantics<ER> {
	
	protected final ISemanticContext context;
	
	public ElementSemantics(PickedElement<?> pickedElement) {
		this.context = pickedElement.context;
	}
	
	/* ----------------- Note #equals and #hashCode contract ----------------- */
	
	@Override
	public final boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public final int hashCode() {
		return super.hashCode();
	}
	
	/* ----------------- ----------------- */
	
	private ER resolution;
	private ReentrantLock resolutionMetadataLock = new ReentrantLock();
	private long resolutionThreadId = 0;
	private Condition completionCondition;
	
	protected final ER getElementResolution() {
		return getOrCreateElementResolution();
	}
	
	protected ER getOrCreateElementResolution() {
		
		resolutionMetadataLock.lock();
		try {
			
			if(resolution != null) {
				return resolution;
			}
			
			// First, check if a thread is already analysing this node
			if(resolutionThreadId != 0) {
				awaitCompletionByOtherThread();
				assertNotNull(resolution);
				return resolution;
			}
			
			// If not, the current thread will do the analysis
			
			// First mark, this node as being processed by this thread: 
			resolutionThreadId = Thread.currentThread().getId();
			completionCondition = resolutionMetadataLock.newCondition(); // condition for other threads to wait on.
			
			ER createdResolution;
			
			resolutionMetadataLock.unlock();
			try {
				// perform the computation.
				createdResolution = assertNotNull(createResolution());
			} finally {
				resolutionMetadataLock.lock();
			}
			
			resolution = createdResolution;
			completionCondition.signalAll();
			
		} finally {
			resolutionMetadataLock.unlock();
		}
		return resolution;
	}

	private static LoopDetector loopDetector = new LoopDetector();
	
	private void awaitCompletionByOtherThread() {
		assertTrue(resolutionMetadataLock.isLocked());
		
		long currentThreadId = Thread.currentThread().getId();
		
		try {
			// Mark this thread as waiting on resolutionThreadId
			loopDetector.registerWaitingThread(currentThreadId, resolutionThreadId);
		} catch (ResolutionLoopException e) {
			resolution = createLoopResolution();
			return;
		}
		
		try {
			// await for other thread to complete on the completion condition 
			assertTrue(completionCondition != null);
			completionCondition.awaitUninterruptibly(); // TODO: make interruptable
		} finally {
			loopDetector.unregisterWaitingThread(currentThreadId);
		}
	}
	
	
	// TODO: optimization: put information about a partial result that can be resolved without a context
	// in the ILanguageElement itself. 
	// This way, such information can be re-used a new resolution is created in a different context.
	/** 
	 * Create and return the resolution object for this semantics analysis. Non-null.
	 * The resulting object must also be immutable!
	 */
	protected abstract ER createResolution();
	
	/**
	 * Create a resolution object representing a loop error.
	 * No further node/element analysis can be performed after this!
	 */
	protected abstract ER createLoopResolution();
	
}