package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.declarations.AbstractConditionalDeclaration.VersionSymbol;

/**
 * Debug/Version specification declaration
 */
public class DeclarationDebugVersionSpec extends ASTNode implements IDeclaration {
	
	public final boolean isDebug;
	public final VersionSymbol value;
	
	public DeclarationDebugVersionSpec(boolean isDebug, VersionSymbol value) {
		this.isDebug = isDebug;
		this.value = parentize(value);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECLARATION_DEBUG_VERSION_SPEC;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, value);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(isDebug ? "debug = " : "version = ");
		cp.append(value);
		cp.append(";");
	}
	
}