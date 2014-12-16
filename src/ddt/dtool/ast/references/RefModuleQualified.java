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
package ddt.dtool.ast.references;

import java.util.Collection;
import java.util.Collections;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.definitions.Module;

/** An entity reference starting at module scope. 
 * Example: "a = .foo;"
 */
public class RefModuleQualified extends CommonQualifiedReference {
	
	public RefModuleQualified(RefIdentifier qualifiedId) {
		super(qualifiedId);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.REF_MODULE_QUALIFIED;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, qualifiedId);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(".");
		cp.appendNodeOrNullAlt(qualifiedId, "/*MISSING*/");
	}
	
	@Override
	public int getDotOffset() {
		return getStartPos();
	}
	
	@Override
	public Collection<INamedElement> findRootDefUnits(ISemanticContext moduleResolver) {
		final Module module = getModuleNode_();
		return Collections.<INamedElement>singletonList(module);
	}
	
}