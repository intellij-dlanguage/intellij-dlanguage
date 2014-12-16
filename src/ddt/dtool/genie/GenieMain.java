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
import static ddt.melnorme.utilbox.core.CoreUtil.areEqual;
import static ddt.melnorme.utilbox.core.CoreUtil.array;
import static ddt.melnorme.utilbox.misc.StringUtil.newSpaceFilledString;

import java.io.PrintStream;

import ddt.melnorme.utilbox.misc.ArrayUtil;
import ddt.dtool.genie.cmdline.StartServerOperation;


public class GenieMain {
	
	public static final String CMDLINE_PROGRAM_NAME = "genie";
	
	public static final AbstractCmdlineOperation[] commands = array(
		new StartServerOperation(),
		new CmdlineHelpOperation()
	);
	
	public static void main(String[] args) {
		try {
			runApp(args);
		} catch (GenieClientAppException gce) {
			System.out.println(gce.getMessage());
			Throwable throwable = gce.getCause();
			if(throwable != null) {
				System.out.println(throwable);
			}
			System.exit(1);
		}
	}
	
	public static void runApp(String[] args) {
		args = args.length == 0 ? array("help") : args;
		String commandName = args[0];
		String[] newArgs = ArrayUtil.removeAt(args, 0);
		
		for (AbstractCmdlineOperation command : commands) {
			if(command.tryHandling(commandName, newArgs)) {
				return;
			}
		}
		
		System.err.println("Unknown command: " + commandName);
	}
	
	@SuppressWarnings("serial")
	public static class GenieClientAppException extends RuntimeException {
		
		public GenieClientAppException(String message, Throwable cause) {
			super(assertNotNull(message), cause);
		}
		
	}
	
	public static abstract class AbstractCmdlineOperation extends ProgramArgumentsHelper {
		
		protected final String commandName;
		protected String[] rawArgs;
		
		public AbstractCmdlineOperation(String commandName) {
			this.commandName = commandName;
		}
		
		public String getCommandName() {
			return commandName;
		}
		
		public void printOneLineSummary(PrintStream out) {
			out.print("   " + commandName + newSpaceFilledString(10 - commandName.length()) + " - ");
			out.println(getOneLineSummary());
		}
		
		public abstract String getOneLineSummary();
		
		public abstract void printCommandHelp(PrintStream out);
		
		protected String helpUsageIntro() {
			return "Usage: " + CMDLINE_PROGRAM_NAME + " " + getCommandName() + " ";
		}
		
		/* -----------------  ----------------- */
		
		public boolean tryHandling(String commandString, String[] args) {
			if(!commandString.equals(commandName)) {
				return false;
			}
			this.rawArgs = args;
			parseArgs(args);
			processArgs();
			validateNoMoreUnprocessedArguments();
			
			perform();
			return true;
		}
		
		protected abstract void processArgs();
		
		@Override
		protected RuntimeException handleArgumentsError(String message) {
			return errorBail(message, null);
		}
		
		protected RuntimeException errorBail(String message, Throwable throwable) {
			throw new GenieClientAppException(message, throwable);
		}
		
		protected abstract void perform();
		
	}
	
	public static class CmdlineHelpOperation extends AbstractCmdlineOperation {
		
		public CmdlineHelpOperation() {
			super("help");
		}
		
		@Override
		public String getOneLineSummary() {
			return "Display help.";
		}
		
		@Override
		public void printCommandHelp(PrintStream out) {
			out.println(helpUsageIntro() + "[<command>]");
			out.println();
			out.println("Display full help for given <command>.");
		}
		
		protected String commandToHelp;
		
		@Override
		protected void processArgs() {
			commandToHelp = retrieveFirstUnparsedArgument(true);
		}
		
		@Override
		public void perform() {
			if(commandToHelp == null) {
				printGeneralHelp();
				return;
			}
			
			for (AbstractCmdlineOperation command : commands) {
				if(areEqual(command.commandName, commandToHelp)) {
					command.printCommandHelp(System.out);
					return;
				}
			}
		}
		
		protected void printGeneralHelp() {
			System.out.println(GenieServer.ENGINE_NAME + " - " + GenieServer.ENGINE_VERSION);
			System.out.println();
			System.out.println("usage: " + CMDLINE_PROGRAM_NAME + " <command> [<args>]");
			System.out.println();
			System.out.println("Available commands:");
			
			for (AbstractCmdlineOperation command : commands) {
				command.printOneLineSummary(System.out);
			}
			
			System.out.println();
		}
		
	}
	
}