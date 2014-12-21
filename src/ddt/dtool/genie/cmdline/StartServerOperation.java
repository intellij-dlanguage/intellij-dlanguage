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

import java.io.PrintStream;

import ddt.dtool.engine.DToolServer;
import ddt.dtool.genie.GenieMain.AbstractCmdlineOperation;
import ddt.dtool.genie.GenieServer;
import ddt.dtool.util.StatusException;

public class StartServerOperation extends AbstractCmdlineOperation {
	
	public static final String SENTINEL_FILE_NAME = ".dtoolgenie";
	public static final String SENTINEL_FILE_UIString = "$HOME/" + SENTINEL_FILE_NAME;
	
	public StartServerOperation() {
		super("start");
	}
	
	@Override
	public String getOneLineSummary() {
		return "Start the DToolGenie server.";
	}
	
	@Override
	public void printCommandHelp(PrintStream out) {
		out.println(helpUsageIntro() + "[<port>] [force]");
		out.println();
		out.println("Start the Genie server, listening on given <port>. This will fail if an ");
		out.println("already running Genie server is detected, unless the 'force' option is given.");
		out.println();
		out.println("When the Genie server starts, a file is created in "+SENTINEL_FILE_UIString); 
		out.println("with the port number of the started server. This file is deleted when the server");
		out.println("terminates, and thus can be used to determine if a Genie server is running.");
	}
	
	protected boolean force;
	protected int requestedPortNumber;
	
	@Override
	protected void processArgs() {
		force = getFlag("force");
		String portNumberArg = retrieveFirstUnparsedArgument(true);
		requestedPortNumber = portNumberArg == null ? 0 : parsePositiveInt(portNumberArg);
	}
	
	@Override
	protected void perform() {
		if(GenieServer.getSentinelFile().exists() && force == false) {
			errorBail(
				"Failed to create server sentinel file, perhaps server is running already?\n"
				+ "  Use argument 'force' to start anyways (sentinel file will be overriden)." , 
				null);
		}
		
		try {
			DToolServer dtoolServer = new DToolServer();
			GenieServer genieServer = new GenieServer(dtoolServer, requestedPortNumber);
			genieServer.runServer();
		} catch (StatusException se) {
			throw errorBail("Error starting server. ", se);
		}
		
	}
	
}