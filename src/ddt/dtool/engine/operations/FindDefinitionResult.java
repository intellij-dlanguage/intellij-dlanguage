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
package ddt.dtool.engine.operations;

import static ddt.dtool.engine.operations.FindDefinitionOperation.FIND_DEF_ReferenceResolveFailed;
import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import ddt.dtool.ast.references.Reference;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.symbols.INamedElement;

public class FindDefinitionResult {
	
	public final String errorMessage;
	public final List<FindDefinitionResultEntry> results;
	
	// Optional extra info:
	public final Collection<INamedElement> resultsRaw; // Can be null
	public final Reference pickedReference;
	
	public FindDefinitionResult(String errorMessage) {
		this(errorMessage, null);
	}
	
	public FindDefinitionResult(String errorMessage, Reference pickReference) {
		this.errorMessage = errorMessage;
		this.results = null;
		this.resultsRaw = null;
		this.pickedReference = pickReference;
	}
	
	public FindDefinitionResult(List<FindDefinitionResultEntry> results, Reference pickReference,
			Collection<INamedElement> resultsRaw) {
		this.errorMessage = null;
		this.results = results;
		this.resultsRaw = resultsRaw;
		this.pickedReference = pickReference;
	}
	
	public FindDefinitionResult createFailureResult(String errorMessage) {
		return new FindDefinitionResult(errorMessage, null);
	}
	
	public boolean isValidPickRef() {
		return (errorMessage == null || errorMessage.startsWith(FIND_DEF_ReferenceResolveFailed));
	}
	
	public static class FindDefinitionResultEntry {
		
		public final String extendedName;
		public final boolean isLanguageIntrinsic;
		public final Path modulePath;
		public final SourceRange sourceRange;
		
		public FindDefinitionResultEntry(String extendedName, boolean isLanguageIntrinsic, Path compilationUnitPath, 
				SourceRange sourceRange) {
			this.extendedName = assertNotNull(extendedName);
			this.isLanguageIntrinsic = isLanguageIntrinsic;
			this.modulePath = compilationUnitPath;
			this.sourceRange = sourceRange;
		}
		
		public boolean isLanguageIntrinsic() {
			return isLanguageIntrinsic;
		}
		
	}
	
}