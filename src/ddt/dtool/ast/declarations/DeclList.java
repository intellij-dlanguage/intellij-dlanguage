package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeList;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.utilbox.collections.ArrayView;

public class DeclList extends NodeList<ASTNode> {
	
	public DeclList(ArrayView<ASTNode> nodes) {
		super(nodes);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECL_LIST;
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendList(nodes, "\n", true);
	}
	
}