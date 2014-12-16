package ddt.dtool.ast.expressions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.references.Reference;

public class ExpTypeId extends Expression {
	
	public final Reference typeArgument;
	public final Expression expressionArgument;
	
	public ExpTypeId(Reference typeArgument) {
		this.typeArgument = parentize(typeArgument);
		this.expressionArgument = null;
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_TYPEID;
	}
	
	public ExpTypeId(Expression expressionArgument) {
		this.typeArgument = null;
		this.expressionArgument = parentize(expressionArgument);
	}
	
	public Resolvable getArgument() {
		return typeArgument != null ? typeArgument : expressionArgument;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, getArgument());
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("typeid");
		cp.append("(", getArgument(), ")");
	}
	
}