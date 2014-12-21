package ddt.dtool.ast.expressions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.definitions.Symbol;

public class ExpTraits extends Expression {
	
	public final Symbol traitsId;
	public final NodeListView<Resolvable> args;
	
	public ExpTraits(Symbol traitsId, NodeListView<Resolvable> args) {
		this.traitsId = parentize(traitsId);
		this.args = parentize(args);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_TRAITS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, traitsId);
		acceptVisitor(visitor, args);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("__traits");
		if(traitsId != null) {
			cp.append("(", traitsId);
			cp.appendNodeList(", ", args, ",", "");
			cp.append(")");
		}
	}
	
}