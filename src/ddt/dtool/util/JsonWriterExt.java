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
package ddt.dtool.util;

import static ddt.melnorme.utilbox.core.CoreUtil.areEqual;

import java.io.IOException;
import java.io.Writer;

import com.google.gson.stream.JsonWriter;

public class JsonWriterExt extends JsonWriter {

	public JsonWriterExt(Writer out) {
		super(out);
	}
	
	@Override
	public void close() throws IOException {
		try {
			super.close();
		} catch (IOException ioe) {
			if(ioe.getClass() == IOException.class && areEqual(ioe.getMessage(), "Incomplete document")) {
				return; // Ignore this error
			}
			throw ioe;
		}
	}
	
	public void writeProperty(String name, String value) throws IOException {
		name(name);
		value(value);
	}
	
	public void writeProperty(String name, int value) throws IOException {
		name(name);
		value(value);
	}
	
	public void writeProperty(String name, boolean value) throws IOException {
		name(name);
		value(value);
	}
	
}