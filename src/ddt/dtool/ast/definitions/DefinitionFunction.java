package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IFunctionBody;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.common.Token;

/**
 * A definition of a function.
 */
public class DefinitionFunction extends AbstractFunctionDefinition implements IDeclaration, IStatement, 
	IConcreteNamedElement {
	
	public final Reference retType;
	
	public DefinitionFunction(Token[] comments, Reference retType, ProtoDefSymbol defId,
		ArrayView<ITemplateParameter> tplParams, ArrayView<IFunctionParameter> fnParams,
		ArrayView<FunctionAttributes> fnAttributes, Expression tplConstraint, IFunctionBody fnBody)
	{
		super(comments, defId, tplParams, fnParams, fnAttributes, tplConstraint, fnBody);
		this.retType = parentize(retType);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_FUNCTION;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, retType);
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, tplParams);
		acceptVisitor(visitor, fnParams);
		acceptVisitor(visitor, tplConstraint);
		acceptVisitor(visitor, fnBody);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(retType, " ");
		toStringAsCode_fromDefId(cp);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Function;
	}
	
	public INamedElement findReturnTypeTargetDefUnit(ISemanticContext context) {
		if(retType == null) 
			return null;
		return retType.resolveTargetElement(context);
	}
	
	@Override
	public String getExtendedName() {
		return getName() + toStringParametersForSignature();
	}
	
	public String toStringParametersForSignature() {
		return toStringParametersForSignature(fnParams);
	}
	
	public static String toStringParametersForSignature(ArrayView<IFunctionParameter> params) {
		if(params == null) 
			return "";
		String strParams = "(";
		for (int i = 0; i < params.size(); i++) {
			if(i != 0)
				strParams += ", ";
			strParams += params.get(i).toStringForFunctionSignature();
		}
		return strParams + ")";
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new FunctionElementSemantics(this, pickedElement);
	}
	
	public static abstract class AbstractFunctionElementSemantics extends NamedElementSemantics {
		
		public AbstractFunctionElementSemantics(INamedElement element, PickedElement<?> pickedElement) {
			super(element, pickedElement);
		}
		
		@SuppressWarnings("unused")
		public static void resolveSearchInMembersScopeForFunction(CommonScopeLookup search, Reference retType) {
			// Do nothing, a function has no members scope
			// TODO: except for implicit function calls syntax, that needs to be implemented
		}
		
		@Override
		public final INamedElement resolveTypeForValueContext() {
			// TODO implicit function call
			return null;
		}
		
	}
	
	public static class FunctionElementSemantics extends AbstractFunctionElementSemantics {
		
		protected final DefinitionFunction function;
		
		public FunctionElementSemantics(DefinitionFunction defFunction, PickedElement<?> pickedElement) {
			super(defFunction, pickedElement);
			this.function = defFunction;
		}
		
		@Override
		protected IConcreteNamedElement doResolveConcreteElement() {
			return function;
		}
		
		@Override
		public void resolveSearchInMembersScope(CommonScopeLookup search) {
			resolveSearchInMembersScopeForFunction(search, function.retType);
		}
	}
	
}