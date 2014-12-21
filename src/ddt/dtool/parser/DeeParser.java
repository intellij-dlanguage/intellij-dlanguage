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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ddt.melnorme.lang.tooling.ast.ASTVisitor;
import ddt.melnorme.lang.tooling.ast.ParserError;
import ddt.melnorme.lang.tooling.ast.ParserError.ErrorSourceRangeComparator;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.dtool.ast.definitions.Module;
import ddt.dtool.engine.modules.ModuleNamingRules;
import ddt.dtool.parser.DeeParserResult.ParsedModule;
import ddt.dtool.parser.common.LexElementProducer;
import ddt.dtool.parser.common.LexElementSource;
import ddt.dtool.parser.common.Token;

/**
 * Concrete D Parser class
 */
public class DeeParser 
//It's not very elegant, but inheritance is used here just for the purpose of namespace importing:
	extends DeeParser_Statements 
{
	
	protected ArrayList<ParserError> lexerErrors = new ArrayList<>();
	
	public DeeParser(String source) {
		this(new DeeLexer(source));
	}
	
	protected DeeParser(DeeLexer deeLexer) {
		this.source = deeLexer.getSource();
		DeeLexElementProducer deeLexElementProducer = new DeeLexElementProducer();
		this.lexSource = new LexElementSource(deeLexElementProducer.produceLexTokens(deeLexer));
		this.lexerErrors = deeLexElementProducer.lexerErrors;
	}
	
	public static final class DeeLexElementProducer extends LexElementProducer {
		protected ArrayList<ParserError> lexerErrors = new ArrayList<>();
		
		@Override
		protected void tokenParsed(Token token) {
			DeeTokenSemantics.checkTokenErrors(token, lexerErrors);
		}
	}
	
	@Override
	protected final DeeParser thisParser() {
		return this;
	}
	
	public static ParsedModule parseSource(String source, String defaultModuleName) {
		return parseSource(source, defaultModuleName, null);
	}
	
	public static ParsedModule parseSource(String source, String defaultModuleName, Path modulePath) {
		return new DeeParser(source).parseModuleSource(defaultModuleName, modulePath);
	}
	
	public static ParsedModule parseSource(String source, Path modulePath) {
		return new DeeParser(source).parseModuleSource(modulePath);
	}
	
	public ParsedModule parseModuleSource(Path modulePath) {
		String fileName = modulePath.getFileName().toString();
		String defaultModuleName = ModuleNamingRules.getDefaultModuleNameFromFileName(fileName);
		return parseModuleSource(defaultModuleName, modulePath);
	}
	
	public ParsedModule parseModuleSource(String defaultModuleName, Path modulePath) {
		NodeResult<Module> nodeResult = parseModule(defaultModuleName, modulePath);
		return (ParsedModule) prepParseResult(null, nodeResult, modulePath);
	}
	
	public DeeParserResult parseUsingRule(ParseRuleDescription parseRule) {
		NodeResult<? extends ASTNode> nodeResult;
		assertNotNull(parseRule);
		
		if(parseRule == DeeParser.RULE_EXPRESSION) {
			nodeResult = parseExpression();
		} else if(parseRule == DeeParser.RULE_REFERENCE) {
			nodeResult = parseTypeReference();
		} else if(parseRule == DeeParser.RULE_DECLARATION) {
			nodeResult = parseDeclaration();
		} else if(parseRule == RULE_TYPE_OR_EXP) {
			nodeResult = parseTypeOrExpression(true);
		} else if(parseRule == DeeParser.RULE_INITIALIZER) {
			nodeResult = parseInitializer();
		} else if(parseRule == DeeParser.RULE_STATEMENT) {
			nodeResult = parseStatement();
		} else if(parseRule == DeeParser.RULE_STRUCT_INITIALIZER) {
			nodeResult = parseStructInitializer();
		} else {
			throw assertFail();
		}
		return prepParseResult(parseRule, nodeResult, null);
	}
	
	protected DeeParserResult prepParseResult(ParseRuleDescription parseRule, NodeResult<?> nodeResult, 
			Path modulePath) {
		assertTrue(enabled);
		ASTNode node = nodeResult.node;
		if(node != null) {
			ASTNode.doSimpleAnalysisOnTree(node);
		}
		
		List<ParserError> errors = initErrors(lexerErrors, node);
		if(parseRule == null) {
			Module module = (Module) node;
			return new ParsedModule(getSource(), lexSource.lexElementList, module, nodeResult.ruleBroken, errors, 
				modulePath);
		} else {
			return new DeeParserResult(getSource(), lexSource.lexElementList, node, nodeResult.ruleBroken, errors);
		}
	}
	
	public static List<ParserError> initErrors(ArrayList<ParserError> lexerErrors, ASTNode resultNode) {
		return lexerErrors == null ? null : collectErrors(lexerErrors, resultNode);
	}
	
	// TODO: this could be optimized
	protected static ArrayList<ParserError> collectErrors(final ArrayList<ParserError> errors, ASTNode node) {
		if(node != null) {
			node.accept(new ASTVisitor() {
				@Override
				public void postVisit(ASTNode node) {
					for (ParserError parserError : node.getData().getNodeErrors()) {
						errors.add(parserError);
					}
				}
			});
		}
		Collections.sort(errors, new ErrorSourceRangeComparator());
		return errors;
	}
	
	
}