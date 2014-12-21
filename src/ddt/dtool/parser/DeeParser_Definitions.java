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

import static ddt.dtool.util.NewUtils.lazyInitArrayList;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;

import ddt.melnorme.lang.tooling.ast.ParserError;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.ast_actual.ParserErrorTypes;
import ddt.melnorme.utilbox.collections.ArrayView;
import ddt.melnorme.utilbox.misc.ArrayUtil;
import ddt.dtool.ast.declarations.Attribute;
import ddt.dtool.ast.declarations.DeclBlock;
import ddt.dtool.ast.declarations.DeclarationAliasThis;
import ddt.dtool.ast.declarations.DeclarationAllocatorFunction;
import ddt.dtool.ast.declarations.DeclarationAttrib;
import ddt.dtool.ast.declarations.DeclarationAttrib.AttribBodySyntax;
import ddt.dtool.ast.declarations.DeclarationEmpty;
import ddt.dtool.ast.declarations.DeclarationInvariant;
import ddt.dtool.ast.declarations.DeclarationSpecialFunction;
import ddt.dtool.ast.declarations.DeclarationSpecialFunction.SpecialFunctionKind;
import ddt.dtool.ast.declarations.DeclarationUnitTest;
import ddt.dtool.ast.declarations.IDeclaration;
import ddt.dtool.ast.declarations.IncompleteDeclarator;
import ddt.dtool.ast.declarations.InvalidSyntaxElement;
import ddt.dtool.ast.declarations.MissingDeclaration;
import ddt.dtool.ast.definitions.AbstractFunctionDefinition;
import ddt.dtool.ast.definitions.CommonDefinition;
import ddt.dtool.ast.definitions.DeclarationEnum;
import ddt.dtool.ast.definitions.DeclarationMixin;
import ddt.dtool.ast.definitions.DefUnit.ProtoDefSymbol;
import ddt.dtool.ast.definitions.DefVarFragment;
import ddt.dtool.ast.definitions.DefinitionAggregate;
import ddt.dtool.ast.definitions.DefinitionAggregate.IAggregateBody;
import ddt.dtool.ast.definitions.DefinitionAlias;
import ddt.dtool.ast.definitions.DefinitionAlias.DefinitionAliasFragment;
import ddt.dtool.ast.definitions.DefinitionAliasFunctionDecl;
import ddt.dtool.ast.definitions.DefinitionAliasVarDecl;
import ddt.dtool.ast.definitions.DefinitionAliasVarDecl.AliasVarDeclFragment;
import ddt.dtool.ast.definitions.DefinitionClass;
import ddt.dtool.ast.definitions.DefinitionConstructor;
import ddt.dtool.ast.definitions.DefinitionEnum;
import ddt.dtool.ast.definitions.DefinitionEnum.EnumBody;
import ddt.dtool.ast.definitions.DefinitionEnumVar;
import ddt.dtool.ast.definitions.DefinitionEnumVar.DefinitionEnumVarFragment;
import ddt.dtool.ast.definitions.DefinitionFunction;
import ddt.dtool.ast.definitions.DefinitionInterface;
import ddt.dtool.ast.definitions.DefinitionMixinInstance;
import ddt.dtool.ast.definitions.DefinitionStruct;
import ddt.dtool.ast.definitions.DefinitionTemplate;
import ddt.dtool.ast.definitions.DefinitionUnion;
import ddt.dtool.ast.definitions.DefinitionVariable;
import ddt.dtool.ast.definitions.DefinitionVariable.DefinitionAutoVariable;
import ddt.dtool.ast.definitions.EnumMember;
import ddt.dtool.ast.definitions.FunctionAttributes;
import ddt.dtool.ast.definitions.IFunctionParameter;
import ddt.dtool.ast.definitions.Module;
import ddt.dtool.ast.definitions.Module.DeclarationModule;
import ddt.dtool.ast.definitions.Symbol;
import ddt.dtool.ast.definitions.ITemplateParameter;
import ddt.dtool.ast.expressions.ExpInfix.InfixOpType;
import ddt.dtool.ast.expressions.Expression;
import ddt.dtool.ast.expressions.IInitializer;
import ddt.dtool.ast.expressions.InitializerArray;
import ddt.dtool.ast.expressions.InitializerArray.ArrayInitEntry;
import ddt.dtool.ast.expressions.InitializerStruct;
import ddt.dtool.ast.expressions.InitializerStruct.StructInitEntry;
import ddt.dtool.ast.expressions.InitializerVoid;
import ddt.dtool.ast.references.RefIdentifier;
import ddt.dtool.ast.references.RefTypeFunction;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.ast.statements.BlockStatement;
import ddt.dtool.ast.statements.EmptyStatement;
import ddt.dtool.ast.statements.FunctionBody;
import ddt.dtool.ast.statements.FunctionBodyOutBlock;
import ddt.dtool.ast.statements.IFunctionBody;
import ddt.dtool.ast.statements.InOutFunctionBody;
import ddt.dtool.parser.common.AbstractParser;
import ddt.dtool.parser.common.BaseLexElement;
import ddt.dtool.parser.common.IToken;
import ddt.dtool.parser.common.LexElement;
import ddt.dtool.parser.common.Token;
import ddt.dtool.util.NewUtils;
import ddt.dtool.util.SentinelArrayList;

public abstract class DeeParser_Definitions extends DeeParser_Declarations {
	
	public static ArrayList<Token> readStartDDocComments(LexElement declStart, int sourcePosition) {
		ArrayList<Token> docComments = null;
		for (Token token : declStart.getRelevantPrecedingSubChannelTokens()) {
			if(token.getStartPos() < sourcePosition) {
				assertTrue(token.getEndPos() <= sourcePosition);
				continue;
			}
			
			if(DeeTokenSemantics.tokenIsDocComment(token)) {
				docComments = docComments == null ? new ArrayList<Token>(2) : docComments;
				docComments.add(token);
			}
		}
		return docComments;
	}
	
	private static ArrayList<Token> END_DOCCOMMENTS_READ = new SentinelArrayList<>();
	
	public class DefinitionStartInfo {
		
		protected ArrayList<Token> comments;
		protected int extendedStart;
		
		public DefinitionStartInfo(ArrayList<Token> comments, int extendedStart) {
			this.comments = comments;
			this.extendedStart = extendedStart;
		}
	}
	
	public DefinitionStartInfo parseDefStartInfo() {
		ArrayList<Token> comments = readStartDDocComments(lookAheadElement(), getSourcePosition());
		if(comments == null) {
			return new DefinitionStartInfo(comments, lookAheadElement().getStartPos());
		} else {
			return new DefinitionStartInfo(comments, comments.get(0).getStartPos());
		}
	}
	
	public DefParseHelper createDefParseHelper(DefinitionStartInfo defStartInfo) {
		if(defStartInfo == null) {
			defStartInfo = parseDefStartInfo();
		}
		return new DefParseHelper(defStartInfo);
	}
	
	public class DefParseHelper extends ParseHelper {
		
		protected ArrayList<Token> comments;
		protected final int extendedStart;
		
		public DefParseHelper(DefinitionStartInfo defStartInfo) {
			super(lookAheadElement());
			assertNotNull(defStartInfo);
			this.comments = defStartInfo.comments;
			this.extendedStart = defStartInfo.extendedStart;
		}
		
		public final Token[] parseEndDDocComments() {
			assertTrue(comments != END_DOCCOMMENTS_READ);
			
			parsing: {
				if(ruleBroken) break parsing;
				
				LexElement nextLexElement = lookAheadElement();
				
				for (Token token : nextLexElement.getRelevantPrecedingSubChannelTokens()) {
					if(token.type == DeeTokens.LINE_END)
						break;
					if(token.type == DeeTokens.DOCCOMMENT_LINE) {
						comments = lazyInitArrayList(comments);
						comments.add(token);
						getEnabledLexSource().setSourcePosition(token.getEndPos());
					}
				}
			}
			Token[] result = comments == null ? null : ArrayUtil.createFrom(comments, Token.class);
			discardDocComments();
			return result;
		}
		
		public final void discardDocComments() {
			comments = END_DOCCOMMENTS_READ;
		}
		
		public <T extends CommonDefinition> T conclude(T node) {
			node.setExtendedStartPos(extendedStart);
			return super.conclude(node);
		}
		public final <T extends CommonDefinition> NodeResult<T> resultConclude(T node) {
			return result(ruleBroken, conclude(node));
		}
		
	}
	
	/* ----------------------------------------------------------------- */
	
	public AbstractParser.NodeResult<Module> parseModule(String defaultModuleName, Path compilationUnitPath) {
		assertNotNull(defaultModuleName);
		DeclarationModule md = parseModuleDeclaration();
		
		ArrayView<ASTNode> members = parseDeclarations(null, true);
		assertTrue(lookAhead() == DeeTokens.EOF);
		advanceSubChannelTokens(); // Ensure pending whitespace is consumed as well
		assertTrue(getSourcePosition() == lookAheadElement().getStartPos());
		//assertTrue(getSourcePosition() == getSource().length()); //This is not true if explicit EOF token is present 
		
		SourceRange modRange = new SourceRange(0, getSourcePosition());
		
		if(md != null) {
			return result(false, conclude(modRange, 
				new Module(md.getModuleSymbol(), md, members, compilationUnitPath)));
		} else {
			return result(false, conclude(modRange, 
				Module.createModuleNoModuleDecl(defaultModuleName, members, compilationUnitPath, modRange)));
		}
	}
	
	public DeclarationModule parseModuleDeclaration() {
		if(lookAhead() != DeeTokens.KW_MODULE) {
			return null;
		}
		DefParseHelper parse = createDefParseHelper(parseDefStartInfo());
		consumeLookAhead();
		
		ArrayList<IToken> packagesList = new ArrayList<>(2);
		BaseLexElement moduleId;
		
		while(true) {
			moduleId = parse.consumeExpectedIdentifier();
			
			if(!moduleId.isMissingElement() && tryConsume(DeeTokens.DOT)) {
				packagesList.add(moduleId);
				moduleId = null;
				continue;
			}
			break;
		}
		parse.consumeRequired(DeeTokens.SEMICOLON);
		Token[] comments = parse.parseEndDDocComments();
		
		return parse.conclude(new DeclarationModule(comments, arrayViewG(packagesList), moduleId));
	}
	
	public ArrayView<ASTNode> parseDeclarations(DeeTokens nodeListTerminator, boolean consumeCloseBrackets) {
		ArrayList<ASTNode> declarations = new ArrayList<>();
		while(true) {
			if(lookAhead() == nodeListTerminator) {
				break;
			}
			ASTNode decl = parseDeclaration().node;
			if(decl == null) { 
				if(lookAhead() == DeeTokens.EOF || (!consumeCloseBrackets && isCloseBracketChar(lookAhead()))) {
					break;
				}
				decl = parseInvalidElement(RULE_DECLARATION, false);
			}
			declarations.add(decl);
		}
		
		return arrayView(declarations);
	}
	
	
	public static final ParseRuleDescription RULE_DECLARATION = new ParseRuleDescription("Declaration", "Declaration");
	
	public InvalidSyntaxElement parseInvalidElement(ParseRuleDescription expectedRule, 
		boolean inStatementList) {
		LexElement badToken = consumeLookAhead();
		ParseHelper parse = new ParseHelper();
		parse.storeError(createSyntaxError(expectedRule));
		return parse.conclude(new InvalidSyntaxElement(inStatementList, badToken));
	}
	
	public NodeResult<? extends IDeclaration> parseDeclaration() {
		return parseDeclaration(false, false, null);
	}
	public NodeResult<? extends IDeclaration> parseDeclaration(boolean statementsOnly) {
		return parseDeclaration(statementsOnly, false, null);
	}
	public NodeResult<? extends IDeclaration> parseDeclaration(boolean statementsOnly, boolean autoDeclEnabled,
		DefinitionStartInfo defStartInfo) {
		
		DeeTokens laGrouped = assertNotNull(lookAheadGrouped());
		switch (laGrouped) {
		case KW_IMPORT: 
			return parseDeclarationImport();
		case KW_STRUCT:
			return parseDefinitionStruct(defStartInfo);
		case KW_UNION:
			return parseDefinitionUnion(defStartInfo);
		case KW_CLASS:
			return parseDefinitionClass(defStartInfo);
		case KW_INTERFACE:
			return parseDefinitionInterface(defStartInfo);
		case KW_TEMPLATE: 
			return parseTemplateDefinition(defStartInfo);
			
		case KW_ALIAS:
			if(lookAhead(1) == DeeTokens.KW_THIS ||  
				(lookAhead(1) == DeeTokens.IDENTIFIER && lookAhead(2) == DeeTokens.KW_THIS)) {
				return parseDeclarationAliasThis();
			}
			return parseDefinitionAlias(defStartInfo);
		case KW_MIXIN: 
			if(lookAhead(1) == DeeTokens.KW_TEMPLATE) {
				return parseTemplateDefinition(defStartInfo);
			}
			if(lookAhead(1) == DeeTokens.OPEN_PARENS) {
				return parseDeclarationMixinString();
			}
			return parseDeclarationMixin(defStartInfo);
		case KW_ENUM:
			if(canParseDeclarationEnum())
				return parseDeclarationEnum_start();
			if(canParseDefinitionEnum())
				return parseDefinitionEnum_start(defStartInfo);
			break;
		case GROUP_ATTRIBUTE_KW:
			if(shouldParseAsAttributeVsStaticDeclarations()) {
				break;
			}
			if(lookAhead() == DeeTokens.KW_STATIC) { 
				if(lookAhead(1) == DeeTokens.KW_IMPORT) { 
					return parseDeclarationImport();
				}
				if(lookAhead(1) == DeeTokens.KW_ASSERT) { 
					return parseDeclarationStaticAssert();
				}
				if(lookAhead(1) == DeeTokens.KW_IF) { 
					return parseDeclarationStaticIf(statementsOnly);
				}
			}
			throw assertFail();
		case KW_DEBUG:
			if(!statementsOnly && lookAhead(1) == DeeTokens.ASSIGN) {
				return parseDeclarationDebugVersionSpec();
			}
			return parseDeclarationDebugVersion(statementsOnly);
		case KW_VERSION:
			if(!statementsOnly && lookAhead(1) == DeeTokens.ASSIGN) {
				return parseDeclarationDebugVersionSpec();
			}
			return parseDeclarationDebugVersion(statementsOnly);
		case KW_INVARIANT: 
			if(statementsOnly) return declarationNullResult();
			return parseDeclarationInvariant_start();
		case KW_UNITTEST: 
			if(statementsOnly) return declarationNullResult();
			return parseDeclarationUnitTest_start();
		case KW_NEW:
		case KW_DELETE: 
			if(statementsOnly) return declarationNullResult();
			return parseDeclarationAllocatorFunctions();
		case KW_THIS: 
			if(statementsOnly) return declarationNullResult();
			if(lookAhead(1) == DeeTokens.OPEN_PARENS && lookAhead(2) == DeeTokens.KW_THIS) {
				return parseDeclarationPostBlit_start();
			}
			return parseDefinitionConstructor_atThis(createDefParseHelper(defStartInfo));
		case CONCAT:
			if(lookAhead(1) == DeeTokens.KW_THIS)
				return parseDeclarationSpecialFunction();
			break;
		case SEMICOLON:
			return resultConclude(false, srOf(consumeLookAhead(), new DeclarationEmpty()));
		default:
			break;
		}
		
		if(lookAheadGrouped() == DeeTokens.GROUP_ATTRIBUTE_KW && shouldParseAsAttributeVsTypeModifier()) {
			// parse current as attribute
		} else {
			NodeResult<? extends IDeclaration> 
				declResult = parseDeclaration_varOrFunction(defStartInfo, autoDeclEnabled);
			
			if(!isNull(declResult)) {
				return declResult;
			}
		}
		
		if(defStartInfo != null) {
			assertTrue(parseAttribute() == null);
		} else {
			NodeResult<DeclarationAttrib> declAttrib = parseDeclarationAttrib(statementsOnly);
			if(declAttrib != null)
				return declAttrib;
		}
		return declarationNullResult();
	}

	public boolean shouldParseAsAttributeVsTypeModifier() {
		return !(isImmutabilitySpecifier(lookAhead()) && lookAhead(1) == DeeTokens.OPEN_PARENS);
	}
	
	public final boolean canParseDeclarationEnum() {
		return lookAhead(1) == DeeTokens.COLON || lookAhead(1) == DeeTokens.OPEN_BRACE;
	}
	
	public final boolean canParseDefinitionEnum() {
		return (lookAhead(1) == DeeTokens.SEMICOLON) || 
			(lookAhead(1) == DeeTokens.IDENTIFIER && lookAhead(2) == DeeTokens.COLON) ||
			(lookAhead(1) == DeeTokens.IDENTIFIER && lookAhead(2) == DeeTokens.OPEN_BRACE) ||
			(lookAhead(1) == DeeTokens.IDENTIFIER && lookAhead(2) == DeeTokens.ASSIGN) ||
			(lookAhead(1) == DeeTokens.IDENTIFIER && lookAhead(2) == DeeTokens.OPEN_PARENS) ||
			(lookAhead(1) == DeeTokens.IDENTIFIER && lookAhead(2) == DeeTokens.SEMICOLON)
			;
	}
	
	public final boolean shouldParseAsAttributeVsStaticDeclarations() {
		if(lookAhead() == DeeTokens.KW_STATIC) { 
			if(lookAhead(1) == DeeTokens.KW_IMPORT || 
				lookAhead(1) ==  DeeTokens.KW_ASSERT || 
				lookAhead(1) == DeeTokens.KW_IF) {
				return false;
			}
		}
		return true;
	}
	
	public static NodeResult<? extends IDeclaration> declarationNullResult() {
		return AbstractParser.<MissingDeclaration>result(false, null);
	}
	
	/* --------------------- ATTRIBUTES --------------------- */
	
	// Note: make sure this is consistent/synchronized with the code in declaration parse
	public NodeResult<? extends Attribute> parseAttribute() {
		switch (lookAheadGrouped()) {
		case KW_ALIGN: 
			return parseAttribAlign();
		case KW_PRAGMA: 
			return parseAttribPragma();
		case GROUP_PROTECTION_KW: 
			return parseAttribProtection();
		case KW_EXTERN: 
			if(lookAhead(1) == DeeTokens.OPEN_PARENS) {
				return parseAttribLinkage();
			}
			return parseAttribBasic();
		case AT:
			return parseAmpersatAttrib();
		case KW_AUTO:
			return parseAttribBasic();
		case KW_ENUM:
			if(canParseDeclarationEnum() || canParseDefinitionEnum())
				break;
			return parseAttribBasic();
		case GROUP_ATTRIBUTE_KW:
			if(!shouldParseAsAttributeVsTypeModifier())
				break;
			if(!shouldParseAsAttributeVsStaticDeclarations())
				break;
			return parseAttribBasic();
		default:
		}
		return null;
	}
	
	public NodeResult<DeclarationAttrib> parseDeclarationAttrib(boolean isStatementParsing) {
		
		DefinitionStartInfo defStartInfo = parseDefStartInfo();
		ParseHelper parse = new ParseHelper(lookAheadElement());
		ArrayView<Attribute> attributes = parseDefinitionAttributes(parse);
		
		if(attributes == null) {
			parse.checkRuleBroken();
			return null;
		}
		
		ASTNode body = null;
		AttribBodySyntax bodySyntax = AttribBodySyntax.SINGLE_DECL;
		
		if(parse.checkRuleBroken() == false) {
			boolean isPragmaBody = getLastAttributeKind(attributes) == ASTNodeTypes.ATTRIB_PRAGMA;
			boolean autoDeclEnabled = isAutoVarEnablingAttrib(attributes);
			
			if(!isStatementParsing) {
				AttribBodyParseRule ab = new AttribBodyParseRule();
				ab.parseAttribBody(parse, isPragmaBody, defStartInfo, autoDeclEnabled);
				body = ab.declList;
				bodySyntax = ab.bodySyntax;
			} else if(isPragmaBody) {
				body = (ASTNode) parse.checkResult(thisParser().parseUnscopedStatement_toMissing());
			} else {
				NodeResult<? extends IDeclaration> decl = parseDeclaration(true, autoDeclEnabled, defStartInfo);
				body = !isNull(decl) ? decl.node : parseMissingDeclaration(RULE_DECLARATOR);
			}
		}
		
		return parse.resultConclude(new DeclarationAttrib(attributes, bodySyntax, body));
	}
	
	public boolean isAutoVarEnablingAttrib(ArrayView<Attribute> attributes) {
		ASTNodeTypes lastAttribKind = getLastAttributeKind(attributes);
		switch (lastAttribKind) {
		case ATTRIB_LINKAGE:
		case ATTRIB_CPP_LINKAGE:
		case ATTRIB_ALIGN:
		case ATTRIB_PRAGMA:
		case ATTRIB_CUSTOM:
		case NULL:
			return false;
		default:
		}
		return true;
	}
	
	protected ArrayView<Attribute> parseDefinitionAttributes(ParseHelper parse) {
		ArrayList<Attribute> stcList = null;
		
		while(true) {
			NodeResult<? extends Attribute> attribResult = parseAttribute();
			if(attribResult == null) {
				break;
			}
			assertTrue(attribResult.node != null);
			parse.checkResult(attribResult);
			
			stcList = NewUtils.lazyInitArrayList(stcList);
			stcList.add(attribResult.node);
			if(parse.ruleBroken) {
				break;
			}
		}
		parse.requireBrokenCheck();
		return arrayView(stcList);
	}
	
	
	/* --------------------- DEFINITIONS --------------------- */
	
	public static final ParseRuleDescription RULE_DECLARATOR = new ParseRuleDescription("Declarator",
		"Declarator(Reference or Identifier)");
	
	public NodeResult<? extends IDeclaration> parseDeclaration_varOrFunction(DefinitionStartInfo defStartInfo, 
		boolean autoEnablingAttribPresent) {
		if(!(canParseTypeReferenceStart(lookAhead()) || lookAhead() == DeeTokens.IDENTIFIER)) {
			return declarationNullResult();
		}
		DefParseHelper parse = createDefParseHelper(defStartInfo);
		NodeResult<Reference> refResult = parseTypeReference(); // This parses (BasicType + BasicType2) of spec
		assertNotNull(refResult.node);
		Reference ref = parse.checkResult(refResult); 
		
		parsing: {
			if(parse.ruleBroken) break parsing;
			
			ProtoDefSymbol defId = null;
			if(lookAhead() == DeeTokens.IDENTIFIER) {
				defId = defSymbol(consumeLookAhead());
			} else if(autoEnablingAttribPresent && couldHaveBeenParsedAsId(ref)) {
				// Parse as auto declaration instead
				defId = convertRefIdToDef(ref);
				ref = null;
			}
			
			if(defId != null) {
				if(lookAhead() == DeeTokens.OPEN_PARENS) {
					return parseDefinitionFunction_afterIdentifier(parse, ref, defId);
				}
				return parseDefinitionVariable_afterIdentifier(parse, ref, defId);
			} else {
				parse.consumeExpected(DeeTokens.IDENTIFIER);
			}
		}
		parse.discardDocComments();
		parse.clearRuleBroken().consumeRequired(DeeTokens.SEMICOLON);
		return parse.resultConclude(new IncompleteDeclarator(ref));
	}
	
	/* ----------------------------------------- */
	
	
	protected NodeResult<? extends DefinitionVariable> parseDefinitionVariable_afterIdentifier(
		DefParseHelper parse, Reference ref, ProtoDefSymbol defId) 
	{
		ArrayList<DefVarFragment> fragments = null;
		IInitializer init = null;
		Reference cstyleSuffix = null;
		
		final boolean isAutoRef = ref == null;
		
		parsing: {
			if(!isAutoRef) {
				cstyleSuffix = parseCStyleSuffix(parse);
				if(parse.checkRuleBroken()) break parsing;
			}
			
			if(parse.consumeOptional(DeeTokens.ASSIGN)){
				init = parseInitializer().node;
			} else if(isAutoRef) {
				parse.storeError(createExpectedTokenError(DeeTokens.ASSIGN));
			}
			
			while(parse.consumeOptional(DeeTokens.COMMA)) {
				DefVarFragment defVarFragment = parseVarFragment(isAutoRef);
				fragments = lazyInitArrayList(fragments);
				fragments.add(defVarFragment);
			}
		}
		parse.clearRuleBroken().consumeRequired(DeeTokens.SEMICOLON);
		Token[] comments = parse.parseEndDDocComments();
		
		if(isAutoRef) {
			return parse.resultConclude(
				new DefinitionAutoVariable(comments, defId, init, arrayView(fragments)));
		}
		return parse.resultConclude(
			new DefinitionVariable(comments, defId, ref, cstyleSuffix, init, arrayView(fragments)));
	}
	
	protected DefVarFragment parseVarFragment(boolean isAutoRef) {
		ProtoDefSymbol fragId = parseDefId();
		ParseHelper parse = new ParseHelper(fragId.getStartPos());
		IInitializer init = null;
		
		if(!fragId.isMissing()) {
			if(tryConsume(DeeTokens.ASSIGN)){ 
				init = parseInitializer().node;
			} else if(isAutoRef) {
				parse.storeError(createExpectedTokenError(DeeTokens.ASSIGN));
			}
		}
		return parse.conclude(new DefVarFragment(fragId, init));
	}
	
	public static final ParseRuleDescription RULE_INITIALIZER = new ParseRuleDescription("Initializer", "Initializer");
	
	public NodeResult<? extends IInitializer> parseInitializer() {
		if(tryConsume(DeeTokens.KW_VOID)) {
			return resultConclude(false, srOf(lastLexElement(), new InitializerVoid()));
		}
		
		return parseNonVoidInitializer(true);
	}
	
	public NodeResult<? extends IInitializer> parseNonVoidInitializer(boolean createMissing) {
		if(lookAhead() == DeeTokens.OPEN_BRACKET) {
			NodeResult<InitializerArray> arrayInitResult = parseArrayInitializer();
			if(arrayInitResult.ruleBroken) {
				return arrayInitResult;
			}
			InitializerArray arrayInit = arrayInitResult.node;
			assertTrue(arrayInit.getData().hasErrors() == false);
			
			NodeResult<Expression> fullExpInitializer = parseExpression_fromUnary(InfixOpType.ASSIGN, arrayInit);
			if(fullExpInitializer.node == arrayInit) {
				return arrayInitResult;
			}
			if(!arrayInitializerCouldParseAsArrayLiteral(arrayInit)) {
				ParserError error = createError(ParserErrorTypes.INIT_USED_IN_EXP, arrayInit.getSourceRange(), null);
				arrayInit.resetData();
				conclude(error, arrayInit);
			} else {
				// Even if initializer can be parsed as array literal, we place it in exp without any node conversion
				// (this might change in future)
			}
			return fullExpInitializer;
		}
		if(lookAhead() == DeeTokens.OPEN_BRACE) {
			ParserState savedParserState = saveParserState();
			NodeResult<InitializerStruct> structInitResult = parseStructInitializer();
			
			if(structInitResult.ruleBroken) {
				restoreOriginalState(savedParserState);
				return parseExpInitializer(createMissing);
			}
			return structInitResult;
			
		}
		return parseExpInitializer(createMissing);
	}
	
	public boolean arrayInitializerCouldParseAsArrayLiteral(InitializerArray arrayInit) {
		if(arrayInit.entries.size() < 1) {
			return true;
		}
		boolean mustBeMapEntries = arrayInit.entries.get(0).index != null;
		for (ArrayInitEntry entry : arrayInit.entries) {
			if(entry.value instanceof InitializerArray) {
				InitializerArray initArraySubEntry = (InitializerArray) entry.value;
				if(!arrayInitializerCouldParseAsArrayLiteral(initArraySubEntry)) {
					return false;
				}
				return true;
			} else if(!(entry.value instanceof Expression)) {
				return false;
			}
			boolean isMapEntry = entry.index != null;
			if(isMapEntry != mustBeMapEntries)
				return false;
		}
		return true;
	}

	public NodeResult<Expression> parseExpInitializer(boolean createMissing) {
		return createMissing ? 
			parseAssignExpression_toMissing(true, RULE_INITIALIZER) : 
			parseAssignExpression();
	}
	
	public NodeResult<InitializerArray> parseArrayInitializer() {
		ParseArrayInitEntry listParse = new ParseArrayInitEntry();
		listParse.parseList(DeeTokens.OPEN_BRACKET, DeeTokens.COMMA, DeeTokens.CLOSE_BRACKET);
		if(listParse.members == null)
			return nullResult();
		
		return listParse.resultConclude(new InitializerArray(listParse.members));
	}
	
	public class ParseArrayInitEntry extends ElementListParseHelper<ArrayInitEntry> {
		@Override
		protected ArrayInitEntry parseElement(boolean createMissing) {
			Expression index = null;
			IInitializer initializer = null;
			
			if(lookAhead() == DeeTokens.COLON) {
				index = parseAssignExpression_toMissing();
				consumeLookAhead(DeeTokens.COLON);
				initializer = parseNonVoidInitializer(true).node;
			} else {
				initializer = parseNonVoidInitializer(createMissing).node;
				
				if(initializer == null)
					return null;
				
				if(lookAhead() == DeeTokens.COLON && initializerCanParseAsExp(initializer)) {
					if(initializer instanceof InitializerArray) {
						index = (InitializerArray) initializer;
					} else {
						index = (Expression) initializer;
					}
					consumeLookAhead(DeeTokens.COLON);
					initializer = parseNonVoidInitializer(true).node;
				}
			}
			
			ASTNode startNode = index != null ? index : initializer.asNode();
			return concludeNode(srToPosition(startNode, new ArrayInitEntry(index, initializer)));
		}
		
	}
	
	public static boolean initializerCanParseAsExp(IInitializer initializer) {
		return initializer instanceof Expression || initializer instanceof InitializerArray;
	}
	
	public static final ParseRuleDescription RULE_STRUCT_INITIALIZER = 
		new ParseRuleDescription("StructInit", "StructInitializer");
	
	public NodeResult<InitializerStruct> parseStructInitializer() {
		ParseStructInitEntry listParse = new ParseStructInitEntry();
		listParse.parseList(DeeTokens.OPEN_BRACE, DeeTokens.COMMA, DeeTokens.CLOSE_BRACE);
		if(listParse.members == null)
			return nullResult();
		
		return listParse.resultConclude(new InitializerStruct(listParse.members));
	}
	
	public class ParseStructInitEntry extends ElementListParseHelper<StructInitEntry> {
		@Override
		protected StructInitEntry parseElement(boolean createMissing) {
			RefIdentifier member = null;
			if(lookAhead() == DeeTokens.COLON || 
				(lookAhead() == DeeTokens.IDENTIFIER && lookAhead(1) == DeeTokens.COLON)) {
				member = parseRefIdentifier();
				consumeLookAhead(DeeTokens.COLON);
			}
			IInitializer init = parseNonVoidInitializer(createMissing || member != null).node;
			if(init == null)
				return null;
			
			ASTNode startNode = member != null ? member : init.asNode();
			return concludeNode(srToPosition(startNode, new StructInitEntry(member, init)));
		}
	}
	
	protected NodeResult<DefinitionConstructor> parseDefinitionConstructor_atThis(DefParseHelper parse) {
		consumeLookAhead(DeeTokens.KW_THIS);
		
		ProtoDefSymbol defId = defSymbol(lastLexElement()); // TODO: mark this as special DefSymbol
		return parse_FunctionLike(parse, true, null, defId).upcastTypeParam();
	}
	
	/**
	 * Parse a function from this point:
	 * http://dlang.org/declaration.html#DeclaratorSuffix
	 */
	protected NodeResult<DefinitionFunction> parseDefinitionFunction_afterIdentifier(DefParseHelper parse, 
		Reference retType, ProtoDefSymbol defId) 
	{
		assertTrue(defId.isMissing() == false);
		return parse_FunctionLike(parse, false, retType, defId).upcastTypeParam();
	}
	
	protected NodeResult<? extends AbstractFunctionDefinition> parse_FunctionLike(DefParseHelper parse,
		boolean isConstrutor, Reference retType, ProtoDefSymbol defId) {
		
		ArrayView<IFunctionParameter> fnParams = null;
		ArrayView<ITemplateParameter> tplParams = null;
		ArrayView<FunctionAttributes> fnAttributes = null;
		Expression tplConstraint = null;
		IFunctionBody fnBody = null;
		
		parsing: {
			DeeParser_RuleParameters firstParams = parseParameters(parse);
			
			if(firstParams.mode == TplOrFnMode.FN) {
				fnParams = firstParams.getAsFunctionParameters();
			} else if(firstParams.mode == TplOrFnMode.TPL) {
				tplParams = firstParams.getAsTemplateParameters();
			}
			if(parse.ruleBroken) {
				if(firstParams.isAmbiguous()) {
					fnParams = firstParams.toFunctionParameters();
				}
				break parsing;
			}
			
			if(firstParams.isAmbiguous() && lookAhead() == DeeTokens.OPEN_PARENS) {
				tplParams = firstParams.toTemplateParameters();
			}
			
			if(tplParams != null) {
				fnParams = parseFunctionParameters(parse);
				if(parse.ruleBroken) break parsing;
			} else if(firstParams.isAmbiguous()) {
				fnParams = firstParams.toFunctionParameters();
			}
			
			// Function attributes
			fnAttributes = parseFunctionAttributes();
			
			if(tplParams != null) {
				tplConstraint = parseTemplateConstraint(parse);
				if(parse.ruleBroken) break parsing;
			}
			
			fnBody = parse.parseRequiredRule(parseFunctionBody(), RULE_FN_BODY);
		}
		
		Token[] comments = parse.parseEndDDocComments();
		
		if(isConstrutor) {
			return parse.resultConclude(new DefinitionConstructor(
				comments, defId, tplParams, fnParams, fnAttributes, tplConstraint, fnBody));
		}
		
		return parse.resultConclude(new DefinitionFunction(
			comments, retType, defId, tplParams, fnParams, fnAttributes, tplConstraint, fnBody));
	}
	
	protected final DeeParser_RuleParameters parseParameters(ParseHelper parse) {
		return new DeeParser_RuleParameters(TplOrFnMode.AMBIG).parse(parse, false);
	}
	
	protected final ArrayView<IFunctionParameter> parseFunctionParameters(ParseHelper parse) {
		return parseFunctionParameters(parse, false);
	}
	protected ArrayView<IFunctionParameter> parseFunctionParameters(ParseHelper parse, boolean isOptional) {
		DeeParser_RuleParameters fnParametersParse = new DeeParser_RuleParameters(TplOrFnMode.FN);
		return fnParametersParse.parse(parse, isOptional).getAsFunctionParameters();
	}
	
	protected final ArrayView<ITemplateParameter> parseTemplateParameters(ParseHelper parse, boolean isOptional) {
		DeeParser_RuleParameters tplParametersParse = new DeeParser_RuleParameters(TplOrFnMode.TPL);
		return tplParametersParse.parse(parse, isOptional).getAsTemplateParameters();
	}
	
	protected final ArrayView<ITemplateParameter> parseTemplateParametersList() {
		DeeParser_RuleParameters tplParametersParse = new DeeParser_RuleParameters(TplOrFnMode.TPL);
		tplParametersParse.parseParameterList(false);
		return tplParametersParse.getAsTemplateParameters();
	}
	
	public IFunctionParameter parseFunctionParameter() {
		return (IFunctionParameter) new DeeParser_RuleParameters(TplOrFnMode.FN).parseParameter();
	}
	
	public ITemplateParameter parseTemplateParameter() {
		return (ITemplateParameter) new DeeParser_RuleParameters(TplOrFnMode.TPL).parseParameter();
	}
	
	protected ArrayView<FunctionAttributes> parseFunctionAttributes() {
		ArrayList<FunctionAttributes> attributes = null;
		
		while(true) {
			FunctionAttributes attrib = FunctionAttributes.fromToken(lookAhead());
			if(attrib != null) {
				consumeLookAhead();
			} else {
				if(lookAhead() == DeeTokens.AT && lookAhead(1) == DeeTokens.IDENTIFIER) {
					attrib = FunctionAttributes.fromCustomAttribId(lookAheadElement(1).source);
					if(attrib != null) {
						consumeLookAhead();
						consumeLookAhead();
					}
				}
				if(attrib == null)
					break;
			}
			attributes = NewUtils.lazyInitArrayList(attributes);
			attributes.add(attrib);
		}
		return arrayViewG(attributes);
	}
	
	public Expression parseTemplateConstraint(ParseHelper parse) {
		if(!tryConsume(DeeTokens.KW_IF)) {
			return null;
		}
		return parseExpressionAroundParentheses(parse, true, false);
	}
	
	public static final ParseRuleDescription RULE_FN_BODY = new ParseRuleDescription("FnBody", "FunctionBody");
	
	protected NodeResult<? extends IFunctionBody> parseFunctionBody() {
		if(tryConsume(DeeTokens.SEMICOLON)) { 
			return resultConclude(false, srOf(lastLexElement(), new EmptyStatement()));
		}
		NodeResult<BlockStatement> blockResult = thisParser().parseBlockStatement(false, false);
		if(blockResult.node != null)
			return blockResult;
		
		ParseHelper parse = new ParseHelper(-1);
		if(lookAhead() == DeeTokens.KW_IN || lookAhead() == DeeTokens.KW_OUT || lookAhead() == DeeTokens.KW_BODY) {
			parse.setStartPosition(lookAheadElement().getStartPos());
		} else {
			parse.setStartPosition(getSourcePosition()); // It will be missing element
		}
		
		boolean isOutIn = false;
		BlockStatement inBlock = null;
		FunctionBodyOutBlock outBlock = null;
		BlockStatement bodyBlock = null;
		
		parsing: {
			if(tryConsume(DeeTokens.KW_IN)) {
				inBlock = parse.checkResult(parseBlockStatement_toMissing(false));
				if(parse.ruleBroken) break parsing;
				
				if(lookAhead() == DeeTokens.KW_OUT) {
					outBlock = parse.checkResult(parseOutBlock());
					if(parse.ruleBroken) break parsing;
				}
			} else if(lookAhead() == DeeTokens.KW_OUT) {
				isOutIn = true;
				
				outBlock = parse.checkResult(parseOutBlock());
				if(parse.ruleBroken) break parsing;
				
				if(tryConsume(DeeTokens.KW_IN)) {
					inBlock = parse.checkResult(parseBlockStatement_toMissing(false));
					if(parse.ruleBroken) break parsing;
				}
			}
			
			if(tryConsume(DeeTokens.KW_BODY)) {
				bodyBlock = parse.checkResult(parseBlockStatement_toMissing(false));
			} else {
				if(inBlock == null && outBlock == null) {
					return AbstractParser.<FunctionBody>nullResult();
				}
			}
		}
		
		if(inBlock == null && outBlock == null) {
			return parse.resultConclude(new FunctionBody(bodyBlock));
		}
		return parse.resultConclude(new InOutFunctionBody(isOutIn, inBlock, outBlock, bodyBlock));
	}
	
	public NodeResult<BlockStatement> parseBlockStatement_toMissing(boolean brokenIfMissing) {
		return thisParser().parseBlockStatement(true, brokenIfMissing);
	}
	
	protected NodeResult<FunctionBodyOutBlock> parseOutBlock() {
		if(!tryConsume(DeeTokens.KW_OUT))
			return nullResult();
		ParseHelper parse = new ParseHelper();
		
		Symbol id = null;
		BlockStatement block = null;
		
		parsing: {
			if(parse.consumeOptional(DeeTokens.OPEN_PARENS)) {
				id = parseIdSymbol();
				if(parse.consumeRequired(DeeTokens.CLOSE_PARENS).ruleBroken) break parsing;
			}
			
			block = parse.checkResult(parseBlockStatement_toMissing(false));
		}
		
		return parse.resultConclude(new FunctionBodyOutBlock(id, block));
	}
	
	public NodeResult<DefinitionTemplate> parseTemplateDefinition(DefinitionStartInfo defStartInfo) {
		AggregateDefinitionParse adp = new AggregateDefinitionParse(defStartInfo);
		
		boolean isMixin = false;
		if(tryConsume(DeeTokens.KW_MIXIN, DeeTokens.KW_TEMPLATE)) {
			isMixin = true;
		} else if(!tryConsume(DeeTokens.KW_TEMPLATE)) {
			return null;
		}
		adp.parseAggregate(false);
		adp.parseDeclarationBlockBody();
		
		Token[] comments = adp.parseEndDDocComments();
		
		return adp.resultConclude(new DefinitionTemplate(
			comments, isMixin, adp.defId, adp.tplParams, adp.tplConstraint, adp.getDeclBlock()));
	}
	
	public static final ParseRuleDescription RULE_AGGR_BODY = 
		new ParseRuleDescription("AggregateBody", "AggregateBody");
	
	public class AggregateDefinitionParse extends DefParseHelper {

		protected ProtoDefSymbol defId = null;
		protected ArrayView<ITemplateParameter> tplParams = null;
		protected Expression tplConstraint = null;
		protected IAggregateBody declBody = null;
		
		public AggregateDefinitionParse(DefinitionStartInfo defStartInfo) {
			super(defStartInfo == null ? parseDefStartInfo() : defStartInfo);
		}
		
		public void parseAggregate(boolean tplParamsIsOptional) {
			ParseHelper parse = this;
			
			parsing: {
				defId = parse.checkResult(parseDefId());
				if(parse.ruleBroken) break parsing;
				
				tplParams = parseTemplateParameters(parse, tplParamsIsOptional);
				if(parse.ruleBroken) break parsing;
				
				if(tplParams != null) {
					tplConstraint = parseTemplateConstraint(parse);
				}
			}
		}
		
		public final DeclBlock getDeclBlock() {
			return (DeclBlock) declBody;
		}
		
		public void parseDeclarationBlockBody() {
			ParseHelper parse = this;
			if(parse.ruleBroken == false) {
				declBody = parse.parseRequiredRule(parseDeclarationBlock(), RULE_DECLARATION_BLOCK);
			}
		}
		
		public void parseAggregateBody() {
			ParseHelper parse = this;
			if(!parse.ruleBroken) {
				if(tryConsume(DeeTokens.SEMICOLON)) {
					declBody = concludeNode(srOf(lastLexElement(), new DeclarationEmpty()));
				} else {
					declBody = parse.parseRequiredRule(parseDeclarationBlock(), RULE_AGGR_BODY);
				}
			}
		}
		
	}
	
	public static final ParseRuleDescription RULE_DECLARATION_BLOCK = 
		new ParseRuleDescription("DeclarationBlock", "DeclarationBlock");
	
	public NodeResult<DeclBlock> parseDeclarationBlock() {
		if(tryConsume(DeeTokens.OPEN_BRACE) == false) {
			return nullResult();
		}
		ParseHelper parse = new ParseHelper();
		
		ArrayView<ASTNode> declDefs = parseDeclarations(DeeTokens.CLOSE_BRACE, false);
		parse.consumeRequired(DeeTokens.CLOSE_BRACE);
		return parse.resultConclude(new DeclBlock(declDefs));
	}
	
	public final NodeResult<RefTypeFunction> parseRefTypeFunction_afterReturnType(Reference retType) {
		boolean isDelegate = lastLexElement().type == DeeTokens.KW_DELEGATE;
		
		ParseHelper parse = new ParseHelper(retType);
		ArrayView<IFunctionParameter> fnParams = null;
		ArrayView<FunctionAttributes> fnAttributes = null;
		
		parsing: {
			fnParams = parseFunctionParameters(parse);
			if(parse.ruleBroken) break parsing;
			
			fnAttributes = parseFunctionAttributes();
		}
		
		return parse.resultConclude(new RefTypeFunction(retType, isDelegate, fnParams, fnAttributes));
	}
	
	public NodeResult<? extends IDeclaration> parseDefinitionAlias(DefinitionStartInfo defStartInfo) {
		DefParseHelper parse = createDefParseHelper(defStartInfo);
		if(!tryConsume(DeeTokens.KW_ALIAS))
			return null;
		
		if(lookAhead() == DeeTokens.IDENTIFIER && lookAhead(1) == DeeTokens.ASSIGN) {
			return parseDefinitionAlias_atFragmentStart(parse);
		}
		
		// Note that there are heavy similarites between this code and var/function declaration parsing
		ArrayView<Attribute> attributes = null;
		Reference ref = null;
		ProtoDefSymbol defId = null;
		Reference cstyleSuffix = null;
		ArrayList<AliasVarDeclFragment> fragments = null;
		
		parsing: {
			ParserState savedParserState = saveParserState();
			
			attributes = parseDefinitionAttributes(parse);
			if(parse.checkRuleBroken()) break parsing;
			
			NodeResult<Reference> refResult = parseTypeReference();
			ref = refResult.node;
			if(refResult.ruleBroken) break parsing;
			if(ref == null) {
				if(attributes == null) {
					// Return error as if DefinitionAlias was parsed
					return parseDefinitionAlias_atFragmentStart(parse);
				} else {
					ref = parseMissingTypeReference(true);
					break parsing;
				}
			}
			
			if(lookAhead() != DeeTokens.IDENTIFIER && couldHaveBeenParsedAsId(ref)) {
				restoreOriginalState(savedParserState);
				// Return error as if trying to parse DefinitionAlias
				return parseDefinitionAlias_atFragmentStart(parse);
			} else {
				defId = parseDefId();
			}
			
			if(lookAhead() == DeeTokens.OPEN_PARENS) {
				return parseDefinitionAliasFunctionDecl(parse, attributes, ref, defId);
			}
			
			cstyleSuffix = parseCStyleSuffix(parse);
			if(parse.checkRuleBroken()) break parsing;
			
			while(parse.consumeOptional(DeeTokens.COMMA)) {
				fragments = lazyInitArrayList(fragments);
				fragments.add(parseAliasVarDeclFragment());
			}
		}
		defId = nullIdToParseMissingDefId(defId);
		
		parse.clearRuleBroken().consumeRequired(DeeTokens.SEMICOLON);
		Token[] comments = parse.parseEndDDocComments();
		return parse.resultConclude(
			new DefinitionAliasVarDecl(comments, attributes, ref, defId, cstyleSuffix, arrayView(fragments)));
	}
	
	public NodeResult<? extends IDeclaration> parseDefinitionAliasFunctionDecl(DefParseHelper parse, 
		ArrayView<Attribute> attributes, Reference ref, ProtoDefSymbol defId) {
		
		ArrayView<IFunctionParameter> fnParams = parseFunctionParameters(parse, true);
		ArrayView<FunctionAttributes> fnAttributes = null;
		if(!parse.ruleBroken) {
			fnAttributes = parseFunctionAttributes();
		} else {
			parse.clearRuleBroken();
		}
		parse.consumeRequired(DeeTokens.SEMICOLON);
		Token[] comments = parse.parseEndDDocComments();
		return parse.resultConclude(
			new DefinitionAliasFunctionDecl(comments, attributes, ref, defId, fnParams, fnAttributes));
	}
	
	protected AliasVarDeclFragment parseAliasVarDeclFragment() {
		ProtoDefSymbol fragId = parseDefId();
		return conclude(fragId.nameSourceRange, new AliasVarDeclFragment(fragId));
	}
	
	protected NodeResult<DefinitionAlias> parseDefinitionAlias_atFragmentStart(DefParseHelper parse) {
		ArrayList<DefinitionAliasFragment> fragments = new ArrayList<>();
		
		while(true) {
			DefinitionAliasFragment fragment = parseAliasFragment();
			fragments.add(fragment);
			
			if(!tryConsume(DeeTokens.COMMA)) {
				break;
			}
		}
		
		parse.consumeRequired(DeeTokens.SEMICOLON);
		Token[] comments = parse.parseEndDDocComments();
		return parse.resultConclude(new DefinitionAlias(comments, arrayView(fragments)));
	}
	
	public DefinitionAliasFragment parseAliasFragment() {
		ProtoDefSymbol defId = parseDefId();
		ArrayView<ITemplateParameter> tplParams = null;
		Reference ref = null;
		
		ParseHelper parse = new ParseHelper(defId.nameSourceRange.getStartPos());
		
		parsing: {
			parse.checkResult(defId);
			if(parse.ruleBroken) break parsing;
			
			tplParams = parseTemplateParameters(parse, true);
			if(parse.ruleBroken) break parsing;
			
			if(parse.consumeRequired(DeeTokens.ASSIGN).ruleBroken) break parsing;
			
			NodeResult<Reference> refResult = parseTypeReference_ToMissing();
			ref = refResult.node;
		}
		return parse.conclude(new DefinitionAliasFragment(defId, tplParams, ref));
	}
	
	protected NodeResult<DeclarationAliasThis> parseDeclarationAliasThis() {
		if(!tryConsume(DeeTokens.KW_ALIAS))
			return null;
		ParseHelper parse = new ParseHelper();
		
		boolean isAssignSyntax = false;
		RefIdentifier refId = null;
		
		parsing:
		if(tryConsume(DeeTokens.KW_THIS)) {
			isAssignSyntax = true;
			
			if(parse.consumeExpected(DeeTokens.ASSIGN) == false) break parsing;
			
			refId = parseRefIdentifier();
		} else {
			refId = parseRefIdentifier();
			parse.consumeExpected(DeeTokens.KW_THIS);
		}
		
		parse.consumeRequired(DeeTokens.SEMICOLON);
		return parse.resultConclude(new DeclarationAliasThis(isAssignSyntax, refId));
	}
	
	protected NodeResult<? extends IDeclaration> parseDefinitionEnum_start(DefinitionStartInfo defStartInfo) {
		DefParseHelper parse = createDefParseHelper(defStartInfo);
		consumeLookAhead(DeeTokens.KW_ENUM);
		
		if(lookAhead() == DeeTokens.IDENTIFIER
				&& (lookAhead(1) == DeeTokens.OPEN_PARENS || lookAhead(1) == DeeTokens.ASSIGN)) {
			return parseDefinitionEnumVar_afterId(parse);
		}
		
		ProtoDefSymbol defId = parseDefId();
		Reference type = null;
		EnumBody body = null;
		
		parsing : {
			if(tryConsume(DeeTokens.COLON)) {
				type = parse.checkResult(parseTypeReference_ToMissing());
				if(parse.ruleBroken) break parsing;
			}
			if(tryConsume(DeeTokens.SEMICOLON)) {
				body = concludeNode(srOf(lastLexElement(), new DefinitionEnum.NoEnumBody()));
			} else {
				body = parse.parseRequiredRule(parseEnumBody(), RULE_ENUM_BODY);
			}
		}
		Token[] comments = parse.parseEndDDocComments();
		return parse.resultConclude(new DefinitionEnum(comments, defId, type, body));
	}
	
	protected NodeResult<DefinitionEnumVar> parseDefinitionEnumVar_afterId(DefParseHelper parse) {
		ArrayList<DefinitionEnumVarFragment> fragments = new ArrayList<>();
		
		while(true) {
			DefinitionEnumVarFragment fragment = parseEnumVarFragment();
			fragments.add(fragment);
			
			if(!tryConsume(DeeTokens.COMMA)) {
				break;
			}
		}
		
		parse.consumeRequired(DeeTokens.SEMICOLON);
		return parse.resultConclude(new DefinitionEnumVar(arrayView(fragments)));
	}

	public DefinitionEnumVarFragment parseEnumVarFragment() {
		ProtoDefSymbol defId = parseDefId();
		ArrayView<ITemplateParameter> tplParams = null;
		IInitializer initializer = null;
		
		ParseHelper parse = new ParseHelper(defId.nameSourceRange.getStartPos());
		
		parsing: {
			parse.checkResult(defId);
			if(parse.ruleBroken) break parsing;
			
			tplParams = parseTemplateParameters(parse, true);
			if(parse.ruleBroken) break parsing;
			
			if(parse.consumeRequired(DeeTokens.ASSIGN).ruleBroken) break parsing;
			
			initializer = parseInitializer().node;
		}
		return parse.conclude(new DefinitionEnumVarFragment(defId, tplParams, initializer));
	}
	
	protected NodeResult<DeclarationEnum> parseDeclarationEnum_start() {
		consumeLookAhead(DeeTokens.KW_ENUM);
		ParseHelper parse = new ParseHelper();
		
		Reference type = null;
		EnumBody body = null;
		
		parsing : {
			if(tryConsume(DeeTokens.COLON)) {
				type = parse.checkResult(parseTypeReference_ToMissing());
				if(parse.ruleBroken) break parsing;
			}
			body = parse.parseRequiredRule(parseEnumBody(), RULE_ENUM_BODY);
		}
		return parse.resultConclude(new DeclarationEnum(type, body));
	}
	
	public static final ParseRuleDescription RULE_ENUM_BODY = new ParseRuleDescription("EnumBody", "EnumBody");
	
	public NodeResult<EnumBody> parseEnumBody() {
		ParseEnumMember parse = new ParseEnumMember();
		parse.parseList(DeeTokens.OPEN_BRACE, DeeTokens.COMMA, DeeTokens.CLOSE_BRACE);
		if(parse.members == null) {
			return nullResult();
		}
		
		return parse.resultConclude(new EnumBody(parse.members));
	}
	
	public class ParseEnumMember extends ElementListParseHelper<EnumMember> {

		@Override
		protected EnumMember parseElement(boolean createMissing) {
			ParseHelper parse = new ParseHelper(-1);
			
			TypeId_or_Id_RuleFragment typeRef_defId = new TypeId_or_Id_RuleFragment();
			Expression value = null;
			
			typeRef_defId.parseRuleFragment(parse, createMissing);
			if(typeRef_defId.defId == null)
				return null;
			
			if(tryConsume(DeeTokens.ASSIGN)) {
				value = parseAssignExpression_toMissing();
			}
			
			return parse.conclude(new EnumMember(typeRef_defId.type, typeRef_defId.defId, value));
		}
		
	}
	
	public NodeResult<DefinitionStruct> parseDefinitionStruct(DefinitionStartInfo defStartInfo) {
		return parseDefinition_StructOrUnion(defStartInfo).upcastTypeParam();
	}
	public NodeResult<DefinitionUnion> parseDefinitionUnion(DefinitionStartInfo defStartInfo) {
		return parseDefinition_StructOrUnion(defStartInfo).upcastTypeParam();
	}
	
	protected NodeResult<? extends DefinitionAggregate> parseDefinition_StructOrUnion(
		DefinitionStartInfo defStartInfo) {
		AggregateDefinitionParse adp = new AggregateDefinitionParse(defStartInfo);
		if(!(tryConsume(DeeTokens.KW_STRUCT) || tryConsume(DeeTokens.KW_UNION)))
			return null;
		
		boolean isStruct = lastLexElement().type == DeeTokens.KW_STRUCT;
		if(lookAhead() != DeeTokens.IDENTIFIER) {
			adp.defId = nullIdToParseMissingDefId(null);
			adp.parseDeclarationBlockBody();
		} else {
			adp.parseAggregate(true);
			adp.parseAggregateBody();
		}
		
		Token[] comments = adp.parseEndDDocComments();
		
		return adp.resultConclude(isStruct ?
			new DefinitionStruct(comments, adp.defId, adp.tplParams, adp.tplConstraint, adp.declBody) :
			new DefinitionUnion (comments, adp.defId, adp.tplParams, adp.tplConstraint, adp.declBody));
	}
	
	public NodeResult<DefinitionClass> parseDefinitionClass(DefinitionStartInfo defStartInfo) {
		return parseDefinition_ClassOrInterface(defStartInfo).upcastTypeParam();
	}
	public NodeResult<DefinitionInterface> parseDefinitionInterface(DefinitionStartInfo defStartInfo) {
		return parseDefinition_ClassOrInterface(defStartInfo).upcastTypeParam();
	}
	
	protected NodeResult<? extends DefinitionClass> parseDefinition_ClassOrInterface(
		DefinitionStartInfo defStartInfo) {
		
		AggregateDefinitionParse adp = new AggregateDefinitionParse(defStartInfo);
		if(!(tryConsume(DeeTokens.KW_CLASS) || tryConsume(DeeTokens.KW_INTERFACE)))
			return null;
		
		boolean isClass = lastLexElement().type == DeeTokens.KW_CLASS;
		boolean baseAfter = true;
		
		SimpleListParseHelper<Reference> baseClasses = new TypeReferenceSimpleListParse();
		parsing: {
			adp.parseAggregate(true);
			if(adp.ruleBroken) break parsing;
			
			if(tryConsume(DeeTokens.COLON)) {
				baseClasses.parseSimpleList(DeeTokens.COMMA, false, false);
			}
			if(adp.tplParams != null && adp.tplConstraint == null) {
				adp.tplConstraint = parseTemplateConstraint(adp);
				baseAfter = false;
			}
			
			adp.parseAggregateBody();
		}
		
		Token[] comments = adp.parseEndDDocComments();
		
		return adp.resultConclude(isClass ?
			new DefinitionClass(
				comments, adp.defId, adp.tplParams, adp.tplConstraint, baseClasses.members, baseAfter, adp.declBody) :
			new DefinitionInterface(
				comments, adp.defId, adp.tplParams, adp.tplConstraint, baseClasses.members, baseAfter, adp.declBody)
		);
	}
	
	public class TypeReferenceListParse extends ElementListParseHelper<Reference> {
		@Override
		protected Reference parseElement(boolean createMissing) {
			return parseTypeReference(createMissing, true).node;
		}
	}
	
	public class TypeReferenceSimpleListParse extends SimpleListParseHelper<Reference> {
		@Override
		protected Reference parseElement(boolean createMissing) {
			return parseTypeReference(createMissing, true).node;
		}
	}
	
	public NodeResult<? extends IDeclaration> parseDeclarationMixin(DefinitionStartInfo defStartInfo) {
		DefParseHelper parse = createDefParseHelper(defStartInfo);
		if(!tryConsume(DeeTokens.KW_MIXIN))
			return null;
		
		NodeResult<Reference> tplInstanceResult = parseTypeReference_ToMissing();
		Reference tplInstance = tplInstanceResult.node;
		
		if(!tplInstanceResult.ruleBroken && lookAhead() == DeeTokens.IDENTIFIER) {
			ProtoDefSymbol defId = parseDefId();
			parse.consumeRequired(DeeTokens.SEMICOLON);
			Token[] comments = parse.parseEndDDocComments();
			return parse.resultConclude(new DefinitionMixinInstance(comments, defId, tplInstance));
		} else {
			parse.consumeRequired(DeeTokens.SEMICOLON);
			parse.discardDocComments();
			return parse.resultConclude(new DeclarationMixin(tplInstance));
		}
	}
	
	/* -------------------- Function-like declarations -------------------- */

	
	public NodeResult<DeclarationInvariant> parseDeclarationInvariant_start() {
		consumeLookAhead(DeeTokens.KW_INVARIANT);
		ParseHelper parse = new ParseHelper();
		
		BlockStatement body = null;
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			if(parse.consumeRequired(DeeTokens.CLOSE_PARENS).ruleBroken) break parsing;
			body = parse.checkResult(parseBlockStatement_toMissing(false));
		}
		
		return parse.resultConclude(new DeclarationInvariant(body));
	}
	
	public NodeResult<DeclarationUnitTest> parseDeclarationUnitTest_start() {
		consumeLookAhead(DeeTokens.KW_UNITTEST);
		ParseHelper parse = new ParseHelper();
		
		BlockStatement body = parse.checkResult(parseBlockStatement_toMissing(false));
		
		return parse.resultConclude(new DeclarationUnitTest(body));
	}
	
	public NodeResult<DeclarationSpecialFunction> parseDeclarationPostBlit_start() {
		consumeLookAhead(DeeTokens.KW_THIS);
		ParseHelper parse = new ParseHelper();
		return parseDeclarationSpecialFunction_AtParams(parse, SpecialFunctionKind.POST_BLIT);
	}
	
	public NodeResult<DeclarationSpecialFunction> parseDeclarationSpecialFunction() {
		ParseHelper parse = new ParseHelper(lookAheadElement());
		if(!tryConsume(DeeTokens.CONCAT, DeeTokens.KW_THIS)) {
			return null;
		}
		return parseDeclarationSpecialFunction_AtParams(parse, SpecialFunctionKind.DESTRUCTOR);
	}
	
	public NodeResult<DeclarationSpecialFunction> parseDeclarationSpecialFunction_AtParams(ParseHelper parse, 
		SpecialFunctionKind kind) {
		IFunctionBody fnBody = null;
		ArrayView<FunctionAttributes> fnAttributes = null;
		parsing: {
			if(parse.consumeRequired(DeeTokens.OPEN_PARENS).ruleBroken) break parsing;
			if(kind == SpecialFunctionKind.POST_BLIT) {
				parse.consumeExpected(DeeTokens.KW_THIS);
			}
			if(parse.consumeRequired(DeeTokens.CLOSE_PARENS).ruleBroken) break parsing;
			
			fnAttributes = parseFunctionAttributes();
			fnBody = parse.parseRequiredRule(parseFunctionBody(), RULE_FN_BODY);
		}
		
		return parse.resultConclude(new DeclarationSpecialFunction(kind, fnAttributes, fnBody));
	}
	
	public NodeResult<DeclarationAllocatorFunction> parseDeclarationAllocatorFunctions() {
		if((tryConsume(DeeTokens.KW_NEW) || tryConsume(DeeTokens.KW_DELETE)) == false)
			return null;
		ParseHelper parse = new ParseHelper();
		
		boolean isNew = lastLexElement().type == DeeTokens.KW_NEW;
		ArrayView<IFunctionParameter> params = null;
		IFunctionBody fnBody = null;
		
		parsing: {
			params = parseFunctionParameters(parse);
			if(parse.ruleBroken) break parsing;
			
			fnBody = parse.parseRequiredRule(parseFunctionBody(), RULE_FN_BODY);
		}
		
		return parse.resultConclude(new DeclarationAllocatorFunction(isNew, params, fnBody));
	}
	
}