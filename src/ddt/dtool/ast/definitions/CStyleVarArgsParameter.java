package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;


/** 
 * C-style var args paramater, as in: <br>
 * <code> ... </code>
 */
public class CStyleVarArgsParameter extends ASTNode implements IFunctionParameter {
	
	public CStyleVarArgsParameter() {
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.VAR_ARGS_PARAMETER;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("...");
	}
	
	@Override
	public boolean isVariadic() {
		return true;
	}
	
	@Override
	public String getTypeStringRepresentation() {
		return toStringAsCode();
	}
	
	@Override
	public String getInitializerStringRepresentation() {
		return null;
	}
	
	@Override
	public String toStringForFunctionSignature() {
		return toStringAsCode();
	}

}