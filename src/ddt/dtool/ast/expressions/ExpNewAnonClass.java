package ddt.dtool.ast.expressions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.DeclBlock;
import ddt.dtool.ast.references.Reference;

public class ExpNewAnonClass extends Expression {
	
	public final ArrayView<Expression> allocArgs;
	public final ArrayView<Expression> args;
	public final ArrayView<Reference> baseClasses;
	public final DeclBlock declBody;
	
	public ExpNewAnonClass(ArrayView<Expression> allocArgs, ArrayView<Expression> args,
			ArrayView<Reference> baseClasses, DeclBlock declBody) {
		this.allocArgs = parentize(allocArgs);
		this.args = parentize(args);
		this.baseClasses = parentize(baseClasses);
		this.declBody = parentize(declBody);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_NEW_ANON_CLASS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, allocArgs);
		acceptVisitor(visitor, args);
		acceptVisitor(visitor, baseClasses);
		acceptVisitor(visitor, declBody);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("new ");
		cp.appendList("(", allocArgs, ",", ")");
		cp.append("class");
		cp.appendList("(", args, ",", ")");
		cp.append(" ");
		cp.appendList(baseClasses, ",");
		cp.append(declBody);
	}
	
}