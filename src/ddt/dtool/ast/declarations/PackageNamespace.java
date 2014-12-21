/*******************************************************************************
 * Copyright (c) 2011, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.declarations;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.ILanguageElement;
import ddt.melnorme.lang.tooling.ast_actual.ElementDoc;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.NotAValueErrorElement;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.AliasSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.lang.tooling.symbols.AbstractNamedElement;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.misc.ArrayUtil;
import ddt.melnorme.utilbox.misc.StringUtil;
import ddt.dtool.ast.definitions.DefUnit;
import ddt.dtool.ast.definitions.EArcheType;

/**
 * A named element corresponding to a partial package namespace.
 * It does not represent the full package namespace, but just one of the elements containted in the namespace.
 * (the containted element must be a sub-package, or a module) 
 */
public class PackageNamespace extends AbstractNamedElement implements IScopeElement, IConcreteNamedElement {
	
	public static PackageNamespace createPartialDefUnits(String[] packages, INamedElement module, 
			ILanguageElement container) {
		String defName = packages[0];
		packages = ArrayUtil.copyOfRange(packages, 1, packages.length);
		return createPartialDefUnits(defName, packages, module, container);
	}
	
	public static PackageNamespace createPartialDefUnits(String fqName, String[] packages, INamedElement module, 
			ILanguageElement container) {
		if(packages.length == 0) {
			return new PackageNamespace(fqName, module, container);
		} else {
			String childDefName = packages[0];
			String childFqName = fqName + "." + childDefName;
			packages = ArrayUtil.copyOfRange(packages, 1, packages.length);
			PackageNamespace partialDefUnits = createPartialDefUnits(childFqName, packages, module, container);
			return new PackageNamespace(fqName, partialDefUnits, container);
		}
	}
	
	/* -----------------  ----------------- */
	
	protected final String fqName;
	protected final INamedElement containedElement;
	
	public PackageNamespace(String fqName, INamedElement module, ILanguageElement container) {
		super(StringUtil.substringAfterLastMatch(fqName, "."), container);
		this.fqName = fqName;
		this.containedElement = assertNotNull(module);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Package;
	}
	
	@Override
	public String getFullyQualifiedName() {
		return fqName;
	}
	
	@Override
	public INamedElement getParentNamespace() {
		return null;
	}
	
	@Override
	public String getModuleFullyQualifiedName() {
		return null;
	}
	
	@Override
	public DefUnit resolveUnderlyingNode() {
		return null;
	}
	
	@Override
	public ElementDoc resolveDDoc() {
		return null;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + ":" + getFullyQualifiedName() 
			+ "{" + containedElement.getFullyQualifiedName() + "}";
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected final NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new AliasSemantics(this, pickedElement) {
			
			protected final NotAValueErrorElement notAValueErrorElement = new NotAValueErrorElement(element);
			
			@Override
			protected IConcreteNamedElement resolveAliasTarget(ISemanticContext context) {
				return PackageNamespace.this;
			}
			
			@Override
			public INamedElement resolveTypeForValueContext() {
				return notAValueErrorElement;
			};
			
			@Override
			public void resolveSearchInMembersScope(CommonScopeLookup search) {
				search.evaluateScope(PackageNamespace.this);
			}
			
		};
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
//		if(containedElement.getArcheType() == EArcheType.Module) {
//			INamedElementNode resolvedDefUnit = containedElement.resolveUnderlyingNode();
//			if(resolvedDefUnit == null) {
//				// Note that we dont use resolvedDefUnit for evaluateNodeForSearch,
//				// this means modules with mismatched names will match again the import name (file name),
//				// instead of the module declaration name
//				return;
//			}
//		}
		search.evaluateNamedElementForSearch(containedElement);
	}
	
}