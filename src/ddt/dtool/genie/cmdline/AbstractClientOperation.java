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


import static ddt.melnorme.utilbox.misc.StringUtil.UTF8;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import ddt.melnorme.utilbox.misc.FileUtil;
import ddt.melnorme.utilbox.misc.NumberUtil;
import ddt.melnorme.utilbox.misc.NumberUtil.NumberFormatException_;
import ddt.melnorme.utilbox.misc.StringUtil;
import ddt.dtool.genie.GenieMain.AbstractCmdlineOperation;
import ddt.dtool.genie.GenieServer;
import ddt.dtool.util.InputStoringReader;
import ddt.dtool.util.JsonReaderExt;
import ddt.dtool.util.JsonWriterExt;

public abstract class AbstractClientOperation extends AbstractCmdlineOperation {
	
	public static Socket createLocalConnection(int serverPortNumber) throws UnknownHostException, IOException {
		return new Socket("127.0.0.1", serverPortNumber);
	}
	
	public AbstractClientOperation(String commandName) {
		super(commandName);
	}
	
	protected int portNumber = -1;
	
	@Override
	protected void processArgs() {
		String portNumberArg = retrieveFirstUnparsedArgument(true);
		portNumber = portNumberArg == null ? -1 : parsePositiveInt(portNumberArg);
	}
	
	@Override
	public void perform() {
		String responseString = performAndGetResponse();
		handleResponseString(responseString);
	}
	
	protected void handleResponseString(String responseString) {
		System.out.println(responseString);
	}
	
	public String performAndGetResponse() {
		if(portNumber == -1) {
			File sentinelFile = GenieServer.getSentinelFile();
			if(!sentinelFile.exists()) {
				throw errorBail("Did not detect any server running.", null);
			}
			
			String fileContents;
			try {
				fileContents = FileUtil.readStringFromFile(sentinelFile, StringUtil.UTF8);
			} catch (IOException ioe) {
				throw errorBail("Could not read contents of sentinel file: " + sentinelFile, ioe);
			}
			try {
				portNumber = NumberUtil.parseInt(fileContents);
				if(portNumber < 0) {
					throw new NumberFormatException("Negative number");
				}
			} catch (NumberFormatException_ e) {
				throw errorBail("Invalid contents of sentinel file, could not parse port number: " + fileContents, e);
			}
		}
		
		Socket socket;
		try {
			socket = createLocalConnection(portNumber);
		} catch (IOException ioe) {
			throw errorBail("Error opening connection on port " + portNumber + ".", ioe);
		}
		
		try (
			OutputStreamWriter serverInput = new OutputStreamWriter(socket.getOutputStream(), UTF8);
			BufferedReader serverOutput = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF8));
		) {
			
			InputStoringReader<StringWriter> serverOutStoringReader = InputStoringReader.createDefault(serverOutput);
			
			try(
				JsonWriterExt jsonWriter = new JsonWriterExt(serverInput);
				JsonReaderExt jsonReader = new JsonReaderExt(serverOutStoringReader);
			) {
				
				jsonWriter.setLenient(true);
				jsonReader.setLenient(true);
				
				writeRequest(jsonWriter);
				
				jsonReader.skipValue();
				String responseString = serverOutStoringReader.getStoredInput().toString();
				return responseString;
			}
		} catch (IOException ioe) {
			throw errorBail("Exception during client request.", ioe);
		}
	}
	
	public void writeRequest(JsonWriterExt jsonWriter) throws IOException {
		jsonWriter.beginObject();
		writeRequestObjectProperties(jsonWriter);
		jsonWriter.endObject();
		jsonWriter.flush();
	}
	
	protected abstract void writeRequestObjectProperties(JsonWriterExt jsonWriter) throws IOException;
	
}