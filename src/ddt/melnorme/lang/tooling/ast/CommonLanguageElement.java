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
package ddt.melnorme.lang.tooling.ast;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.util.NodeElementUtil;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.ElementSemantics;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.definitions.DefUnit;


public abstract class CommonLanguageElement implements ILanguageElement {
	
	public CommonLanguageElement() {
	}
	
	@Override
	public abstract ILanguageElement getParent();
	
	public INamedElement getParentNamespace() {
		return NodeElementUtil.getOuterNamedElement(this);
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public ElementSemantics<?> getSemantics(ISemanticContext parentContext) {
		ISemanticContext context = getContextForThisElement(parentContext);
		return context.getSemanticsEntry(this);
	}
	
	@Override
	public ISemanticContext getContextForThisElement(ISemanticContext parentContext) {
		return parentContext.findSemanticContext(this);
	}
	
	@Override
	public ElementSemantics<?> createSemantics(PickedElement<?> pickedElement) {
		assertTrue(pickedElement.element == this); // Note this precondition!
		return doCreateSemantics(pickedElement);
	}
	
	@SuppressWarnings("unused")
	protected ElementSemantics<?> doCreateSemantics(PickedElement<?> pickedElement) {
		throw assertFail(); // Not valid unless re-implemented.
	}
	
	@Override
	public void evaluateForScopeLookup(CommonScopeLookup lookup, boolean importsOnly, boolean isSequentialLookup) {
		if(this instanceof INonScopedContainer) {
			INonScopedContainer container = ((INonScopedContainer) this);
			// FIXME: remove need for isSequentialLookup?
			lookup.evaluateScopeElements(container.getMembersIterable(), isSequentialLookup, importsOnly);
		}
		
		if(!importsOnly && this instanceof DefUnit) {
			DefUnit defunit = (DefUnit) this;
			lookup.visitElement(defunit);
		}
	}
	
}