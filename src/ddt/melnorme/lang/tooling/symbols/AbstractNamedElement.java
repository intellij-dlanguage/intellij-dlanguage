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
package ddt.melnorme.lang.tooling.symbols;

import ddt.melnorme.lang.tooling.ast.AbstractElement;
import ddt.melnorme.lang.tooling.ast.ILanguageElement;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.context.ModuleFullName;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;

public abstract class AbstractNamedElement extends AbstractElement implements INamedElement {
	
	protected final String name;
	
	public AbstractNamedElement(String name, ILanguageElement parent) {
		super(parent);
		this.name = name;
	}
	
	@Override
	public final String getName() {
		return name;
	}
	
	@Override
	public String getExtendedName() {
		return name;
	}
	
	@Override
	public String getNameInRegularNamespace() {
		return getName();
	}
	
	@Override
	public ModuleFullName getModuleFullName() {
		return ModuleFullName.fromString(getModuleFullyQualifiedName());
	}
	
	/* ----------------- ----------------- */
	
	@Override
	public NamedElementSemantics getSemantics(ISemanticContext parentContext) {
		return (NamedElementSemantics) super.getSemantics(parentContext);
	}
	@Override
	protected abstract NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement);
	
	@Override
	public final IConcreteNamedElement resolveConcreteElement(ISemanticContext context) {
		return getSemantics(context).resolveConcreteElement().result;
	}
	
	@Override
	public final void resolveSearchInMembersScope(CommonScopeLookup search) {
		getSemantics(search.modResolver).resolveSearchInMembersScope(search);
	}
	
	@Override
	public final INamedElement resolveTypeForValueContext(ISemanticContext context) {
		return getSemantics(context).resolveTypeForValueContext();
	}
	
}