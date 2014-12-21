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

import static ddt.dtool.util.NewUtils.assertInstance;
import static ddt.dtool.util.NewUtils.exactlyOneIsNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.core.CoreUtil.array;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.collections.Indexable;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.engine.analysis.templates.RefTemplateInstanceSemantics;

public class RefTemplateInstance extends Reference implements IQualifierNode, ITemplateRefNode {
	
	public final Reference tplRef;
	public final Resolvable tplSingleArg;
	public final NodeListView<Resolvable> tplArgs;
	
	public RefTemplateInstance(ITemplateRefNode tplRef, Resolvable tplSingleArg, NodeListView<Resolvable> tplArgs) {
		this.tplRef = parentize(assertInstance(tplRef, Reference.class));
		assertTrue(exactlyOneIsNull(tplSingleArg, tplArgs));
		this.tplSingleArg = parentize(tplSingleArg);
		this.tplArgs = parentize(tplArgs);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.REF_TEMPLATE_INSTANCE;
	}
	
	public boolean isSingleArgSyntax() {
		return tplSingleArg != null;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, tplRef);
		acceptVisitor(visitor, tplSingleArg);
		acceptVisitor(visitor, tplArgs);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(tplRef, "!");
		if(isSingleArgSyntax()) {
			cp.append(tplSingleArg);
		} else {
			cp.appendNodeList("(", tplArgs, ", ", ")");
		}
	}
	
	public Indexable<Resolvable> getEffectiveArguments() {
		if(isSingleArgSyntax()) {
			return ArrayView.create(array(tplSingleArg));
		} else {
			return tplArgs;
		}
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected RefTemplateInstanceSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new RefTemplateInstanceSemantics(this, pickedElement);
	};
	
}