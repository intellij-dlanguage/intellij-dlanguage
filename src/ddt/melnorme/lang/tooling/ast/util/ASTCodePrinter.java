/*******************************************************************************
 * Copyright (c) 2013, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.ast.util;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.Iterator;

import ddt.melnorme.lang.tooling.ast.IASTNode;
import ddt.melnorme.lang.tooling.ast_actual.ASTNode;
import ddt.melnorme.lang.tooling.parser.ISourceRepresentation;

public class ASTCodePrinter {
	
	public final String ST_SEP = "\n";
	
	protected final StringBuilder sb = new StringBuilder();
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	public String getPrintedSource() {
		return sb.toString();
	}
	
	public void appendToken(ISourceRepresentation obj) {
		if(obj != null) {
			sb.append(obj.getSourceValue());
		}
	}
	
	public void appendToken(ISourceRepresentation obj, String sep) {
		if(obj != null) {
			sb.append(obj.getSourceValue());
			assertNotNull(sep);
			append(sep);
		}
	}
	
	public void append(String string) {
		if(string != null) {
			sb.append(string);
		}
	}
	
	public void appendStrings(String... strings) {
		for (String string : strings) {
			append(string);
		}
	}
	
	public void append(boolean condition, String string) {
		if(condition) {
			assertNotNull(string);
			sb.append(string);
		}
	}
	
	public void append(IASTNode node) {
		if(node != null) {
			node.asNode().toStringAsCode(this);
		}
	}
	
	public void append(IASTNode node, String sep) {
		if(node != null) {
			node.asNode().toStringAsCode(this);
			assertNotNull(sep);
			append(sep);
		}
	}
	
	public void append(String prefix, IASTNode node) {
		if(node != null) {
			append(prefix);
			node.asNode().toStringAsCode(this);
		}
	}
	
	public void append(String prefix, ASTNode node, String suffix) {
		if(node != null) {
			append(prefix);
			node.toStringAsCode(this);
			append(suffix);
		}
	}
	
	public void appendNodeOrNullAlt(IASTNode node, String nullAlt) {
		if(node != null) {
			node.asNode().toStringAsCode(this);
		} else {
			append(nullAlt);
		}
	}
	
	public void appendTokenList(Iterable<? extends ISourceRepresentation> list, String sep, boolean printLastSep) {
		if(list == null) {
			return;
		}
		for (Iterator<? extends ISourceRepresentation> iter = list.iterator(); iter.hasNext(); ) {
			ISourceRepresentation obj = iter.next();
			append(obj.getSourceValue());
			if(iter.hasNext() || printLastSep) {
				sb.append(sep);
			}
		}
	}
	
	public void appendList(Iterable<? extends IASTNode> list, String sep) {
		appendList(list, sep, false);
	}
	
	public void appendList(Iterable<? extends IASTNode> list, String sep, boolean printLastSep) {
		if(list == null) {
			return;
		}
		
		for (Iterator<? extends IASTNode> iter = list.iterator(); iter.hasNext(); ) {
			IASTNode node = iter.next();
			append(node.asNode());
			if(iter.hasNext() || printLastSep) {
				sb.append(sep);
			}
		}
	}
	
	public boolean appendList(String open, Iterable<? extends IASTNode> args, String sep, String close) {
		return appendList(open, args, sep, close, null);
	}
	
	public boolean appendList(String open, Iterable<? extends IASTNode> args, String sep, String close, 
		String spacingIfArgsNull) {
		return appendList(open, args, sep, false, close, spacingIfArgsNull);
	}
	
	public boolean appendList(String open, Iterable<? extends IASTNode> args, String sep, boolean hasEndingSep,
		String close, String spacingIfArgsNull) {
		if(args != null) {
			append(open);
			appendList(args, sep, hasEndingSep);
			append(close);
			return true;
		} else {
			append(spacingIfArgsNull);
			return false;
		}
	}
	
	public void appendNodeList(String open, NodeListView<? extends IASTNode> args, String sep, String close) {
		appendNodeList(open, args, sep, close, null);
	}
	
	public void appendNodeList(String open, NodeListView<? extends IASTNode> args, String sep, String close, 
		String spacingIfArgsNull) {
		boolean hasEndingSeparator = args == null ? false : args.hasEndingSeparator;
		appendList(open, args, sep, hasEndingSeparator, close, spacingIfArgsNull);
	}
	
}