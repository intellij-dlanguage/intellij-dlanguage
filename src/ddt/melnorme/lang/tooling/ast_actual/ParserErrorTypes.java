/*******************************************************************************
 * Copyright (c) 2011, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.ast_actual;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertFail;
import ddt.melnorme.lang.tooling.ast.ParserError;
import ddt.melnorme.utilbox.misc.StringUtil;
import ddt.dtool.ast.declarations.AttribLinkage.Linkage;
import ddt.dtool.ast.statements.StatementScope.ScopeTypes;
import ddt.dtool.parser.DeeTokens;

public enum ParserErrorTypes {
	
	INVALID_TOKEN_CHARACTERS, // Lexer: invalid characters, cannot form token
	MALFORMED_TOKEN, // Lexer: recovered token has errors
	
	EXPECTED_TOKEN, // expected specific token
	EXPECTED_RULE, // expected valid token for rule
	SYNTAX_ERROR, // unexpected rule in rule start
	
	EXP_MUST_HAVE_PARENTHESES, 
	TYPE_USED_AS_EXP_VALUE,
	INIT_USED_IN_EXP,
	NO_CHAINED_TPL_SINGLE_ARG,
	
	INVALID_EXTERN_ID,
	INVALID_SCOPE_ID,
	INVALID_TRAITS_ID,
	LAST_CATCH,;
	
	public String getUserMessage(ParserError parserError) {
		String msgErrorSource = parserError.msgErrorSource;
		Object msgData = parserError.msgData;
		
		switch(this) {
		case INVALID_TOKEN_CHARACTERS:
			return "Invalid token characters \"" + msgErrorSource + "\", delete these characters.";
		case MALFORMED_TOKEN:
			return "Error during tokenization: " + msgErrorSource;
		case EXPECTED_TOKEN:
			DeeTokens expToken = (DeeTokens) msgData;
			return "Syntax error on token \"" + msgErrorSource + "\", expected " + expToken + " after.";
		case EXPECTED_RULE:
			return "Unexpected token after \"" + msgErrorSource + "\", while trying to parse " + msgData + ".";
		case SYNTAX_ERROR:
			return "Unexpected token \"" + msgErrorSource + "\", while trying to parse " + msgData + ".";
		case EXP_MUST_HAVE_PARENTHESES:
			return "Expression " + msgErrorSource + " must be parenthesized when next to operator: " + msgData + ".";
		case TYPE_USED_AS_EXP_VALUE:
			return "The type " + msgErrorSource + " cannot be used as an expression value.";
		case NO_CHAINED_TPL_SINGLE_ARG:
			return "The template '!' single argument " + msgErrorSource + 
				" cannot be used next to other template '!' single arguments.";
		case INIT_USED_IN_EXP:
			return "The initializer " + msgErrorSource + " cannot be used as part of an expression.";
		
		case INVALID_EXTERN_ID:
			return "Invalid linkage specifier \"" + msgErrorSource + "\", valid ones are: " +
				StringUtil.collToString(Linkage.values(), ",") + ".";
		case INVALID_SCOPE_ID:
			return "Invalid scope specifier \"" + msgErrorSource + "\", must be one of: " +
				StringUtil.collToString(ScopeTypes.values(), ",") + ".";
		case INVALID_TRAITS_ID:
			return "Invalid traits id \"" + msgErrorSource + "\".";
		case LAST_CATCH:
			return "Catch without parameter must be last catch (and only one per try statement). ";
			
		}
		throw assertFail();
	}
	
}