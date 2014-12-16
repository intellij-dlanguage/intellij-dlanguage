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
package ddt.melnorme.lang.tooling.engine.resolver;

import static ddt.melnorme.utilbox.misc.CollectionUtil.getFirstElementOrNull;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.expressions.Resolvable;

public abstract class VarSemantics extends ConcreteElementSemantics {
	
	public VarSemantics(IConcreteNamedElement element, PickedElement<?> pickedElement) {
		super(element, pickedElement);
	}
	
	@Override
	public void resolveSearchInMembersScope(CommonScopeLookup search) {
		INamedElement effectiveType = resolveTypeForValueContext();
		search.evaluateInMembersScope(resolveConcreteElement(effectiveType));
	}
	
	@Override
	public INamedElement resolveTypeForValueContext() {
		Resolvable declaredType = getTypeReference();
		if(declaredType != null) {
			// TODO: handle finding multiple elements
			return getFirstElementOrNull(declaredType.findTargetDefElements(context));
		}
		return null;
	}
	
	protected abstract Resolvable getTypeReference();
	
}