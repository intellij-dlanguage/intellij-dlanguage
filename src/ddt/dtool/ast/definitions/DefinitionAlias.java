package ddt.dtool.ast.definitions;


import static ddt.dtool.util.NewUtils.assertCast;
import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.AliasSemantics.RefAliasSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.misc.IteratorUtil;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.common.Token;

/**
 * A definition of an alias, in the new syntax:
 * <code>alias Identifier = Type [, Identifier = Type]* ;</code>
 * 
 * Not an actual {@link CommonDefinition} class, might change in future.
 * 
 * @see http://dlang.org/declaration.html#AliasDeclaration
 */
public class DefinitionAlias extends ASTNode implements IDeclaration, IStatement, INonScopedContainer {
	
	public final Token[] comments;
	public final ArrayView<DefinitionAliasFragment> aliasFragments;
	
	public DefinitionAlias(Token[] comments, ArrayView<DefinitionAliasFragment> aliasFragments) {
		this.comments = comments;
		this.aliasFragments = parentize(aliasFragments);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_ALIAS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, aliasFragments);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("alias ");
		cp.appendList(aliasFragments, ", ", false);
		cp.append(";");
	}
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		return IteratorUtil.nonNullIterable(aliasFragments);
	}
	
	public Token[] getDefinitionContainerDocComments() {
		return comments;
	}
	
	public static class DefinitionAliasFragment extends DefUnit {
		
		public final ArrayView<ITemplateParameter> tplParams; // Since 2.064
		public final Reference target;
		
		public DefinitionAliasFragment(ProtoDefSymbol defId, ArrayView<ITemplateParameter> tplParams, 
				Reference target) {
			super(defId);
			this.tplParams = parentize(tplParams);
			this.target = parentize(target);
		}
		
		@Override
		public DefinitionAlias getParent_Concrete() {
			return assertCast(getParent(), DefinitionAlias.class);
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.DEFINITION_ALIAS_FRAGMENT;
		}
		
		@Override
		public void visitChildren(IASTVisitor visitor) {
			acceptVisitor(visitor, defname);
			acceptVisitor(visitor, tplParams);
			acceptVisitor(visitor, target);
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.append(defname);
			cp.appendList("(", tplParams, ",", ") ");
			cp.append(" = ", target);
		}
		
		@Override
		public EArcheType getArcheType() {
			return EArcheType.Alias;
		}
		
		@Override
		public Token[] getDocComments() {
			return getParent_Concrete().getDefinitionContainerDocComments();
		}
		
		/* -----------------  ----------------- */
		
		
		@Override
		protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
			return new RefAliasSemantics(this, pickedElement) {
				@Override
				protected Reference getAliasTarget() {
					return target;
				}
			};
		}
		
	}
	
}