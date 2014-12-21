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
package ddt.dtool.genie.cmdline;

import static ddt.dtool.genie.cmdline.StartServerOperation.SENTINEL_FILE_UIString;

import java.io.IOException;
import java.io.PrintStream;

import ddt.dtool.genie.GenieServer;
import ddt.dtool.util.JsonWriterExt;

public class ShutdownServerRequest extends AbstractClientOperation {
	
	public ShutdownServerRequest() {
		super("shutdown");
	}
	
	@Override
	public String getOneLineSummary() {
		return "Shutdown the " + GenieServer.ENGINE_NAME + " server.";
	}
	
	@Override
	public void printCommandHelp(PrintStream out) {
		out.println(helpUsageIntro() + "[<port>]");
		out.println();
		out.println("Requests a shutdown of the Genie server.");
		out.println();
		out.println("Will connect to the server on given <port>. If this parameter is ommited, will try to");
		out.println("auto-detect which port the server is running, based on the " + SENTINEL_FILE_UIString + "file");
		out.println();
	}
	
	protected int portNumber = -1;
	
	@Override
	protected void processArgs() {
		super.processArgs();
	}
	
	@Override
	protected void writeRequestObjectProperties(JsonWriterExt jsonWriter) throws IOException {
		jsonWriter.name("shutdown");
		jsonWriter.beginObject();
		jsonWriter.endObject();
	}
	
}