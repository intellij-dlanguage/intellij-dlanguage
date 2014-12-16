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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.Hashtable;

public class DeeLexerKeywordHelper {
	
	protected final static Hashtable<String, DeeTokens> keywordTable;
	
	static {
		Hashtable<String, DeeTokens> temp = new Hashtable<String, DeeTokens>();
		
		storeKeyword(temp, DeeTokens.KW_ABSTRACT);
		storeKeyword(temp, DeeTokens.KW_ALIAS);
		storeKeyword(temp, DeeTokens.KW_ALIGN);
		storeKeyword(temp, DeeTokens.KW_ASM);
		storeKeyword(temp, DeeTokens.KW_ASSERT);
		storeKeyword(temp, DeeTokens.KW_AUTO);
		storeKeyword(temp, DeeTokens.KW_BODY);
		storeKeyword(temp, DeeTokens.KW_BOOL);
		storeKeyword(temp, DeeTokens.KW_BREAK);
		storeKeyword(temp, DeeTokens.KW_BYTE);
		storeKeyword(temp, DeeTokens.KW_CASE);
		storeKeyword(temp, DeeTokens.KW_CAST);
		storeKeyword(temp, DeeTokens.KW_CATCH);
		storeKeyword(temp, DeeTokens.KW_CDOUBLE);
		storeKeyword(temp, DeeTokens.KW_CENT);
		storeKeyword(temp, DeeTokens.KW_CFLOAT);
		storeKeyword(temp, DeeTokens.KW_CHAR);
		storeKeyword(temp, DeeTokens.KW_CLASS);
		storeKeyword(temp, DeeTokens.KW_CONST);
		storeKeyword(temp, DeeTokens.KW_CONTINUE);
		storeKeyword(temp, DeeTokens.KW_CREAL);
		storeKeyword(temp, DeeTokens.KW_DCHAR);
		storeKeyword(temp, DeeTokens.KW_DEBUG);
		storeKeyword(temp, DeeTokens.KW_DEFAULT);
		storeKeyword(temp, DeeTokens.KW_DELEGATE);
		storeKeyword(temp, DeeTokens.KW_DELETE);
		storeKeyword(temp, DeeTokens.KW_DEPRECATED);
		storeKeyword(temp, DeeTokens.KW_DO);
		storeKeyword(temp, DeeTokens.KW_DOUBLE);
		storeKeyword(temp, DeeTokens.KW_ELSE);
		storeKeyword(temp, DeeTokens.KW_ENUM);
		storeKeyword(temp, DeeTokens.KW_EXPORT);
		storeKeyword(temp, DeeTokens.KW_EXTERN);
		storeKeyword(temp, DeeTokens.KW_FALSE);
		storeKeyword(temp, DeeTokens.KW_FINAL);
		storeKeyword(temp, DeeTokens.KW_FINALLY);
		storeKeyword(temp, DeeTokens.KW_FLOAT);
		storeKeyword(temp, DeeTokens.KW_FOR);
		storeKeyword(temp, DeeTokens.KW_FOREACH);
		storeKeyword(temp, DeeTokens.KW_FOREACH_REVERSE);
		storeKeyword(temp, DeeTokens.KW_FUNCTION);
		storeKeyword(temp, DeeTokens.KW_GOTO);
		storeKeyword(temp, DeeTokens.KW_IDOUBLE);
		storeKeyword(temp, DeeTokens.KW_IF);
		storeKeyword(temp, DeeTokens.KW_IFLOAT);
		storeKeyword(temp, DeeTokens.KW_IMMUTABLE);
		storeKeyword(temp, DeeTokens.KW_IMPORT);
		storeKeyword(temp, DeeTokens.KW_IN);
		storeKeyword(temp, DeeTokens.KW_INOUT);
		storeKeyword(temp, DeeTokens.KW_INT);
		storeKeyword(temp, DeeTokens.KW_INTERFACE);
		storeKeyword(temp, DeeTokens.KW_INVARIANT);
		storeKeyword(temp, DeeTokens.KW_IREAL);
		storeKeyword(temp, DeeTokens.KW_IS);
		storeKeyword(temp, DeeTokens.KW_LAZY);
		storeKeyword(temp, DeeTokens.KW_LONG);
		storeKeyword(temp, DeeTokens.KW_MACRO);
		storeKeyword(temp, DeeTokens.KW_MIXIN);
		storeKeyword(temp, DeeTokens.KW_MODULE);
		storeKeyword(temp, DeeTokens.KW_NEW);
		storeKeyword(temp, DeeTokens.KW_NOTHROW);
		storeKeyword(temp, DeeTokens.KW_NULL);
		storeKeyword(temp, DeeTokens.KW_OUT);
		storeKeyword(temp, DeeTokens.KW_OVERRIDE);
		storeKeyword(temp, DeeTokens.KW_PACKAGE);
		storeKeyword(temp, DeeTokens.KW_PRAGMA);
		storeKeyword(temp, DeeTokens.KW_PRIVATE);
		storeKeyword(temp, DeeTokens.KW_PROTECTED);
		storeKeyword(temp, DeeTokens.KW_PUBLIC);
		storeKeyword(temp, DeeTokens.KW_PURE);
		storeKeyword(temp, DeeTokens.KW_REAL);
		storeKeyword(temp, DeeTokens.KW_REF);
		storeKeyword(temp, DeeTokens.KW_RETURN);
		storeKeyword(temp, DeeTokens.KW_SCOPE);
		storeKeyword(temp, DeeTokens.KW_SHARED);
		storeKeyword(temp, DeeTokens.KW_SHORT);
		storeKeyword(temp, DeeTokens.KW_STATIC);
		storeKeyword(temp, DeeTokens.KW_STRUCT);
		storeKeyword(temp, DeeTokens.KW_SUPER);
		storeKeyword(temp, DeeTokens.KW_SWITCH);
		storeKeyword(temp, DeeTokens.KW_SYNCHRONIZED);
		storeKeyword(temp, DeeTokens.KW_TEMPLATE);
		storeKeyword(temp, DeeTokens.KW_THIS);
		storeKeyword(temp, DeeTokens.KW_THROW);
		storeKeyword(temp, DeeTokens.KW_TRUE);
		storeKeyword(temp, DeeTokens.KW_TRY);
		storeKeyword(temp, DeeTokens.KW_TYPEDEF);
		storeKeyword(temp, DeeTokens.KW_TYPEID);
		storeKeyword(temp, DeeTokens.KW_TYPEOF);
		storeKeyword(temp, DeeTokens.KW_UBYTE);
		storeKeyword(temp, DeeTokens.KW_UCENT);
		storeKeyword(temp, DeeTokens.KW_UINT);
		storeKeyword(temp, DeeTokens.KW_ULONG);
		storeKeyword(temp, DeeTokens.KW_UNION);
		storeKeyword(temp, DeeTokens.KW_UNITTEST);
		storeKeyword(temp, DeeTokens.KW_USHORT);
		storeKeyword(temp, DeeTokens.KW_VERSION);
		storeKeyword(temp, DeeTokens.KW_VOID);
		storeKeyword(temp, DeeTokens.KW_VOLATILE);
		storeKeyword(temp, DeeTokens.KW_WCHAR);
		storeKeyword(temp, DeeTokens.KW_WHILE);
		storeKeyword(temp, DeeTokens.KW_WITH);
		
		storeKeyword(temp, DeeTokens.KW___TRAITS);
		storeKeyword(temp, DeeTokens.KW___GSHARED);
		storeKeyword(temp, DeeTokens.KW___THREAD);
		storeKeyword(temp, DeeTokens.KW___VECTOR);
		
		storeKeyword(temp, DeeTokens.KW___FILE__);
		storeKeyword(temp, DeeTokens.KW___LINE__);
		storeKeyword(temp, DeeTokens.KW___MODULE__);
		storeKeyword(temp, DeeTokens.KW___FUNCTION__);
		storeKeyword(temp, DeeTokens.KW___PRETTY_FUNCTION__);
		
		
		storeKeyword(temp, DeeTokens.KW___DATE__);
		storeKeyword(temp, DeeTokens.KW___TIME__);
		storeKeyword(temp, DeeTokens.KW___TIMESTAMP__);
		storeKeyword(temp, DeeTokens.KW___VENDOR__);
		storeKeyword(temp, DeeTokens.KW___VERSION__);
		
		temp.put("__EOF__", DeeTokens.EOF); // Special keyword value that can generate an EOF
		
		keywordTable = temp;
	}
	
	/** Returns the token that matches given string, or null if none matches. */
	public static final DeeTokens getKeywordToken(String string) {
		return keywordTable.get(string);
	}
	
	private static void storeKeyword(Hashtable<String, DeeTokens> table, DeeTokens keyword) {
		String key = keyword.getSourceValue();
		assertTrue(table.get(key) == null);
		table.put(key, keyword);
	}
	
}