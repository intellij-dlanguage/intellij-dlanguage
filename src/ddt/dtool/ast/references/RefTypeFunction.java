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

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.TypeReferenceSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.core.CoreUtil;
import ddt.dtool.ast.definitions.FunctionAttributes;
import ddt.dtool.ast.definitions.IFunctionParameter;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.engine.analysis.DeeLanguageIntrinsics;
import ddt.dtool.engine.analysis.DeeLanguageIntrinsics.DeeIntrinsicType;

/**
 * A function pointer type
 */
public class RefTypeFunction extends CommonNativeTypeReference {
	
	public final Reference retType;
	public final boolean isDelegate;
	public final ArrayView<IFunctionParameter> params;
	public final ArrayView<FunctionAttributes> fnAttributes;
	
	public RefTypeFunction(Reference retType, boolean isDelegate, ArrayView<IFunctionParameter> params, 
		ArrayView<FunctionAttributes> fnAttributes) {
		this.retType = parentize(retType);
		this.isDelegate = isDelegate;
		this.params = parentize(params);
		this.fnAttributes = fnAttributes;
	}
	
	public final ArrayView<ASTNode> getParams_asNodes() {
		return CoreUtil.blindCast(params);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.REF_TYPE_FUNCTION;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, retType);
		acceptVisitor(visitor, params);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(retType, " ");
		cp.append(isDelegate ? "delegate" : "function");
		cp.appendList("(", getParams_asNodes(), ",", ") ");
		cp.appendTokenList(fnAttributes, " ", true);
	}
	
	/* -----------------  ----------------- */

	@Override
	protected TypeReferenceSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeReferenceSemantics(this, pickedElement) {
		
			@Override
			public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
				return Resolvable.wrapResult(intrinsicFunctionTypeInstance);
			}
			
		};
	}
	
	public static final IntrinsicFunctionType intrinsicFunctionTypeInstance = new IntrinsicFunctionType();
	
	public static class IntrinsicFunctionType extends DeeIntrinsicType {
		public IntrinsicFunctionType() {
			DeeLanguageIntrinsics.D2_063_intrinsics.super("<funtion>", null);
		}
	}
	
}