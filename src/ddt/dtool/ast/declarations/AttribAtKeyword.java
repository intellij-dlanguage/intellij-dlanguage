package ddt.dtool.ast.declarations;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.ast.definitions.Symbol;

public class AttribAtKeyword extends Attribute {
	
	public final Symbol attribId;
	
	public AttribAtKeyword(Symbol attribId) {
		this.attribId = parentize(assertNotNull(attribId));
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.ATTRIB_AT_KEYWORD;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, attribId);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("@", attribId);
	}
	
}