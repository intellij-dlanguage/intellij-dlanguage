/*******************************************************************************
 * Copyright (c) 2008, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ary Borenszweig - initial API and implementation?
 *    Bruno Medeiros - refactoring and some bugfixes
 *******************************************************************************/
package ddt.dtool.ddoc;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Utility class to do ddoc macro replacing.
 */
public class DdocMacros {
	
	private final static Map<String, String> defaultMacros;
	
	static {
		// See http://dlang.org/ddoc.html
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("B", "<b>$0</b>");
		map.put("I", "<i>$0</i>");
		map.put("U", "<u>$0</u>");
		map.put("P", "<p>$0</p>");
		map.put("DL", "<dl>$0</dl>");
		map.put("DT", "<dt>$0</dt>");
		map.put("DD", "<dd>$0</dd>");
		map.put("TABLE", "<table border=\"1\" cellpadding=\"4\">$0</table>");
		map.put("TR", "<tr>$0</tr>");
		map.put("TH", "<th>$0</th>");
		map.put("TD", "<td>$0</td>");
		map.put("OL", "<ol>$0</ol>");
		map.put("UL", "<ul>$0</ul>");
		map.put("LI", "<li>$0</li>");
		map.put("BIG", "<big>$0</big>");
		map.put("SMALL", "<small>$0</small>");
		map.put("BR", "<br>");
		map.put("LINK", "<a href=\"$0\" target=\"_blank\">$0</a>");
		map.put("LINK2", "<a href=\"$1\" target=\"_blank\">$+</a>");
		map.put("LPAREN", "(");
		map.put("RPAREN", ")");
		
		map.put("RED", "<font color=red>$0</font>");
		map.put("BLUE", "<font color=blue>$0</font>");
		map.put("GREEN", "<font color=green>$0</font>");
		map.put("YELLOW", "<font color=yellow>$0</font>");
		map.put("BLACK", "<font color=black>$0</font>");
		map.put("WHITE", "<font color=white>$0</font>");
		
		// These two macros are the same, the "D" one is a newer version that was renamed.
		map.put("D", "<span class=\"code\">$0</span>");
		map.put("D_CODE", "<span class=\"code\">$0</span>");
		
		// TODO ddoc macro provider
		map.put("D_COMMENT", "<span class=\"java_single_line_comment\">$0</span>");
		map.put("D_STRING", "<span class=\"java_string\">$0</span>");
		map.put("D_KEYWORD", "<span class=\"java_keyword\">$0</span>");
		map.put("D_PSYMBOL", "$(U $0)");
		map.put("D_PARAM", "$(I $0)");
		
		map.put("DDOC", 
		"<html><head>"+
		"<META http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">"+
		"<title>$(TITLE)</title>"+
//		"<link rel="stylesheet" type="text/css" href="style.css">"
		"</head><body>"+
		"<h1>$(TITLE)</h1>"+
		"$(BODY)"+
		"</body></html>"
		);
		
		map.put("DDOC_COMMENT", "<!-- $0 -->");
		map.put("DDOC_DECL", "$(DT $(BIG $0))");
		map.put("DDOC_DECL_DD", "$(DD $0)");
		map.put("DDOC_DITTO", "$(BR) $0");
		map.put("DDOC_SECTIONS", "$0");
		map.put("DDOC_SUMMARY", "$0$(BR)$(BR)");
		map.put("DDOC_DESCRIPTION", "$0$(BR)$(BR)");
		map.put("DDOC_AUTHORS", "$(B Authors:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_BUGS", "$(RED BUGS:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_COPYRIGHT", "$(B Copyright:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_DATE", "$(B Date:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_DEPRECATED", "$(RED Deprecated:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_EXAMPLES", "$(B Examples:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_HISTORY", "$(B History:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_LICENSE", "$(B License:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_RETURNS", "$(B Returns:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_SEE_ALSO", "$(B See Also:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_STANDARDS", "$(B Standards:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_THROWS", "$(B Throws:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_VERSION", "$(B Version:)$(BR) $0$(BR)$(BR)");
		map.put("DDOC_SECTION_H", "$(B $0)$(BR)$(BR)");
		map.put("DDOC_SECTION", "$0$(BR)$(BR)");
		map.put("DDOC_MEMBERS", "$(DL $0)");
		map.put("DDOC_MODULE_MEMBERS", "$(DDOC_MEMBERS $0)");
		map.put("DDOC_CLASS_MEMBERS", "$(DDOC_MEMBERS $0)");
		map.put("DDOC_STRUCT_MEMBERS", "$(DDOC_MEMBERS $0)");
		map.put("DDOC_ENUM_MEMBERS", "$(DDOC_MEMBERS $0)");
		map.put("DDOC_TEMPLATE_MEMBERS", "$(DDOC_MEMBERS $0)");
		map.put("DDOC_PARAMS", "$(B Params:)$(BR)\n$(TABLE $0)$(BR)");
		map.put("DDOC_PARAM_ROW", "$(TR $0)");
		map.put("DDOC_PARAM_ID", "$(TD $0)");
		map.put("DDOC_PARAM_DESC", "$(TD $0)");
		map.put("DDOC_BLANKLINE", "$(BR)$(BR)");
		
		map.put("DDOC_ANCHOR", "<a name=\"$1\"></a>");
		map.put("DDOC_PSYMBOL", "$(U $0)");
		map.put("DDOC_KEYWORD", "$(B $0)");
		map.put("DDOC_PARAM", "$(I $0)");
		
		defaultMacros = Collections.unmodifiableMap(map);
	}
	
	/**
	 * Returns a map of the default macros. The key is the macro name,
	 * the value is the replacement. This map in unmodifiable.
	 */
	public static Map<String, String> getDefaultMacros() {
		return defaultMacros;
	}
	
	/**
	 * Replaces the macros found in the given string with the given macros
	 * map.
	 * @param source the string to replace
	 * @param macros the macros map
	 * @return the replaced string
	 */
	public static String replaceMacros(String source, Map<String, String> macros) {
		TreeSet<String> expandedMacros = new TreeSet<String>();
		return replaceMacros(source, macros, expandedMacros);
	}
	
	public static String replaceMacros(String source, Map<String, String> macroDefinitions, 
		Set<String> expandedMacros) {
		DdocMacros ddocMacroProcessor = new DdocMacros(source, 0, macroDefinitions);
		return ddocMacroProcessor.replaceMacros(expandedMacros);
	}
	
	protected String source;
	protected int position;
	
	protected Map<String, String> macroDefinitions;
	
	public DdocMacros(String source, int position, Map<String, String> macroDefinitions) {
		this.source = source;
		this.position = position;
		this.macroDefinitions = macroDefinitions;
	}
	
	/** Gets the character from absolute position index, or EOF if index exceeds source.length. */
	public static int getCharacter(String source, int index) {
		if(index >= source.length()) {
			return -1;
		}
		return source.charAt(index);
	}
	
	public final int lookAhead(int offset) {
		return getCharacter(source, position + offset);
	}
	
	public final int lookAhead() {
		return getCharacter(source, position);
	}
	
	public final char lookAheadChar() {
		int character = getCharacter(source, position);
		assertTrue(character != -1);
		return (char) character;
	}
	
	/**
	 * @param expandedMacros the expanded macros so far. Used for cycle detection.
	 * @return
	 */
	private String replaceMacros(Set<String> expandedMacros) {
		// Total string
		StringBuilder sb = new StringBuilder();
		
		int length = source.length();
		
		for(; position < length; position++) {
			char ch = source.charAt(position);
			if (ch != '$') {
				sb.append(ch);
				continue;
			}
			
			if(lookAhead(1) != '(') {
				sb.append(ch);
				continue;
			}
			
			String result = assertNotNull(evaluateMacro(expandedMacros));
			sb.append(result);
		}
		
		return sb.toString();
	}
	
	private String evaluateMacro(Set<String> expandedMacros) {
		int length = source.length();
		
		// In case a macro is started but not finished
		StringBuilder rawSource = new StringBuilder();
		rawSource.append("$(");
		
		position += 2;
		if (position == length) {
			return rawSource.toString();
		}
		char ch = source.charAt(position);
		
		// The current argument in the macro
		StringBuilder currentArgument = new StringBuilder();
		// Argument $0
		StringBuilder $0 = new StringBuilder();
		// Argument $+
		StringBuilder $plus = new StringBuilder();
		List<String> arguments = new ArrayList<String>();
		
		boolean foundSpace = false;
		boolean foundComma = false;
		
		int parensCount = 0;
		for(; position < length; position++) {
			ch = source.charAt(position);
			if (ch == '$' && lookAhead(1) == '(') {
				
				String result = evaluateMacro(expandedMacros);
				assertNotNull(result);
				currentArgument.append(result);				
				rawSource.append(result);
				if (foundSpace) {
					$0.append(result);
				}
				if (foundComma) {
					$plus.append(result);
				}
				continue;
			} else if (ch == ' ' && !foundSpace) {
				foundSpace = true;
				arguments.add(currentArgument.toString());
				currentArgument.setLength(0);
				rawSource.append(ch);
				continue;
			} else if (ch == ')' && parensCount == 0) {
				arguments.add(currentArgument.toString());
				
				String macroName = arguments.get(0);
				String replacement = macroDefinitions.get(macroName);
				if (replacement == null) {
					rawSource.append(ch);
					// If macro not found, return raw source
					return rawSource.toString();
				}
				// Recursive step: replace macros in replacement
				if (expandedMacros.contains(macroName)) {
					return cycleErrorString(macroName);
				} else {
					expandedMacros.add(macroName);
					replacement = replaceMacros(replacement, macroDefinitions, expandedMacros);
					expandedMacros.remove(macroName);
					
					replacement = replaceParameters(replacement, arguments, $0.toString(), $plus.toString());
					return replacement;
				}
			} else if (ch == ',') {
				if (foundComma) {
					$plus.append(ch);
				}
				foundComma = true;
				arguments.add(currentArgument.toString());
				currentArgument.setLength(0);
				$0.append(ch);
				continue;
			} else if (ch == '(') {
				parensCount++;
			} else if (ch == ')') {
				parensCount--;
			}
			
			currentArgument.append(ch);				
			rawSource.append(ch);
			if (foundSpace) {
				$0.append(ch);
			}
			if (foundComma) {
				$plus.append(ch);
			}
		}
		
		return rawSource.toString();
	}
	
	public static String cycleErrorString(String macroName) {
		return "$DDOC ERROR - CYCLE DETECTED WITH MACRO: "+macroName+" $";
	}
	
	private static String replaceParameters(String string, List<String> arguments, String $0, String $plus) {
		StringBuilder sb = new StringBuilder();
		
		int length = string.length();
		for(int i = 0; i < length; i++) {
			char c = string.charAt(i);
			if (c == '$' && i < length - 1) {
				i++;
				c = string.charAt(i);
				if ('0' <= c && c <= '9') {
					int index = c - '0';
					if (index == 0) {
						sb.append($0);
					} else if (1 <= index && index < arguments.size()) {
						sb.append(arguments.get(index));
					} else {
						// Default behaviour of DMD
						sb.append($0);
					}
				} else if (c == '+') {
					sb.append($plus);
				} else {
					sb.append('$');
					sb.append(c);
				}
			} else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

}
