/*******************************************************************************
 * Copyright (c) 2012, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.ast;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import static ddt.melnorme.utilbox.core.CoreUtil.areEqual;

import java.util.Comparator;

import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.ast_actual.ParserErrorTypes;

public class ParserError {
	
	public final ParserErrorTypes errorType;
	public final SourceRange sourceRange;
	public final String msgErrorSource;
	public final Object msgData;
	
	public ParserError(ParserErrorTypes errorType, SourceRange sourceRange, String msgErrorSource, Object msgData) {
		this.errorType = assertNotNull(errorType);
		this.sourceRange = assertNotNull(sourceRange);
		this.msgErrorSource = msgErrorSource;
		this.msgData = msgData;
	}
	
	public int getOffset() {
		return sourceRange.getOffset();
	}
	
	public int getLength() {
		return sourceRange.getLength();
	}
	
	public int getStartPos() {
		return getOffset();
	}
	
	public int getEndPos() {
		return getOffset() + getLength();
	}
	
	public int getLineNumber() {
		return 0; //TODO
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ParserError))
			return false;
		
		ParserError other = (ParserError) obj;
		return errorType == other.errorType && areEqual(sourceRange, other.sourceRange) 
			&& areEqual(msgErrorSource, other.msgErrorSource) && areEqual(msgData, other.msgData);
	}
	
	public static final class ErrorSourceRangeComparator implements Comparator<ParserError> {
		@Override
		public int compare(ParserError o1, ParserError o2) {
			int compareResult = o1.sourceRange.compareTo(o2.sourceRange);
			return compareResult;
		}
	}
	
	@Override
	public String toString() {
		return "ERROR:" + errorType + sourceRange.toString() +
			(msgErrorSource == null ? "" : ("【"+msgErrorSource+"】")) + "("+msgData+")";
	}
	
	public String getUserMessage() {
		return errorType.getUserMessage(this);
	}
	
}