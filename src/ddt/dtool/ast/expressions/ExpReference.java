package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.Collection;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.references.Reference;

/**
 * An Expression wrapping a {@link Reference}
 */
public class ExpReference extends Expression {
	
	public final Reference ref;
	
	public ExpReference(Reference ref) {
		assertNotNull(ref);
		this.ref = parentize(ref);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_REFERENCE;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, ref);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(ref);
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ResolvableSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ResolvableSemantics(this, pickedElement) {
		
			@Override
			public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
				return ref.getSemantics(context).findTargetDefElements(findOneOnly);
			}
			
			@Override
			public Collection<INamedElement> resolveTypeOfUnderlyingValue() {
				return ref.getSemantics(context).resolveTypeOfUnderlyingValue();
			}
			
		};
	}
	
}