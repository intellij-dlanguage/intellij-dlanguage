package ddt.dtool.ast.expressions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

/** 
 * This class represents a syntax error where an expression delimited by parentheses was expected.
 * It is used instead of null so that it can provide the source range of where the parentheses were expected. 
 */
public class MissingParenthesesExpression extends Expression {
	
	public MissingParenthesesExpression() {
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
		cp.append("/*( MissingExp )*/");
	}
	
	public static void appendParenthesesExp(ASTCodePrinter cp, Expression expression) {
		if(expression instanceof MissingParenthesesExpression) {
			cp.append(expression);
		} else {
			cp.append("(", expression, ") ");
		}
	}
	
}