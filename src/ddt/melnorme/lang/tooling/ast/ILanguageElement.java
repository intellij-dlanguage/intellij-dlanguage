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

import java.nio.file.Path;

import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.ElementSemantics;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;

/**
 * Language element is a data node, part of a node tree, that encompasses data about a language/program.
 * Usually parsed from string source, but not always.
 * 
 * Some properties:
 *   The data it holds is immutable after the node has been parsed or fully constructed. 
 *   (TODO: need to formalize this better) 
 */
public interface ILanguageElement {
	
	/** @return the parent element of this element. null if it is the top element of the tree. */
	ILanguageElement getParent();
	
	/** 
	 * @return true if this is a pre-defined/native language element. 
	 * (example: primitives such as int, void, or native types like arrays, pointer types).
	 * This is a special case for which the elements do not have a well defined containing module path. 
	 */
	public boolean isLanguageIntrinsic();
	
	/**
	 * @returnt the path of the module file from where this element was parsed or created.
	 * This is important because it is used to find which semantic context to use for the semantic element.
	 * Non-null in most cases, but it can be null.
	 */
	public Path getModulePath();
	
	
	public void evaluateForScopeLookup(CommonScopeLookup lookup, boolean importsOnly, boolean isSequentialLookup);
	
	
	/* ----------------- Semantic resolution: ----------------- */
	
	/**
	 * Create the semantics object for this element. 
	 * The semantics object will be bound to the given {@link ISemanticContext} context.
	 * Subclasses should reimplement when applicable.
	 * Note that only the semantic context should be calling this class.
	 */
	public ElementSemantics<?> createSemantics(PickedElement<?> pickedElement);
	
	/**
	 * Should perform exactly this: <code>parentContext.getSemanticsEntry(this)</code>
	 * @return the semantics object. Should be the same on every call. Non-null.
	 */
	public ElementSemantics<?> getSemantics(ISemanticContext parentContext);
	
	/**
	 * @return the context where this element is directly contained in.
	 */
	public ISemanticContext getContextForThisElement(ISemanticContext parentContext);
	
}