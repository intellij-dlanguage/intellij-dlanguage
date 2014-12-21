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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

/**
 * An entry caching some value, derived from a file as input. 
 * Keeps tracks of file and value timestamps, to see if current value is stale or not with regards to the file input.
 */
public class FileCachingEntry<VALUE> {
	
	protected final Path filePath;
	
	protected volatile VALUE value;
	protected volatile FileTime valueTimeStamp;
	
	public FileCachingEntry(Path path) {
		this.filePath = path;
	}
	
	public VALUE getValue() {
		return value;
	}
	
	public FileTime getValueTimeStamp() {
		return valueTimeStamp;
	}
	
	public void markStale() {
		valueTimeStamp = null;
	}
	
	public synchronized boolean isStale() {
		FileTime lastModifiedTime;
		try {
			lastModifiedTime = Files.getLastModifiedTime(filePath);
		} catch (IOException e) {
			return true;
		}
		
		if(valueTimeStamp == null || valueTimeStamp.toMillis() < lastModifiedTime.toMillis()) {
			return true;
		}
		return false;
	}
	
	public void updateValue(VALUE value) {
		updateValue(value, null);
	}
	
	public void updateValue(VALUE value, FileTime newTimeStampMaximum) {
		assertNotNull(newTimeStampMaximum);
		
		FileTime newValueTimeStamp;
		try {
			newValueTimeStamp = Files.getLastModifiedTime(filePath);
		} catch (IOException e) {
			newValueTimeStamp = FileTime.fromMillis(0);
		}
		if(newTimeStampMaximum != null && newTimeStampMaximum.compareTo(newValueTimeStamp) < 0) {
			newValueTimeStamp = newTimeStampMaximum;
		}
		internalSetValue(value, newValueTimeStamp);
	}
	
	protected synchronized void internalSetValue(VALUE value, FileTime newTimeStamp) {
		this.value = value;
		this.valueTimeStamp = newTimeStamp;
	}
	
}