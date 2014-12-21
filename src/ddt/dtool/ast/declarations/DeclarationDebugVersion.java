package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.declarations.DeclarationAttrib.AttribBodySyntax;
import ddt.dtool.ast.statements.IStatement;

public class DeclarationDebugVersion extends AbstractConditionalDeclaration {
	
	public final boolean isDebug;
	public final VersionSymbol value;
	
	public DeclarationDebugVersion(boolean isDebug, VersionSymbol value, AttribBodySyntax bodySyntax, 
		ASTNode thenBody, ASTNode elseBody) {
		super(bodySyntax, thenBody, elseBody);
		this.isDebug = isDebug;
		this.value = parentize(value);
	}
	
	public DeclarationDebugVersion(boolean isDebug, VersionSymbol value, IStatement thenBody, IStatement elseBody) {
		super(thenBody, elseBody);
		this.isDebug = isDebug;
		this.value = parentize(value);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECLARATION_DEBUG_VERSION;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, value);
		acceptVisitor(visitor, body);
		acceptVisitor(visitor, elseBody);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(isDebug ? "debug " : "version ");
		cp.append("(", value, ")");
		toStringAsCodeBodyAndElseBody(cp);
	}
	
}