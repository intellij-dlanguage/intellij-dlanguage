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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single ddoc comment.
 */
public class Ddoc {
	
	private List<DdocSection> sections;
	private DdocSection paramsSection;
	private DdocSection macrosSection;
	
	public Ddoc() {
		this.sections = new ArrayList<DdocSection>();
	}
	
	/**
	 * Adds a new section at the end of this ddoc.
	 * @param section the section to add
	 */
	public void addSection(DdocSection section) {
		this.sections.add(section);
		
		if (section.getKind() == DdocSection.PARAMS_SECTION) {
			paramsSection = section;
		} else if (section.getKind() == DdocSection.MACROS_SECTION) {
			macrosSection = section;
		}
	}
	
	/**
	 * Returns the sections of this ddoc.
	 * @return the sections
	 */
	public DdocSection[] getSections() {
		return sections.toArray(new DdocSection[sections.size()]);
	}
	
	/**
	 * Returns the "Params" section, if any
	 * @return the "Params" section, if any
	 */
	public DdocSection getParamsSection() {
		return paramsSection;
	}
	
	/**
	 * Returns the "Macros" section, if any
	 * @return the "Macros" section, if any
	 */
	public DdocSection getMacrosSection() {
		return macrosSection;
	}
	
	/**
	 * Determines if this ddoc is made of the word "ditto" only.
	 * @return <code>true</code> if this ddoc is made of the word "ditto" only,
	 * <code>false</code> otherwise
	 */
	public boolean isDitto() {
		if (sections.size() == 1) {
			DdocSection section = sections.get(0);
			return section.getKind() == DdocSection.NORMAL_SECTION
				&& section.getText().trim().equalsIgnoreCase("ditto"); //$NON-NLS-1$
		}
		return false; 
	}

	/**
	 * Merges this ddoc with another one. Normal and code sections
	 * are added at the end, while the params and macros sections
	 * are merged.
	 * @param other the other ddoc to merge
	 */
	public void merge(Ddoc other) {
		for(DdocSection otherSection : other.getSections()) {
			switch(otherSection.getKind()) {
			case DdocSection.NORMAL_SECTION:
			case DdocSection.CODE_SECTION:
				addSection(otherSection);
				break;
			case DdocSection.PARAMS_SECTION:
				if (paramsSection == null) {
					paramsSection = otherSection;
				} else {
					paramsSection.addParameters(otherSection.getParameters());
				}
				break;
			case DdocSection.MACROS_SECTION:
				if (macrosSection == null) {
					macrosSection = otherSection;
				} else {
					macrosSection.addParameters(otherSection.getParameters());
				}
				break;
			}
		}
	}

	/**
	 * Merges the other ddoc's macros with this ddoc.
	 */
	public void mergeMacros(Ddoc otherDdoc) {
		DdocSection otherMacros = otherDdoc.getMacrosSection();
		if (otherMacros == null) return;
		
		if (macrosSection == null) {
			macrosSection = otherMacros;
		} else {
			macrosSection.addParameters(otherMacros.getParameters());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(DdocSection section : sections) {
			sb.append(section.toString());
			sb.append("\n\n");
		}
		return sb.toString();
	}

}
