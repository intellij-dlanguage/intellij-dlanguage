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
package ddt.melnorme.lang.tooling.engine.intrinsics;

import java.util.Collection;

import ddt.melnorme.lang.tooling.ast.AbstractElement;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.IResolvable;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.ResolutionLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

/**
 * Helper reference class.
 */
public class ModuleQualifiedReference extends AbstractElement implements IResolvable {
	
	public final String moduleFullName;
	public final String elementName;
	
	public ModuleQualifiedReference(String moduleFullName, String elementName) {
		super(null);
		this.moduleFullName = moduleFullName;
		this.elementName = elementName;
	}
	
	@Override
	public String toStringAsCode() {
		return moduleFullName + "." + elementName;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public ResolvableSemantics getSemantics(ISemanticContext parentContext) {
		return (ResolvableSemantics) super.getSemantics(parentContext);
	}
	@Override
	public ResolvableSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ResolvableSemantics(this, pickedElement) {
		
		@Override
		public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
			INamedElement module = ResolvableSemantics.findModuleUnchecked(context, moduleFullName);
			if(module == null) 
				return null;
			IConcreteNamedElement moduleConcrete = module.resolveConcreteElement(context);
			
			ResolutionLookup search = new ResolutionLookup(elementName, null, -1, findOneOnly, context);
			search.evaluateInMembersScope(moduleConcrete);
			return search.getMatchedElements();
		}
		
		@Override
		public Collection<INamedElement> resolveTypeOfUnderlyingValue() {
			return ResolvableSemantics.resolveTypeOfUnderlyingValue(context, findTargetDefElements(true));
		}
		
	};
	}
	
	@Override
	public final Collection<INamedElement> findTargetDefElements(ISemanticContext context) {
		return getSemantics(context).findTargetDefElements(true);
	}
	
	@Override
	public final Collection<INamedElement> findTargetDefElements(ISemanticContext context, boolean findFirstOnly) {
		return getSemantics(context).findTargetDefElements(true);
	}
	
}