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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.ArrayList;

import ddt.melnorme.lang.tooling.ast.ParserError;
import ddt.melnorme.lang.tooling.ast.util.NodeListView;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.ast_actual.ParserErrorTypes;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.dtool.ast.declarations.AbstractConditionalDeclaration.VersionSymbol;
import ddt.dtool.ast.declarations.AttribAlign;
import ddt.dtool.ast.declarations.AttribAtKeyword;
import ddt.dtool.ast.declarations.AttribBasic;
import ddt.dtool.ast.declarations.AttribBasic.AttributeKinds;
import ddt.dtool.ast.declarations.AttribCppLinkage;
import ddt.dtool.ast.declarations.AttribCustom;
import ddt.dtool.ast.declarations.AttribLinkage;
import ddt.dtool.ast.declarations.AttribLinkage.Linkage;
import ddt.dtool.ast.declarations.AttribPragma;
import ddt.dtool.ast.declarations.AttribProtection;
import ddt.dtool.ast.declarations.AttribProtection.EProtection;
import ddt.dtool.ast.declarations.Attribute;
import ddt.dtool.ast.declarations.DeclList;
import ddt.dtool.ast.declarations.DeclarationAttrib.AttribBodySyntax;
import ddt.dtool.ast.declarations.DeclarationDebugVersion;
import ddt.dtool.ast.declarations.DeclarationDebugVersionSpec;
import ddt.dtool.ast.declarations.DeclarationEmpty;
import ddt.dtool.ast.declarations.DeclarationImport;
import ddt.dtool.ast.declarations.DeclarationImport.IImportFragment;
import ddt.dtool.ast.declarations.DeclarationMixinString;
import ddt.dtool.ast.declarations.DeclarationStaticAssert;
import ddt.dtool.ast.declarations.DeclarationStaticIf;
import ddt.dtool.ast.declarations.ImportAlias;
import ddt.dtool.ast.declarations.ImportContent;
import ddt.dtool.ast.declarations.ImportSelective;
import ddt.dtool.ast.declarations.ImportSelective.IImportSelectiveSelection;
import ddt.dtool.ast.declarations.ImportSelectiveAlias;
import ddt.dtool.ast.declarations.MissingDeclaration;
import ddt.dtool.ast.definitions.DefUnit.ProtoDefSymbol;
import ddt.dtool.ast.definitions.Symbol;
import ddt.dtool.ast.expressions.ExpMixinString;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.references.RefImportSelection;
import ddt.dtool.ast.references.RefModule;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.IStatement;
import ddt.dtool.parser.DeeParser_Definitions.DefinitionStartInfo;
import ddt.dtool.parser.common.BaseLexElement;
import ddt.dtool.parser.common.IToken;
import ddt.dtool.parser.common.LexElement;


public abstract class DeeParser_Declarations extends DeeParser_Parameters {
	
	public NodeResult<DeclarationImport> parseDeclarationImport() {
		ParseHelper parse = new ParseHelper(lookAheadElement().getStartPos());
		
		boolean isStatic = false;
		if(tryConsume(DeeTokens.KW_IMPORT)) {
		} else if(tryConsume(DeeTokens.KW_STATIC, DeeTokens.KW_IMPORT)) {
			isStatic = true;
		} else {
			return null;
		}
		
		ArrayList<IImportFragment> fragments = new ArrayList<IImportFragment>();
		do {
			IImportFragment fragment = parseImportFragment();
			assertNotNull(fragment);
			fragments.add(fragment);
		} while(tryConsume(DeeTokens.COMMA));
		
		parse.consumeRequired(DeeTokens.SEMICOLON);
		return parse.resultConclude(new DeclarationImport(isStatic, arrayView(fragments)));
	}
	
	public IImportFragment parseImportFragment() {
		ProtoDefSymbol aliasId = null;
		
		IImportFragment fragment;
		
		if(lookAhead() == DeeTokens.IDENTIFIER && lookAhead(1) == DeeTokens.ASSIGN
			|| lookAhead() == DeeTokens.ASSIGN) {
			aliasId = parseDefId();
			ParseHelper parse = new ParseHelper(aliasId.getStartPos());
			consumeLookAhead(DeeTokens.ASSIGN);
			
			RefModule refModule = parseRefModule();
			fragment = parse.conclude(new ImportAlias(aliasId, refModule));
		} else {
			RefModule refModule = parseRefModule();
			fragment = conclude(srOf(refModule, new ImportContent(refModule)));
		}
		
		if(tryConsume(DeeTokens.COLON)) {
			return parseSelectiveModuleImport(fragment);
		}
		
		return fragment;
	}
	
	public RefModule parseRefModule() {
		ArrayList<IToken> packages = new ArrayList<IToken>(2);
		
		ParseHelper parse = new ParseHelper(-1);
		while(true) {
			BaseLexElement id = parse.consumeExpectedIdentifier();
			
			if(!id.isMissingElement() && tryConsume(DeeTokens.DOT)) {
				packages.add(id);
			} else {
				int idStartPos = id.getEffectiveStartPos();
				parse.setStartPosition(packages.size() > 0 ? packages.get(0).getStartPos() : idStartPos);
				return parse.conclude(new RefModule(arrayViewG(packages), id));
			}
		}
	}
	
	public ImportSelective parseSelectiveModuleImport(IImportFragment fragment) {
		ParseHelper parse = new ParseHelper(fragment.asNode());
		ArrayList<IImportSelectiveSelection> selFragments = new ArrayList<IImportSelectiveSelection>();
		
		do {
			IImportSelectiveSelection importSelSelection = parseImportSelectiveSelection();
			selFragments.add(importSelSelection);
			
		} while(tryConsume(DeeTokens.COMMA));
		
		return parse.conclude(new ImportSelective(fragment, arrayView(selFragments)));
	}
	
	public IImportSelectiveSelection parseImportSelectiveSelection() {
		
		if(lookAhead() == DeeTokens.IDENTIFIER && lookAhead(1) == DeeTokens.ASSIGN
			|| lookAhead() == DeeTokens.ASSIGN) {
			ProtoDefSymbol defId = parseDefId();
			consumeLookAhead(DeeTokens.ASSIGN);
			ParseHelper parse = new ParseHelper(defId.getStartPos());
			
			RefImportSelection refImportSelection = parseRefImportSelection();
			return parse.conclude(new ImportSelectiveAlias(defId, refImportSelection));
		} else {
			return parseRefImportSelection();
		}
	}
	
	public RefImportSelection parseRefImportSelection() {
		BaseLexElement idToken = consumeExpectedContentToken(DeeTokens.IDENTIFIER);
		return conclude(idToken.getMissingError(), 
			srEffective(idToken, new RefImportSelection(idTokenToString(idToken))));
	}
	
	public static final ParseRuleDescription RULE_DECLBODY = 
		new ParseRuleDescription("DeclOrBlock", "Declaration or Block");
	
	protected class AttribBodyParseRule {
		public AttribBodySyntax bodySyntax = AttribBodySyntax.SINGLE_DECL;
		public ASTNode declList;
		
		public AttribBodyParseRule parseAttribBody(ParseHelper parse, boolean acceptEmptyDecl, 
			DefinitionStartInfo defStartInfo, boolean autoDeclEnabled) {
			if(tryConsume(DeeTokens.COLON)) {
				bodySyntax = AttribBodySyntax.COLON;
				declList = parseDeclList(null);
			} else {
				parseDeclBlockOrSingle(parse, acceptEmptyDecl, defStartInfo, autoDeclEnabled);
			}
			return this;
		}
		
		public AttribBodyParseRule parseDeclBlockOrSingle(ParseHelper parse, boolean acceptEmptyDecl, 
			DefinitionStartInfo defStartInfo, boolean autoDeclEnabled) {
			if(lookAhead() == DeeTokens.OPEN_BRACE) {
				bodySyntax = AttribBodySyntax.BRACE_BLOCK;
				declList = parse.checkResult(thisParser().parseDeclarationBlock());
			} else {
				declList = parse.checkResult(thisParser().parseDeclaration(false, autoDeclEnabled, defStartInfo));
				if(declList == null) {
					declList = parseMissingDeclaration(RULE_DECLBODY);
				} else if(declList instanceof DeclarationEmpty && !acceptEmptyDecl) {
					parse.storeError(createSyntaxError(DeeParser.RULE_DECLARATION));
				}
			}
			return this;
		}
	}
	
	protected DeclList parseDeclList(DeeTokens bodyListTerminator) {
		ParseHelper parse = new ParseHelper(getSourcePosition());
		
		ArrayView<ASTNode> declDefs = thisParser().parseDeclarations(bodyListTerminator, false);
		advanceSubChannelTokens();
		return parse.conclude(new DeclList(declDefs));
	}
	
	public MissingDeclaration parseMissingDeclaration(ParseRuleDescription expectedRule) {
		int nodeStart = getSourcePosition();
		advanceSubChannelTokens();
		ParserError error = createErrorExpectedRule(expectedRule);
		return conclude(error, srToPosition(nodeStart, new MissingDeclaration()));
	}
	
	public static ASTNodeTypes getLastAttributeKind(ArrayView<Attribute> attributes) {
		if(attributes == null) {
			return ASTNodeTypes.NULL;
		}
		assertTrue(attributes.size() > 0);
		Attribute lastAttrib = attributes.get(attributes.size() - 1);
		return lastAttrib.getNodeType();
	}
	
	public NodeResult<? extends AttribLinkage> parseAttribLinkage() {
		if(!tryConsume(DeeTokens.KW_EXTERN))
			return null;
		ParseHelper parse = new ParseHelper();
		
		String linkageStr = null;
		
		parsing: {
			if(tryConsume(DeeTokens.OPEN_PARENS)) {
				linkageStr = "";
				
				LexElement linkageToken = consumeIf(DeeTokens.IDENTIFIER);
				if(linkageToken != null ) {
					linkageStr = linkageToken.getSourceValue();
					if(linkageStr.equals("C") && tryConsume(DeeTokens.INCREMENT)) {
						linkageStr = Linkage.CPP.name;
					}
				}
				
				if(Linkage.fromString(linkageStr) == null) {
					parse.storeError(createErrorOnLastToken(ParserErrorTypes.INVALID_EXTERN_ID, null));
				}
				
				if(linkageStr.equals(Linkage.CPP.name)) {
					return parseAttribCppLinkage_fromLinkaged(parse, linkageStr);
				}
				
				if(parse.consumeRequired(DeeTokens.CLOSE_PARENS).ruleBroken) break parsing;
			}
		}
		
		return parse.resultConclude(new AttribLinkage(linkageStr));
	}
	
	protected NodeResult<AttribCppLinkage> parseAttribCppLinkage_fromLinkaged(ParseHelper parse, String linkageStr) {
		Reference typeRef = null;
		if(parse.consumeExpected(DeeTokens.COMMA)) {
			typeRef = parseTypeReference_ToMissing().node;
		}
		parse.consumeRequired(DeeTokens.CLOSE_PARENS);
		
		return parse.resultConclude(new AttribCppLinkage(linkageStr, typeRef));
	}
	
	public NodeResult<AttribAlign> parseAttribAlign() {
		if(!tryConsume(DeeTokens.KW_ALIGN))
			return null;
		ParseHelper parse = new ParseHelper();
		
		BaseLexElement alignNum = null;
		
		parsing: {
			if(tryConsume(DeeTokens.OPEN_PARENS)) {
				alignNum = consumeExpectedContentToken(DeeTokens.INTEGER_DECIMAL);
				parse.storeError(alignNum.getMissingError());
				
				if(parse.consumeRequired(DeeTokens.CLOSE_PARENS).ruleBroken) break parsing;
			}
		}
		
		return parse.resultConclude(new AttribAlign(alignNum));
	}
	
	public NodeResult<AttribPragma> parseAttribPragma() {
		if(!tryConsume(DeeTokens.KW_PRAGMA))
			return null;
		ParseHelper parse = new ParseHelper();
		
		Symbol pragmaId = null;
		NodeListView<Expression> expList = null;
		
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			pragmaId = parseIdSymbol();
			
			if(tryConsume(DeeTokens.COMMA)) {
				expList = parseExpArgumentList(parse, false, DeeTokens.CLOSE_PARENS);
			} else {
				parse.consumeRequired(DeeTokens.CLOSE_PARENS);
			}
			if(parse.ruleBroken) break parsing;
		}
		
		return parse.resultConclude(new AttribPragma(pragmaId, expList));
	}
	
	public NodeResult<AttribProtection> parseAttribProtection() {
		if(lookAheadGrouped() != DeeTokens.GROUP_PROTECTION_KW) {
			return null;
		}
		LexElement protToken = consumeLookAhead();
		ParseHelper parse = new ParseHelper();
		EProtection protection = DeeTokenSemantics.getProtectionFromToken(protToken.type);
		
		return parse.resultConclude(new AttribProtection(protection));
	}
	
	public NodeResult<AttribBasic> parseAttribBasic() {
		AttributeKinds attrib = AttributeKinds.fromToken(lookAhead());
		if(attrib == null)
			return null;
		
		consumeLookAhead();
		ParseHelper parse = new ParseHelper();
		if(attrib == AttributeKinds.DEPRECATED) {
			parseExpressionAroundParentheses(parse, false, false);
			// TODO: tests for this, confirm spec
		}
		return parse.resultConclude(new AttribBasic(attrib));
	}
	
	public static final ParseRuleDescription RULE_ID_OR_EXP_ARGLIST = 
		new ParseRuleDescription("IdOrExpArgList", "ID or (<Expression List>)");
	
	public NodeResult<? extends Attribute> parseAmpersatAttrib() {
		if(!tryConsume(DeeTokens.AT)) 
			return null;
		
		ParseHelper parse = new ParseHelper();
		
		Reference baseRef = null;
		NodeListView<Expression> args = null;
		
		if(lookAhead() == DeeTokens.IDENTIFIER && DeeTokenSemantics.isPredefinedAttribId(lookAheadElement())) {
			BaseLexElement traitsId = consumeLookAhead(DeeTokens.IDENTIFIER);
			Symbol attribIdentifier = conclude(srOf(traitsId, new Symbol(traitsId.getSourceValue())));
			return parse.resultConclude(new AttribAtKeyword(attribIdentifier));
		}
		
		parsing: {
			baseRef = attemptParseRefIdentifier();
			if(parse.ruleBroken) break parsing;
			
			if(lookAhead() == DeeTokens.NOT) {
				baseRef = parse.checkResult(
					parseTypeReference_withLeftReference(baseRef, RefParseRestrictions.TEMPLATE_ONLY));
			}
			if(parse.ruleBroken) break parsing;
			
			args = parseParenthesesDelimited_ExpArgumentList(parse);
			
			if(args == null && baseRef == null) {
				parse.storeError(createErrorExpectedRule(RULE_ID_OR_EXP_ARGLIST));
			}
		}
		return parse.resultConclude(new AttribCustom(baseRef, args));		
	}
	
	public NodeResult<DeclarationStaticIf> parseDeclarationStaticIf(boolean isStatement) {
		ParseHelper parse = new ParseHelper(lookAheadElement());
		if(!tryConsume(DeeTokens.KW_STATIC, DeeTokens.KW_IF))
			return null;
		
		Expression exp = null;
		ConditionalBodyParseRule body = new ConditionalBodyParseRule();
		
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			exp = parseAssignExpression_toMissing();
			if(parse.consumeRequired(DeeTokens.CLOSE_PARENS).ruleBroken) break parsing;
			
			body.parseConditionalBody(parse, isStatement);
		}
		if(isStatement) {
			return parse.resultConclude(new DeclarationStaticIf(exp, body.thenBodySt, body.elseBodySt));
		}
		return parse.resultConclude(new DeclarationStaticIf(exp, body.bodySyntax, body.declList, body.elseBody));
	}
	
	public NodeResult<DeclarationDebugVersion> parseDeclarationDebugVersion(boolean isStatement) {
		if(!(tryConsume(DeeTokens.KW_DEBUG) || tryConsume(DeeTokens.KW_VERSION)))
			return null;
		boolean isDebug = lastLexElement().type == DeeTokens.KW_DEBUG;
		ParseHelper parse = new ParseHelper();
		
		VersionSymbol value = null;
		ConditionalBodyParseRule body = new ConditionalBodyParseRule();
		
		parsing: {
			if(parse.consume(DeeTokens.OPEN_PARENS, isDebug, true)) {
				if(lookAhead() == DeeTokens.KW_ASSERT || lookAhead() == DeeTokens.KW_UNITTEST) {
					value = createVersionSymbol(consumeLookAhead());
				} else {
					value = parseConditionalValue(isDebug, parse);
				}
				parse.consumeRequired(DeeTokens.CLOSE_PARENS);
			}
			if(parse.ruleBroken) break parsing;
			
			body.parseConditionalBody(parse, isStatement);
		}
		if(isStatement) {
			return parse.resultConclude(new DeclarationDebugVersion(isDebug, value, body.thenBodySt, body.elseBodySt));
		}
		return parse.resultConclude(
			new DeclarationDebugVersion(isDebug, value, body.bodySyntax, body.declList, body.elseBody));
	}
	
	protected class ConditionalBodyParseRule extends AttribBodyParseRule {
		
		public ASTNode elseBody = null;
		public IStatement thenBodySt = null;
		public IStatement elseBodySt = null;
		
		public void parseConditionalBody(ParseHelper parse, boolean isStatement) {
			
			if(isStatement) {
				thenBodySt = parse.checkResult(thisParser().parseUnscopedStatement_toMissing()); 
				if(parse.ruleBroken) return;
				
				if(tryConsume(DeeTokens.KW_ELSE)) {
					elseBodySt = parse.checkResult(thisParser().parseUnscopedStatement_toMissing());
				}
			} else {
				parseAttribBody(parse, false, null, false);
				if(parse.ruleBroken) return;
				
				if(bodySyntax != AttribBodySyntax.COLON) {
					if(tryConsume(DeeTokens.KW_ELSE)) {
						elseBody = new AttribBodyParseRule().
							parseDeclBlockOrSingle(parse, false, null, false).declList;
					}
				}
			}
		}
		
	}
	
	/* ----------------------------------------- */
	
	public static final ParseRuleDescription RULE_DEBUG_ARG = 
		new ParseRuleDescription("DebugArg", "DebugArgument");
	public static final ParseRuleDescription RULE_VERSION_ARG = 
		new ParseRuleDescription("VersionArg", "VersionArgument");
	
	public NodeResult<DeclarationDebugVersionSpec> parseDeclarationDebugVersionSpec() {
		if(!(tryConsume(DeeTokens.KW_DEBUG) || tryConsume(DeeTokens.KW_VERSION)))
			return null;
		boolean isDebug = lastLexElement().type == DeeTokens.KW_DEBUG;
		ParseHelper parse = new ParseHelper();
		
		VersionSymbol value = null;
		if(parse.consumeExpected(DeeTokens.ASSIGN)) {
			value = parseConditionalValue(isDebug, parse);
		}
		parse.consumeRequired(DeeTokens.SEMICOLON);
		
		return parse.resultConclude(new DeclarationDebugVersionSpec(isDebug, value));
	}
	
	protected VersionSymbol parseConditionalValue(boolean isDebug, ParseHelper parse) {
		if(lookAhead() == DeeTokens.IDENTIFIER || lookAheadGrouped() == DeeTokens.GROUP_INTEGER) {
			return createVersionSymbol(consumeLookAhead());
		} else { 
			parse.storeError(createErrorExpectedRule(isDebug ? RULE_DEBUG_ARG : RULE_VERSION_ARG));
			return createVersionSymbol(consumeSubChannelTokensNoError());
		}
	}
	
	public VersionSymbol createVersionSymbol(BaseLexElement token) {
		return conclude(srOf(token, new VersionSymbol(token.getSourceValue())));
	}
	
	public NodeResult<DeclarationStaticAssert> parseDeclarationStaticAssert() {
		ParseHelper parse = new ParseHelper(lookAheadElement());
		if(!tryConsume(DeeTokens.KW_STATIC, DeeTokens.KW_ASSERT)) 
			return null;
		
		Expression pred = null;
		Expression msg = null;
		
		if(parse.consumeExpected(DeeTokens.OPEN_PARENS)) {
			
			pred = parseAssignExpression_toMissing();
			if(tryConsume(DeeTokens.COMMA)) {
				msg = parseAssignExpression_toMissing();
			}
			
			parse.consumeExpected(DeeTokens.CLOSE_PARENS);
		}
		parse.consumeRequired(DeeTokens.SEMICOLON);
		
		return parse.resultConclude(new DeclarationStaticAssert(pred, msg));
	}
	
	public NodeResult<DeclarationMixinString> parseDeclarationMixinString() {
		if(lookAhead() != DeeTokens.KW_MIXIN) {
			return null;
		}
		
		ParseHelper parse = new ParseHelper(lookAheadElement());
		
		ExpMixinString mixinExpression = parseMixinExpression().node;
		
		parse.consumeRequired(DeeTokens.SEMICOLON);
		return parse.resultConclude(new DeclarationMixinString(mixinExpression));
	}
	
}