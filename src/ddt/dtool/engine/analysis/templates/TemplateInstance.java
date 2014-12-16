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

import static ddt.melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;
import ddt.melnorme.lang.tooling.ast.IASTVisitor;
import ddt.melnorme.lang.tooling.ast.INamedElementNode;
import ddt.melnorme.lang.tooling.ast.util.ASTCodePrinter;
import ddt.melnorme.lang.tooling.ast_actual.ASTNodeTypes;
import ddt.melnorme.lang.tooling.engine.PickedElement;
import ddt.melnorme.lang.tooling.engine.resolver.NamedElementSemantics;
import ddt.melnorme.lang.tooling.engine.resolver.TypeSemantics;
import ddt.melnorme.lang.tooling.engine.scoping.CommonScopeLookup;
import ddt.melnorme.lang.tooling.symbols.IConcreteNamedElement;
import ddt.melnorme.utilbox.collections.Indexable;
import ddt.dtool.ast.definitions.DefUnit;
import ddt.dtool.ast.definitions.DefinitionTemplate;
import ddt.dtool.ast.definitions.EArcheType;

public class TemplateInstance extends DefUnit implements IConcreteNamedElement {
	
	public final Indexable<INamedElementNode> tplArguments;
	protected final DefinitionTemplate template;
	
	public TemplateInstance(DefinitionTemplate template, Indexable<INamedElementNode> tplArguments) {
		super(assertNotNull(template).defname.createCopy());
		this.template = template;
		this.tplArguments = tplArguments;
		
		setSourceRange(template.getSourceRange());
		setParsedStatus();
	}
	
	@Override
	public void toStringAsCode(ASTCodePrinter cp) {
		cp.appendList("【", tplArguments, ",", "】 ");
		// TODO
	}
	
	@Override
	public void visitChildren(IASTVisitor visitor) {
		// TODO: need to clone template
	}
	
	@Override
	public ASTNodeTypes getNodeType() {
		return ASTNodeTypes.DEFINITION_TEMPLATE;
	}
	
	@Override
	public EArcheType getArcheType() {
		return EArcheType.Template;
	}
	
	/* -----------------  ----------------- */
	
	@Override
	public NamedElementSemantics doCreateSemantics(PickedElement<?> pickedElement) {
		return new TypeSemantics(this, pickedElement) {
			
			@Override
			public void resolveSearchInMembersScope(CommonScopeLookup search) {
				search.evaluateScopeNodeList(tplArguments);
			}
			
		};
	}
	
}