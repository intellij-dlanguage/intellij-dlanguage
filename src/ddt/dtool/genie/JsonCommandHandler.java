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
package ddt.dtool.genie;

import static ddt.melnorme.utilbox.core.CoreUtil.areEqual;

import java.io.IOException;

import ddt.dtool.engine.DToolServer;
import ddt.dtool.genie.GenieServer.GenieCommandException;
import ddt.dtool.util.JsonReaderExt;
import ddt.dtool.util.JsonWriterExt;

public abstract class JsonCommandHandler extends JsonDeserializeHelper<GenieCommandException> {
	
	protected final String commandName;
	protected final GenieServer genieServer;
	
	public JsonCommandHandler(String commandName, GenieServer genieServer) {
		this.commandName = commandName;
		this.genieServer = genieServer;
	}
	
	public boolean canHandle(String requestName) {
		return areEqual(commandName, requestName);
	}
	
	protected DToolServer getDToolServer() {
		return genieServer.getDToolServer(); 
	}
	
	protected JsonReaderExt jsonParser;
	protected JsonWriterExt jsonWriter;
	
	public void processCommand(JsonReaderExt jsonParser, JsonWriterExt jsonWriter) throws IOException {
		this.jsonParser = jsonParser;
		this.jsonWriter = jsonWriter;
		
		parseCommandInput();
		try {
			processCommandResponse();
		} catch (GenieCommandException gce) {
			throw new IOException(gce);
		}
	}
	
	protected void processCommandResponse() throws IOException, GenieCommandException {
		jsonWriter.beginObject();
		jsonWriter.writeProperty("command", commandName);
		writeResponseJsonContents();
		jsonWriter.endObject();
		jsonWriter.flush();
	};
	
	protected abstract void writeResponseJsonContents() throws IOException, GenieCommandException;
	
	protected void parseCommandInput() throws IOException {
		jsonParser.skipValue();
	}
	
	@Override
	protected GenieCommandException validationError(String message) {
		return new GenieCommandException(message);
	}
	
}