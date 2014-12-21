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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ddt.melnorme.lang.tooling.ast.ASTNodeFinder;
import ddt.melnorme.lang.tooling.ast.INamedElementNode;
import ddt.melnorme.lang.tooling.ast.SourceRange;
import ddt.melnorme.lang.tooling.context.ISemanticContext;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.melnorme.utilbox.core.CommonException;
import ddt.dtool.ast.definitions.DefSymbol;
import ddt.dtool.ast.definitions.Module;
import ddt.dtool.ast.references.CommonQualifiedReference;
import ddt.dtool.ast.references.NamedReference;
import ddt.dtool.ast.references.Reference;
import ddt.dtool.engine.ResolvedModule;
import ddt.dtool.engine.SemanticManager;
import ddt.dtool.engine.operations.FindDefinitionResult.FindDefinitionResultEntry;

public class FindDefinitionOperation extends AbstractDToolOperation {
	
	public static final String FIND_DEF_PickedElementAlreadyADefinition = 
		"Element next to cursor is already a definition, not a reference.";
	public static final String FIND_DEF_NoReferenceFoundAtCursor = 
		"No reference found next to cursor.";
	public static final String FIND_DEF_MISSING_REFERENCE_AT_CURSOR = 
		FIND_DEF_NoReferenceFoundAtCursor;
	public static final String FIND_DEF_NoNameReferenceAtCursor = 
		"No name reference found next to cursor.";
	public static final String FIND_DEF_ReferenceResolveFailed = 
		"Definition not found for reference: ";
			
	public FindDefinitionOperation(SemanticManager semanticManager, Path filePath, int offset, Path compilerPath,
			String dubPath) throws CommonException {
		super(semanticManager, filePath, offset, compilerPath, dubPath);
	}
	
	public FindDefinitionResult findDefinition() throws CommonException {
		final ResolvedModule resolvedModule = getResolvedModule(fileLoc);
		final ISemanticContext mr = resolvedModule.getSemanticContext();
		Module module = resolvedModule.getModuleNode();
		
		assertEquals(module.compilationUnitPath, fileLoc.path);
		return findDefinition(module, offset, mr);
	}
	
	public static FindDefinitionResult findDefinition(Module module, final int offset, final ISemanticContext mr) {
		
		ASTNodeFinder nodeFinder = new ASTNodeFinder(module, offset, true);
		
		if(nodeFinder.matchOnLeft instanceof NamedReference) {
			NamedReference namedReference = (NamedReference) nodeFinder.matchOnLeft;
			return doFindDefinition(namedReference, mr);
		} else if(nodeFinder.match instanceof Reference) {
			Reference reference = (Reference) nodeFinder.match;
			return doFindDefinition(reference, mr);
		} else if(nodeFinder.match instanceof DefSymbol){
			return new FindDefinitionResult(FIND_DEF_PickedElementAlreadyADefinition);
		}
		
		return new FindDefinitionResult(FIND_DEF_NoReferenceFoundAtCursor);
	}
	
	public static FindDefinitionResult doFindDefinition(Reference reference, final ISemanticContext mr) {
		if(reference instanceof NamedReference) {
			NamedReference namedReference = (NamedReference) reference;
			if(namedReference.isMissingCoreReference()) {
				return new FindDefinitionResult(FIND_DEF_MISSING_REFERENCE_AT_CURSOR, namedReference);
			} if(namedReference instanceof CommonQualifiedReference) {
				// Then the cursor is not actually next to an identifier.
				return new FindDefinitionResult(FIND_DEF_NoNameReferenceAtCursor);
			} else {
				return doFindDefinitionForRef(namedReference, mr);
			}
		} else {
			return new FindDefinitionResult(FIND_DEF_NoNameReferenceAtCursor);
		}
	}
	
	public static FindDefinitionResult doFindDefinitionForRef(Reference ref, ISemanticContext moduleResolver) {
		
		Collection<INamedElement> namedElements = ref.findTargetDefElements(moduleResolver, false);
		
		if(namedElements == null || namedElements.size() == 0) {
			return new FindDefinitionResult(FIND_DEF_ReferenceResolveFailed + ref.toStringAsCode(), ref);
		}
		
		List<FindDefinitionResultEntry> results = new ArrayList<>();
		for (INamedElement namedElement : namedElements) {
			final INamedElementNode node = namedElement.resolveUnderlyingNode();
			
			Path compilationUnitPath = null;
			SourceRange sourceRange = null;
			
			if(node != null) { // This can happen with intrinsic elements 
				compilationUnitPath = node.getModuleNode().getCompilationUnitPath();
				sourceRange = node.getNameSourceRangeOrNull();
			}
			
			results.add(new FindDefinitionResultEntry(
				namedElement.getExtendedName(),
				namedElement.isLanguageIntrinsic(), 
				compilationUnitPath,
				sourceRange));
		}
		
		return new FindDefinitionResult(results, ref, namedElements);
	}

}