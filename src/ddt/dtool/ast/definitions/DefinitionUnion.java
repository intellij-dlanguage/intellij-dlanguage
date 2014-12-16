package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.parser.common.Token;

/**
 * A definition of an union aggregate.
 */
public class DefinitionUnion extends DefinitionAggregate {
	
	public DefinitionUnion(Token[] comments, ProtoDefSymbol defId, ArrayView<ITemplateParameter> tplParams,
		Expression tplConstraint, IAggregateBody aggrBody) {
		super(comments, defId, tplParams, tplConstraint, aggrBody);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_UNION;
	}
	
	@Override	
	public void visitChildren(IASTVisitor visitor) {
		acceptNodeChildren(visitor);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		aggregateToStringAsCode(cp, "union ", true);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Union;
	}
	
}