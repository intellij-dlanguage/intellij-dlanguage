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
package ddt.dtool.engine.util;

import java.util.HashMap;

public abstract class CachingRegistry<KEY, ENTRY> {
	
	protected final HashMap<KEY, ENTRY> map = new HashMap<>();
	
	public synchronized ENTRY getEntry(KEY key) {
		
		ENTRY entry;
		
		if(map.containsKey(key)) {
			entry = map.get(key);
		} else {
			entry = createEntry(key);
			map.put(key, entry);
		}
		
		return entry;
	}
	
	public synchronized ENTRY getEntryOrNull(KEY key) {
		return map.get(key);
	}
	
	protected abstract ENTRY createEntry(KEY key);
	
}