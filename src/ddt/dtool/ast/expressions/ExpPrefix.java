package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.parser.DeeTokens;

public class ExpPrefix extends Expression {
	
	public static enum PrefixOpType {
		ADDRESS(DeeTokens.AND),
		PRE_INCREMENT(DeeTokens.INCREMENT),
		PRE_DECREMENT(DeeTokens.DECREMENT),
		REFERENCE(DeeTokens.STAR),
		NEGATIVE(DeeTokens.MINUS),
		POSITIVE(DeeTokens.PLUS),
		NOT(DeeTokens.NOT),
		COMPLEMENT(DeeTokens.CONCAT),
		
		DELETE(DeeTokens.KW_DELETE),
		;
		
		public final DeeTokens token;
		
		private PrefixOpType(DeeTokens token) {
			this.token = token;
			assertTrue(token.hasSourceValue());
		}
		
		private static final PrefixOpType[] mapping = initMapping(PrefixOpType.values());
		
		private static PrefixOpType[] initMapping(PrefixOpType[] tokenEnum) {
			PrefixOpType[] mappingArray = new PrefixOpType[DeeTokens.values().length];
			for (PrefixOpType prefixOpType : tokenEnum) {
				int ix = prefixOpType.token.ordinal();
				assertTrue(mappingArray[ix] == null);
				mappingArray[ix] = prefixOpType;
			}
			return mappingArray;
		}
		
		public static PrefixOpType tokenToPrefixOpType(DeeTokens token) {
			return mapping[token.ordinal()];
		}
		
		public String getSourceValue() {
			return token.getSourceValue();
		}
		
	}
	
	public final PrefixOpType kind;
	public final Expression exp;
	
	public ExpPrefix(PrefixOpType kind, Expression exp) {
		this.kind = assertNotNull(kind);
		this.exp = parentize(exp);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_PREFIX;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, exp);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendStrings(kind.getSourceValue(), " ");
		cp.append(exp);
	}
	
}