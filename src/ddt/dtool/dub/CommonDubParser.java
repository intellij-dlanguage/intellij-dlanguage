/*******************************************************************************
 * Copyright (c) 2013, 2013 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.dub;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import ddt.melnorme.utilbox.misc.FileUtil;
import ddt.melnorme.utilbox.misc.StringUtil;

import com.google.gson.stream.JsonToken;

import ddt.dtool.dub.DubBundle.DubBundleException;
import ddt.dtool.util.JsonReaderExt;

public abstract class CommonDubParser {
	
	protected static String readStringFromFile(File file) throws IOException, FileNotFoundException {
		return FileUtil.readStringFromFile(file, StringUtil.UTF8);
	}
	
	protected DubBundleException dubError;
	
	public CommonDubParser() {
	}
	
	protected void putError(String message) {
		if(dubError == null) {
			dubError = new DubBundleException(message);
		}
	}
	
	protected void parseFromSource(String source) throws DubBundleException {
		try(JsonReaderExt jsonParser = new JsonReaderExt(new StringReader(source))) {
			jsonParser.setLenient(true);
			
			readData(jsonParser);
			
			jsonParser.consumeExpected(JsonToken.END_DOCUMENT);
			assertTrue(jsonParser.peek() == JsonToken.END_DOCUMENT);
		} catch (IOException e) {
			throw new DubBundleException(e);
		}
	}
	
	protected abstract void readData(JsonReaderExt jsonParser) throws IOException;
	
}