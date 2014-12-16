package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.parser.common.Token;

/**
 * A definition of an interface aggregate. 
 */
public class DefinitionInterface extends DefinitionClass {
	
	public DefinitionInterface(Token[] comments, ProtoDefSymbol defId, ArrayView<ITemplateParameter> tplParams,
		Expression tplConstraint, ArrayView<Reference> baseClasses, boolean baseClassesAfterConstraint, 
		IAggregateBody aggrBody) 
	{
		super(comments, defId, tplParams, tplConstraint, baseClasses, baseClassesAfterConstraint, aggrBody);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_INTERFACE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptNodeChildren(visitor);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		classLikeToStringAsCode(cp, "interface ");
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Interface;
	}
	
}