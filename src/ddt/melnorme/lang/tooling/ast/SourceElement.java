/*******************************************************************************
 * Copyright (c) 2014, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.melnorme.lang.tooling.ast;

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertTrue;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;


public abstract class SourceElement extends CommonLanguageElement implements ISourceElement {
	
	public SourceElement() {
		super();
	}
	
	/** Source range start position */
	protected int sourceStart = -1;
	/** Source range end position */
	protected int sourceEnd = -1;
	
	
	/* ------------------------  Source range ------------------------ */
	
	
	/** Gets the source range start position. */
	@Override
	public final int getStartPos() {
		assertTrue(hasSourceRangeInfo());
		return sourceStart;
	}
	
	/** Gets the source range end position. */
	@Override
	public final int getEndPos() {
		assertTrue(hasSourceRangeInfo());
		return sourceEnd;
	}
	
	/** Gets the source range start position, aka offset. */
	@Override
	public final int getOffset() {
		assertTrue(hasSourceRangeInfo());
		return sourceStart;
	}
	
	/** Gets the source range length. */
	@Override
	public final int getLength() {
		assertTrue(hasSourceRangeInfo());
		return sourceEnd - sourceStart;
	}
	
	public final SourceRange getSourceRange() {
		assertTrue(hasSourceRangeInfo());
		return new SourceRange(getStartPos(), getLength());
	}
	
	public final SourceRange getSourceRangeOrNull() {
		if(hasSourceRangeInfo()) {
			return getSourceRange();
		}
		return null;
	}
	
	/** Checks if the node has source range info. */
	public final boolean hasSourceRangeInfo() {
		return this.sourceStart != -1;
	}
	
	/** Sets the source positions, which must be valid. */
	public final void setSourcePosition(int startPos, int endPos) {
		assertTrue(!hasSourceRangeInfo()); // Can only be set once
		assertTrue(startPos >= 0);
		assertTrue(endPos >= startPos);
		this.sourceStart = startPos;
		this.sourceEnd = endPos;
	}
	
	/** Sets the source range of the receiver to given startPositon and given length */
	public final void setSourceRange(int startPosition, int length) {
		setSourcePosition(startPosition, startPosition + length);
	}
	
	/** Sets the source range according to given sourceRange. */
	public final void setSourceRange(SourceRange sourceRange) {
		setSourcePosition(sourceRange.getOffset(), sourceRange.getOffset() + sourceRange.getLength());
	}
	
	/* -----------------  ----------------- */
	
	/** 
	 * Returns a source representation of this element
	 * 
	 * For nodes: if node parsed without errors then this representation should be equal 
	 * to the original parsed source (disregarding sub-channel tokens). Otherwise, if there were errors, 
	 * this method should still try to print something as close as possible to the original parsed source. 
	 * 
	 * All tokens that were consumed should be printed.
	 * 
	 * Expected tokens that were *not* consumed should preferably be printed as well, but it is not strictly required.
	 * 
	 * This method may be used by for UI display (or tests), but it is not precise enough to be 
	 * used for any semantic purposes.
	 * 
	 */
	public final String toStringAsCode() {
		ASTCodePrinter cp = new ASTCodePrinter();
		toStringAsCode(cp);
		return cp.toString();
	}
	
	/** @see #toStringAsCode() */
	public abstract void toStringAsCode(ASTCodePrinter cp);
	
	
	// String printer helper
	
	public final String toStringClassName() {
		return this.getClass().getSimpleName();
	}
	
}