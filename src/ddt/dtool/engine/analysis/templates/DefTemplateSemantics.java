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
package ddt.dtool.engine.analysis.templates;

import ddt.melnorme.lang.tooling.engine.NotAValueErrorElement;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ConcreteElementSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.definitions.DefinitionTemplate;

public final class DefTemplateSemantics extends ConcreteElementSemantics {
	
	protected final DefinitionTemplate defTemplate;
	protected final NotAValueErrorElement notAValueErrorElement;
	
	public DefTemplateSemantics(DefinitionTemplate defTemplate, PickedElement<?> pickedElement) {
		super(defTemplate, pickedElement);
		this.defTemplate = defTemplate;
		this.notAValueErrorElement = new NotAValueErrorElement(defTemplate);
	}
	
	@Override
	public void resolveSearchInMembersScope(CommonScopeLookup search) {
		if(defTemplate.wrapper) {
			// TODO: go straight to members of wrapped definition
		}
		IScopeElement scope = defTemplate.decls; //TODO create empty scope.
		if(scope != null) {
			search.evaluateScope(scope);
		}
	}
	
	@Override
	public INamedElement resolveTypeForValueContext() {
		if(defTemplate.wrapper) {
			// TODO: go straight to members of wrapped definition
		}
		return notAValueErrorElement;
	}
}