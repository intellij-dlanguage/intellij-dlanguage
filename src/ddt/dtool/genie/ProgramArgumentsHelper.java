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

import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashSet;

import ddt.melnorme.utilbox.misc.MiscUtil;

public abstract class ProgramArgumentsHelper {
	
	protected final LinkedHashSet<String> parsedArgs = new LinkedHashSet<>();
	
	protected void parseArgs(String[] rawArgs) {
		for (String arg : rawArgs) {
			if(parsedArgs.contains(arg)) {
				handleArgumentsError("Duplicate argument: " + arg);
			}
			parsedArgs.add(arg);
		}
	}
	
	protected abstract RuntimeException handleArgumentsError(String message);
	
	protected boolean getFlag(String argName) {
		return parsedArgs.remove(argName);
	}
	
	protected String retrieveFirstUnparsedArgument(boolean mustBeLastArgument) {
		Iterator<String> iterator = parsedArgs.iterator();
		
		String firstRemainingArgument = null;
		if(iterator.hasNext()) {
			firstRemainingArgument = iterator.next();
			iterator.remove();
		}
		
		if(mustBeLastArgument) {
			validateNoMoreUnprocessedArguments();
		}
		return firstRemainingArgument;
	}
	
	protected void validateNoMoreUnprocessedArguments() {
		String firstRemainingArgument = retrieveFirstUnparsedArgument(false);
		if(firstRemainingArgument != null) {
			handleArgumentsError("Unknown argument: " + firstRemainingArgument);
		}
	}
	
	protected int parsePositiveInt(String stringArg) {
		int intValue = parseInt(stringArg);
		if(intValue < 0) {
			handleArgumentsError("Argument not positive integer: " + stringArg);
		}
		return intValue;
	}
	
	protected int parseInt(String stringArg) {
		try {
			return Integer.parseInt(stringArg);
		} catch (NumberFormatException e) {
			throw handleArgumentsError("Argument not integer: " + stringArg);
		}
	}
	
	protected Path parseValidPath(String stringArgument) {
		Path path = MiscUtil.createPathOrNull(stringArgument);
		if(path == null) {
			throw handleArgumentsError("Invalid path : " + stringArgument);
		}
		return path;
	}
	
}