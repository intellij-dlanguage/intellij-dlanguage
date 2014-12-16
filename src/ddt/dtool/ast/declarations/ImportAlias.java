/*******************************************************************************
 * Copyright (c) 2011, 2014 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package ddt.dtool.ast.declarations;

import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.AliasSemantics.TypeAliasSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.dtool.ast.declarations.DeclarationImport.IImportFragment;
import ddt.dtool.ast.definitions.DefUnit;
import ddt.dtool.ast.definitions.EArcheType;
import ddt.dtool.ast.expressions.Resolvable;
import ddt.dtool.ast.references.RefModule;

public class ImportAlias extends DefUnit implements IImportFragment {
		
	public final RefModule moduleRef;
	
	public ImportAlias(ProtoDefSymbol defId, RefModule refModule) {
		super(defId);
		this.moduleRef = parentize(refModule);
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.IMPORT_ALIAS;
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		acceptVisitor(visitor, defname);
		acceptVisitor(visitor, moduleRef);
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendStrings(getName(), " = ");
		cp.append(moduleRef);
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Alias; // Maybe should be ImportAlias
	}
	
	@Override
	public RefModule getModuleRef() {
		return moduleRef;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public void searchInSecondaryScope(CommonScopeLookup options) {
		// Do nothing. Aliasing imports do not contribute secondary-space DefUnits
		// TODO: this is a bug in D, it's not according to spec.
	}
	
	@Override
	protected NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeAliasSemantics(this, pickedElement) {
			
			@Override
			protected Resolvable getAliasTarget() {
				return moduleRef;
			}
		};
	}
	
}