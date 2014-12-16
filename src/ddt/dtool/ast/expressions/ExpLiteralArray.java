package ddt.dtool.ast.expressions;

import java.util.Collection;
import java.util.Collections;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.ExpSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.engine.analysis.DeeLanguageIntrinsics;

public class ExpLiteralArray extends Expression {
	
	public final NodeListView<Expression> elements;
	
	public ExpLiteralArray(NodeListView<Expression> elements) {
		this.elements = parentize(elements);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_LITERAL_ARRAY;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, elements);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendNodeList("[ ", elements, ", " , " ]");
	}
	
	/* -----------------  ----------------- */
	
	@Override
	protected ExpSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ExpSemantics(this, pickedElement) {
		
		@Override
		public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
			return Collections.<INamedElement>singleton(DeeLanguageIntrinsics.D2_063_intrinsics.dynArrayType);
		}
		
	};
	}
	
}