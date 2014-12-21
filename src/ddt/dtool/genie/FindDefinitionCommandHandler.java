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

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import ddt.dtool.engine.operations.FindDefinitionResult;
import ddt.dtool.engine.operations.FindDefinitionResult.FindDefinitionResultEntry;
import ddt.dtool.genie.GenieServer.GenieCommandException;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.utilbox.core.CommonException;
import ddt.melnorme.utilbox.misc.StringUtil;

public class FindDefinitionCommandHandler extends JsonCommandHandler {
	
	public FindDefinitionCommandHandler(GenieServer genieServer) {
		super("find_definition", genieServer);
	}
	
	protected HashMap<String, Object> commandArguments;
	
	@Override
	protected void parseCommandInput() throws IOException {
		commandArguments = jsonParser.readJsonObject();
	}
	
	@Override
	protected void writeResponseJsonContents() throws IOException, GenieCommandException {
		System.out.println(StringUtil.collToString(commandArguments.entrySet(), "\n"));
		
		Path modulePath = getPath(commandArguments, "filepath");
		int offset = getInt(commandArguments, "offset");
		Path dubPath = getPath(commandArguments, "dubpath", true); // TODO: test this
		String dubPathString = dubPath == null ? null : dubPath.toString();
		
		FindDefinitionResult cmdResult;
		try {
			cmdResult = getDToolServer().doFindDefinition(modulePath, offset, dubPathString);
		} catch (CommonException e) {
			// TODO: refactor common exception and GenieCommandException
			throw new GenieCommandException(e.getMessage());
		}
		
		if(cmdResult.errorMessage != null) {
			jsonWriter.writeProperty("error", cmdResult.errorMessage);
		}
		
		if(cmdResult.results != null) {
			jsonWriter.name("results");
			
			jsonWriter.beginArray();
			for (FindDefinitionResultEntry resultEntry : cmdResult.results) {
				writeResultEntry(resultEntry);
			}
			jsonWriter.endArray();
		}
		
	}
	
	protected void writeResultEntry(FindDefinitionResultEntry result) throws IOException {
		jsonWriter.beginObject();
		jsonWriter.writeProperty("extendedName", result.extendedName);
		jsonWriter.writeProperty("isIntrinsic", result.isLanguageIntrinsic);
		if(result.modulePath != null) {
			jsonWriter.writeProperty("modulePath", result.modulePath.toString());
		}
		SourceRange sourceRange = result.sourceRange;
		if(sourceRange != null) {
			jsonWriter.writeProperty("offset", sourceRange.getOffset());
			jsonWriter.writeProperty("length", sourceRange.getLength());
		}
		jsonWriter.endObject();
	}
	
}