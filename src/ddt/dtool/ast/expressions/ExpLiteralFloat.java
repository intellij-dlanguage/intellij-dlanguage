package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.Collection;
import java.util.Collections;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.ExpSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.engine.analysis.DeeLanguageIntrinsics;
import ddt.dtool.parser.common.IToken;

public class ExpLiteralFloat extends Expression {
	
	public final IToken floatNum;
	
	public ExpLiteralFloat(IToken floatNum) {
		this.floatNum = assertNotNull(floatNum);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_LITERAL_FLOAT;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendToken(floatNum);
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ExpSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ExpSemantics(this, pickedElement) {
		
		@Override
		public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
			return Collections.<INamedElement>singleton(DeeLanguageIntrinsics.D2_063_intrinsics.float_type);
		}
		
	};
	}
	
}