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
package ddt.dtool.dub;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ddt.melnorme.utilbox.misc.StringUtil;
import ddt.melnorme.utilbox.process.ExternalProcessHelper.ExternalProcessResult;

public abstract class DubBuildOutputParser<E extends Exception> {
	
	public void handleResult(ExternalProcessResult processResult) throws E {
		int buildExitValue = processResult.exitValue;
		String stderr = processResult.stderr.toString(StringUtil.UTF8);
		
		if(buildExitValue != 0) {
			String dubErrorLine = getDubError(stderr);
			processDubFailure(dubErrorLine);
		}
		
		processCompilerErrors(stderr);
	}
	
	public String getDubError(String stderr) {
		Matcher matcher = Pattern.compile("^(Error executing command.*)$", Pattern.MULTILINE).
				matcher(stderr);
		if(matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	
	protected abstract void processDubFailure(String dubErrorLine) throws E;
	
	protected static final String ERROR_REGEX = "^([^():\\n]*)"+"\\(([^:)\\n]*)\\):"+"\\sError:\\s(.*)$";
	protected static final Pattern ERROR_MATCHER = Pattern.compile(ERROR_REGEX, Pattern.MULTILINE);
	
	public void processCompilerErrors(String stderr) {
		Matcher matcher = ERROR_MATCHER.matcher(stderr);
		while(matcher.find()) {
			String file = matcher.group(1);
			String lineStr = matcher.group(2);
			String errorMsg = matcher.group(3);
			
			processCompilerError(file, lineStr, errorMsg);
		}
	}
	
	protected abstract void processCompilerError(String file, String lineStr, String errorMsg);
	
}