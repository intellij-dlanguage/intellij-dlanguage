package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeList;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.engine.scoping.IScopeElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.definitions.DefinitionAggregate.IAggregateBody;
import ddt.dtool.ast.definitions.DefinitionClass;

public class DeclBlock extends NodeList<ASTNode> implements IAggregateBody, IScopeElement {
	
	public DeclBlock(ArrayView<ASTNode> nodes) {
		super(nodes);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DECL_BLOCK;
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendList("{\n", nodes, "\n", "\n}\n");
	}
	
	@Override
	public void resolveSearchInScope(CommonScopeLookup search) {
		search.evaluateScopeNodeList(nodes, false);
		// TODO: a more typesafe alternative to this check
		if(getParent() instanceof DefinitionClass) {
			DefinitionClass definitionClass = (DefinitionClass) getParent();
			definitionClass.getSemantics(search.modResolver).resolveSearchInSuperScopes(search);
		}
	}
	
}