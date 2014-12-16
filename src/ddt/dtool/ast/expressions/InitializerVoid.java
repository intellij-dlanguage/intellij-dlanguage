package ddt.dtool.ast.expressions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

public class InitializerVoid extends Initializer {
	
	public InitializerVoid() {
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.INITIALIZER_VOID;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("void");
	}
	
}