package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.Collection;
import java.util.Collections;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.ExpSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.engine.analysis.DeeLanguageIntrinsics;

public class InitializerArray extends Expression implements IInitializer {
	
	public final NodeListView<ArrayInitEntry> entries;
	
	public InitializerArray(NodeListView<ArrayInitEntry> indexes) {
		this.entries = parentize(assertNotNull(indexes));
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.INITIALIZER_ARRAY;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, entries);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendNodeList("[", entries, ", ", "]");
	}
	
	public static class ArrayInitEntry extends ASTNode {
		public final Expression index;
		public final IInitializer value;
		
		public ArrayInitEntry(Expression index, IInitializer value) {
			this.index = parentize(index);
			this.value = parentize(assertNotNull(value));
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.ARRAY_INIT_ENTRY;
		}
		
		@Override
		public void visitChildren(IASTVisitor visitor) {
			acceptVisitor(visitor, index);
			acceptVisitor(visitor, value);
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.append(index, " : ");
			cp.append(value);
		}
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