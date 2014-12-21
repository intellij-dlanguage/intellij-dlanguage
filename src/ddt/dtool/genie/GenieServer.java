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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.CoreUtil.array;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import ddt.melnorme.utilbox.misc.FileUtil;
import ddt.melnorme.utilbox.misc.StringUtil;

import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;

import ddt.dtool.engine.DToolServer;
import ddt.dtool.genie.cmdline.StartServerOperation;
import ddt.dtool.util.JsonReaderExt;
import ddt.dtool.util.JsonWriterExt;
import ddt.dtool.util.StatusException;

public class GenieServer extends AbstractSocketServer {
	
	protected final DToolServer dtoolServer;
	protected File sentinelFile;
	
	public static final String ENGINE_NAME = "D Tool Genie";
	public static final String ENGINE_VERSION = "0.1.0";
	public static final String ENGINE_PROTOCOL_VERSION = "0.1";
	
	public static File getSentinelFile() {
		return new File(System.getProperty("user.home"), StartServerOperation.SENTINEL_FILE_NAME);
	}
	
	public GenieServer(DToolServer dtoolServer, int portNumber) throws StatusException {
		super(portNumber);
		this.dtoolServer = assertNotNull(dtoolServer);
		
		dtoolServer.logMessage(" ------ Started Genie Server ------ ");
		logMessage("Listening on port: " + this.portNumber);
		
		this.sentinelFile = getSentinelFile();
		try {
			FileUtil.writeStringToFile(sentinelFile, getServerPortNumber()+"", StringUtil.UTF8);
		} catch (IOException e) {
			throw new StatusException("Error writing to sentinel file " + sentinelFile, e);
		}
	}
	
	public DToolServer getDToolServer() {
		return dtoolServer;
	}
	
	@Override
	public void logMessage(String message) {
		dtoolServer.logMessage("# GenieServer: " + message);
	}
	@Override
	public void logError(String message, Throwable throwable) {
		dtoolServer.logError("# GenieServer: " + message, throwable);
	}
	@Override
	public void handleInternalError(Throwable throwable) {
		dtoolServer.handleInternalError(throwable);
	}
	
	@Override
	public void runServer() {
		try {
			super.runServer();
		} finally {
			disposeSentinelFile();
		}
	}
	
	public void shutdown() {
		closeServerSocket();
		disposeSentinelFile(); // Note that some connection handlers can still be active
	}
	
	protected void disposeSentinelFile() {
		if(sentinelFile != null) {
			sentinelFile.delete();
			sentinelFile = null;
		}
	}
	
	@Override
	protected GenieConnectionHandler createConnectionHandlerRunnable(Socket clientSocket) {
		return new GenieConnectionHandler(clientSocket);
	}
	
	@Override
	protected Thread createHandlerThread(ConnectionHandlerRunnable genieConnectionHandler) {
		Thread thread = super.createHandlerThread(genieConnectionHandler);
		thread.setName("GenieConnectionHandler:" + genieConnectionHandler.clientSocket.getPort());
		return thread;
	}
	
	public class GenieConnectionHandler extends ConnectionHandlerRunnable {
		
		public GenieConnectionHandler(Socket clientSocket) {
			super(clientSocket);
		}
		
		@Override
		protected void handleConnectionStream() throws IOException {
			try(
				BufferedReader serverInput = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream(), StringUtil.UTF8)); 
				OutputStreamWriter serverResponse = 
					new OutputStreamWriter(clientSocket.getOutputStream(), StringUtil.UTF8);
				JsonReaderExt jsonParser = new JsonReaderExt(serverInput);
				JsonWriterExt jsonWriter = new JsonWriterExt(serverResponse);
			) {
				
				try {
					jsonParser.setLenient(true);
					jsonWriter.setLenient(true);
					
					while(!jsonParser.isEOF()) {
						processJsonMessage(jsonParser, jsonWriter);
					}
				} catch (MalformedJsonException jsonException) {
					logProtocolMessageError(jsonException);
					return;
				}
			}
		}
		
	}
	
	public void logProtocolMessageError(Throwable throwable) {
		logError("Protocol message error: ", throwable);
	}
	
	public JsonCommandHandler[] getCommandHandlers() {
		return array(
			new AboutCommandHandler(this),
			new ShudtownCommandHandler(this),
			new FindDefinitionCommandHandler(this)
		);
	}
	
	protected void processJsonMessage(JsonReaderExt jsonParser, JsonWriterExt jsonWriter) throws IOException {
		jsonParser.consumeExpected(JsonToken.BEGIN_OBJECT);
		try {
			
			String commandName = jsonParser.consumeExpectedPropName();
			
			for (JsonCommandHandler commandHandler : getCommandHandlers()) {
				if(commandHandler.canHandle(commandName)) {
					commandHandler.processCommand(jsonParser, jsonWriter);
					return;
				}
			}
			
			new JsonCommandHandler(commandName, this) {
				@Override
				protected void writeResponseJsonContents() throws IOException {
					String msg = "Unknown command: " + commandName;
					logProtocolMessageError(validationError(msg));
					jsonWriter.writeProperty("error", msg);
				};
			}.processCommand(jsonParser, jsonWriter);
			
		} finally {
			jsonParser.consumeExpected(JsonToken.END_OBJECT);
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class GenieCommandException extends StatusException {
		
		public GenieCommandException(String message) {
			super(message, null);
		}
		
	}
	
	public static class AboutCommandHandler extends JsonCommandHandler {
		
		public AboutCommandHandler(GenieServer genieServer) {
			super("about", genieServer);
		}
		
		@Override
		protected void writeResponseJsonContents() throws IOException {
			jsonWriter.writeProperty("engine", ENGINE_NAME);
			jsonWriter.writeProperty("version", ENGINE_VERSION);
			jsonWriter.writeProperty("protocol_version", ENGINE_PROTOCOL_VERSION);
		}
		
	}
	
	public static class ShudtownCommandHandler extends JsonCommandHandler {
		
		public ShudtownCommandHandler(GenieServer genieServer) {
			super("shutdown", genieServer);
		}
		
		@Override
		protected void processCommandResponse() throws IOException, GenieCommandException {
			super.processCommandResponse();
			genieServer.shutdown();
		}
		
		@Override
		protected void writeResponseJsonContents() throws IOException {
		}
		
	}
	
}