package ddt.dtool.ast.expressions;

import java.util.Collection;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.ExpSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.references.Reference;

public class ExpCast extends Expression {
	
	public final Reference type;
	public final Resolvable exp;
	
	public ExpCast(Reference castType, Expression exp) {
		this.exp = parentize(exp);
		this.type = parentize(castType);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_CAST;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, type);
		acceptVisitor(visitor, exp);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("cast");
		cp.append("(", type, ")");
		cp.append(exp);
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ExpSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ExpSemantics(this, pickedElement) {
		
		@Override
		public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
			return findTargetElementsForReference(context, type, findOneOnly);
		}
		
	};
	}
	
}