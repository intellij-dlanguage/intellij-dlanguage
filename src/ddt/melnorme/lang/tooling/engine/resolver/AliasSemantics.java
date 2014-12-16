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

import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.ErrorElement;
import ddt.melnorme.lang.tooling.engine.NotAValueErrorElement;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

public abstract class AliasSemantics extends NamedElementSemantics {
	
	public AliasSemantics(INamedElement element, PickedElement<?> pickedElement) {
		super(element, pickedElement);
	}
	
	protected IConcreteNamedElement resolveAliasTarget_nonNull() {
		IConcreteNamedElement result = resolveAliasTarget(context);
		if(result == null) {
			return ErrorElement.newNotFoundError(element, null);
		}
		return result;
	}
	
	protected abstract IConcreteNamedElement resolveAliasTarget(ISemanticContext context);
	
	@Override
	protected IConcreteNamedElement doResolveConcreteElement() {
		return resolveAliasTarget_nonNull();
	}
	
	@Override
	public void resolveSearchInMembersScope(CommonScopeLookup search) {
		search.evaluateInMembersScope(resolveAliasTarget_nonNull());
	}
	
	@Override
	public INamedElement resolveTypeForValueContext() {
		return resolveAliasTarget_nonNull().resolveTypeForValueContext(context);
	}
	
	/* -----------------  ----------------- */
	
	public abstract static class RefAliasSemantics extends AliasSemantics {

		public RefAliasSemantics(INamedElement element, PickedElement<?> pickedElement) {
			super(element, pickedElement);
		}
		
		@Override
		protected IConcreteNamedElement resolveAliasTarget(ISemanticContext context) {
			return resolveAliasTarget(getAliasTarget());
		}
		
		protected abstract IResolvable getAliasTarget();
		
		protected IConcreteNamedElement resolveAliasTarget(IResolvable aliasTarget) {
			if(aliasTarget == null) {
				return null;
			}
			INamedElement namedElement = aliasTarget.getSemantics(context).resolveTargetElement().getSingleResult();
			return resolveConcreteElement(namedElement);
		}
		
	}
	
	public abstract static class TypeAliasSemantics extends RefAliasSemantics {
		
		public TypeAliasSemantics(INamedElement aliasDef, PickedElement<?> pickedElement) {
			super(aliasDef, pickedElement);
		}
		
		@Override
		public INamedElement resolveTypeForValueContext() {
			// FIXME: fix leak here, this element should be created only once per resolution.
			return new NotAValueErrorElement(element);
		};
		
	}
	
}