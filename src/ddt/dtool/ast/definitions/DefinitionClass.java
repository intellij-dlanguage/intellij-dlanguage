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
package ddt.dtool.ast.definitions;

import static ddt.melnorme.utilbox.misc.IteratorUtil.nonNullIterable;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.intrinsics.InstrinsicsScope;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.core.CoreUtil;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.engine.analysis.DeeLanguageIntrinsics;
import ddt.dtool.parser.common.Token;

/**
 * A definition of a class aggregate.
 */
public class DefinitionClass extends DefinitionAggregate {
	
	public final ArrayView<Reference> baseClasses;
	public final boolean baseClassesAfterConstraint;
	
	public DefinitionClass(Token[] comments, ProtoDefSymbol defId, ArrayView<ITemplateParameter> tplParams,
		Expression tplConstraint, ArrayView<Reference> baseClasses, boolean baseClassesAfterConstraint, 
		IAggregateBody aggrBody) 
	{
		super(comments, defId, tplParams, tplConstraint, aggrBody);
		this.baseClasses = parentize(baseClasses);
		this.baseClassesAfterConstraint = baseClassesAfterConstraint;
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_CLASS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptNodeChildren(visitor);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		classLikeToStringAsCode(cp, "class ");
	}
	
	public void classLikeToStringAsCode(ASTCodePrinter cp, String keyword) {
		cp.append(keyword);
		cp.append(defname, " ");
		cp.appendList("(", tplParams, ",", ") ");
		if(baseClassesAfterConstraint) DefinitionTemplate.tplConstraintToStringAsCode(cp, tplConstraint);
		cp.appendList(": ", baseClasses, ",", " ");
		if(!baseClassesAfterConstraint) DefinitionTemplate.tplConstraintToStringAsCode(cp, tplConstraint);
		cp.append(aggrBody, "\n");
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Class;
	}
	
	@Override
	protected void acceptNodeChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, tplParams);
		if(baseClassesAfterConstraint)
			acceptVisitor(visitor, tplConstraint);
		acceptVisitor(visitor, baseClasses);
		if(!baseClassesAfterConstraint)
			acceptVisitor(visitor, tplConstraint);
		acceptVisitor(visitor, aggrBody);
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public final ClassSemantics getSemantics(ISemanticContext parentContext) {
		return (ClassSemantics) super.getSemantics(parentContext);
	}
	@Override
	protected ClassSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		InstrinsicsScope commonTypeScope = DeeLanguageIntrinsics.D2_063_intrinsics.createObjectPropertiesScope(this);
		return new ClassSemantics(this, commonTypeScope, pickedElement);
	}
	
	public class ClassSemantics extends AggregateSemantics {
		
		public ClassSemantics(IConcreteNamedElement classElement, InstrinsicsScope commonTypeScope,
				PickedElement<?> pickedElement) {
			super(classElement, commonTypeScope, pickedElement);
		}
		
		public void resolveSearchInSuperScopes(CommonScopeLookup search) {
			ISemanticContext context = search.modResolver;
			
			for(Reference baseclass : CoreUtil.nullToEmpty(baseClasses)) {
				INamedElement baseClassElem = baseclass.resolveTargetElement(context);
				if(baseClassElem == null)
					continue;
				
				if(baseClassElem instanceof DefinitionClass) {
					DefinitionClass baseClassDef = (DefinitionClass) baseClassElem;
					search.evaluateScope(baseClassDef.membersScope);
				}
			}
		}
		
		public INamedElement resolveSuperClass(ISemanticContext mr) {
			
			for (Reference baseClassRef : nonNullIterable(baseClasses)) {
				INamedElement baseClass = baseClassRef.resolveTargetElement(mr);
				
				if(baseClass.getArcheType() == EArcheType.Interface) {
					continue;
				}
				if(baseClass instanceof DefinitionClass) {
					return baseClass;
				}
			}
			// TODO test implicit object reference
			return DeeLanguageIntrinsics.OBJECT_CLASS_REF.getSemantics(mr).resolveTargetElement().result;
		}
		
	}
	
}