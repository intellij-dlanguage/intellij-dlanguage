package ddt.dtool.ast.expressions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

/** 
 * This class represents a syntax error where an expression was expected.
 * It is used instead of null so that it can provide the source range of where the expression was expected. 
 */
public class MissingExpression extends Expression {
	
	public MissingExpression() {
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.MISSING_EXPRESSION;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("/*MissingExp*/");
	}
	
}