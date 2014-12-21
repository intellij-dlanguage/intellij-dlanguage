package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.references.RefIdentifier;
import ddt.dtool.ast.statements.IStatement;

/**
 * @see http://dlang.org/declaration.html#AliasThisDeclaration
 * 
 * (Technically not allowed as statement, but parse so anyways.)
 */
public class DeclarationAliasThis extends ASTNode implements IDeclaration, IStatement {
	
	public final boolean isAssignSyntax;
	public final RefIdentifier targetMember;
	
	public DeclarationAliasThis(boolean isAssignSyntax, RefIdentifier targetMember) {
		this.isAssignSyntax = isAssignSyntax;
		this.targetMember = parentize(targetMember);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECLARATION_ALIAS_THIS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, targetMember);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("alias ");
		if(isAssignSyntax) {
			cp.append("this");
			cp.append(" = ", targetMember);
		} else {
			cp.append(targetMember);
			cp.append(" this");
		}
		cp.append(";");
	}
	
}
