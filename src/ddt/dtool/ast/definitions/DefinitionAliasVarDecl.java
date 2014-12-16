package ddt.dtool.ast.definitions;


import static ddt.dtool.util.NewUtils.assertCast;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.AliasSemantics.RefAliasSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.INonScopedContainer;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.misc.IteratorUtil;
import ddt.dtool.ast.declarations.Attribute;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.common.Token;

/**
 * A definition of an alias, in the old syntax:
 * <code>alias StorageClasses BasicType Declarators</code>
 * when declarator declares one or more variable-like aliases.
 * 
 * @see http://dlang.org/declaration.html#AliasDeclaration
 */
// Note implementation similarities with {@link DefinitionVariable} and {@link DefVarFragment}
public class DefinitionAliasVarDecl extends CommonDefinition implements IDeclaration, IStatement, INonScopedContainer {
	
	public final ArrayView<Attribute> aliasedAttributes;
	public final Reference target;
	public final Reference cstyleSuffix;
	public final ArrayView<AliasVarDeclFragment> fragments;
	
	public DefinitionAliasVarDecl(Token[] comments, ArrayView<Attribute> aliasedAttributes, Reference target,
		ProtoDefSymbol defId, Reference cstyleSuffix, ArrayView<AliasVarDeclFragment> fragments) {
		super(comments, defId);
		this.aliasedAttributes = parentize(aliasedAttributes);
		this.target = parentize(target);
		this.cstyleSuffix = parentize(cstyleSuffix);
		this.fragments = parentize(fragments);
		assertTrue(fragments == null || fragments.size() > 0);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_ALIAS_VAR_DECL;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, aliasedAttributes);
		acceptVisitor(visitor, target);
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, cstyleSuffix);
		acceptVisitor(visitor, fragments);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("alias ");
		cp.appendList(aliasedAttributes, " ", true);
		cp.append(target, " ");
		cp.append(defname);
		cp.append(cstyleSuffix);
		cp.appendList(", ", fragments, ", ", "");
		cp.append(";");
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Alias;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public Iterable<? extends IASTNode> getMembersIterable() {
		return IteratorUtil.nonNullIterable(fragments);
	}
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new RefAliasSemantics(this, pickedElement) {
			@Override
			protected Reference getAliasTarget() {
				return target;
			}
		};
	}
	
	/* -----------------  ----------------- */
	
	public static class AliasVarDeclFragment extends DefUnit {
		
		public AliasVarDeclFragment(ProtoDefSymbol defId) {
			super(defId);
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.ALIAS_VAR_DECL_FRAGMENT;
		}
		
		@Override
		public DefinitionAliasVarDecl getParent_Concrete() {
			return assertCast(parent, DefinitionAliasVarDecl.class);
		}
		
		@Override
		public void visitChildren(IASTVisitor visitor) {
			acceptVisitor(visitor, defname);
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.append(defname);
		}
		
		@Override
		public EArcheType getArcheType() {
			return EArcheType.Alias;
		}
		
		protected Reference getAliasTarget() {
			return getParent_Concrete().target;
		}
		
		/* -----------------  ----------------- */
		
		@Override
		protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
			return new RefAliasSemantics(this, pickedElement) {
				@Override
				protected Reference getAliasTarget() {
					return AliasVarDeclFragment.this.getAliasTarget();
				}
			};
		}
		
	}
	
}