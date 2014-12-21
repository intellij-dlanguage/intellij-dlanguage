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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.core.CoreUtil.nullToEmpty;

import java.util.ArrayList;
import java.util.Collection;

import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.context.ModuleFullName;
import ddt.melnorme.lang.tooling.context.ModuleSourceException;
import ddt.melnorme.lang.tooling.engine.ElementSemantics;
import ddt.melnorme.lang.tooling.engine.ErrorElement;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

public abstract class ResolvableSemantics extends ElementSemantics<ResolvableResult> {
	
	private final IResolvable resolvable;
	
	public ResolvableSemantics(IResolvable resolvable, PickedElement<?> pickedElement) {
		super(pickedElement);
		assertTrue(pickedElement.element == resolvable);
		this.resolvable = resolvable;
	}
	
	public final ResolvableResult resolveTargetElement() {
		return getElementResolution();
	}
	
	@Override
	protected ResolvableResult createLoopResolution() {
		// TODO: test this path
		return new ResolvableResult(ErrorElement.newLoopError(resolvable, null));
	}
	
	@Override
	protected ResolvableResult createResolution() {
		INamedElement result = null;
		Collection<INamedElement> namedElems = findTargetDefElements(true);
		if(namedElems != null && !namedElems.isEmpty()) {
			result = namedElems.iterator().next();
		}
		
		if(result == null) {
			result = ErrorElement.newNotFoundError(resolvable);
		}
		
		return new ResolvableResult(result);
	}
	
	public Collection<INamedElement> resolveTypeOfUnderlyingValue() {
		Collection<INamedElement> resolvedElements = this.findTargetDefElements(false);
		
		return resolveTypeOfUnderlyingValue(context, resolvedElements); 
	}
	
	/* TODO: deprecate this: */
	/** Finds the named element matching this {@link IResolvable}. 
	 * If no results are found, return null. */
	public abstract Collection<INamedElement> findTargetDefElements(boolean findOneOnly);
	
	public static Collection<INamedElement> resolveTypeOfUnderlyingValue(ISemanticContext mr, 
		Collection<INamedElement> resolvedElements) {
		ArrayList<INamedElement> resolvedTypeForValueContext = new ArrayList<>();
		for (INamedElement defElement : nullToEmpty(resolvedElements)) {
			INamedElement resolveTypeForValueContext = defElement.resolveTypeForValueContext(mr);
			if(resolvedTypeForValueContext != null) {
				resolvedTypeForValueContext.add(resolveTypeForValueContext);
			}
		}
		return resolvedTypeForValueContext;
	}
	
	
	protected Collection<INamedElement> resolveToInvalidValue() {
		return null; // TODO
	}
	
	public abstract static class TypeReferenceSemantics extends ResolvableSemantics {
		
		public TypeReferenceSemantics(IResolvable resolvable, PickedElement<?> pickedElement) {
			super(resolvable, pickedElement);
		}
		
		@Override
		public Collection<INamedElement> resolveTypeOfUnderlyingValue() {
			return resolveToInvalidValue();
		}
		
	}
	
	public abstract static class ExpSemantics extends ResolvableSemantics {
		
		public ExpSemantics(IResolvable resolvable, PickedElement<?> pickedElement) {
			super(resolvable, pickedElement);
		}
		
		@Override
		public abstract Collection<INamedElement> findTargetDefElements(boolean findOneOnly);
		
		@Override
		public Collection<INamedElement> resolveTypeOfUnderlyingValue() {
			return findTargetDefElements(true); // TODO need to review this
		}
		
	}
	
	/* ----------------- module lookup helpers ----------------- */
	
	public static INamedElement findModuleUnchecked(ISemanticContext mr, ModuleFullName moduleName) {
		try {
			return mr.findModule(moduleName);
		} catch (ModuleSourceException pse) {
			/* TODO: add error to SemanticResolution / semantic operation. */
			return null;
		}
	}
	
	public static INamedElement findModuleUnchecked(ISemanticContext mr, String moduleFullName) {
		return ResolvableSemantics.findModuleUnchecked(mr, new ModuleFullName(moduleFullName));
	}
	
}