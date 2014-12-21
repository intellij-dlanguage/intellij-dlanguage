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
package ddt.dtool.engine;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.utilbox.misc.Location;
import ddt.dtool.ast.definitions.Module;
import ddt.dtool.parser.DeeParserResult.ParsedModule;

public class ResolvedModule {
	
	protected final ParsedModule parsedModule;
	protected final AbstractBundleResolution semanticContext;
	
	public ResolvedModule(ParsedModule parsedModule, AbstractBundleResolution semanticContext) {
		this.parsedModule = assertNotNull(parsedModule);
		this.semanticContext = semanticContext;
	}
	
	public ParsedModule getParsedModule() {
		return parsedModule;
	}
	
	public String getSource() {
		return parsedModule.source;
	}
	
	public Module getModuleNode() {
		return parsedModule.module;
	}
	
	public Location getModulePath() {
		return Location.createValidOrNull(parsedModule.modulePath);
	}
	
	public ISemanticContext getSemanticContext() {
		return semanticContext;
	}
	
}