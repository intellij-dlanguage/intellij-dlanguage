package ddt.dtool.ast.definitions;

import ddt.melnorme.lang.tooling.parser.ISourceRepresentation;
import ddt.dtool.parser.DeeTokens;

public enum FunctionAttributes implements ISourceRepresentation {
	CONST(DeeTokens.KW_CONST.getSourceValue()), 
	IMMUTABLE(DeeTokens.KW_IMMUTABLE.getSourceValue()), 
	INOUT(DeeTokens.KW_INOUT.getSourceValue()), 
	SHARED(DeeTokens.KW_SHARED.getSourceValue()),
	
	PURE(DeeTokens.KW_PURE.getSourceValue()),
	NOTHROW(DeeTokens.KW_NOTHROW.getSourceValue()),
	
	AT_PROPERTY("@property"),
	AT_SAFE("@safe"),
	AT_TRUSTED("@trusted"),
	AT_SYSTEM("@system"),
	AT_DISABLE("@disable"),
	;
	public final String sourceValue;
	
	private FunctionAttributes(String sourceValue) {
		this.sourceValue = sourceValue;
	}
	
	@Override
	public String getSourceValue() {
		return sourceValue;
	}
	
	public static FunctionAttributes fromToken(DeeTokens token) {
		switch (token) {
		case KW_CONST: return CONST;
		case KW_IMMUTABLE: return IMMUTABLE;
		case KW_INOUT: return INOUT;
		case KW_SHARED: return SHARED;
		case KW_PURE: return PURE;
		case KW_NOTHROW: return NOTHROW;
		
		default: return null;
		}
	}
	
	//This could be slightly optimized with a hash table
	public static FunctionAttributes fromCustomAttribId(String customAttribName) {
		if(customAttribName.equals("property")) return AT_PROPERTY;
		if(customAttribName.equals("safe")) return AT_SAFE;
		if(customAttribName.equals("trusted")) return AT_TRUSTED;
		if(customAttribName.equals("system")) return AT_SYSTEM;
		if(customAttribName.equals("disable")) return AT_DISABLE;
		return null;
	}
}