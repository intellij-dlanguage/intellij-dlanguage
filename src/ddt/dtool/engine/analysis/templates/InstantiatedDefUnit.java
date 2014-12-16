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
package ddt.dtool.engine.analysis.templates;


import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.dtool.ast.definitions.DefSymbol;
import ddt.dtool.ast.definitions.DefUnit;

public abstract class InstantiatedDefUnit extends DefUnit {
	
	public InstantiatedDefUnit(DefSymbol defname) {
		super(defname.createCopy());
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.append("【");
		toStringAsCode_instantiatedDefUnit(cp);
		cp.append("】");
	}
	
	public abstract void toStringAsCode_instantiatedDefUnit(ASTCodePrinter cp);
	
}