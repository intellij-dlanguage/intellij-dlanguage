package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.statements.IStatement;

public class MissingDeclaration extends ASTNode implements IDeclaration 
, IStatement //TODO: remove this eventually
{
	
	public MissingDeclaration() {
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.MISSING_DECLARATION;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
	}
	
}