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

/**
 * Represents a single ddoc section.
 */
public class DdocSection {
	
	/**
	 * A parameter in a "Params" or "Macros" section.
	 */
	public static class Parameter {
		
		private String name;
		private String text;
		
		/**
		 * Constructs a parameter with a name and text
		 * @param name the name
		 * @param text the text
		 */
		public Parameter(String name, String text) {
			this.name = name;
			this.text = text;
		}
		
		/**
		 * Returns the name of the parameter.
		 * @return the name of the parameter
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Returns the text of the parameter.
		 * @return the text of the parameter
		 */
		public String getText() {
			return text;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name);
			sb.append(" = ");
			sb.append(text);
			return sb.toString();
		}
		
	}
	
	/**
	 * Constant for a normal ddoc section.
	 */
	public final static int NORMAL_SECTION = 0;
	
	/**
	 * Constant for the "Params" ddoc section.
	 */
	public final static int PARAMS_SECTION = 1;
	
	/**
	 * Constant for the "Macros" ddoc section.
	 */
	public final static int MACROS_SECTION = 2;
	
	/**
	 * Constant for a code ddoc section.
	 */
	public final static int CODE_SECTION = 3;
	
	private final String name;
	private final String text;
	private final int kind;
	
	private Parameter[] parameters;
	
	public DdocSection(String name, int kind, String text) {
		this.name = name;
		this.text = text;
		this.kind = kind;
	}
	
	public DdocSection(String name, int kind, String text, Parameter[] parameters) {
		this.name = name;
		this.text = text;
		this.kind = kind;
		this.parameters = parameters;
	}
	
	public int getKind() {
		return this.kind;
	}
	
	public String getName() {
		return name;
	}
	
	public String getText() {
		return text;
	}
	
	public Parameter[] getParameters() {
		if (parameters == null) {
			return new Parameter[0];
		}		
		return parameters;
	}
	
	public void addParameters(Parameter[] others) {
		if (others.length == 0) return;
		
		Parameter[] newParameters = new Parameter[parameters.length + others.length];
		System.arraycopy(parameters, 0, newParameters, 0, parameters.length);
		System.arraycopy(others, 0, newParameters, parameters.length, others.length);
		parameters = newParameters;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (name != null) {
			sb.append(name);
			sb.append(":");
			sb.append("\n");
		}
		switch(kind) {
		case NORMAL_SECTION:
			sb.append(text);
			break;
		case MACROS_SECTION:
		case PARAMS_SECTION:
			if (parameters != null) {
				for(Parameter param : parameters) {
					sb.append("  ");
					sb.append(param);
					sb.append("\n");
				}
			}
			break;
		case CODE_SECTION:
			sb.append("---\n");
			sb.append(text);
			sb.append("---\n");
			break;
		}
		return sb.toString();
	}

}
