/*******************************************************************************
 * Copyright (c) 2013, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.parser;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertUnreachable;

import java.util.ArrayList;

import ddt.melnorme.lang.tooling.ast.ParserError;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ParserErrorTypes;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.misc.ArrayUtil;
import ddt.dtool.ast.declarations.DeclBlock;
import ddt.dtool.ast.declarations.StaticIfExpIs;
import ddt.dtool.ast.declarations.StaticIfExpIs.StaticIfExpIsDefUnit;
import ddt.dtool.ast.definitions.CStyleRootRef;
import ddt.dtool.ast.definitions.DefUnit.ProtoDefSymbol;
import ddt.dtool.ast.definitions.FunctionAttributes;
import ddt.dtool.ast.definitions.IFunctionParameter;
import ddt.dtool.ast.definitions.Symbol;
import ddt.dtool.ast.definitions.ITemplateParameter;
import ddt.dtool.ast.expressions.ExpArrayLength;
import ddt.dtool.ast.expressions.ExpAssert;
import ddt.dtool.ast.expressions.ExpCall;
import ddt.dtool.ast.expressions.ExpCast;
import ddt.dtool.ast.expressions.ExpCastQual;
import ddt.dtool.ast.expressions.ExpCastQual.CastQualifiers;
import ddt.dtool.ast.expressions.ExpConditional;
import ddt.dtool.ast.expressions.ExpFunctionLiteral;
import ddt.dtool.ast.expressions.ExpImportString;
import ddt.dtool.ast.expressions.ExpIndex;
import ddt.dtool.ast.expressions.ExpInfix;
import ddt.dtool.ast.expressions.ExpInfix.InfixOpType;
import ddt.dtool.ast.expressions.ExpIs;
import ddt.dtool.ast.expressions.ExpIs.ExpIsSpecialization;
import ddt.dtool.ast.expressions.ExpLiteralArray;
import ddt.dtool.ast.expressions.ExpLiteralBool;
import ddt.dtool.ast.expressions.ExpLiteralChar;
import ddt.dtool.ast.expressions.ExpLiteralFloat;
import ddt.dtool.ast.expressions.ExpLiteralInteger;
import ddt.dtool.ast.expressions.ExpLiteralMapArray;
import ddt.dtool.ast.expressions.ExpLiteralMapArray.MapArrayLiteralKeyValue;
import ddt.dtool.ast.expressions.ExpLiteralString;
import ddt.dtool.ast.expressions.ExpMixinString;
import ddt.dtool.ast.expressions.ExpNew;
import ddt.dtool.ast.expressions.ExpNewAnonClass;
import ddt.dtool.ast.expressions.ExpNull;
import ddt.dtool.ast.expressions.ExpParentheses;
import ddt.dtool.ast.expressions.ExpPostfixOperator;
import ddt.dtool.ast.expressions.ExpPostfixOperator.PostfixOpType;
import ddt.dtool.ast.expressions.ExpPrefix;
import ddt.dtool.ast.expressions.ExpPrefix.PrefixOpType;
import ddt.dtool.ast.expressions.ExpReference;
import ddt.dtool.ast.expressions.ExpSimpleLambda;
import ddt.dtool.ast.expressions.ExpSimpleLambda.SimpleLambdaDefUnit;
import ddt.dtool.ast.expressions.ExpSuper;
import ddt.dtool.ast.expressions.ExpThis;
import ddt.dtool.ast.expressions.ExpTraits;
import ddt.dtool.ast.expressions.ExpTypeId;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.MissingExpression;
import ddt.dtool.ast.expressions.MissingParenthesesExpression;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.IQualifierNode;
import ddt.dtool.ast.references.ITemplateRefNode;
import ddt.dtool.ast.references.RefIdentifier;
import ddt.dtool.ast.references.RefIndexing;
import ddt.dtool.ast.references.RefModuleQualified;
import ddt.dtool.ast.references.RefPrimitive;
import ddt.dtool.ast.references.RefQualified;
import ddt.dtool.ast.references.RefSlice;
import ddt.dtool.ast.references.RefTemplateInstance;
import ddt.dtool.ast.references.RefTypeDynArray;
import ddt.dtool.ast.references.RefTypeModifier;
import ddt.dtool.ast.references.RefTypeModifier.TypeModifierKinds;
import ddt.dtool.ast.references.RefTypePointer;
import ddt.dtool.ast.references.RefTypeof;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IFunctionBody;
import ddt.dtool.parser.DeeParser_Parameters.DeeParser_RuleParameters;
import ddt.dtool.parser.DeeParser_Parameters.TplOrFnMode;
import ddt.dtool.parser.common.BaseLexElement;
import ddt.dtool.parser.common.IToken;
import ddt.dtool.parser.common.LexElement;


public abstract class DeeParser_RefOrExp extends DeeParser_Common {
	
	/* --------------------  reference parsing  --------------------- */
	
	public static final ParseRuleDescription RULE_REFERENCE = 
		new ParseRuleDescription("Reference", "Reference");
	public static final ParseRuleDescription RULE_TPL_SINGLE_ARG = 
		new ParseRuleDescription("TplSingleArg", "TemplateSingleArgument");
	
	public NodeResult<Reference> parseTypeReference() {
		return parseTypeReference_start(RefParseRestrictions.PARSE_ANY);
	}
	
	public NodeResult<Reference> parseTypeReference(boolean createMissing, boolean reportMissingError) {
		return parseTypeReference(createMissing, reportMissingError, false);
	}
	
	public NodeResult<Reference> parseTypeReference(boolean createMissing, boolean reportMissingError, 
		boolean brokenIfMissing) {
		NodeResult<Reference> typeRef = parseTypeReference();
		if(isNull(typeRef) && createMissing) {
			return result(brokenIfMissing, parseMissingTypeReference(reportMissingError));
		}
		return typeRef;
	}
	
	public NodeResult<Reference> parseTypeReference_ToMissing() {
		return parseTypeReference(true, true);
	}
	
	public NodeResult<Reference> parseTypeReference_ToMissing(boolean brokenIfMissing) {
		return parseTypeReference(true, true, brokenIfMissing);
	}
	
	public Reference parseMissingTypeReference(boolean reportMissingError) {
		ParseRuleDescription expectedRule = reportMissingError ? RULE_REFERENCE : null;
		return parseMissingTypeReference(expectedRule);
	}
	
	public Reference parseMissingTypeReference(ParseRuleDescription expectedRule) {
		SourceRange sourceRange = consumeSubChannelTokensNoError().getSourceRange();
		ParserError error = expectedRule != null ? createErrorExpectedRule(expectedRule) : null;
		return createMissingTypeReferenceNode(sourceRange, error);
	}
	
	public Reference createMissingTypeReferenceNode(SourceRange sourceRange, ParserError error) {
		RefIdentifier refMissing = new RefIdentifier(null);
		refMissing.setSourceRange(sourceRange);
		assertTrue(refMissing.isMissing());
		return conclude(error, refMissing);
	}
	
	public static enum RefParseRestrictions {
		PARSE_ANY,
		EXP_ONLY,
		TEMPLATE_ONLY,
		;
		
		public boolean templateOnly() {
			return this == TEMPLATE_ONLY;
		}
		
		public boolean canParsePointer() {
			return this == PARSE_ANY;
		}
		public boolean canParseBracketRef() {
			return this == PARSE_ANY;
		}
		public boolean canParseFunctionTypes() {
			return this == PARSE_ANY;
		}
	}
	
	public NodeResult<Reference> parseTypeReference_start(RefParseRestrictions refRestrictions) {
		DeeTokens lookAhead = lookAhead();
		NodeResult<Reference> result = parseTypeReference_start_do(refRestrictions);
		assertTrue(canParseTypeReferenceStart(lookAhead) == (result.node != null));
		return result;
	}
	
	protected NodeResult<Reference> parseTypeReference_start_do(RefParseRestrictions refRestrictions) {

		NodeResult<? extends Reference> refParseResult;
		
		TypeModifierKinds typeModifier = determineTypeModifier(lookAhead());
		if(typeModifier != null) {
			refParseResult = parseRefTypeModifier_start(typeModifier);
		} else {
			switch (lookAhead().getGroupingToken()) {
			case IDENTIFIER: 
				return parseTypeReference_withLeftReference(parseRefIdentifier(), refRestrictions);
			case GROUP_PRIMITIVE_KW: 
				return parseTypeReference_withLeftReference(parseRefPrimitive_start(lookAhead()), refRestrictions);
			case DOT: 
				refParseResult = parseRefModuleQualified(); break;
			case KW_TYPEOF: 
				refParseResult = parseRefTypeof(); break;
			
			default:
				return nullResult();
			}
		}
		
		if(refParseResult.ruleBroken) 
			return refParseResult.<Reference>upcastTypeParam();
		return parseTypeReference_withLeftReference(refParseResult.node, refRestrictions);
	}
	
	protected static boolean canParseTypeReferenceStart(DeeTokens lookAhead) {
		TypeModifierKinds typeModifier = determineTypeModifier(lookAhead);
		if(typeModifier != null) {
			return true;
		} else {
			switch (lookAhead.getGroupingToken()) {
			case IDENTIFIER: 
			case GROUP_PRIMITIVE_KW: 
			case DOT: 
			case KW_TYPEOF: 
				return true;
			
			default:
				return false;
			}
		}
	}
	
	public static TypeModifierKinds determineTypeModifier(DeeTokens tokenType) {
		switch (tokenType) {
		case KW_CONST: return TypeModifierKinds.CONST;
		case KW_IMMUTABLE: return TypeModifierKinds.IMMUTABLE;
		case KW_SHARED: return TypeModifierKinds.SHARED;
		case KW_INOUT: return TypeModifierKinds.INOUT;
		case KW___VECTOR: return TypeModifierKinds.VECTOR;
		default:
			return null;
		}
	}
	
	protected static boolean isTypeModifier(DeeTokens tokenType) {
		return determineTypeModifier(tokenType) != null;
	}
	/** Note: consider interaction with {@link #determineTypeModifier(DeeTokens)} */
	protected static boolean isImmutabilitySpecifier(DeeTokens tokenType) {
		switch (tokenType) {
		case KW_CONST: case KW_IMMUTABLE: case KW_SHARED: case KW_INOUT: 
			return true;
		default:
			return false;
		}
	}
	
	public RefIdentifier attemptParseRefIdentifier() {
		if(lookAhead() != DeeTokens.IDENTIFIER) {
			return null;
		}
		return parseRefIdentifier();
	}
	
	public RefIdentifier parseRefIdentifier() {
		BaseLexElement id = consumeExpectedContentToken(DeeTokens.IDENTIFIER);
		return conclude(id.getMissingError(), srEffective(id, new RefIdentifier(idTokenToString(id))));
	}
	
	protected RefPrimitive parseRefPrimitive_start(DeeTokens primitiveType) {
		LexElement primitive = consumeLookAhead(primitiveType);
		return conclude(srOf(primitive, new RefPrimitive(primitive)));
	}
	
	public NodeResult<RefModuleQualified> parseRefModuleQualified() {
		if(!tryConsume(DeeTokens.DOT))
			return nullResult();
		int nodeStart = lastLexElement().getStartPos();
		
		RefIdentifier id = parseRefIdentifier();
		return resultConclude(id.isMissing(), srToPosition(nodeStart, new RefModuleQualified(id)));
	}
	
	public NodeResult<RefTypeof> parseRefTypeof() {
		if(!tryConsume(DeeTokens.KW_TYPEOF))
			return null;
		ParseHelper parse = new ParseHelper();
		
		Expression exp = null;
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			
			if(tryConsume(DeeTokens.KW_RETURN)) {
				exp = conclude(srOf(lastLexElement(), new RefTypeof.ExpRefReturn()));
			} else {
				exp = parseExpression_toMissing();
			}
			parse.consumeRequired(DeeTokens.CLOSE_PARENS);
		}
		return parse.resultConclude(new RefTypeof(exp));
	}
	
	protected NodeResult<RefTypeModifier> parseRefTypeModifier_start(TypeModifierKinds modKind) {
		assertTrue(lookAhead().sourceValue.equals(modKind.sourceValue));
		consumeLookAhead();
		ParseHelper parse = new ParseHelper();
		
		Reference ref = null;
		boolean hasParens = false;
		if(parse.consumeOptional(DeeTokens.OPEN_PARENS)) {
			ref = parseTypeReference_ToMissing().node;
			parse.consumeRequired(DeeTokens.CLOSE_PARENS);
			hasParens = true;
		} else {
			if(modKind == TypeModifierKinds.VECTOR) {
				parse.consumeRequired(DeeTokens.OPEN_PARENS);
			} else {
				ref = parse.checkResult(parseTypeReference_ToMissing(true));
			}
		}
		return parse.resultConclude(new RefTypeModifier(modKind, ref, hasParens));
	}
	
	protected NodeResult<Reference> parseTypeReference_withLeftReference(Reference leftRef, 
		RefParseRestrictions refRestrictions) {
		assertNotNull(leftRef);
		
		ParseHelper parse = new ParseHelper(leftRef.getStartPos());
		
		if(isTemplateInstanceLookahead() && isValidTemplateReferenceSyntax(leftRef)){ // template instance
			consumeLookAhead();
			
			ITemplateRefNode tplRef = (ITemplateRefNode) leftRef;
			NodeListView<Resolvable> tplArgs = null;
			Resolvable singleArg = null;
			
			if(tryConsume(DeeTokens.OPEN_PARENS)) {
				tplArgs = parseTypeOrExpArgumentList(parse, DeeTokens.COMMA, DeeTokens.CLOSE_PARENS);
			} else {
				if(leftRef instanceof RefTemplateInstance) {
					RefTemplateInstance refTplInstance = (RefTemplateInstance) leftRef;
					if(refTplInstance.isSingleArgSyntax()) {
						parse.storeError(createError(ParserErrorTypes.NO_CHAINED_TPL_SINGLE_ARG, 
							refTplInstance.getSourceRange(), null));
					}
				}
				
				if(lookAhead().getGroupingToken() == DeeTokens.GROUP_PRIMITIVE_KW) {
					singleArg = parseRefPrimitive_start(lookAhead());	
				} else if(lookAhead() == DeeTokens.IDENTIFIER) { 
					singleArg = parseRefIdentifier();
				} else {
					singleArg = nullExpToParseMissing(parseSimpleLiteral(), RULE_TPL_SINGLE_ARG);
				}
			}
			leftRef = parse.conclude(new RefTemplateInstance(tplRef, singleArg, tplArgs));
			
		} else if(refRestrictions.templateOnly()) {
			return result(false, leftRef);
		} else if(lookAhead() == DeeTokens.DOT && leftRef instanceof IQualifierNode) {
			if(lookAhead(1) == DeeTokens.KW_NEW && refRestrictions == RefParseRestrictions.EXP_ONLY) {
				return result(false, leftRef);
			}
			IQualifierNode qualifier = (IQualifierNode) leftRef;
			assertTrue(!RefQualified.isExpressionQualifier(qualifier));
			leftRef = parseRefQualified(parse, qualifier);
		} else if(refRestrictions.canParsePointer() && tryConsume(DeeTokens.STAR)) {
			leftRef = conclude(srToPosition(leftRef, new RefTypePointer(leftRef)));
		} else if(refRestrictions.canParseBracketRef() && lookAhead() == DeeTokens.OPEN_BRACKET) {
			leftRef = parseBracketReference(leftRef, parse);
		} else if(refRestrictions.canParseFunctionTypes() && 
			(tryConsume(DeeTokens.KW_FUNCTION) || tryConsume(DeeTokens.KW_DELEGATE))) {
			leftRef = parse.checkResult(thisParser().parseRefTypeFunction_afterReturnType(leftRef));
		} else {
			return result(false, leftRef);
		}
		if(parse.ruleBroken)
			return result(true, leftRef);
		return parseTypeReference_withLeftReference(leftRef, refRestrictions);
	}
	
	public Reference parseRefQualified(ParseHelper parse, IQualifierNode qualifier) {
		LexElement dotToken = consumeLookAhead(DeeTokens.DOT);
		RefIdentifier qualifiedId = parseRefIdentifier();
		parse.setRuleBroken(qualifiedId.isMissing());
		return parse.conclude(new RefQualified(qualifier, dotToken.getStartPos(), qualifiedId));
	}
	
	public Reference parseBracketReference(Reference leftRef, ParseHelper parse) {
		consumeLookAhead(DeeTokens.OPEN_BRACKET);
		
		TypeOrExpResult argTypeOrExp = parseTypeOrExpression(InfixOpType.ASSIGN); 
		
		if(lookAhead() == DeeTokens.DOUBLE_DOT) {
			Expression startIndex = nullExpToParseMissing(argTypeOrExp.toExpression().node);
			consumeLookAhead(DeeTokens.DOUBLE_DOT);
			Expression endIndex = parseAssignExpression_toMissing();
			parse.consumeRequired(DeeTokens.CLOSE_BRACKET);
			return parse.conclude(new RefSlice(leftRef, startIndex, endIndex));
		}
		parse.consumeRequired(DeeTokens.CLOSE_BRACKET);
		
		Resolvable resolvable = argTypeOrExp.toFinalResult(true).node;
		if(resolvable == null) {
			return parse.conclude(new RefTypeDynArray(leftRef));
		} else {
			return parse.conclude(new RefIndexing(leftRef, resolvable));
		}
	}
	
	public boolean isTemplateInstanceLookahead() {
		return lookAhead() == DeeTokens.NOT && !(lookAhead(1) == DeeTokens.KW_IN || lookAhead(1) == DeeTokens.KW_IS);
	}
	
	public boolean isValidTemplateReferenceSyntax(Reference leftRef) {
		return leftRef instanceof ITemplateRefNode;
	}
	
	public Reference parseCStyleSuffix(ParseHelper parse) {
		if(lookAhead() != DeeTokens.OPEN_BRACKET) {
			parse.requireBrokenCheck();
			return null;
		}
		CStyleRootRef cstyleRootRef = conclude(srAt(lookAheadElement().getStartPos()), new CStyleRootRef());
		NodeResult<Reference> cstyleDeclaratorSuffix = parseCStyleDeclaratorSuffix(cstyleRootRef);
		parse.requireBrokenCheck();
		return parse.checkResult(cstyleDeclaratorSuffix);
	}
	
	protected NodeResult<Reference> parseCStyleDeclaratorSuffix(Reference leftRef) {
		if(lookAhead() != DeeTokens.OPEN_BRACKET) {
			return result(false, leftRef);
		}
		ParseHelper parse = new ParseHelper(leftRef.getStartPos());
		leftRef = parseBracketReference(leftRef, parse);
		if(parse.ruleBroken)
			return result(true, leftRef);
		return parseCStyleDeclaratorSuffix(leftRef);
	}
	
	/* --------------------- EXPRESSIONS --------------------- */
	
	public static final ParseRuleDescription RULE_EXPRESSION = 
		new ParseRuleDescription("Expression", "Expression");
	public static final ParseRuleDescription RULE_TYPE_OR_EXP = 
		new ParseRuleDescription("ToE", "TypeRerefence or Expression");
	
	public static final InfixOpType ANY_OPERATOR = InfixOpType.COMMA;
	
	
	public final NodeResult<Expression> parseExpression() {
		return parseExpression(ANY_OPERATOR);
	}
	public final NodeResult<Expression> parseExpression_toMissing(boolean breakOnMissing, 
		ParseRuleDescription expectedRule) {
		return nullExpToParseMissing(parseExpression(), breakOnMissing, expectedRule);
	}
	public final Expression parseExpression_toMissing() {
		return nullExpToParseMissing(parseExpression().node);
	}
	
	
	public final NodeResult<Expression> parseAssignExpression() {
		return parseExpression(InfixOpType.ASSIGN);
	}
	public final NodeResult<Expression> parseAssignExpression_toMissing(boolean breakOnMissing, 
		ParseRuleDescription expectedRule) {
		return nullExpToParseMissing(parseAssignExpression(), breakOnMissing, expectedRule);
	}
	public final Expression parseAssignExpression_toMissing() {
		return nullExpToParseMissing(parseAssignExpression().node);
	}
	
	
	protected NodeResult<Expression> parseExpression(InfixOpType precedenceLimit) {
		return new ParseRule_Expression().rule_parseExpression(precedenceLimit);
	}
	protected Expression parseExpression_toMissing(InfixOpType precedenceLimit) {
		return nullExpToParseMissing(parseExpression(precedenceLimit).node);
	}

	
	/* ---------------- Missing stuff ---------------- */
	
	protected Expression nullExpToParseMissing(Expression exp) {
		return nullExpToParseMissing(exp, RULE_EXPRESSION);
	}
	protected Expression nullExpToParseMissing(Expression exp, ParseRuleDescription expectedRule) {
		return exp != null ? exp : parseMissingExpression(expectedRule);
	}
	
	public final NodeResult<Expression> nullExpToParseMissing(NodeResult<Expression> expResult, 
		boolean breakOnMissing, ParseRuleDescription expectedRule) {
		return expResult.node != null ? expResult :
			result(expResult.ruleBroken || breakOnMissing, parseMissingExpression(expectedRule));
	}
	
	protected Expression parseMissingExpression(ParseRuleDescription expectedRule) {
		return parseMissingExpression(expectedRule, true);
	}
	
	protected Expression parseMissingExpression(ParseRuleDescription expectedRule, boolean consumeIgnoreTokens) {
		int nodeStart = getSourcePosition();
		if(consumeIgnoreTokens) {
			advanceSubChannelTokens();
		}
		int nodeEnd = getSourcePosition();
		return createMissingExpression(expectedRule, lastLexElement(), nodeStart, nodeEnd);
	}
	
	protected Expression createMissingExpression(ParseRuleDescription expectedRule, LexElement previousToken,
		int nodeStart, int nodeEnd) {
		
		ParserError error = expectedRule != null ? 
			createErrorExpectedRule(expectedRule, previousToken.getSourceRange()) : null;
		
		return conclude(error, srBounds(nodeStart, nodeEnd, new MissingExpression()));
	}
	
	public boolean isMissing(Expression exp) {
		return exp == null || exp instanceof MissingExpression;
	}
	
	public Expression createExpReference(Reference reference, boolean reportError) {
		ExpReference expReference = createExpReference(reference);
		return conclude(reportError ? createErrorTypeAsExpValue(reference) : null, expReference);
	}
	
	protected ExpReference createExpReference(Reference ref) {
		ExpReference node = new ExpReference(ref);
		node.setSourceRange(ref.getSourceRange());
		return node;
	}
	
	protected ParserError createErrorTypeAsExpValue(Reference reference) {
		return createError(ParserErrorTypes.TYPE_USED_AS_EXP_VALUE, reference.getSourceRange(), null);
	}
	
	/* ============================ TypeOrExp ============================ */

/** Note: Whenever this class is instantiated, then it must be called only with one of the rule_* methods,
 * this is to ensure that the parser state is restored properly when {@link ParseRule_Expression} is done.
 */
protected class ParseRule_Expression {
	
	public boolean breakRule;
	
	public ParseRule_Expression() {
		breakRule = false;
	}
	
	public NodeResult<Expression> rule_parseExpression(InfixOpType precedenceLimit) {
		return toResult(parseTypeOrExpression_start(precedenceLimit));
	}
	
	public NodeResult<Expression> rule_parseUnaryExpression() {
		return toResult(parseUnaryExpression());
	}
	
	public NodeResult<Expression> rule_parseTypeOrExpression_fromUnary(InfixOpType precedenceLimit, 
			Expression unaryExp) {
		return toResult(parseTypeOrExpression_fromUnary(precedenceLimit, unaryExp));
	}
	
	public NodeResult<Expression> toResult(Expression exp) {
		if(breakRule) {
			setEnabled(true);
		} 
		assertTrue(isEnabled());
		return result(breakRule, exp);
	}
	
	protected boolean shouldReturnToParseRuleTopLevel(@SuppressWarnings("unused") Expression expSoFar) {
		assertTrue(isEnabled() == !breakRule);
		return breakRule;
	}
	
	protected void setToEParseBroken(boolean parseBroken) {
		this.breakRule = parseBroken;
		if(breakRule) {
			setEnabled(false);
		}
	}
	
	protected Expression expConclude(NodeResult<? extends Expression> result) {
		setToEParseBroken(result.ruleBroken);
		return result.node;
	}
	
	protected Expression parseTypeOrExpression_start(InfixOpType precedenceLimit) {
		Expression prefixExp;
		Resolvable prefixExpResolvable = parsePrimaryExpression();
		if(prefixExpResolvable == null || prefixExpResolvable instanceof Expression) {
			prefixExp = (Expression) prefixExpResolvable;
		} else {
			Reference ref = (Reference) prefixExpResolvable;
			boolean isTypeAsExpError = !refIsAllowedInExp(ref, breakRule || lookAhead() == DeeTokens.OPEN_PARENS);
			prefixExp = createExpReference(ref, isTypeAsExpError);
		}
		
		if(prefixExp == null || shouldReturnToParseRuleTopLevel(prefixExp)) {
			return prefixExp;
		}
		
		return parseTypeOrExpression_fromUnary(precedenceLimit, prefixExp);
	}
	
	public Expression parseTypeOrExpression_fromUnary(InfixOpType precedenceLimit, Expression unaryExp) {
		unaryExp = parsePostfixExpression(unaryExp);
		if(shouldReturnToParseRuleTopLevel(unaryExp)) {
			return unaryExp;
		}
		
		return parseInfixOperators(precedenceLimit, unaryExp);
	}
	
	protected Expression parseUnaryExpression() {
		return parseTypeOrExpression_start(InfixOpType.NULL);
	}
	
	protected Resolvable parsePrimaryExpression() {
		Expression simpleLiteral = parseSimpleLiteral();
		if(simpleLiteral != null) {
			return simpleLiteral;
		}
		
		switch (lookAhead()) {
		case KW_ASSERT:
			return expConclude(parseAssertExpression());
		case KW_MIXIN:
			return expConclude(parseMixinExpression());
		case KW_IMPORT:
			return expConclude(parseImportExpression());
		case KW_TYPEID:
			return expConclude(parseTypeIdExpression());
		case KW_NEW:
			return expConclude(parseNewExpression());
		case KW_CAST:
			return expConclude(parseCastExpression());
		case KW_IS:
			return expConclude(parseIsExpression());
		case KW___TRAITS:
			return expConclude(parseTraitsExpression());
		case AND:
		case INCREMENT:
		case DECREMENT:
		case STAR:
		case MINUS:
		case PLUS:
		case NOT:
		case CONCAT:
		case KW_DELETE: {
			LexElement prefixExpOpToken = consumeLookAhead();
			PrefixOpType prefixOpType = PrefixOpType.tokenToPrefixOpType(prefixExpOpToken.type);
			
			Expression exp = parseUnaryExpression();
			if(exp == null) {
				exp = parseMissingExpression(RULE_EXPRESSION);
				setToEParseBroken(true);
			}
			
			return conclude(srToPosition(prefixExpOpToken, new ExpPrefix(prefixOpType, exp)));
		}
		case OPEN_PARENS:
			return expConclude(matchParenthesesStart());
			
		case OPEN_BRACE: {
			int startPos = lookAheadElement().getStartPos();
			return expConclude(parseFunctionLiteral_atFunctionBody(startPos, null, null, null, null));
		}
		case KW_FUNCTION:
		case KW_DELEGATE:
			return expConclude(parseFunctionLiteral_start());
			
		case OPEN_BRACKET:
			return parseBracketList(null);
		case IDENTIFIER:
			if(lookAhead(1) == DeeTokens.LAMBDA) {
				return expConclude(parseSimpleLambdaLiteral_start());
			} // else fallthrough to TypeReference:
		default:
			NodeResult<Reference> typeRefResult = parseTypeReference_start(RefParseRestrictions.EXP_ONLY);
			Reference ref = typeRefResult.node;
			if(!(ref instanceof RefQualified || ref instanceof RefModuleQualified)) {
				setToEParseBroken(typeRefResult.ruleBroken);
			}
			return ref;
		}
	}
	
	protected Expression parsePostfixExpression(Expression exp) {
		
		switch (lookAhead()) {
		case DECREMENT:
		case INCREMENT: {
			exp = parsePostfixOpExpression_atOperator(exp);
			return parsePostfixExpression(exp);
		}
		case POW: {
			return parseInfixOperator(exp, InfixOpType.POW);
		}
		case OPEN_PARENS: {
			exp = expConclude(parseCallExpression_atParenthesis(exp));
			if(shouldReturnToParseRuleTopLevel(exp))
				return exp;
			return parsePostfixExpression(exp);
		}
		case OPEN_BRACKET: {
			exp = parseBracketList(exp);
			if(shouldReturnToParseRuleTopLevel(exp)) {
				return exp;
			}
			return parsePostfixExpression(exp);
		}
		case DOT: {
			ParseHelper parse = new ParseHelper(exp.getStartPos());
			if(lookAhead(1) == DeeTokens.KW_NEW) {
				consumeLookAhead(DeeTokens.DOT);
				consumeLookAhead(DeeTokens.KW_NEW);
				return expConclude(parseNewExpression_do(parse, exp));
			}
			
			final Expression qualifier = exp;
			exp = null;
			if(qualifier instanceof ExpReference) {
				ExpReference expReference = (ExpReference) qualifier;
				if(expReference.ref instanceof RefQualified) {
					assertTrue(((RefQualified) expReference.ref).isExpressionQualifier);
				} else if(expReference.ref instanceof RefTemplateInstance) {
				} else {
					assertFail(); // ...otherwise refqualified would have been parsed already
				}
			}
			Reference ref = parseRefQualified(parse, qualifier);
			if(!parse.ruleBroken) {
				ref = parseTypeReference_withLeftReference(ref, RefParseRestrictions.TEMPLATE_ONLY).node; 
			}
			return parsePostfixExpression(conclude(createExpReference(ref)));
		}
		default:
			return exp;
		}
	}
	
	protected Expression parseInfixOperators(InfixOpType precedenceLimit, final Expression leftExp) {
		DeeTokens gla = lookAheadGrouped();
		
		InfixOpType infixOpAhead = InfixOpType.tokenToInfixOpType(gla);
		if(lookAhead() == DeeTokens.NOT) {
			if(lookAhead(1) == DeeTokens.KW_IS) {
				infixOpAhead = InfixOpType.NOT_IS;
			} else if(lookAhead(1) == DeeTokens.KW_IN) {
				infixOpAhead = InfixOpType.NOT_IN;
			}
		}
		
		if(infixOpAhead == null) {
			return leftExp;
		}
		
		// If lower precedence it can't be parsed to right expression, 
		// instead this expression must become left children of new parent
		if(infixOpAhead.precedence < precedenceLimit.precedence)
			return leftExp;
		
		Expression exp = parseInfixOperator(leftExp, infixOpAhead);
		if(shouldReturnToParseRuleTopLevel(exp)) {
			return exp;
		}
		
		return parseInfixOperators(precedenceLimit, exp);
	}
	
	public InfixOpType getPrecedenceForInfixOpRightExp(InfixOpType infixOpLA) {
		switch (infixOpLA.category) {
		case SLICE: return InfixOpType.SLICE;
		case COMMA: return InfixOpType.COMMA;
		case ASSIGN: return InfixOpType.ASSIGN;
		case CONDITIONAL: return InfixOpType.CONDITIONAL;
		case LOGICAL_OR: return InfixOpType.LOGICAL_AND;
		case LOGICAL_AND: return InfixOpType.OR;
		case OR: return InfixOpType.XOR;
		case XOR: return InfixOpType.AND;
		case AND: return InfixOpType.EQUALS;
		case EQUALS: return InfixOpType.SHIFT;
		case SHIFT: return InfixOpType.ADD;
		case ADD: return InfixOpType.MUL;
		case MUL: return InfixOpType.NULL;
		case POW: return InfixOpType.NULL;
		default:
			throw assertUnreachable();
		}
	}
	
		public Expression parseInfixOperator(final Expression leftExp, final InfixOpType opType) {
			ParseHelper parse = new ParseHelper(assertNotNull(leftExp));
			
			Expression rightExp = null;
			
			consumeLookAhead();
			if(opType == InfixOpType.NOT_IS || opType == InfixOpType.NOT_IN) {
				consumeLookAhead(); // consume second token
			}
			
			if(opType != InfixOpType.MUL) {
				parse.storeError(checkValidAssociativityN(leftExp, opType));
			} else {
				assertTrue(lastLexElement().type == DeeTokens.STAR);
			}
			
			Expression middleExp = null;
			
			parsing: {
				if(opType == InfixOpType.CONDITIONAL) {
					middleExp = nullExpToParseMissing(parseExpression().node);
					
					if(parse.consumeRequired(DeeTokens.COLON).ruleBroken) {
						setToEParseBroken(true);
						break parsing;
					}
				}
				
				InfixOpType rightExpPrecedence = getPrecedenceForInfixOpRightExp(opType);
				
				NodeResult<Expression> expResult = parseExpression(rightExpPrecedence);
				setToEParseBroken(expResult.ruleBroken);
				rightExp = expResult.node;
				
				if(isMissing(rightExp)) {
					rightExp = parseMissingExpression(RULE_EXPRESSION);
					setToEParseBroken(true);
				} else {
					parse.storeError(checkValidAssociativityN(rightExp, opType));
				}
			}
			
			if(opType == InfixOpType.CONDITIONAL) {
				return parse.conclude(new ExpConditional(leftExp, middleExp, rightExp));
			}
			
			return parse.conclude(new ExpInfix(leftExp, opType, rightExp));
		}
		
	
	protected ParserError checkValidAssociativityN(Expression exp, InfixOpType op) {
		// Check for some syntax situations which are technically not allowed by the grammar:
		switch (op.category) {
		case OR: case XOR: case AND: case EQUALS:
			if(exp instanceof ExpInfix && ((ExpInfix) exp).kind.category == InfixOpType.EQUALS) {
				return createError(ParserErrorTypes.EXP_MUST_HAVE_PARENTHESES, exp.getSourceRange(), op.sourceValue);
			}
		default: return null;
		}
	}
	
	public Expression parseArrayLiteral() {
		return parseBracketList(null);
	}
	
		protected Expression parseBracketList(Expression calleeExp) {
			if(tryConsume(DeeTokens.OPEN_BRACKET) == false)
				return null;
			
			final boolean isExpIndexing = calleeExp != null;
			ParseHelper parse = isExpIndexing ? new ParseHelper(calleeExp) : new ParseHelper();
			
			ArrayList<Expression> elements = new ArrayList<Expression>(4);
			ArrayList<MapArrayLiteralKeyValue> mapElements = null;
			
			boolean firstElement = true;
			
			while(true) {
				Expression exp1;
				Expression exp2 = null;
				ParseHelper exp2parse = null;
				
				exp1 = parseExpression(InfixOpType.SLICE).node;
				if(lookAhead() == DeeTokens.COMMA) {
					exp1 = nullExpToParseMissing(exp1);
				}
				
				if(firstElement) {
					
					if(!isExpIndexing && lookAhead() == DeeTokens.COLON) {
						exp1 = nullExpToParseMissing(exp1);
						consumeLookAhead(DeeTokens.COLON);
						exp2parse = new ParseHelper(exp1);
						exp2 = parseAssignExpression_toMissing();
						mapElements = new ArrayList<MapArrayLiteralKeyValue>();
					} else if(exp1 == null) {
						break;
					}
				} else {
					if(mapElements != null) {
						if(lookAhead() == DeeTokens.COLON) {
							exp1 = nullExpToParseMissing(exp1);
						}
						
						if(exp1 != null) {
							exp2parse = new ParseHelper(exp1);
							if(exp2parse.consumeExpected(DeeTokens.COLON)) {
								exp2 = parseAssignExpression_toMissing();
							}
						}
					}
				}
				firstElement = false;
				
				if(mapElements == null ) {
					elements.add(exp1);
				} else {
					if(exp2parse == null) {
						mapElements.add(null);
					} else {
						mapElements.add(exp2parse.conclude(new MapArrayLiteralKeyValue(exp1, exp2)));
					}
				}
				
				if(tryConsume(DeeTokens.COMMA)) {
					assertTrue(exp1 != null);
					continue;
				}
				break;
			}
			
			parse.consumeRequired(DeeTokens.CLOSE_BRACKET);
			setToEParseBroken(parse.ruleBroken);
			
			if(calleeExp == null) {
				if(mapElements != null ) {
					return parse.conclude(new ExpLiteralMapArray(nodeListView(mapElements)));
				} else {
					return parse.conclude(new ExpLiteralArray(nodeListView(elements)));
				}
			}
			return parse.conclude(new ExpIndex(calleeExp, nodeListView(elements)));
		}
		
} /* ---------------- ParseRule_TypeOrExp END----------------*/
	
	protected static boolean refIsAllowedInExp(Reference ref, boolean allowOpCallTypeRefs) {
		switch (ref.getNodeType()) {
		case REF_PRIMITIVE:
		case REF_TYPE_FUNCTION:
			return false;
		case REF_MODIFIER:
			if(allowOpCallTypeRefs == false)
				return false;
			RefTypeModifier refModifier = (RefTypeModifier) ref;
			return refModifier.ref == null ? true : refIsAllowedInExp(refModifier.ref, true);
		case REF_TYPEOF:
			return allowOpCallTypeRefs;
		case REF_TYPE_DYN_ARRAY:
		case REF_TYPE_POINTER:
		case REF_INDEXING:
			return false;
		default:
			return true;
		}
	}
	
	/* ---------------- parse TypeOrExp ----------------*/
	
	public NodeResult<Resolvable> parseTypeOrExpression() {
		return parseTypeOrExpression(true);
	}
	
	public NodeResult<Resolvable> parseTypeOrExpression(boolean ambiguousToRef) {
		return parseTypeOrExpression(ANY_OPERATOR, ambiguousToRef);
	}
	
	public NodeResult<Resolvable> parseTypeOrAssignExpression(boolean ambiguousToRef) {
		return parseTypeOrExpression(InfixOpType.ASSIGN, ambiguousToRef);
	}
	
	public NodeResult<Resolvable> parseTypeOrExpression(InfixOpType precedenceLimit, boolean ambiguousToRef) {
		return parseTypeOrExpression(precedenceLimit).toFinalResult(ambiguousToRef).upcastTypeParam();
	}
	
	protected Resolvable nullTypeOrExpToParseMissing(Resolvable exp) {
		return exp != null ? exp : parseMissingExpression(RULE_TYPE_OR_EXP);
	}
	
	protected TypeOrExpResult parseTypeOrExpression(InfixOpType precedenceLimit) {
		ParserState initialState = saveParserState();
		
		NodeResult<Reference> refResult = parseTypeReference();
		ParserState refResultState = saveParserState();
		assertTrue(isEnabled());
		restoreOriginalState(initialState);
		
		NodeResult<Expression> expResult = parseExpression(precedenceLimit);
		int expResultLexPosition = getEnabledLexSource().getLexElementPosition();
		int refResultLexPosition = refResultState.lexSource.getLexElementPosition();
		
		if(expResultLexPosition > refResultLexPosition) {
			return new TypeOrExpResult(null, expResult);
		} else if(refResultLexPosition > expResultLexPosition) {
			restoreOriginalState(refResultState);
			return new TypeOrExpResult(refResult, null);
		} else {
			return new TypeOrExpResult(refResult, expResult);
		}
	}
	
	protected final class TypeOrExpResult {
		
		private NodeResult<Reference> refResult;
		private NodeResult<Expression> expResult;
		
		public TypeOrExpResult(NodeResult<Reference> refResult, NodeResult<Expression> expResult) {
			this.refResult = refResult;
			this.expResult = expResult;
		}
		
		public boolean isNull() {
			return (refResult == null && expResult == null) ||
				(refResult != null && expResult != null && refResult.node == null);
		}
		
		public boolean isExpOnly() {
			return !isNull() && refResult == null;
		}
		
		public boolean isRefOnly() {
			return !isNull() && expResult == null;
		}
		
		public NodeResult<Reference> toReference() {
			assertTrue(!isExpOnly());
			if(isNull()) {
				return nullResult();
			}
			return refResult;
		}
		
		public NodeResult<Expression> toExpression() {
			assertTrue(!isRefOnly());
			if(isNull()) {
				return nullResult();
			}
			return assertNotNull(expResult);
		}
		
		public NodeResult<? extends Resolvable> toFinalResult(boolean ambiguousToRef) {
			if(isRefOnly()) {
				return refResult;
			}
			if(isExpOnly()) {
				return expResult;
			}
			return ambiguousToRef ? refResult : expResult;
		}
	}
	
	protected Expression resolvableToExp(Resolvable resolvable, boolean reportError) {
		if(resolvable instanceof Reference) {
			Reference reference = (Reference) resolvable;
			return createExpReference(reference, reportError);
		}
		return (Expression) resolvable;
	}
	
	public NodeResult<Expression> parseUnaryExpression_toMissing() {
		NodeResult<Expression> result = new ParseRule_Expression().rule_parseUnaryExpression();
		return nullExpToParseMissing(result, false, RULE_EXPRESSION);
	}
	
	public NodeResult<Expression> parseExpression_fromUnary(InfixOpType precedenceLimit, Expression unaryExp) {
		return new ParseRule_Expression().rule_parseTypeOrExpression_fromUnary(precedenceLimit, unaryExp);
	}
	
	public Expression parseSimpleLiteral() {
		switch (lookAheadGrouped()) {
		case KW_TRUE: 
		case KW_FALSE:
			consumeLookAhead();
			return conclude(srOf(lastLexElement(), new ExpLiteralBool(lastLexElement().type == DeeTokens.KW_TRUE)));
		case KW_THIS:
			consumeLookAhead();
			return conclude(srOf(lastLexElement(), new ExpThis()));
		case KW_SUPER:
			consumeLookAhead();
			return conclude(srOf(lastLexElement(), new ExpSuper()));
		case KW_NULL:
			consumeLookAhead();
			return conclude(srOf(lastLexElement(), new ExpNull()));
		case DOLLAR:
			consumeLookAhead();
			return conclude(srOf(lastLexElement(), new ExpArrayLength()));
			
		case GROUP_INTEGER:
			consumeLookAhead();
			return conclude(srOf(lastLexElement(), new ExpLiteralInteger(lastLexElement())));
		case CHARACTER: 
			consumeLookAhead();
			return conclude(srOf(lastLexElement(), new ExpLiteralChar(lastLexElement())));
		case GROUP_FLOAT:
			consumeLookAhead();
			return conclude(srOf(lastLexElement(), new ExpLiteralFloat(lastLexElement())));
		case GROUP_STRING:
			return parseStringLiteral();
		default:
			return null;
		}
	}
	
	public Expression parseStringLiteral() {
		ArrayList<IToken> stringTokens = new ArrayList<IToken>(1);
		
		while(lookAheadGrouped() == DeeTokens.GROUP_STRING) {
			IToken string = consumeLookAhead();
			stringTokens.add(string);
		}
		IToken[] tokenStrings = ArrayUtil.createFrom(stringTokens, IToken.class);
		return conclude(srToPosition(tokenStrings[0].getStartPos(), new ExpLiteralString(tokenStrings)));
	}
	
	protected ExpPostfixOperator parsePostfixOpExpression_atOperator(Expression exp) {
		LexElement op = consumeLookAhead();
		return conclude(srToPosition(exp, new ExpPostfixOperator(exp, PostfixOpType.tokenToPrefixOpType(op.type))));
	}
	
	protected NodeResult<ExpCall> parseCallExpression_atParenthesis(Expression callee) {
		ParseHelper parse = new ParseHelper(callee);
		NodeListView<Expression> args = parseParenthesesDelimited_ExpArgumentList(parse);
		return parse.resultConclude(new ExpCall(callee, args));
	}
	
	protected NodeListView<Expression> parseExpArgumentList(ParseHelper parse, boolean canBeEmpty, 
		DeeTokens tokenLISTCLOSE) {
		SimpleListParseHelper<Expression> elementListParse = new SimpleListParseHelper<Expression>() {
			@Override
			protected Expression parseElement(boolean createMissing) {
				Expression arg = parseAssignExpression().node;
				return createMissing ? nullExpToParseMissing(arg) : arg;
			}
		};
		elementListParse.parseSimpleList(DeeTokens.COMMA, canBeEmpty, true);
		
		parse.consumeRequired(tokenLISTCLOSE);
		return elementListParse.members;
	}
	
	protected final NodeListView<Expression> parseParenthesesDelimited_ExpArgumentList(ParseHelper parse) {
		if(tryConsume(DeeTokens.OPEN_PARENS)) {
			return parseExpArgumentList(parse, true, DeeTokens.CLOSE_PARENS);
		} else {
			return null;
		}
	}
	
	protected final class TypeOrExpArgumentListSimpleParse extends SimpleListParseHelper<Resolvable> {
		@Override
		protected Resolvable parseElement(boolean createMissing) {
			Resolvable arg = parseTypeOrAssignExpression(true).node;
			return createMissing ? nullTypeOrExpToParseMissing(arg) : arg;
		}
	}
	
	protected final NodeListView<Resolvable> parseTypeOrExpArgumentList(ParseHelper parse, DeeTokens tkSEP, 
		DeeTokens tkCLOSE) {
		
		SimpleListParseHelper<Resolvable> elementListParse = new TypeOrExpArgumentListSimpleParse();
		elementListParse.parseSimpleList(tkSEP, true, true);
		parse.consumeRequired(tkCLOSE);
		return elementListParse.members;
	}
	
	protected NodeResult<? extends Expression> matchParenthesesStart() {
		assertTrue(lookAhead() == DeeTokens.OPEN_PARENS);
		ParseHelper parse = new ParseHelper(lookAheadElement());
		
		ParserState savedParserState = saveParserState();
		
		DeeParser_RuleParameters fnParametersRule = thisParser().new DeeParser_RuleParameters(TplOrFnMode.FN);
		fnParametersRule.parseParameters(parse);
		
		if(!parse.ruleBroken) {
			ArrayView<FunctionAttributes> fnAttributes = thisParser().parseFunctionAttributes();
			
			if(lookAhead() == DeeTokens.OPEN_BRACE || lookAhead() == DeeTokens.LAMBDA) {
				ArrayView<IFunctionParameter> fnParams = fnParametersRule.getAsFunctionParameters();
				return parseFunctionLiteral_atFunctionBody(parse.nodeStart, null, null, fnParams, fnAttributes);
			}
		}
		
		restoreOriginalState(savedParserState);
		return parseParenthesesExp();
	}
	
	protected NodeResult<ExpSimpleLambda> parseSimpleLambdaLiteral_start() {
		ProtoDefSymbol defId = parseDefId();
		consumeLookAhead(DeeTokens.LAMBDA);
		
		ParseHelper parse = new ParseHelper(defId.getStartPos());
		Expression bodyExp = parse.checkResult(parseAssignExpression_toMissing(true, RULE_EXPRESSION));
		
		SimpleLambdaDefUnit lambdaDefId = conclude(defId.nameSourceRange, new SimpleLambdaDefUnit(defId));
		return parse.resultConclude(new ExpSimpleLambda(lambdaDefId, bodyExp));
	}
	
	public NodeResult<ExpFunctionLiteral> parseFunctionLiteral_start() {
		assertTrue(lookAhead() == DeeTokens.KW_FUNCTION || lookAhead() == DeeTokens.KW_DELEGATE);
		consumeLookAhead();
		boolean isFunctionKeyword = lastLexElement().type == DeeTokens.KW_FUNCTION;
		ParseHelper parse = new ParseHelper();
		
		Reference retType = parseTypeReference().node;
		
		ArrayView<IFunctionParameter> fnParams = null;
		ArrayView<FunctionAttributes> fnAttributes = null;
		
		parsing: {
			fnParams = thisParser().parseFunctionParameters(parse);
			if(parse.ruleBroken) break parsing;
			
			fnAttributes = thisParser().parseFunctionAttributes();
			
			return parseFunctionLiteral_atFunctionBody(parse.nodeStart, isFunctionKeyword, retType, fnParams, 
				fnAttributes);
		}
		
		return parse.resultConclude(
			new ExpFunctionLiteral(isFunctionKeyword, retType, fnParams, fnAttributes, null, null));
	}
	
	protected NodeResult<ExpFunctionLiteral> parseFunctionLiteral_atFunctionBody(int nodeStart,
		Boolean isFunctionKeyword, Reference retType, ArrayView<IFunctionParameter> fnParams,
		ArrayView<FunctionAttributes> fnAttributes) 
	{
		if(tryConsume(DeeTokens.LAMBDA)) {
			assertTrue(fnParams != null);
			NodeResult<Expression> litBody = parseAssignExpression_toMissing(true, RULE_EXPRESSION);
			
			return resultConclude(litBody.ruleBroken, srToPosition(nodeStart, 
				new ExpFunctionLiteral(isFunctionKeyword, retType, fnParams, fnAttributes, null, litBody.node)));
		} else {
			NodeResult<? extends IFunctionBody> litBody = thisParser().parseBlockStatement(true, true);
			
			return resultConclude(litBody.ruleBroken, srToPosition(nodeStart, 
				new ExpFunctionLiteral(isFunctionKeyword, retType, fnParams, fnAttributes, litBody.node, null)));
		}
	}
	
	public NodeResult<ExpParentheses> parseParenthesesExp() {
		if(!tryConsume(DeeTokens.OPEN_PARENS))
			return null;
		ParseHelper parse = new ParseHelper();
		
		TypeOrExpResult arg = parseTypeOrExpression(ANY_OPERATOR);
		Resolvable resolvable;
		
		boolean isDotAfterParensSyntax = lookAhead() == DeeTokens.CLOSE_PARENS && lookAhead(1) == DeeTokens.DOT;
		if(isDotAfterParensSyntax) {
			resolvable = nullTypeOrExpToParseMissing(arg.toFinalResult(true).node);
		} else {
			resolvable = arg.toFinalResult(false).node;
			resolvable = nullExpToParseMissing(resolvableToExp(resolvable, true));
		}
		parse.consumeRequired(DeeTokens.CLOSE_PARENS);
		
		return parse.resultConclude(new ExpParentheses(isDotAfterParensSyntax, resolvable));
	}
	
	public NodeResult<ExpAssert> parseAssertExpression() {
		if(tryConsume(DeeTokens.KW_ASSERT) == false)
			return null;
		ParseHelper parse = new ParseHelper();
		
		Expression exp = null;
		Expression msg = null;
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			exp = parseAssignExpression_toMissing();
			if(tryConsume(DeeTokens.COMMA)) {
				msg = parseAssignExpression_toMissing();
			}
			parse.consumeRequired(DeeTokens.CLOSE_PARENS);
		}
		
		return parse.resultConclude(new ExpAssert(exp, msg));
	}
	
	public NodeResult<ExpImportString> parseImportExpression() {
		if(tryConsume(DeeTokens.KW_IMPORT) == false)
			return null;
		ParseHelper parse = new ParseHelper();
		
		Expression expParentheses = parseExpressionAroundParentheses(parse, true, true);
		return parse.resultConclude(new ExpImportString(expParentheses));
	}
	
	public NodeResult<ExpMixinString> parseMixinExpression() {
		if(tryConsume(DeeTokens.KW_MIXIN) == false)
			return null;
		ParseHelper parse = new ParseHelper();
		
		Expression expParentheses = parseExpressionAroundParentheses(parse, true, true);
		return parse.resultConclude(new ExpMixinString(expParentheses));
	}

	public Expression parseExpressionAroundParentheses(ParseHelper parse, boolean isRequired, 
		boolean brokenIfMissing) {
		boolean isOptional = !isRequired;
		if(parse.consume(DeeTokens.OPEN_PARENS, isOptional, brokenIfMissing) == false) {
			if(!isOptional) {
				return conclude(srToPosition(getSourcePosition(), new MissingParenthesesExpression()));
			}
			return null;
		} else {
			Expression exp = parseExpression_toMissing();
			parse.consumeRequired(DeeTokens.CLOSE_PARENS);
			return exp;
		}
	}
	
	public NodeResult<ExpTypeId> parseTypeIdExpression() {
		if(tryConsume(DeeTokens.KW_TYPEID) == false)
			return null;
		ParseHelper parse = new ParseHelper();
		
		Reference ref = null;
		Expression exp = null;
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			Resolvable resolvable = nullTypeOrExpToParseMissing(parseTypeOrExpression(true).node);
			if(resolvable instanceof Reference) {
				ref = (Reference) resolvable;
			} else {
				exp = (Expression) resolvable;
			}
			parse.consumeRequired(DeeTokens.CLOSE_PARENS);
		}
		if(ref != null) {
			return parse.resultConclude(new ExpTypeId(ref));
		}
		return parse.resultConclude(new ExpTypeId(exp));
	}
	
	public NodeResult<? extends Expression> parseNewExpression() {
		if(!tryConsume(DeeTokens.KW_NEW))
			return nullResult();
		ParseHelper parse = new ParseHelper();
		
		Expression outerClass = null;
		return parseNewExpression_do(parse, outerClass);
	}
	
	public NodeResult<? extends Expression> parseNewExpression_do(ParseHelper parse, Expression outerClass) {
		NodeListView<Expression> allocArgs = null;
		Reference type = null;
		NodeListView<Expression> args = null;
		
		parsing: {
			assertTrue(!parse.ruleBroken);
			
			allocArgs = parseParenthesesDelimited_ExpArgumentList(parse);
			if(parse.ruleBroken) break parsing;
			
			if(outerClass == null && parse.consumeOptional(DeeTokens.KW_CLASS)) {
				return parseNewAnonClassExpression_afterClassKeyword(parse, allocArgs);
			}
			
			type = parse.checkResult(parseTypeReference_ToMissing(true));
			if(parse.ruleBroken) break parsing;
			
			args = parseParenthesesDelimited_ExpArgumentList(parse);
		}
		
		return parse.resultConclude(new ExpNew(outerClass, allocArgs, type, args));
	}
	
	protected NodeResult<ExpNewAnonClass> parseNewAnonClassExpression_afterClassKeyword(ParseHelper parse, 
		ArrayView<Expression> allocArgs) {
		
		ArrayView<Expression> args = null;
		SimpleListParseHelper<Reference> baseClasses = thisParser().new TypeReferenceSimpleListParse();
		DeclBlock declBody = null;
		
		parsing: {
			args = parseParenthesesDelimited_ExpArgumentList(parse);
			if(parse.ruleBroken) break parsing;
			
			baseClasses.parseSimpleList(DeeTokens.COMMA, true, false);
			
			declBody = parse.parseRequiredRule(thisParser().parseDeclarationBlock(), DeeParser.RULE_DECLARATION_BLOCK);
		}
		
		return parse.resultConclude(new ExpNewAnonClass(allocArgs, args, baseClasses.members, declBody));
	}
	
	public NodeResult<? extends Expression> parseCastExpression() {
		if(!tryConsume(DeeTokens.KW_CAST))
			return null;
		ParseHelper parse = new ParseHelper();
		
		Reference type = null;
		CastQualifiers qualifier = null;
		Expression exp = null;
		
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			
			qualifier = parseCastQualifier();
			if(qualifier == null) {
				type = parseTypeReference(true, false).node;
			}
			if(parse.consumeRequired(DeeTokens.CLOSE_PARENS).ruleBroken) break parsing;
			
			exp = parse.checkResult(parseUnaryExpression_toMissing());
		}
		
		if(qualifier != null) {
			return parse.resultConclude(new ExpCastQual(qualifier, exp));
		} else {
			return parse.resultConclude(new ExpCast(type, exp));
		}
	}
	
	public CastQualifiers parseCastQualifier() {
		switch (lookAhead()) {
		case KW_CONST:
			return parseCastQualifier(DeeTokens.KW_SHARED, CastQualifiers.CONST_SHARED, CastQualifiers.CONST);
		case KW_INOUT:
			return parseCastQualifier(DeeTokens.KW_SHARED, CastQualifiers.INOUT_SHARED, CastQualifiers.INOUT);
		case KW_SHARED:
			if(lookAhead(2) == DeeTokens.CLOSE_PARENS && tryConsume(DeeTokens.KW_SHARED, DeeTokens.KW_CONST))
				return CastQualifiers.SHARED_CONST;
			return parseCastQualifier(DeeTokens.KW_INOUT, CastQualifiers.SHARED_INOUT, CastQualifiers.SHARED);
		case KW_IMMUTABLE:
			if(lookAhead(1) == DeeTokens.CLOSE_PARENS) {
				consumeLookAhead();
				return CastQualifiers.IMMUTABLE;
			}
		default: return null;
		}
	}
	
	public CastQualifiers parseCastQualifier(DeeTokens token1, CastQualifiers altDouble, CastQualifiers altSingle) {
		if(lookAhead(2) == DeeTokens.CLOSE_PARENS && lookAhead(1) == token1) {
			consumeLookAhead();
			consumeLookAhead();
			return altDouble;
		} else if(lookAhead(1) == DeeTokens.CLOSE_PARENS) {
			consumeLookAhead();
			return altSingle;
		} else {
			return null;
		}
	}
	
	public static final ParseRuleDescription RULE_IS_TYPE_SPEC = 
		new ParseRuleDescription("IsTypeSpecialization", "IsTypeSpecialization");
	
	public NodeResult<? extends Expression> parseIsExpression() {
		if(!tryConsume(DeeTokens.KW_IS))
			return null;
		ParseHelper parse = new ParseHelper();
		
		Reference typeRef = null;
		StaticIfExpIsDefUnit isExpDefUnit = null;
		ExpIsSpecialization specKind = null;
		Reference specTypeRef = null;
		ArrayView<ITemplateParameter> tplParams = null;
		
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			
			typeRef = parseTypeReference_ToMissing().node;
			
			if(lookAhead() == DeeTokens.IDENTIFIER) {
				ProtoDefSymbol defId = parseDefId();
				isExpDefUnit = concludeNode(srOf(lastLexElement(), new StaticIfExpIsDefUnit(defId)));
			}
			
			if(tryConsume(DeeTokens.COLON)) {
				specKind = ExpIsSpecialization.TYPE_SUBTYPE;
				specTypeRef = parseTypeReference_ToMissing().node;
			} else if(tryConsume(DeeTokens.EQUALS)) {
				specKind = determineIsExpArchetype();
				
				if(specKind != null ) {
					consumeLookAhead();
				} else {
					specKind = ExpIsSpecialization.TYPE_EXACT;
					
					specTypeRef = parseTypeReference().node;
					if(specTypeRef == null) {
						specTypeRef = parseMissingTypeReference(RULE_IS_TYPE_SPEC);						
					}
				}
			}
			
			if((specKind == ExpIsSpecialization.TYPE_SUBTYPE || specKind == ExpIsSpecialization.TYPE_EXACT) 
				&& tryConsume(DeeTokens.COMMA)) {
				tplParams = thisParser().parseTemplateParametersList();
			}
			
			parse.consumeRequired(DeeTokens.CLOSE_PARENS);
		}
		
		if(isExpDefUnit != null || tplParams != null) {
			return parse.resultConclude(new StaticIfExpIs(typeRef, isExpDefUnit, specKind, specTypeRef, tplParams));
		} else {
			return parse.resultConclude(new ExpIs(typeRef, specKind, specTypeRef));
		}
	}
	
	protected ExpIsSpecialization determineIsExpArchetype() {
		if(isImmutabilitySpecifier(lookAhead()) && 
			(lookAhead(1) == DeeTokens.OPEN_PARENS || canParseTypeReferenceStart(lookAhead(1))))
			return null;
		
		switch (lookAhead()) {
		case KW_STRUCT: return ExpIsSpecialization.STRUCT;
		case KW_UNION: return ExpIsSpecialization.UNION;
		case KW_CLASS: return ExpIsSpecialization.CLASS;
		case KW_INTERFACE: return ExpIsSpecialization.INTERFACE;
		case KW_ENUM: return ExpIsSpecialization.ENUM;
		case KW_FUNCTION: return ExpIsSpecialization.FUNCTION;
		case KW_TYPEDEF: return ExpIsSpecialization.TYPEDEF;
		case KW_DELEGATE: return ExpIsSpecialization.DELEGATE;
		case KW_SUPER: return ExpIsSpecialization.SUPER;
		case KW_CONST:
			return ExpIsSpecialization.CONST;
		case KW_IMMUTABLE: 
			return ExpIsSpecialization.IMMUTABLE;
		case KW_INOUT: 
			return ExpIsSpecialization.INOUT;
		case KW_SHARED: 
			return ExpIsSpecialization.SHARED;
		
		case KW_RETURN: return ExpIsSpecialization.RETURN;
		case IDENTIFIER: 
			if(lookAheadElement().getSourceValue().equals("__parameters"))
				return ExpIsSpecialization.__PARAMETERS;
		default:
			return null;
		}
	}
	
	public NodeResult<ExpTraits> parseTraitsExpression() {
		if(!tryConsume(DeeTokens.KW___TRAITS))
			return null;
		ParseHelper parse = new ParseHelper();
		
		Symbol traitsId = null;
		NodeListView<Resolvable> args = null;
		
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			
			traitsId = parseTraitsId();
			
			if(parse.consumeExpected(DeeTokens.COMMA)) { 
				SimpleListParseHelper<Resolvable> elementListParse = new TypeOrExpArgumentListSimpleParse();
				elementListParse.parseSimpleList(DeeTokens.COMMA, true, true);
				args = elementListParse.members;
			}
			
			parse.consumeRequired(DeeTokens.CLOSE_PARENS);
		}
		
		return parse.resultConclude(new ExpTraits(traitsId, args));
	}
	
	public Symbol parseTraitsId() {
		BaseLexElement traitsId = consumeExpectedContentToken(DeeTokens.IDENTIFIER);
		ParserError error = DeeTokenSemantics.checkTraitsId(traitsId);
		return conclude(error, srOf(traitsId, new Symbol(traitsId.getSourceValue())));
	}
	
}