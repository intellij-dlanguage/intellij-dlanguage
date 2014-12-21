package ddt.dtool.ast.definitions;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.parser.DeeTokens;
import ddt.dtool.parser.common.LexElement;

public interface IFunctionParameter extends IASTNode {
	
	public static enum FunctionParamAttribKinds {
		AUTO(DeeTokens.KW_AUTO),
		
		CONST(DeeTokens.KW_CONST), 
		IMMUTABLE(DeeTokens.KW_IMMUTABLE), 
		INOUT(DeeTokens.KW_INOUT), 
		SHARED(DeeTokens.KW_SHARED),
		
		FINAL(DeeTokens.KW_FINAL),
		IN(DeeTokens.KW_IN),
		LAZY(DeeTokens.KW_LAZY),
		OUT(DeeTokens.KW_OUT),
		REF(DeeTokens.KW_REF),
		SCOPE(DeeTokens.KW_SCOPE),
		;
		public final DeeTokens token;

		private FunctionParamAttribKinds(DeeTokens token) {
			this.token = token;
		}
		
		public String getSourceValue() {
			return token.getSourceValue();
		}
		
		public static FunctionParamAttribKinds fromToken(DeeTokens token) {
			switch (token) {
			case KW_AUTO: return AUTO;
			case KW_CONST: return CONST;
			case KW_IMMUTABLE: return IMMUTABLE;
			case KW_INOUT: return INOUT;
			case KW_SHARED: return SHARED;
			case KW_FINAL: return FINAL;
			case KW_IN: return IN;
			case KW_LAZY: return LAZY;
			case KW_OUT: return OUT;
			case KW_REF: return REF;
			case KW_SCOPE: return SCOPE;
			default: return null;
			}
		}
		static { 
			for (FunctionParamAttribKinds attrib : values()) {
				assertTrue(FunctionParamAttribKinds.fromToken(attrib.token) == attrib);
			}
		}
	}
	
	public static class FnParameterAttributes {
		
		public final ArrayView<LexElement> attribs;
		
		public FnParameterAttributes(ArrayView<LexElement> attribList) {
			attribs = assertNotNull(attribList);
			for (LexElement token : attribs) {
				assertTrue(FunctionParamAttribKinds.fromToken(token.type) != null);
			}
		}
		
		public void toStringAsCode(ASTCodePrinter cp) {
			cp.appendTokenList(attribs, " ", true);
		}
		
		public static final FnParameterAttributes EMPTY_FN_PARAMS = 
			new FnParameterAttributes(ArrayView.create(new LexElement[0]));
		
		public static FnParameterAttributes create(ArrayView<LexElement> attribList) {
			return attribList == null ? EMPTY_FN_PARAMS : new FnParameterAttributes(attribList);
		}
		
	}
	
	boolean isVariadic();
	
	/** @return a string representation of the type of this function parameter. */
	String getTypeStringRepresentation();
	
	/** @return a string representation of the initializer. Can be null.*/
	String getInitializerStringRepresentation();
	
	/** @return a string representation of this parameter, for use as part of a function signature. */
	String toStringForFunctionSignature();

}