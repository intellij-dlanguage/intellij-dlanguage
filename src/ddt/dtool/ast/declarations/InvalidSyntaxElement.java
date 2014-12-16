package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.common.IToken;

public class InvalidSyntaxElement extends ASTNode implements IDeclaration, IStatement {
	
	public final boolean isStatementContext;
	public final IToken badToken;
	
	public InvalidSyntaxElement(IToken badToken) {
		this(false, badToken);
	}
	public InvalidSyntaxElement(boolean isStatementContext, IToken badToken) {
		this.isStatementContext = isStatementContext;
		this.badToken = badToken;
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.INVALID_SYNTAX;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendToken(badToken);
	}
	
}