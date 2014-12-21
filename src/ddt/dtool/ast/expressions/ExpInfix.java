package ddt.dtool.ast.expressions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.dtool.parser.DeeTokens;

/**
 * An infix binary expression.
 */
public class ExpInfix extends Expression {
	
	/**
	 * Infix operations types (not just binary expressions)
	 * The ordering of these elements is important as it defines precedence (lower to higher)
	 */
	public static enum InfixOpType {
		COMMA(DeeTokens.COMMA),
		
		SLICE(DeeTokens.DOUBLE_DOT),
		
		ASSIGN(DeeTokens.ASSIGN),
		MINUS_ASSIGN(        ASSIGN, DeeTokens.MINUS_ASSIGN),
		PLUS_ASSIGN(         ASSIGN, DeeTokens.PLUS_ASSIGN), 
		DIV_ASSIGN(          ASSIGN, DeeTokens.DIV_ASSIGN), 
		MULT_ASSIGN(         ASSIGN, DeeTokens.MULT_ASSIGN), 
		MOD_ASSIGN(          ASSIGN, DeeTokens.MOD_ASSIGN), 
		POW_ASSIGN(          ASSIGN, DeeTokens.POW_ASSIGN),
		AND_ASSIGN(          ASSIGN, DeeTokens.AND_ASSIGN), 
		OR_ASSIGN(           ASSIGN, DeeTokens.OR_ASSIGN), 
		XOR_ASSIGN(          ASSIGN, DeeTokens.XOR_ASSIGN), 
		CONCAT_ASSIGN(       ASSIGN, DeeTokens.CONCAT_ASSIGN),
		LEFT_SHIFT_ASSIGN(   ASSIGN, DeeTokens.LEFT_SHIFT_ASSIGN),
		RIGHT_SHIFT_ASSIGN(  ASSIGN, DeeTokens.RIGHT_SHIFT_ASSIGN), 
		TRIPLE_RSHIFT_ASSIGN(ASSIGN, DeeTokens.TRIPLE_RSHIFT_ASSIGN),
		
		CONDITIONAL(DeeTokens.QUESTION),
		
		LOGICAL_OR(DeeTokens.LOGICAL_OR),
		
		LOGICAL_AND(DeeTokens.LOGICAL_AND),
		
		OR(DeeTokens.OR),
		
		XOR(DeeTokens.XOR),
		
		AND(DeeTokens.AND),
		
		EQUALS(DeeTokens.EQUALS),
		NOT_EQUAL(    EQUALS, DeeTokens.NOT_EQUAL),
		IS(           EQUALS, DeeTokens.KW_IS), 
		NOT_IS(       EQUALS, "!is"),
		IN(           EQUALS, DeeTokens.KW_IN),
		NOT_IN(       EQUALS, "!in"),
		LESS_THAN(    EQUALS, DeeTokens.LESS_THAN),
		LESS_EQUAL(   EQUALS, DeeTokens.LESS_EQUAL), 
		GREATER_THAN( EQUALS, DeeTokens.GREATER_THAN), 
		GREATER_EQUAL(EQUALS, DeeTokens.GREATER_EQUAL),
		LESS_GREATER( EQUALS, DeeTokens.LESS_GREATER), 
		LESS_GREATER_EQUAL(EQUALS, DeeTokens.LESS_GREATER_EQUAL),
		UNORDERED_E(  EQUALS, DeeTokens.UNORDERED_E), 
		UNORDERED(    EQUALS, DeeTokens.UNORDERED),
		UNORDERED_GE( EQUALS, DeeTokens.UNORDERED_GE), 
		UNORDERED_G(  EQUALS, DeeTokens.UNORDERED_G), 
		UNORDERED_LE( EQUALS, DeeTokens.UNORDERED_LE), 
		UNORDERED_L(  EQUALS, DeeTokens.UNORDERED_L),
		
		SHIFT(DeeTokens.LEFT_SHIFT),  
		RIGHT_SHIFT(    SHIFT, DeeTokens.RIGHT_SHIFT),
		UNSIGNED_RSHIFT(SHIFT, DeeTokens.TRIPLE_RSHIFT),
		
		ADD(DeeTokens.PLUS),
		MINUS( ADD, DeeTokens.MINUS),
		CONCAT(ADD, DeeTokens.CONCAT),
		
		MUL(DeeTokens.STAR),
		DIV(MUL, DeeTokens.DIV),
		MOD(MUL, DeeTokens.MOD),
		
		POW(DeeTokens.POW),
		
		NULL(null, (String) null), // Special entry that doesn't represent any infix operator
		;
		
		public final InfixOpType category;
		public final String sourceValue;
		public final int precedence;
		
		InfixOpType(InfixOpType category, String sourceValue) {
			this.category = category == null ? this : category;
			this.precedence = category == null ? Holder.precedenceCounter++ : category.precedence;
			this.sourceValue = sourceValue;
		}
		
		InfixOpType(InfixOpType category, DeeTokens tokenType) {
			this(category, tokenType.getSourceValue());
			int ix = tokenType.ordinal();
			assertTrue(Holder.mapping[ix] == null);
			Holder.mapping[ix] = this;
		}
		
		InfixOpType(DeeTokens tokenType) {
			this(null, tokenType);
		}
		
		protected static class Holder {
			private static int precedenceCounter = 1;
			private static final InfixOpType[] mapping = new InfixOpType[DeeTokens.values().length];
		}
		
		public static InfixOpType tokenToInfixOpType(DeeTokens token) {
			return Holder.mapping[token.ordinal()];
		}
		
	}
	
	public final Expression leftExp;
	public final Expression rightExp;
	public final InfixOpType kind;
	
	public ExpInfix(Expression left, InfixOpType kind, Expression right) {
		this.leftExp = parentize(left);
		this.kind = kind;
		assertTrue(this.kind != InfixOpType.NULL);
		this.rightExp = parentize(right);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.EXP_INFIX;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, leftExp);
		acceptVisitor(visitor, rightExp);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append(leftExp);
		cp.appendStrings(" ", kind.sourceValue, " "); // Some operators have alpha so we need spaces to sep.
		cp.append(rightExp);
	}
	
}