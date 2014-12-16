package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.Collection;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.ExpSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

public class ExpParentheses extends Expression {
	
	public final boolean isDotAfterParensSyntax;
	public final Resolvable resolvable;
	
	public ExpParentheses(boolean isDotAfterParensSyntax, Resolvable resolvable) {
		this.isDotAfterParensSyntax = isDotAfterParensSyntax;
		this.resolvable = parentize(assertNotNull(resolvable));
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_PARENTHESES;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, resolvable);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("(", resolvable, ")");
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ExpSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ExpSemantics(this, pickedElement) {
		
		@Override
		public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
			return resolvable.getSemantics(context).findTargetDefElements(findOneOnly);
		}
		
	};
	}
	
}