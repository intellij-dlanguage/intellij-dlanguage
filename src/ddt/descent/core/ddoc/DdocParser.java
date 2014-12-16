/*******************************************************************************
 * Copyright (c) 2008, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ary Borenszweig - initial API and implementation? (Descent project)
 *******************************************************************************/
package ddt.descent.core.ddoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import ddt.melnorme.lang.tooling.ast_actual.ElementDoc;
import ddt.descent.core.ddoc.DdocSection.Parameter;
import ddt.dtool.parser.DeeLexingUtil;

/**
 * Parser of ddoc documentation comments.
 */
public class DdocParser {
	
	private String text;
	
	private char docChar;
	private String docEnd;
	
	private String currentSectionName;
	private int currentSectionType;
	private StringBuilder currentSectionText;
	
	private String currentParameterName;
	private StringBuilder currentParameterText;
	
	private String startOfCodeLine;
	
	private ElementDoc ddoc;
	private List<Parameter> parameters;
	
	/**
	 * Creates a parser for the given text.
	 * @param text the text to parse
	 */
	public DdocParser(String text) {
		this.text = text;
	}
	
	/**
	 * Parses the text and returns the Ddoc information.
	 * @return the ddoc information
	 */
	public ElementDoc parse() {
		try {
			return internalParse();
		} catch (IOException e) {
			return new ElementDoc();
		}
	}
	
	private ElementDoc internalParse() throws IOException {
		ddoc = new ElementDoc();
		
		StringReader stringReader = new StringReader(text);
		BufferedReader reader = new BufferedReader(stringReader);
		
		String firstLine = reader.readLine();
		if (firstLine == null) {
			return ddoc;
		}
		
		firstLine = firstLine.trim();
		if (firstLine.length() < 3) {
			return ddoc;
		}
		
		docChar = firstLine.charAt(1);
		docEnd = docChar + "/"; //$NON-NLS-1$
		
		currentSectionText = new StringBuilder();
		currentSectionText.append(getContentThatMatters(firstLine.substring(3)));
		
		currentSectionType = DdocSection.NORMAL_SECTION;
		
		currentParameterName = null;
		currentParameterText = new StringBuilder();
		
		String line;
		while((line = reader.readLine()) != null) {
			line = getContentThatMatters(line);
			
			if (line.length() == 0 && 
					currentSectionName == null && 
					currentSectionType == DdocSection.NORMAL_SECTION &&
					currentSectionText.length() > 0) {
				addCurrentSection();
				continue;
			}
			
			if (isCodeStartOrEnd(line)) {
				if (currentSectionText.length() > 0 || currentSectionName != null) {
					addCurrentSection();
				}
				
				currentSectionName = null;
				if (currentSectionType == DdocSection.CODE_SECTION) {
					currentSectionType = DdocSection.NORMAL_SECTION;
					startOfCodeLine = null;
				} else  {
					currentSectionType = DdocSection.CODE_SECTION;
					startOfCodeLine = line;
				}
				continue;
			}
			
			int colonIndex = getColonOfSectionIndex(line);
			if (colonIndex != -1 && DeeLexingUtil.isValidDIdentifier(line.substring(0, colonIndex).trim())) {
				if (currentSectionType == DdocSection.CODE_SECTION) {
					currentSectionType = DdocSection.NORMAL_SECTION;
				}
				
				if (startOfCodeLine != null) {
					currentSectionText.insert(0, startOfCodeLine + " "); //$NON-NLS-1$
				}
				startOfCodeLine = null;
				
				if (currentSectionText.length() > 0 || currentSectionName != null) {
					addCurrentSection();
				}
				
				currentSectionType = DdocSection.NORMAL_SECTION;
				
				currentSectionName = line.substring(0, colonIndex);
				currentSectionText.append(line.substring(colonIndex + 1).trim());
				
				if ("Params".equals(currentSectionName) || "Macros".equals(currentSectionName)) { //$NON-NLS-1$ 
					currentSectionType = "Params".equals(currentSectionName) ? 
						DdocSection.PARAMS_SECTION : DdocSection.MACROS_SECTION; //$NON-NLS-1$
					currentParameterName = null;
					currentParameterText.setLength(0);
					parameters = new ArrayList<Parameter>();
					
					if (currentSectionText.length() == 0) {
						continue;
					}
					
					line = currentSectionText.toString();
				} else {
					continue;
				}				
			}
			
			if (currentSectionType == DdocSection.PARAMS_SECTION || currentSectionType == DdocSection.MACROS_SECTION) {
				int equalsIndex = getEqualsIndex(line);
				if (equalsIndex == -1) {
					if (currentParameterName != null) {
						if (currentParameterText.length() > 0) {
							appendSpace(currentParameterText);
						}
						currentParameterText.append(line);
					}
					continue;
				}
				
				if (currentParameterName != null) {
					parameters.add(new Parameter(currentParameterName, currentParameterText.toString()));
					currentParameterText.setLength(0);
				}
				
				currentParameterName = line.substring(0, equalsIndex).trim();
				currentParameterText.append(line.substring(equalsIndex + 1).trim());
			}
			
			if (currentSectionText.length() > 0) {
				appendSpace(currentSectionText);
			}
			currentSectionText.append(line);
		}
		
		if (currentSectionText.length() > 0 || currentSectionName != null) {
			addCurrentSection();
		}
		
		reader.close();
		stringReader.close();
		
		return ddoc;
	}

	/**
	 * Trims a line and removes the leading * or +.
	 */
	private String getContentThatMatters(String line) {
		line = line.trim();
		
		if (line.endsWith(docEnd)) {
			int i = line.length() - 2;
			while(i >= 0 && line.charAt(i) == '*') {
				i--;
			}
			line = line.substring(0, i + 1);
			if (currentSectionType != DdocSection.CODE_SECTION) {
				line = line.trim();
			}
		}
		
		int i = 0;
		while(i < line.length() && line.charAt(i) == docChar) {
			i++;
		}
		
		line = line.substring(i);
		if (currentSectionType != DdocSection.CODE_SECTION) {
			line = line.trim();
		}
		return line;
	}
	
	/**
	 * Returns the index of the ':' character, if this
	 * line is a section marker. Returns -1 if
	 * the line is not a section.
	 */
	private static int getColonOfSectionIndex(String line) {
		if (line.length() == 0 || line.charAt(0) == ':') {
			return -1;
		}
		
		int i;
		for(i = 1; i < line.length(); i++) {
			char c = line.charAt(i);
			if (Character.isWhitespace(c)) {
				return -1;
			}
			if (c == ':') {
				return i;
			}
		}
		return -1;
	}
	
	private static int getEqualsIndex(String line) {
		int indexOfEquals = line.indexOf('=');
		if (indexOfEquals == -1) return -1;
		
		return indexOfEquals;
	}
	
	private static boolean isCodeStartOrEnd(String line) {
		line = line.trim();
		if (line.length() < 3) {
			return false;
		}
		
		for(int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != '-') {
				return false;
			}
		}
		
		return true;
	}
	
	private void appendSpace(StringBuilder sb) {
		if (currentSectionType == DdocSection.CODE_SECTION) {
			sb.append("\n"); //$NON-NLS-1$
		} else {
			sb.append(" "); //$NON-NLS-1$
		}
	}
	
	private void addCurrentSection() {
		if (parameters != null) {
			if (currentParameterName != null) {
				parameters.add(new Parameter(currentParameterName, currentParameterText.toString()));
			}
			ddoc.addSection(new DdocSection(currentSectionName, currentSectionType, 
				currentSectionText.toString().trim(), parameters.toArray(new Parameter[parameters.size()])));
		} else {
			ddoc.addSection(new DdocSection(currentSectionName, currentSectionType, 
				currentSectionText.toString().trim()));
		}
		currentSectionText.setLength(0);
	}

}
