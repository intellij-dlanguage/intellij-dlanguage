package ddt.dtool.ast.expressions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

public class ExpAssert extends Expression {
	
	public final Expression exp;
	public final Expression msg;
	
	public ExpAssert(Expression exp, Expression msg) {
		this.exp = parentize(exp);
		this.msg = parentize(msg);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_MIXIN_STRING;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, exp);
		acceptVisitor(visitor, msg);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("assert");
		if(exp != null) {
			cp.append("(", exp);
			cp.append(",", msg);
			cp.append(")");
		}
	}
}