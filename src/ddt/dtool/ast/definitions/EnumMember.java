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
package ddt.dtool.ast.definitions;

import static ddt.dtool.util.NewUtils.assertCast;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.dtool.ast.definitions.DefinitionEnum.EnumBody;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.IInitializer;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.engine.analysis.CommonDefVarSemantics;
import ddt.dtool.engine.analysis.IVarDefinitionLike;

public class EnumMember extends DefUnit implements IVarDefinitionLike {
	
	public final Reference type;
	public final Expression value;
	
	public EnumMember(Reference type, ProtoDefSymbol defId, Expression value) {
		super(defId);
		this.type = parentize(type);
		this.value = parentize(value);
	}
	
	@Override
	protected EnumBody getParent_Concrete() {
		return assertCast(parent, EnumBody.class);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.ENUM_MEMBER;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, value);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(type, " ");
		cp.append(defname);
		cp.append(" = ", value);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.EnumMember;
	}
	
	protected Reference getEffectiveTypeReference() {
		return type != null ? type : getEnumParentType();
	}
	
	public Reference getEnumParentType() {
		EnumBody enumBody = getParent_Concrete();
		ASTNode parentEnum = enumBody.getParent();
		if(parentEnum instanceof DeclarationEnum) {
			DeclarationEnum declarationEnum = (DeclarationEnum) parentEnum;
			return declarationEnum.type; 
		}
		if(parentEnum instanceof DefinitionEnum) {
			DefinitionEnum definitionEnum = (DefinitionEnum) parentEnum;
			return definitionEnum.type; 
		} 
		return null;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new CommonDefVarSemantics(this, pickedElement) {
			
			@Override
			protected Resolvable getTypeReference() {
				return getEffectiveTypeReference();
			}
		};
	}
	
	@Override
	public Reference getDeclaredType() {
		return type;
	}
	
	@Override
	public IInitializer getDeclaredInitializer() {
		return value;
	}
	
}