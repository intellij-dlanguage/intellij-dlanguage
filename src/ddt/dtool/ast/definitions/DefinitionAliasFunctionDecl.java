package ddt.dtool.ast.definitions;


import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.core.CoreUtil;
import ddt.dtool.ast.declarations.Attribute;
import ddt.dtool.ast.definitions.DefinitionFunction.AbstractFunctionElementSemantics;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.common.Token;

/**
 * A definition of an alias, in the old syntax:
 * <code>alias BasicType Declarator ;</code>
 * when Declarator declares a function.
 * 
 * @see http://dlang.org/declaration.html#AliasDeclaration
 */
public class DefinitionAliasFunctionDecl extends CommonDefinition implements IStatement {
	
	public final ArrayView<Attribute> aliasedAttributes;
	public final Reference target;
	public final ArrayView<IFunctionParameter> fnParams;
	public final ArrayView<FunctionAttributes> fnAttributes;
	
	public DefinitionAliasFunctionDecl(Token[] comments, ArrayView<Attribute> aliasedAttributes, Reference target, 
		ProtoDefSymbol defId, ArrayView<IFunctionParameter> fnParams, ArrayView<FunctionAttributes> fnAttributes) {
		super(comments, defId);
		this.aliasedAttributes = parentize(aliasedAttributes);
		this.target = parentize(target);
		this.fnParams = parentize(fnParams);
		this.fnAttributes = fnAttributes;
		assertTrue(fnAttributes == null || fnParams != null);
	}
	
	public final ArrayView<ASTNode> getParams_asNodes() {
		return CoreUtil.blindCast(fnParams);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_ALIAS_FUNCTION_DECL;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, aliasedAttributes);
		acceptVisitor(visitor, target);
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, fnParams);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("alias ");
		cp.appendList(aliasedAttributes, " ", true);
		cp.append(target, " ");
		cp.append(defname);
		cp.appendList("(", getParams_asNodes(), ",", ") ");
		cp.appendTokenList(fnAttributes, " ", true);
		cp.append(";");
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Alias;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new FunctionalAliasSemantics(this, pickedElement);
	}
	
	public class FunctionalAliasSemantics extends AbstractFunctionElementSemantics {
		
		public FunctionalAliasSemantics(INamedElement element, PickedElement<?> pickedElement) {
			super(element, pickedElement);
		}
		
		@Override
		protected IConcreteNamedElement doResolveConcreteElement() {
			return null; // TODO: test and implement
		}
		
		@Override
		public void resolveSearchInMembersScope(CommonScopeLookup search) {
			resolveSearchInMembersScopeForFunction(search, target);
		}
	}
	
}