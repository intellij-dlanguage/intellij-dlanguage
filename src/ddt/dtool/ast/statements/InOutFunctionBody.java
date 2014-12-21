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
package ddt.dtool.ast.statements;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

public class InOutFunctionBody extends FunctionBody implements IFunctionBody {
	
	public final boolean isOutIn; // Indicates if 'out' block appears before 'in' block in the source
	public final BlockStatement inBlock;
	public final FunctionBodyOutBlock outBlock;
	
	public InOutFunctionBody(boolean isOutIn, BlockStatement inBlock, FunctionBodyOutBlock outBlock, 
		BlockStatement bodyBlock) {
		super(bodyBlock, false);
		this.isOutIn = isOutIn;
		this.inBlock = parentize(inBlock);
		this.outBlock = parentize(outBlock);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.IN_OUT_FUNCTION_BODY;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, isOutIn ? outBlock : inBlock);
		acceptVisitor(visitor, isOutIn ? inBlock : outBlock);
		acceptVisitor(visitor, bodyBlock);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		if(isOutIn) {
			cp.append(outBlock);
			cp.append("in", inBlock);
		} else {
			cp.append("in", inBlock);
			cp.append(outBlock);
		}
		cp.append("body", bodyBlock);
	}
	
}