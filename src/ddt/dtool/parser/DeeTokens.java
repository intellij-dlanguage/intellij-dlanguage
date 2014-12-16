/*******************************************************************************
 * Copyright (c) 2012, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.parser;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

enum SubChannelTokenFlag { FLAG }

/**
 * Tokens produced by the D Lexer.
 * Some of these tokens are synthetic - not actually produced by the Lexer - 
 * but used by the parser to group other tokens into categories. 
 */
public enum DeeTokens {
	
	EOF,
	
	INVALID_TOKEN(SubChannelTokenFlag.FLAG),
	
	LINE_END(SubChannelTokenFlag.FLAG),
	WHITESPACE(SubChannelTokenFlag.FLAG),
	
	GROUP_COMMENT(SubChannelTokenFlag.FLAG),
	COMMENT_MULTI    (GROUP_COMMENT), 
	COMMENT_NESTED   (GROUP_COMMENT), 
	COMMENT_LINE     (GROUP_COMMENT),
	DOCCOMMENT_MULTI (GROUP_COMMENT), 
	DOCCOMMENT_NESTED(GROUP_COMMENT),
	DOCCOMMENT_LINE  (GROUP_COMMENT),
	
	SCRIPT_LINE_INTRO(SubChannelTokenFlag.FLAG),
	SPECIAL_TOKEN_LINE(SubChannelTokenFlag.FLAG),
	
	
	IDENTIFIER,
	
	GROUP_STRING(), // Note: special keyword tokens also have this category
	STRING_WYSIWYG(GROUP_STRING),
	STRING_DQ     (GROUP_STRING), 
	STRING_HEX    (GROUP_STRING), 
	STRING_DELIM  (GROUP_STRING), 
	STRING_TOKENS (GROUP_STRING),
	
	CHARACTER,
	
	GROUP_INTEGER(), // Note: special keyword tokens also have this category
	INTEGER_DECIMAL(GROUP_INTEGER), 
	INTEGER_BINARY (GROUP_INTEGER), 
	INTEGER_OCTAL  (GROUP_INTEGER), 
	INTEGER_HEX    (GROUP_INTEGER),
	
	GROUP_FLOAT(),
	FLOAT_DECIMAL(GROUP_FLOAT), 
	FLOAT_HEX    (GROUP_FLOAT),
	
	OPEN_PARENS("("),
	CLOSE_PARENS(")"),
	OPEN_BRACE("{"),
	CLOSE_BRACE("}"),
	OPEN_BRACKET("["),
	CLOSE_BRACKET("]"),
	SEMICOLON(";"),
	COLON(":"),
	
	
	QUESTION("?"),
	COMMA(","),
	DOLLAR("$"),
	AT("@"),
	
	DOT("."), 
	DOUBLE_DOT(".."), 
	TRIPLE_DOT("..."),
	
	DECREMENT("--"), 
	INCREMENT("++"),	
	MINUS("-"), MINUS_ASSIGN("-="), 
	PLUS("+"), PLUS_ASSIGN("+="), 
	DIV("/"), DIV_ASSIGN("/="), 
	STAR("*"), MULT_ASSIGN("*="), 
	MOD("%"), MOD_ASSIGN("%="),
	POW("^^"), POW_ASSIGN("^^="), 
	
	AND("&"), AND_ASSIGN("&="), 
	OR("|"), OR_ASSIGN("|="), 
	XOR("^"), XOR_ASSIGN("^="),
	CONCAT("~"), CONCAT_ASSIGN("~="),
	LOGICAL_AND("&&"), 
	LOGICAL_OR("||"), 
	
	LAMBDA("=>"),
	
	ASSIGN("="), EQUALS("=="),
	NOT("!"), NOT_EQUAL("!="),
	
	LESS_THAN("<"), LESS_EQUAL("<="), GREATER_THAN(">"), GREATER_EQUAL(">="),
	LESS_GREATER("<>"), LESS_GREATER_EQUAL("<>="),
	UNORDERED_E("!<>"), UNORDERED("!<>="), 
	UNORDERED_GE("!<"), UNORDERED_G("!<="), UNORDERED_LE("!>"), UNORDERED_L("!>="),
	
	LEFT_SHIFT("<<"), LEFT_SHIFT_ASSIGN("<<="), 
	RIGHT_SHIFT(">>"), RIGHT_SHIFT_ASSIGN(">>="), 
	TRIPLE_RSHIFT(">>>"), TRIPLE_RSHIFT_ASSIGN(">>>="),
	
	
	GROUP_PRIMITIVE_KW(),
	KW_BOOL("bool",     GROUP_PRIMITIVE_KW),
	KW_VOID("void",     GROUP_PRIMITIVE_KW),
	KW_BYTE("byte",     GROUP_PRIMITIVE_KW), KW_UBYTE("ubyte",     GROUP_PRIMITIVE_KW),
	KW_SHORT("short",   GROUP_PRIMITIVE_KW), KW_USHORT("ushort",   GROUP_PRIMITIVE_KW),
	KW_INT("int",       GROUP_PRIMITIVE_KW), KW_UINT("uint",       GROUP_PRIMITIVE_KW), 
	KW_LONG("long",     GROUP_PRIMITIVE_KW), KW_ULONG("ulong",     GROUP_PRIMITIVE_KW),
	KW_CENT("cent",     GROUP_PRIMITIVE_KW), KW_UCENT("ucent",     GROUP_PRIMITIVE_KW),  
	
	KW_CHAR("char",     GROUP_PRIMITIVE_KW), 
	KW_WCHAR("wchar",   GROUP_PRIMITIVE_KW), 
	KW_DCHAR("dchar",   GROUP_PRIMITIVE_KW),
	
	KW_FLOAT("float",    GROUP_PRIMITIVE_KW), 
	KW_DOUBLE("double",  GROUP_PRIMITIVE_KW), 
	KW_REAL("real",      GROUP_PRIMITIVE_KW),
	KW_IFLOAT("ifloat",  GROUP_PRIMITIVE_KW), 
	KW_IDOUBLE("idouble",GROUP_PRIMITIVE_KW), 
	KW_IREAL("ireal",    GROUP_PRIMITIVE_KW),  
	KW_CFLOAT("cfloat",  GROUP_PRIMITIVE_KW), 
	KW_CDOUBLE("cdouble",GROUP_PRIMITIVE_KW), 
	KW_CREAL("creal",    GROUP_PRIMITIVE_KW),
	
	
	GROUP_PROTECTION_KW(),
	KW_PRIVATE("private",     GROUP_PROTECTION_KW), 
	KW_PACKAGE("package",     GROUP_PROTECTION_KW), 
	KW_PROTECTED("protected", GROUP_PROTECTION_KW),
	KW_PUBLIC("public",       GROUP_PROTECTION_KW), 
	KW_EXPORT("export",       GROUP_PROTECTION_KW),
	
	GROUP_ATTRIBUTE_KW(),
	
	KW_ABSTRACT("abstract",         GROUP_ATTRIBUTE_KW),
	KW_DEPRECATED("deprecated",     GROUP_ATTRIBUTE_KW), 
	KW_FINAL("final",               GROUP_ATTRIBUTE_KW), 
	KW_NOTHROW("nothrow",           GROUP_ATTRIBUTE_KW), 
	KW_OVERRIDE("override",         GROUP_ATTRIBUTE_KW), 
	KW_PURE("pure",                 GROUP_ATTRIBUTE_KW), 
	KW_SCOPE("scope",               GROUP_ATTRIBUTE_KW), 
	KW_STATIC("static",             GROUP_ATTRIBUTE_KW), 
	KW_SYNCHRONIZED("synchronized", GROUP_ATTRIBUTE_KW),
	KW_REF("ref"                  , GROUP_ATTRIBUTE_KW),
	
	KW_CONST("const",               GROUP_ATTRIBUTE_KW),
	KW_IMMUTABLE("immutable",       GROUP_ATTRIBUTE_KW),
	KW_INOUT("inout",               GROUP_ATTRIBUTE_KW), 
	KW_SHARED("shared",             GROUP_ATTRIBUTE_KW),
	
	
	KW_AUTO("auto"),
	KW_ALIAS("alias"), 
	KW_ALIGN("align"), 
	KW_ASM("asm"), 
	KW_ASSERT("assert"),
	KW_BODY("body"), 
	KW_BREAK("break"), 
	KW_CASE("case"), 
	KW_CAST("cast"), 
	KW_CATCH("catch"), 
	KW_CLASS("class"), 
	KW_CONTINUE("continue"),
	KW_DEBUG("debug"), 
	KW_DEFAULT("default"), 
	KW_DELEGATE("delegate"), 
	KW_DELETE("delete"),  
	KW_DO("do"), 
	KW_ELSE("else"), 
	KW_ENUM("enum"), 
	KW_EXTERN("extern"),
	KW_FALSE("false"), 
	KW_FINALLY("finally"), 
	KW_FOR("for"),
	KW_FOREACH("foreach"), 
	KW_FOREACH_REVERSE("foreach_reverse"),
	KW_FUNCTION("function"),
	KW_GOTO("goto"),
	KW_IF("if"),
	KW_IMPORT("import"),
	KW_IN("in"),
	KW_INTERFACE("interface"),
	KW_INVARIANT("invariant"),
	KW_IS("is"),
	KW_LAZY("lazy"),
	KW_MACRO("macro"),
	KW_MIXIN("mixin"),
	KW_MODULE("module"),
	KW_NEW("new"),
	KW_NULL("null"),
	KW_OUT("out"),
	KW_PRAGMA("pragma"),
	KW_RETURN("return"),
	KW_STRUCT("struct"),
	KW_SUPER("super"),
	KW_SWITCH("switch"),
	KW_TEMPLATE("template"),
	KW_THIS("this"),
	KW_THROW("throw"),
	KW_TRUE("true"),
	KW_TRY("try"),
	KW_TYPEDEF("typedef"), // This is deprecated, but we still have it as keyword
	KW_TYPEID("typeid"),
	KW_TYPEOF("typeof"),
	KW_UNION("union"),
	KW_UNITTEST("unittest"),
	KW_VERSION("version"),
	KW_VOLATILE("volatile"),
	KW_WHILE("while"),
	KW_WITH("with"),
	
	KW___TRAITS("__traits"),
	KW___GSHARED("__gshared", GROUP_ATTRIBUTE_KW), 
	KW___THREAD("__thread",  GROUP_ATTRIBUTE_KW),
	KW___VECTOR("__vector"),
	
	
	KW___FILE__("__FILE__", GROUP_STRING), 
	KW___LINE__("__LINE__", GROUP_INTEGER), 
	KW___MODULE__("__MODULE__", GROUP_STRING), 
	KW___FUNCTION__("__FUNCTION__", GROUP_STRING), 
	KW___PRETTY_FUNCTION__("__PRETTY_FUNCTION__", GROUP_STRING),
	
	KW___DATE__("__DATE__", GROUP_STRING),
	KW___TIME__("__TIME__", GROUP_STRING),
	KW___TIMESTAMP__("__TIMESTAMP__", GROUP_STRING),
	KW___VENDOR__("__VENDOR__", GROUP_STRING),
	KW___VERSION__("__VERSION__", GROUP_INTEGER),
	
	;
	
	public final String sourceValue;
	public final boolean isSubChannel; // Flag for tokens like whitespace and comments, that are mostly ignored
	public final DeeTokens groupToken;
	
	private DeeTokens(String sourceValue, boolean isSubChannel, DeeTokens groupToken) {
		this.sourceValue = sourceValue;
		this.isSubChannel = isSubChannel;
		this.groupToken = groupToken == null ? this : groupToken;
	}
	
	private DeeTokens(String sourceValue, boolean isSubChannel) {
		this(sourceValue, isSubChannel, null);
	}
	
	private DeeTokens(String sourceValue) {
		this(sourceValue, false);
	}
	
	private DeeTokens() {
		this(null, false);
	}
	
	private DeeTokens(DeeTokens groupToken) {
		this(null, groupToken.isSubChannel, groupToken);
	}
	
	private DeeTokens(@SuppressWarnings("unused") SubChannelTokenFlag subChannelMarker) {
		this(null, true);
	}
	
	
	private DeeTokens(String sourceValue, DeeTokens groupToken) {
		this(sourceValue, groupToken.isSubChannel, groupToken);
	}
	
	public final boolean hasSourceValue() {
		return sourceValue != null;
	}
	
	public final String getSourceValue() {
		assertNotNull(sourceValue);
		return sourceValue;
	}
	
	public final DeeTokens getGroupingToken() {
		return groupToken;
	}
	
	public boolean isKeyword() {
		return DeeTokenHelper.isKeyword(this);
	}
	
	public boolean isAlphaNumeric() {
		return this == DeeTokens.IDENTIFIER || isKeyword();
	}
	
}