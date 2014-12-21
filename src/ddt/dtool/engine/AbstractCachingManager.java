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
package ddt.dtool.engine;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.utilbox.core.CommonException;
import ddt.dtool.engine.util.CachingRegistry;


public abstract class AbstractCachingManager<KEY, VALUE, UPDATE_OPTIONS> {
	
	public AbstractCachingManager() {
	}
	
	protected final CachingRegistry<KEY, VALUE> infos = new CachingRegistry<KEY, VALUE>() {
		
		@Override
		protected VALUE createEntry(KEY key) {
			return doCreateEntry(key);
		}
	};
	
	protected abstract VALUE doCreateEntry(KEY key);
	
	protected VALUE getEntry(KEY key) {
		return infos.getEntry(key);
	}
	
	public boolean checkIsEntryStale(KEY key) {
		VALUE entry = infos.getEntryOrNull(key);
		return entry == null ? true : doCheckIsEntryStale(key, entry);
	}
	
	/** Lock for reading/modifying the whole registry. */
	protected final Object entriesLock = new Object();
	
	public VALUE getUpdatedEntry(KEY key, UPDATE_OPTIONS options) throws CommonException {
		VALUE entry = getEntry(key);
		if(doCheckIsEntryStale(key, entry)) {
			return updateEntry(key, options);
		}
		return entry;
	}
	
	public abstract boolean doCheckIsEntryStale(KEY key, VALUE entry);
	
	/** Lock for performing the computation of update operations. */
	protected final Object updateOperationLock = new Object();
	
	protected VALUE updateEntry(KEY key, UPDATE_OPTIONS options) throws CommonException {
		assertNotNull(options);
		synchronized(updateOperationLock) {
			VALUE staleInfo = getEntry(key);
			// Recheck stale status after acquiring lock, it might have been updated in the meanwhile.
			// Otherwise unnecessary update operations might occur if two threads tried to update at the same time.
			if(doCheckIsEntryStale(key, staleInfo) == false)
				return staleInfo; // No longer stale.
			
			doUpdateEntry(key, staleInfo, options);
			return staleInfo;
		}
	}
	
	protected abstract void doUpdateEntry(KEY key, VALUE staleInfo, UPDATE_OPTIONS options) throws CommonException;
	
}