package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;

public class ExpLiteralMapArray extends Expression {
	
	public final NodeListView<MapArrayLiteralKeyValue> entries;
	
	public ExpLiteralMapArray(NodeListView<MapArrayLiteralKeyValue> entries) {
		this.entries = parentize(assertNotNull(entries));
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_LITERAL_MAPARRAY;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, entries);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendNodeList("[ ", entries, ", ", " ]");
	}
	
	
	public static class MapArrayLiteralKeyValue extends ASTNode {
		public final Expression key;
		public final Expression value;
		
		public MapArrayLiteralKeyValue(Expression key, Expression value) {
			this.key = parentize(assertNotNull(key));
			this.value = parentize(value);
		}
		
		@Override
		public ASTNodeTypes getNodeType() {
			return ASTNodeTypes.MAPARRAY_ENTRY;
		}
		
		@Override
		public void visitChildren(IASTVisitor visitor) {
			acceptVisitor(visitor, key);
			acceptVisitor(visitor, value);
		}
		
		@Override
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.append(key);
			cp.append(" : ");
			cp.append(value);
		}
	}
	
}