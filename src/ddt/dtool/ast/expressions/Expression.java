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
package ddt.dtool.ast.expressions;


import java.util.Collection;
import java.util.Collections;

import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.ResolvableSemantics.ExpSemantics;
import ddt.melnorme.lang.tooling.symbols.INamedElement;
import ddt.dtool.ast.references.IQualifierNode;

public abstract class Expression extends Resolvable implements IQualifierNode, IInitializer {
	
	/* -----------------  ----------------- */
	
	@Override
	protected ResolvableSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new ExpSemantics(this, pickedElement) {
			@Override
			public Collection<INamedElement> findTargetDefElements(boolean findOneOnly) {
				return Collections.emptySet(); // TODO
			}
		};
	}
	
}