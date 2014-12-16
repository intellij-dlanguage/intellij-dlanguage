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


public class DeeLexingUtil {
	
	/* ----------------- D Identifiers: ----------------- */
	// Some stuff here breaks on UTF32 supplementary characters (we don't care much)
	
	public static boolean isValidDIdentifier(String text) {
		if(!DeeLexingUtil.isValidDAlphaNumeric(text))
			return false;
		
		// Check for keywords
		DeeTokens keywordToken = DeeLexerKeywordHelper.getKeywordToken(text);
		if(keywordToken != null) 
			return false;
		
		return true;
	}
	
	public static boolean isValidDAlphaNumeric(String text) {
		if(text.length() == 0) 
			return false;
		
		if(!(Character.isLetter(text.charAt(0)) || text.charAt(0) == '_'))
			return false;
		
		int pos = 0;
		int length = text.length();
		for(pos = 1; pos < length; ++pos){
			if(!Character.isLetterOrDigit(text.charAt(pos)) && !(text.charAt(pos) == '_'))
				return false;
		}
		
		return true;
	}
	
}