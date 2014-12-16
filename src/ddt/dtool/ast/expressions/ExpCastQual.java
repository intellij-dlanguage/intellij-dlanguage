package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.Collection;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.ExpSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

public class ExpCastQual extends Expression {
	
	public static enum CastQualifiers {
		CONST("const"),
		CONST_SHARED("const shared"),
		INOUT("inout"),
		INOUT_SHARED("inout shared"),
		SHARED("shared"),
		SHARED_CONST("shared const"),
		SHARED_INOUT("shared inout"),
		IMMUTABLE("immutable"),
		;
		
		private String sourceValue;
		
		private CastQualifiers(String sourceValue) {
			this.sourceValue = sourceValue;
		}
		public String toStringAsCode() {
			return sourceValue;
		}
	}
	
	public final CastQualifiers castQualifier;
	public final Resolvable exp;
	
	public ExpCastQual(CastQualifiers castQualifier, Expression exp) {
		this.castQualifier = assertNotNull(castQualifier);
		this.exp = parentize(exp);
		assertTrue(exp == null || castQualifier != null);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_CAST_QUAL;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, exp);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("cast");
		cp.appendStrings("(", castQualifier.toStringAsCode(), ")");
		cp.append(exp);
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ExpSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ExpSemantics(this, pickedElement) {
		
		@Override
		public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
			return findTargetElementsForReference(context, exp, findOneOnly);
		}
		
	};
	}
	
}